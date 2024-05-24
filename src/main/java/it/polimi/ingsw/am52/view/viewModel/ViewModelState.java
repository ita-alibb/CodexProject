package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.tui.TuiController;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.ListLobbyResponseData;
import it.polimi.ingsw.am52.view.tui.state.ViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelState extends ModelObservable {

    private static ViewModelState INSTANCE;

    /**
     * The type of view printed in terminal
     */
    private ViewType type;

    /**
     * The lobby list
     */
    private Map<Integer,Integer> lobbies;

    /**
     * The lobby I am currently in
     */
    private int currentLobbyId = -1;

    /**
     * The players in lobby
     */
    private List<String> nicknames;

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
     * This player's starter card
     */
    private int starterCard;

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

    //Singleton, every calls edit this class here. Then every View displays what they need starting from here Ex: Menu
    private ViewModelState(){
        super();
        type = ViewType.MENU;
        lobbies = new HashMap<Integer,Integer>();
        nicknames = new ArrayList<>();
        commonObjectives = new ArrayList<>();
        visibleResourceCards = new ArrayList<>();
        visibleGoldCards = new ArrayList<>();
        playerHand = new ArrayList<>();
        playerObjectives = new ArrayList<>();
        starterCard = -1;
        resourceDeck = true;
        goldDeck = true;
        scoreboard = new HashMap<>();
        phase = GamePhase.LOBBY;
    }

    public synchronized static ViewModelState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelState();
        }
        return INSTANCE;
    }

    public void broadcastUpdate(BaseResponseData response) {
        //TODO: needs to think a better handling
        if (response instanceof JoinLobbyResponseData) {
            this.updateJoinLobby((JoinLobbyResponseData) response);
        }
        else if (response instanceof LeaveGameResponseData) {
            this.updateLeaveGame((LeaveGameResponseData) response);
        }
    }

    // region Setters used by controller to edit the Model, REMEMBER TO CALL this.notifyObservers(); at end of method
    public void updateLobbyList(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers();
    }

    public void updateJoinLobby(JoinLobbyResponseData joinLobby){
        this.currentLobbyId = joinLobby.getLobbyId();
        this.nicknames = joinLobby.getNicknames();
        this.phase = joinLobby.getStatus().gamePhase;

        // Change automatically the view displayed
        this.type = ViewType.LOBBY;
        this.notifyObservers();

        if (this.phase == GamePhase.INIT) {
            TuiController.initGame();
        }
    }

    public void updateLeaveGame(LeaveGameResponseData leaveGame){
        if (!leaveGame.isBroadcast) {
            this.currentLobbyId = -1;
            this.nicknames = new ArrayList<>();
            this.phase = leaveGame.getStatus().gamePhase;

            // Change automatically the view displayed
            this.type = ViewType.MENU;
        }
        else {
            this.nicknames = this.removeNickname(leaveGame.getUsername());
        }

        this.notifyObservers();
    }

    public void updateInitGame(InitGameResponseData initGame) {
        this.nicknames = initGame.playersNickname;
        this.commonObjectives = initGame.commonObjectiveIds;
        this.visibleResourceCards = initGame.visibleResourceCardIds;
        this.visibleGoldCards = initGame.visibleGoldCardIds;
        this.playerHand = initGame.playerHandCardIds;
        this.playerObjectives = initGame.playerObjectiveCardIds;
        this.starterCard = initGame.starterCardId;
        // TODO : Check on possible incoming errors
        this.phase = initGame.getStatus().gamePhase;
        this.scoreboard = new HashMap<>();
        for (String nick : this.nicknames) {
            this.scoreboard.put(nick,0);
        }

        //Change automatically the view displayed
        this.type = ViewType.COMMON_BOARD;
        this.notifyObservers();
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
    // endregion

    //region Utils

    private List<String> removeNickname(String nickname) {
        List<String> newNicknames = new ArrayList<>();
        for (String nick : nicknames) {
            if (!nick.equals(nickname)) {
                newNicknames.add(nick);
            }
        }
        return newNicknames;
    }

    /**
     * Method to get the view that has to be shown
     * @return the view Type
     */
    public ViewType getViewTypeShown() {
        return type;
    }

    /**
     * Method to set the view that has to be shown
     * @param type  The view to be set
     */
    public void setViewTypeShown(ViewType type) {
        this.type = type;
    }

    //endregion
}
