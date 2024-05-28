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
     * The order in the game
     */
    private int turnOrder;

    /**
     * The list of opponents
     */
    private List<OpponentModel> opponents;

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
    }

    public synchronized static ViewModelState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelState();
        }
        return INSTANCE;
    }

    public synchronized void broadcastUpdate(BaseResponseData response) {
        //TODO: needs to think a better handling
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
    }

    // region Setters used by controller to edit the Model, REMEMBER TO CALL this.notifyObservers(); at end of method
    public void updateLobbyList(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers(EventType.LIST_LOBBY);
    }

    public void updateJoinLobby(JoinLobbyResponseData joinLobby){
        this.currentLobbyId = joinLobby.getLobbyId();
        this.nicknames = joinLobby.getNicknames();
        this.phase = joinLobby.getStatus().gamePhase;

        // Change automatically the view displayed
        this.type = ViewType.LOBBY;
        this.notifyObservers(EventType.JOIN_LOBBY);

        if (this.phase == GamePhase.INIT) {
            // TODO: Handle errors
            ClientConnection.initGame();
        }
    }

    public void updateLeaveGame(LeaveGameResponseData leaveGame){
        if (!leaveGame.isBroadcast) {
            this.currentLobbyId = -1;
            this.clientNickname = "";
            this.nicknames = new ArrayList<>();
            this.phase = leaveGame.getStatus().gamePhase;

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
            } else {
                this.turnOrder = i++;
            }
        }

        this.commonObjectives = initGame.getCommonObjectiveIds();
        this.visibleResourceCards = initGame.getVisibleResourceCardIds();
        this.visibleGoldCards = initGame.getVisibleGoldCardIds();
        this.playerHand = initGame.getPlayerHandCardIds();
        this.playerObjectives = initGame.getPlayerObjectiveCardIds();
        this.starterCard = initGame.getStarterCardId();
        // TODO : Check on possible incoming errors
        this.phase = initGame.getStatus().gamePhase;
        this.scoreboard = new HashMap<>();
        for (String nick : this.nicknames) {
            this.scoreboard.put(nick,0);
        }

        //Change automatically the view displayed
        this.type = ViewType.SETUP;
        this.notifyObservers(EventType.INIT_GAME);
    }

    public void updateSelectObjective(SelectObjectiveResponseData selectObjective) {
        if (!selectObjective.isBroadcast) {
            this.secretObjective = selectObjective.getObjective();
            this.playerObjectives.remove((Integer) selectObjective.getObjective());
        }

        this.phase = selectObjective.getStatus().gamePhase;

        if (this.phase == GamePhase.PLACING) {
            this.currentPlayer = selectObjective.getStatus().currPlayer;
            this.type = ViewType.BOARD;
        }

        if (!selectObjective.isBroadcast || this.phase == GamePhase.PLACING) {
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

        this.phase = placeStarterCard.getStatus().gamePhase;

        if (this.phase == GamePhase.PLACING) {
            this.currentPlayer = placeStarterCard.getStatus().currPlayer;
            this.type = ViewType.BOARD;
        }

        if (!placeStarterCard.isBroadcast || this.phase == GamePhase.PLACING) {
            this.notifyObservers(EventType.PLACE_STARTER_CARD);
        }
    }

    public void updatePlaceCard(PlaceCardResponseData placeCard) {
        if (Objects.equals(this.currentPlayer, this.clientNickname)) {
            this.board.put(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
            this.availableSlots = placeCard.getAvailableSlots();
        } else {
            var opponent = this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), this.currentPlayer)).findFirst().get();
            opponent.addCard(placeCard.getPlacedSlot(), new CardIds(placeCard.getCardId(), placeCard.getFace()));
        }

        this.phase = placeCard.getStatus().gamePhase;

        this.notifyObservers(EventType.PLACE_CARD);
    }

    public void updateDrawCard(DrawCardResponseData drawCard) {
        if (!drawCard.isBroadcast) {
            this.playerHand.add(drawCard.getCardId());

            switch (DrawType.fromInteger(drawCard.getDeck())) {
                case DrawType.GOLD : this.goldDeck = !drawCard.isEmpty(); break;
                case DrawType.RESOURCE: this.resourceDeck = !drawCard.isEmpty(); break;
                case null : break;
            }
        }

        this.phase = drawCard.getStatus().gamePhase;
        this.currentPlayer = drawCard.getStatus().currPlayer;

        this.notifyObservers(EventType.DRAW_CARD);

        if (this.phase == GamePhase.END) {
            // TODO: Handle errors
            ClientConnection.endGame();
        }
    }

    public void updateTakeCard(TakeCardResponseData takeCard) {
        if (!takeCard.isBroadcast) {
            this.playerHand.add(takeCard.getTakenCardId());
        }

        switch (DrawType.fromInteger(takeCard.getType())) {
            case DrawType.GOLD : {
                this.goldDeck = !takeCard.isEmpty();
                this.visibleGoldCards.remove(takeCard.getTakenCardId());
                this.visibleGoldCards.add(takeCard.getShownCardId());
                break;
            }
            case DrawType.RESOURCE: {
                this.resourceDeck = !takeCard.isEmpty();
                this.visibleResourceCards.remove(takeCard.getTakenCardId());
                this.visibleResourceCards.add(takeCard.getShownCardId());
                break;
            }
            case null : break;
        }

        this.phase = takeCard.getStatus().gamePhase;
        this.currentPlayer = takeCard.getStatus().currPlayer;

        this.notifyObservers(EventType.TAKE_CARD);

        if (this.phase == GamePhase.END) {
            // TODO: Handle errors
            ClientConnection.endGame();
        }

    }

    public void updateEndGame(EndGameResponseData endGame) {
        this.winners = endGame.getWinners();
        this.phase = GamePhase.END;

        // Put the view in the common board
        this.type = ViewType.COMMON_BOARD;

        this.notifyObservers(EventType.END_GAME);
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
        return nicknames;
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

    public BoardMap<BoardSlot, CardIds> getBoard() {
        //Maybe call here to transform id into cards
        if (Objects.equals(this.clientNickname, this.viewTypeNickname)){
            return this.board;
        } else {
            return this.opponents.stream().filter(o -> Objects.equals(o.getNickname(), this.viewTypeNickname)).findFirst().get().getBoard();
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
        return winners;
    }

    //endregion
}
