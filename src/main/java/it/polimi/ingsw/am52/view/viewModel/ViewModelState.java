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

public class ViewModelState extends ModelObservable {

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
    private int currentLobbyId = -1;

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

    private List<String> chatRecords;

    private String disconnectedPlayer;

    //Singleton, every calls edit this class here. Then every View displays what they need starting from here Ex: Menu
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

    public synchronized static ViewModelState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelState();
        }
        return INSTANCE;
    }

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

        //TODO: At the moment, it doesn't work
        updateInputReaderOnBroadcast();
    }

    // region Setters used by controller to edit the Model, REMEMBER TO CALL this.notifyObservers(); at end of method
    public void updateLobbyList(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers(EventType.LIST_LOBBY);
    }

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

    public void updatePlaceCard(PlaceCardResponseData placeCard) {
        if (Objects.equals(this.currentPlayer, this.clientNickname)) {
            this.board.put(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
            this.availableSlots = placeCard.getAvailableSlots();
            this.playerHand.remove((Integer) placeCard.getCardId());
        } else {
            var opponent = this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), this.currentPlayer)).findFirst().get();
            opponent.addCard(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
        }

        this.scoreboard.put(placeCard.getPlayer(), this.scoreboard.get(placeCard.getPlayer()) + placeCard.getScore());

        this.phase = placeCard.getStatus().getGamePhase();

        this.notifyObservers(EventType.PLACE_CARD);
    }

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

    // region Getters used in views
    public Map<Integer, Integer> getLobbies() {
        return lobbies;
    }

    public int getCurrentLobbyId() {
        return currentLobbyId;
    }

    public List<String> getNicknames() {
        return Collections.unmodifiableList(this.nicknames);
    }

    public List<Integer> getCommonObjectives() {
        return commonObjectives;
    }

    public List<Integer> getVisibleResourceCards() {
        return visibleResourceCards;
    }

    public List<Integer> getVisibleGoldCards() {
        return visibleGoldCards;
    }

    public List<Integer> getPlayerHand() {
        return playerHand;
    }

    public List<Integer> getPlayerObjectives() {
        return playerObjectives;
    }

    public int getStarterCard() {
        return starterCard;
    }

    public boolean getResourceDeck() {
        return resourceDeck;
    }

    public boolean getGoldDeck() {
        return goldDeck;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public ViewType getType() {
        return type;
    }

    public Map<String, Integer> getScoreboard() {
        return scoreboard;
    }

    public int getSecretObjective() {
        return secretObjective;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }

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
    // endregion

    //region Utils

    /**
     * Method to get the view that has to be shown
     * @return the view Type
     */
    public ViewType getViewTypeShown() {
        return type;
    }

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

    public boolean setViewTypeNickname(String viewTypeNickname) {
        if (this.nicknames.contains(viewTypeNickname)) {
            this.viewTypeNickname = viewTypeNickname;
            return true;
        }
        return false;
    }

    public boolean isClientView() {
        return Objects.equals(this.clientNickname, this.viewTypeNickname);
    }

    public String getClientNickname() {
        return clientNickname;
    }

    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
        this.viewTypeNickname = clientNickname;
    }

    public boolean isClientTurn() {
        return Objects.equals(this.clientNickname, this.currentPlayer);
    }

    public List<BoardSlot> getAvailableSlots() {
        return availableSlots;
    }

    public List<String> getWinners() {
        return Collections.unmodifiableList(this.winners);
    }

    public int getTurn() {
        return turn;
    }

    //endregion
}
