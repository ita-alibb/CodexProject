package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.exceptions.*;
import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.cards.Card;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.network.Sender;
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
     * @param clientId the client who requested
     * @param user the user that joined
     */
    public JoinLobbyResponseData joinLobby(int clientId, User user) {
        if (!this.lobby.addPlayer(user)) {
            return new JoinLobbyResponseData(new ResponseStatus(503, "Cannot add Player"));
        }

        JoinLobbyResponseData res;

        if (this.lobby.isFull()) {
            if(this.startGame()){
                res = new JoinLobbyResponseData(new ResponseStatus(this.game.getStatusResponse()), this.getId());
            } else {
                res = new JoinLobbyResponseData(new ResponseStatus(503, "Cannot start Game"), this.getId());
            }
        } else {
            res = new JoinLobbyResponseData(new ResponseStatus(), this.getId());
        }

        // Notify the clients and Response
        return res;
    }

    /**
     * Method to join a lobby.
     *
     */
    public LeaveGameResponseData leaveLobby(int clientId) {
        var user = this.lobby.getPlayer(clientId);

        if (user.isEmpty()) {
            //TODO: ERROR
            return new LeaveGameResponseData(new ResponseStatus(404, "User not found"));
        }

        try {
            this.lobby.removePlayer(user.get().getUsername());
        } catch (Exception ex) {
            // TODO: ERROR
            return new LeaveGameResponseData(new ResponseStatus(405, "Player cannot be removed"));
        }

        if (this.lobby.isEmpty()) {
            ServerController.getInstance().deleteGame(this);
        }

        // Notify the clients and Response
        return new LeaveGameResponseData(new ResponseStatus(), "Lobby leaved!");
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
     * Method to draw a card
     * @param clientId  The ID of the client
     * @param deck      The deck to draw from
     */
    public DrawCardResponseData drawCard(int clientId, int deck) {
        if (!Objects.equals(this.getNickname(clientId), this.game.getCurrentPlayer().getNickname())) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, "Not your turn!"));
        }
        try {
            switch (DrawType.fromInteger(deck)) {
                case DrawType.RESOURCE -> this.game.drawResourceCard();
                case DrawType.GOLD -> this.game.drawGoldCard();
                case null, default -> {
                    return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 1, "Invalid deck"));
                }
            }
        } catch (PlayerException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 32, e.getMessage()));
        } catch (DeckException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 70, e.getMessage()));
        } catch (IllegalStateException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 99, e.getMessage()));
        } catch (GameException e) {
            return new DrawCardResponseData(new ResponseStatus(this.game.getStatusResponse(), 3, e.getMessage()));
        }

        //Notify the success of the action
        boolean isEmpty = false;
        switch (DrawType.fromInteger(deck)) {
            case DrawType.RESOURCE -> {
                if (this.game.getResourceDeckCount() == 0) {
                    isEmpty = true;
                }
            }
            case DrawType.GOLD -> {
                if (this.game.getGoldDeckCount() == 0) {
                    isEmpty = true;
                }
            }
        }

        return new DrawCardResponseData(
                new ResponseStatus(this.game.getStatusResponse()),
                this.game.getPlayer(this.getNickname(clientId)).getHand().toList().getLast().getCardId(),
                deck,
                isEmpty
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
                    this.game.getPlayer(nickname).getStarterCard().getCardId()
            );
        } catch (Exception e) {
            // TODO: better logging
            System.out.println("Exception thrown on GameController.initGame: " + e.getMessage());
            response = new InitGameResponseData(new ResponseStatus(this.game.getStatusResponse(), 503, "Method not working"));
        }

        return response;
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
            //TODO: better logging
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
     * Get the id of the Game which is the same as the Lobby
     * @return the id
     */
    public int getId(){
        return this.lobby.getId();
    }

    /**
     * Method called to disconnect a player in a game
     * @param handler the clientHandler that disconnects
     */
    public void disconnect(ClientHandler handler) {
        try {
            // TODO: How to handle disconnection? Set on User isConnected to false and enable reconnection or trigger the end phase of the game and shut down the game?
            this.lobby.removePlayer(handler.getClientId());
            ServerController.getInstance().disconnect(handler);
        } catch (Exception e) {
            // TODO: Improve logging
            System.out.println("Exception on disconnect: " + e.getMessage());
        }
    }

    /**
     * Private method to broadcast the responses
     * @param clientToExclude the client id who did the request, if you broadcast to him, he will receive double message
     */
    public List<Sender> handlerToBroadcast(int clientToExclude){
        return this.lobby.handlerToBroadcast(clientToExclude);
    }

    // endregion
}
