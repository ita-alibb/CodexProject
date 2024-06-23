package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.ListLobbyResponseData;
import it.polimi.ingsw.am52.view.tui.state.ViewType;

import java.util.*;

import static it.polimi.ingsw.am52.view.tui.InputReader.updateInputReaderOnBroadcast;

/**
 * The object ViewModelState represents an image of the current situation of the model for the client.
 * This class is a singleton, and the constructor is private because it is used only to instantiate the object one single time, and then only modify that instance.
 */
public class ViewModelState extends ModelObservable {

    //region Private Fields

    private static ViewModelState INSTANCE;

    /**
     * The type of view printed in terminal
     */
    private ViewType type;

    /**
     * The POV shown
     */
    private String viewTypeNickname;

    /**
     * The lobby list
     */
    private Map<Integer,Integer> lobbies;

    /**
     * The lobby I am currently in
     */
    private int  currentLobbyId = -1;

    /**
     * The nicknames in the lobby
     */
    private List<String> nicknames;

    /**
     * The nicknames of the winners
     */
    private List<String> winners;

    /**
     * My nickname in the lobby
     */
    private String clientNickname;

    /**
     * The current turn in the game
     */
    private int turn;

    /**
     * The list of opponents
     */
    private final List<OpponentModel> opponents;

    /**
     * The common objectives of the game
     */
    private List<Integer> commonObjectives;

    /**
     * The visible resource cards
     */
    private List<Integer> visibleResourceCards;

    /**
     * The visible gold cards
     */
    private List<Integer> visibleGoldCards;

    /**
     * This player's hand
     */
    private List<Integer> playerHand;

    /**
     * This player's objective cards to choose
     */
    private List<Integer> playerObjectives;

    /**
     * The objectiveSelected
     */
    private int secretObjective;

    /**
     * The starter card received
     */
    private int starterCard;

    /**
     * This player's board
     */
    private final BoardMap<BoardSlot,CardIds> board;

    /**
     * The available slots for placing
     */
    private List<BoardSlot> availableSlots;

    /**
     * true if the resource deck is full, otherwise false
     */
    private boolean resourceDeck;

    /**
     * true if the gold deck is full, otherwise false
     */
    private boolean goldDeck;

    /**
     * The score, associated to his player
     */
    private Map<String, Integer> scoreboard;

    /**
     * The phase of the game
     */
    private GamePhase phase;

    /**
     * The current player
     */
    private String currentPlayer;

    /**
     * The record of the previous messages of this client
     */
    private final List<String> chatRecords;

    /**
     * The nickname of the disconnected player, who caused the crash of the current game
     */
    private String disconnectedPlayer;

    //endregion

    //region Constructor

    /**
     * The constructor of the class ViewModelState.
     * The variables are instantiated but not updated, because when it is created, the client is in the menu, so this data doesn't exist.
     */
    private ViewModelState(){
        super();
        type = ViewType.MENU;
        lobbies = new HashMap<>();
        nicknames = new ArrayList<>();
        clientNickname = "";
        commonObjectives = new ArrayList<>();
        visibleResourceCards = new ArrayList<>();
        visibleGoldCards = new ArrayList<>();
        playerHand = new ArrayList<>();
        playerObjectives = new ArrayList<>();
        board = new BoardMap<>();
        resourceDeck = true;
        goldDeck = true;
        scoreboard = new HashMap<>();
        secretObjective = -1;
        phase = GamePhase.LOBBY;
        currentPlayer = "";
        opponents = new ArrayList<>();
        availableSlots = new ArrayList<>();
        turn = 1;
        chatRecords = new ArrayList<>();
        disconnectedPlayer = "";
    }

