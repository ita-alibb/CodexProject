package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.exceptions.*;
import it.polimi.ingsw.am52.json.dto.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.cards.Card;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.server.ClientHandler;
import it.polimi.ingsw.am52.network.server.Sender;
import it.polimi.ingsw.am52.json.response.ResponseStatus;

import java.util.*;

/**
 * Class to control the Game
 */
public class GameController {
    /**
     * The game Lobby, it is used to handle the current logged user
     */
    private final GameLobby lobby;

    /**
     * The GameManager, it controls all the Model
     */
    private GameManager game;

    /**
     * Constructor of the GameController
     * @param lobby The Lobby linked to this Game
     */
    public GameController(GameLobby lobby) {
        this.lobby = lobby;
    }

    // region Endpoints

    /**
     * The method to join the lobby
     * @param user the user that joined
     */
    public synchronized JoinLobbyResponseData joinLobby(User user) {
        try {
            if (this.game != null) {
                return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 403, "Game already started"));
            }

            if (!this.lobby.addPlayer(user)) {
                return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 403, "Nickname not available"));
            }

            JoinLobbyResponseData res;

            if (this.lobby.isFull()) {
                if(this.startGame()){
                    res = new JoinLobbyResponseData(new ResponseStatus(this.game.getStatusResponse()), this.getId(), this.lobby.getPlayersNickname());
                } else {
                    res = new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 503, "Cannot start Game"), this.getId(), this.lobby.getPlayersNickname());
                }
            } else {
                res = new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY,0,""), this.getId(), this.lobby.getPlayersNickname());
            }

            // Notify the clients and Response
            return res;
        } catch (Exception ex) {
            return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 503, "Cannot add player"));
        }
    }

    /**
     * Method to leave a lobby.
     *
     */
    public LeaveGameResponseData leaveLobby(int clientId) {
        var user = this.lobby.getPlayer(clientId);

        if (user.isEmpty()) {
            return new LeaveGameResponseData(new ResponseStatus(GamePhase.LOBBY, 404, "User not found"));
        }

        var nick = user.get().getUsername();

        try {
            this.lobby.removePlayer(nick);
        } catch (Exception ex) {
            return new LeaveGameResponseData(new ResponseStatus(GamePhase.LOBBY, 405, "Player cannot be removed"));
        }

        if (this.lobby.isEmpty()) {
            ServerController.getInstance().deleteGame(this);
        }

        // Notify the clients and Response
        return new LeaveGameResponseData(new ResponseStatus(GamePhase.LOBBY,0,""), nick, ServerController.getInstance().getLobbies());
    }

    /**
     * Method to select the secret objective
     * @param clientId  The ID of the client
     * @param objective The ID of the chosen objective
     */
    public SelectObjectiveResponseData selectObjective(int clientId, int objective) {
        try {
            this.game.setPlayerChosenObject(this.getNickname(clientId), objective);
        } catch (NoSuchElementException e) {
            return new SelectObjectiveResponseData(new ResponseStatus(this.game.getStatusResponse(), 404, "Player not found"));
        } catch (PlayerException e) {
            return new SelectObjectiveResponseData(new ResponseStatus(this.game.getStatusResponse(), 60, e.getMessage()));
        } catch (GameException e) {
            return new SelectObjectiveResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }

        //Notify the success of the action
        return new SelectObjectiveResponseData(new ResponseStatus(this.game.getStatusResponse()), objective);
    }

    /**
     * Method to place the starter card
     * @param clientId  The ID of the client
     * @param cardId    The ID of the starter card
     * @param face      The face of the starter card
     */
    public PlaceStarterCardResponseData placeStarterCard(int clientId, int cardId, int face) {
        try {
            this.game.placeStarterCard(this.getNickname(clientId), cardId, face);
        } catch (NoSuchElementException e) {
            return new PlaceStarterCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 404, "Player not found"));
        } catch (PlayingBoardException e) {
            return new PlaceStarterCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 50, e.getMessage()));
        } catch (GameException e) {
            return new PlaceStarterCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }

        //Notify the success of the action
        return new PlaceStarterCardResponseData(
                new ResponseStatus(this.game.getStatusResponse()),
                cardId,
                face,
                this.getNickname(clientId),
                this.game.getPlayer(this.getNickname(clientId)).getPlayingBoard().getAvailableSlots().toList()
        );
    }

    /**
     * Method to place a card
     * @param clientId  The ID of the client
     * @param cardId    The ID of the card
     * @param face      The face of the card
     * @param slot      The position of the card
     */
    public PlaceCardResponseData placeCard(int clientId, int cardId, int face, BoardSlot slot) {
        if (!Objects.equals(this.getNickname(clientId), this.game.getCurrentPlayer().getNickname())) {
            return new PlaceCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, "Not your turn!"));
        }
        try {
            this.game.placeCard(cardId, face, slot);
        } catch (PlayerException e) {
            return new PlaceCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 32, e.getMessage()));
        } catch (CardException e) {
            return new PlaceCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 30, e.getMessage()));
        } catch (PlayingBoardException e) {
            return new PlaceCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 31, e.getMessage()));
        } catch (GameException e) {
            return new PlaceCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }

        //Notify the success of the action
        return new PlaceCardResponseData(
                new ResponseStatus(this.game.getStatusResponse()),
                cardId,
                face,
                slot,
                this.game.getPlayer(this.getNickname(clientId)).getPlayingBoard().getAvailableSlots().toList(),
                this.getNickname(clientId),
                this.game.getScoreBoard().get(this.getNickname(clientId))
        );
    }

    /**
     * Method to get the init game data
     *
     */
    public InitGameResponseData initGame(int clientId) {
        InitGameResponseData response;
        try {
            var nickname = this.getNickname(clientId);

            response = new InitGameResponseData(
                    new ResponseStatus(this.game.getStatusResponse()),
                    this.lobby.getPlayersNickname(),
                    this.game.getCommonObjectives(),
                    this.game.getVisibleResourceCards(),
                    this.game.getVisibleGoldCards(),
                    this.game.getPlayer(nickname).getHand().stream().map(Card::getCardId).toList(),
                    this.game.getPlayerObjectiveOptions(nickname).stream().map(Objective::getObjectiveId).toList(),
                    this.game.getPlayer(nickname).getStarterCard().getCardId(),
                    this.game.peekNextCard(DrawType.RESOURCE),
                    this.game.peekNextCard(DrawType.GOLD)
            );
        } catch (Exception e) {
            System.out.println("Exception thrown on GameController.initGame: " + e.getMessage());
            response = new InitGameResponseData(new ResponseStatus(this.game.getStatusResponse(), 503, "Method not working"));
        }

        return response;
    }

    /**
     * Method to handle the ending phase of a game
     */
    public EndGameResponseData endGame() {
        try {
            return new EndGameResponseData(new ResponseStatus(this.game.getStatusResponse()), this.game.getWinners());
        } catch (GameException e) {
            return new EndGameResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }
    }

    /**
     * Method to draw a card
     * @param clientId  The ID of the client
     * @param deck      The deck to draw from
     */
    public DrawCardResponseData drawCard(int clientId, int deck) {
        if (!Objects.equals(this.getNickname(clientId), this.game.getCurrentPlayer().getNickname())) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, "Not your turn!"));
        }
        try {
            var nextCard = this.game.peekNextCard(DrawType.fromInteger(deck));
            switch (DrawType.fromInteger(deck)) {
                case DrawType.RESOURCE -> this.game.drawResourceCard();
                case DrawType.GOLD -> this.game.drawGoldCard();
                case null, default -> {
                    return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 1, "Invalid deck"));
                }
            }

            if (!this.game.getPlayer(this.getNickname(clientId)).getHand().stream().map(Card::getCardId).toList().contains(nextCard)) {
                throw new DeckException("Deck failed to draw");
            }

            //Notify the success of the action
            return new DrawCardResponseData(
                    new ResponseStatus(this.game.getStatusResponse()),
                    nextCard,
                    deck,
                    this.game.peekNextCard(DrawType.fromInteger(deck))
            );
        } catch (PlayerException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 32, e.getMessage()));
        } catch (DeckException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 70, e.getMessage()));
        } catch (IllegalStateException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 99, e.getMessage()));
        } catch (GameException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }
    }

    /**
     * Method to get the init game data
     *
     */
    public TakeCardResponseData takeCard(int clientId, int cardId, int type) {
        try {
            var drawType = DrawType.fromInteger(type);
            if (!Objects.equals(this.getNickname(clientId), this.game.getCurrentPlayer().getNickname())) {
                return new TakeCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, "Not your turn!"));
            }

            int shownCard;

            if (drawType == DrawType.RESOURCE){
                shownCard = this.game.takeResourceCard(cardId);
            } else if (drawType == DrawType.GOLD) {
                shownCard = this.game.takeGoldCard(cardId);
            } else {
                return new TakeCardResponseData(new ResponseStatus(this.game.getStatusResponse(),400, "Bad request type"));
            }

            return new TakeCardResponseData(
                    new ResponseStatus(this.game.getStatusResponse()),
                    cardId,
                    shownCard,
                    type,
                    this.game.peekNextCard(drawType)
            );
        } catch (Exception ex) {
            return new TakeCardResponseData(new ResponseStatus(this.game.getStatusResponse(),503, "Exception on take card"));
        }
    }
    //endregion

    // region Utilities

    /**
     * This method starts a game:
     * Delete the GameLobby from the available list
     * Init the GameController
     * Notify all user by in the lobby
     */
    private boolean startGame(){
        if (this.game != null){
            return false;
        }

        try {
            this.game = new GameManager(this.lobby.getPlayersNickname());
            return true;
        } catch (Exception e) {
            System.out.println("Exception on startGame: " + e.getMessage());
            return false;
        }
    }

    /**
     * @return The username of the player who made an action
     */
    private String getNickname(int clientId) {
        return this.lobby.getPlayer(clientId).get().getUsername();
    }

    /**
     * @return The id of the player
     */
    protected int getClientId(String nickname) {
        return this.lobby.getPlayer(nickname).map(User::getClientId).orElse(-1);
    }

    /**
     * Get the id of the Game which is the same as the Lobby
     * @return the id
     */
    public int getId(){
        return this.lobby.getId();
    }

    /**
     * Method called to disconnect a player in a game
     * @param handler the clientHandler that disconnects
     *
     * @return the EndGameResponseData due to disconnection
     */
    public synchronized EndGameResponseData disconnect(ClientHandler handler, int disconnectedClientId) {
        var disconnectedNickname = "";
        try {
            disconnectedNickname = this.getNickname(disconnectedClientId);
            this.lobby.removePlayer(handler.getClientId());

            ServerController.getInstance().disconnect(handler);

            if (this.lobby.isEmpty()) {
                ServerController.getInstance().deleteGame(this);
            }
        } catch (Exception e) {
            System.out.println("Exception on disconnect: " + e.getMessage());
        }
        List<String> winners = new ArrayList<>();

        if (this.game != null) {
            winners = this.game.getWinners();
        }

        return new EndGameResponseData(new ResponseStatus(GamePhase.END, 0, ""), winners, disconnectedNickname);
    }

    /**
     * Private method to broadcast the responses
     * @param clientToExclude the client id who did the request, if you broadcast to him, he will receive double message
     */
    public List<Sender> handlerToBroadcast(int clientToExclude){
        return this.lobby.handlerToBroadcast(clientToExclude);
    }

    /**
     * Private method to broadcast the responses
     * @param client the client to which forward the response
     */
    public Sender specificHandlerToBroadcast(int client){
        return this.lobby.getSpecificHandlerToBroadcast(client);
    }

    /**
     * Method used to return he number of player that can enter the lobby
     * @return the number of free spaces
     */
    public int getFreeSpace() {
        return this.lobby.getFreeSpace();
    }

    // endregion
}