    /**
     * The method to access the singleton.
     * @return The ViewModelState which already exists; if there isn't, a new ViewModelState object
     */
    public synchronized static ViewModelState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelState();
        }
        return INSTANCE;
    }

    //endregion

    //region Update Setters

    /**
     * Method to handle each case of broadcast from the network.
     * @param response  The response from the network, which can be of different types depending on the method which it comes from.
     */
    public synchronized void broadcastUpdate(BaseResponseData response) {
        if (response instanceof JoinLobbyResponseData) {
            this.updateJoinLobby((JoinLobbyResponseData) response);
        }
        else if (response instanceof LeaveGameResponseData) {
            this.updateLeaveGame((LeaveGameResponseData) response);
        }
        else if (response instanceof SelectObjectiveResponseData) {
            this.updateSelectObjective((SelectObjectiveResponseData) response);
        }
        else if (response instanceof PlaceStarterCardResponseData) {
            this.updatePlaceStarterCard((PlaceStarterCardResponseData) response);
        }
        else if (response instanceof PlaceCardResponseData) {
            this.updatePlaceCard((PlaceCardResponseData) response);
        }
        else if (response instanceof DrawCardResponseData) {
            this.updateDrawCard((DrawCardResponseData) response);
        }
        else if (response instanceof TakeCardResponseData) {
            this.updateTakeCard((TakeCardResponseData) response);
        }
        else if (response instanceof EndGameResponseData) {
            this.updateEndGame((EndGameResponseData) response);
        }
        else if (response instanceof ChatResponseData) {
            this.updateChat((ChatResponseData) response);
        }

        updateInputReaderOnBroadcast();
    }

    /**
     * The method to show the list of the lobbies in the Server.
     * @param listLobby     The response from the Server.
     */
    public void updateLobbyList(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers(EventType.LIST_LOBBY);
    }

    /**
     * The method to join a lobby and update the necessary fields.
     * @param joinLobby     The response from the Server.
     */
    public void updateJoinLobby(JoinLobbyResponseData joinLobby){
        this.currentLobbyId = joinLobby.getLobbyId();
        this.nicknames = new ArrayList<>(joinLobby.getNicknames());
        this.phase = joinLobby.getStatus().getGamePhase();

        // Change automatically the view displayed
        this.type = ViewType.LOBBY;
        this.notifyObservers(EventType.JOIN_LOBBY);

        if (this.phase == GamePhase.INIT) {
            // TODO: Handle errors
            ClientConnection.initGame();
        }
    }

    /**
     * The method to leave a lobby and overwrite the fields previously updated.
     * @param leaveGame     The response from the Server
     */
    public void updateLeaveGame(LeaveGameResponseData leaveGame){
        if (!leaveGame.getIsBroadcast()) {
            this.currentLobbyId = -1;
            this.clientNickname = "";
            this.nicknames = new ArrayList<>();
            this.phase = leaveGame.getStatus().getGamePhase();
            this.chatRecords.clear();

            // Change automatically the view displayed
            this.type = ViewType.MENU;
        }
        else {
            this.nicknames.remove(leaveGame.getUsername());
        }
        this.lobbies = leaveGame.getLobbies();

        this.notifyObservers(EventType.LEAVE_GAME);
    }

    /**
     * The method to initialize a game, when all the players are connected to a lobby.
     * @param initGame      The response from the Server.
     */
    public void updateInitGame(InitGameResponseData initGame) {
        this.nicknames = initGame.getPlayersNickname();
        //init opponent representation
        int i = 0;
        for (String nickname : this.nicknames){
            if (!Objects.equals(nickname, this.clientNickname)) {
                this.opponents.add(new OpponentModel(nickname, i++));
            }
        }

        this.commonObjectives = initGame.getCommonObjectiveIds();
        this.visibleResourceCards = initGame.getVisibleResourceCardIds();
        this.visibleGoldCards = initGame.getVisibleGoldCardIds();
        this.playerHand = initGame.getPlayerHandCardIds();
        this.playerObjectives = initGame.getPlayerObjectiveCardIds();
        this.starterCard = initGame.getStarterCardId();
        this.phase = initGame.getStatus().getGamePhase();
        this.scoreboard = new HashMap<>();
        for (String nick : this.nicknames) {
            this.scoreboard.put(nick,0);
        }

        this.disconnectedPlayer = "";

        //Change automatically the view displayed
        this.type = ViewType.SETUP;
        this.notifyObservers(EventType.INIT_GAME);
    }

    /**
     * The method to update the secret objective, only if the response is not a broadcast.
     * @param selectObjective       The response from the Server
     */
    public void updateSelectObjective(SelectObjectiveResponseData selectObjective) {
        if (!selectObjective.getIsBroadcast()) {
            this.secretObjective = selectObjective.getObjective();
            this.playerObjectives.remove((Integer) selectObjective.getObjective());
        }

        this.phase = selectObjective.getStatus().getGamePhase();

        if (this.phase == GamePhase.PLACING) {
            this.currentPlayer = selectObjective.getStatus().getCurrPlayer();
            this.type = ViewType.BOARD;
        }

        if (!selectObjective.getIsBroadcast() || this.phase == GamePhase.PLACING) {
            this.notifyObservers(EventType.SELECT_OBJECTIVE);
        }
    }

    /**
     * The method to place a starter card and initialize the board field, only if the response is not broadcast.
     * If it is, it is added to the opponent information.
     * @param placeStarterCard      The response from the Server
     */
    public void updatePlaceStarterCard(PlaceStarterCardResponseData placeStarterCard) {
        if (Objects.equals(placeStarterCard.getNickname(), this.clientNickname)) {
            this.board.put(new BoardSlot(0,0), new CardIds(placeStarterCard.getCardId(), placeStarterCard.getFace()));
            this.availableSlots = placeStarterCard.getBoardSlots();
        } else {
            this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), placeStarterCard.getNickname())).findFirst().get().addCard(new BoardSlot(0,0), new CardIds(placeStarterCard.getCardId(), placeStarterCard.getFace()));
        }

        this.phase = placeStarterCard.getStatus().getGamePhase();

        if (this.phase == GamePhase.PLACING) {
            this.currentPlayer = placeStarterCard.getStatus().getCurrPlayer();
            this.type = ViewType.BOARD;
        }

        if (!placeStarterCard.getIsBroadcast() || this.phase == GamePhase.PLACING) {
            this.notifyObservers(EventType.PLACE_STARTER_CARD);
        }
    }

    /**
     * The method to place a card, on your board if the response is not broadcast, if it is on the opponent board.
     * @param placeCard     The response from the Server.
     */
    public void updatePlaceCard(PlaceCardResponseData placeCard) {
        if (Objects.equals(this.currentPlayer, this.clientNickname)) {
            this.board.put(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
            this.playerHand.remove((Integer) placeCard.getCardId());
            this.availableSlots = placeCard.getAvailableSlots();
        } else {
            var opponent = this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), this.currentPlayer)).findFirst().get();
            opponent.addCard(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
        }

        this.scoreboard.put(placeCard.getPlayer(), this.scoreboard.get(placeCard.getPlayer()) + placeCard.getScore());

        this.phase = placeCard.getStatus().getGamePhase();

        this.notifyObservers(EventType.PLACE_CARD);
    }

    /**
     * The method to update the hand of the player, only if the response is not broadcast.
     * @param drawCard      The response from the Server.
     */
    public void updateDrawCard(DrawCardResponseData drawCard) {
        if (!drawCard.getIsBroadcast()) {
            this.playerHand.add(drawCard.getCardId());

            switch (DrawType.fromInteger(drawCard.getDeck())) {
                case DrawType.GOLD : this.goldDeck = !drawCard.isEmpty(); break;
                case DrawType.RESOURCE: this.resourceDeck = !drawCard.isEmpty(); break;
                case null : break;
            }
        }

        this.phase = drawCard.getStatus().getGamePhase();
        this.currentPlayer = drawCard.getStatus().getCurrPlayer();
        if (Objects.equals(this.currentPlayer, this.nicknames.getFirst())) {
            this.turn++;
        }

        this.notifyObservers(EventType.DRAW_CARD);

        if (this.phase == GamePhase.END) {
            ClientConnection.endGame();
        }
    }

    /**
     * The method to take one of the visible cards and update them, only if the response is not broadcast.
     * @param takeCard      The response from the Server.
     */
    public void updateTakeCard(TakeCardResponseData takeCard) {
        if (!takeCard.getIsBroadcast()) {
            this.playerHand.add(takeCard.getTakenCardId());
        }

        switch (DrawType.fromInteger(takeCard.getType())) {
            case DrawType.GOLD : {
                this.goldDeck = !takeCard.isEmpty();
                this.visibleGoldCards.remove((Integer) takeCard.getTakenCardId());
                this.visibleGoldCards.add(takeCard.getShownCardId());
                break;
            }
            case DrawType.RESOURCE: {
                this.resourceDeck = !takeCard.isEmpty();
                this.visibleResourceCards.remove((Integer) takeCard.getTakenCardId());
                this.visibleResourceCards.add(takeCard.getShownCardId());
                break;
            }
            case null : break;
        }

        this.phase = takeCard.getStatus().getGamePhase();
        this.currentPlayer = takeCard.getStatus().getCurrPlayer();
        if (Objects.equals(this.currentPlayer, this.nicknames.getFirst())) {
            this.turn++;
        }

        this.notifyObservers(EventType.TAKE_CARD);

        if (this.phase == GamePhase.END) {
            ClientConnection.endGame();
        }

    }

    /**
     * The method to declare the end of the game and the winner.
     * @param endGame       The response from the Server.
     */
    public void updateEndGame(EndGameResponseData endGame) {
        this.winners = endGame.getWinners();
        this.disconnectedPlayer = endGame.getDisconnectedPlayerNickname();

        this.phase = GamePhase.END;
        // Put the view in the common board
        this.type = ViewType.END;

        this.notifyObservers(EventType.END_GAME);
    }

    /**
     * The method to update the registered chat
     * @param chat the ChatResponseData from the network
     */
    public void updateChat(ChatResponseData chat) {
        if (chat.getMessage() != null && !chat.getMessage().trim().isEmpty()) {
            this.chatRecords.add(chat.getMessage());

            this.notifyObservers(EventType.CHAT);
        }
    }

    // endregion

    //region Getters

    /**
     * @return  The lobbies contained in the Server
     */
    public Map<Integer, Integer> getLobbies() {
        return lobbies;
    }

    /**
     * @return  The ID of the lobby the player entered
     */
    public int getCurrentLobbyId() {
        return currentLobbyId;
    }

    /**
     * @return  The players' nickname
     */
    public List<String> getNicknames() {
        return Collections.unmodifiableList(this.nicknames);
    }

    /**
     * @return  The common objectives of the game
     */
    public List<Integer> getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * @return  The visible resource cards
     */
    public List<Integer> getVisibleResourceCards() {
        return visibleResourceCards;
    }

    /**
     * @return  The visible gold cards
     */
    public List<Integer> getVisibleGoldCards() {
        return visibleGoldCards;
    }

    /**
     * @return  This player's hand
     */
    public List<Integer> getPlayerHand() {
        return playerHand;
    }

    /**
     * @return  The player's secret objective
     */
    public List<Integer> getPlayerObjectives() {
        return playerObjectives;
    }

    /**
     * @return  The player's starter card
     */
    public int getStarterCard() {
        return starterCard;
    }

    /**
     * @return  True if the resource deck has available cards, otherwise False
     */
    public boolean getResourceDeck() {
        return resourceDeck;
    }

    /**
     * @return  True if the gold deck has available cards, otherwise False
     */
    public boolean getGoldDeck() {
        return goldDeck;
    }

    /**
     * @return  The current phase
     */
    public GamePhase getPhase() {
        return phase;
    }

    /**
     * @return  The scores of all the players
     */
    public Map<String, Integer> getScoreboard() {
        return scoreboard;
    }

    /**
     * @return  The player's secret objective
     */
    public int getSecretObjective() {
        return secretObjective;
    }

    /**
     * @return  The current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return  The nickname of the disconnected player
     */
    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }

    /**
     * @return  The board of the player which is visualized
     */
    public BoardMap<BoardSlot, CardIds> getBoard() {
        //Maybe call here to transform id into cards
        if (Objects.equals(this.clientNickname, this.viewTypeNickname)){
            return this.board;
        } else {
            return this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), this.viewTypeNickname)).findFirst().get().getBoard();
        }
    }

    /**
     * Method to get the chat records
     * @return the chat records as immutable list
     */
    public List<String> getChatRecords() {
        return Collections.unmodifiableList(this.chatRecords);
    }

    //endregion
    public BoardMap<BoardSlot, CardIds> getBoard(String nickname) {
        //Maybe call here to transform id into cards
        if (Objects.equals(this.clientNickname,nickname)){
            return this.board;
        } else {
            return this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), nickname)).findFirst().get().getBoard();
        }
    }
    // endregion

    //region Utils

    /**
     * Method to get the view that has to be shown
     * @return the view Type
     */
    public ViewType getViewTypeShown() {
        return type;
    }

    /**
     * @return  The player we are watching the setup
     */
    public String getViewTypeNickname() {
        return viewTypeNickname;
    }

    /**
     * Method to set the view that has to be shown
     * @param type  The view to be set
     */
    public void setViewTypeShown(ViewType type) {
        this.type = type;
    }

    /**
     * Set the player we want to watch
     * @param viewTypeNickname  The nickname of the player
     * @return  True if the view has been update, otherwise False
     */
    public boolean setViewTypeNickname(String viewTypeNickname) {
        if (this.nicknames.contains(viewTypeNickname)) {
            this.viewTypeNickname = viewTypeNickname;
            return true;
        }
        return false;
    }

    /**
     * @return  True if the current view is the one of this client, otherwise False
     */
    public boolean isClientView() {
        return Objects.equals(this.clientNickname, this.viewTypeNickname);
    }

    /**
     * @return  The nickname of this client
     */
    public String getClientNickname() {
        return clientNickname;
    }

    /**
     * Sets the nickname of this client
     * @param clientNickname    The new nickname of the client
     */
    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
        this.viewTypeNickname = clientNickname;
    }

    /**
     * @return  True if it's the turn of this client, otherwise False
     */
    public boolean isClientTurn() {
        return Objects.equals(this.clientNickname, this.currentPlayer);
    }

    /**
     * @return  The list of available slots on the board
     */
    public List<BoardSlot> getAvailableSlots() {
        return availableSlots;
    }

    /**
     * @return  The winner/s of this game
     */
    public List<String> getWinners() {
        return Collections.unmodifiableList(this.winners);
    }

    /**
     * @return  The current turn
     */
    public int getTurn() {
        return turn;
    }

    //endregion
}
