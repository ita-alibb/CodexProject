package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.ListLobbyResponseData;
import it.polimi.ingsw.am52.view.tui.TuiPrinter;
import it.polimi.ingsw.am52.view.tui.state.ViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelState extends ModelObservable {

    private static ViewModelState INSTANCE;

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

    //Singleton, every calls edit this class here. Then every View displays what they need starting from here Ex: Menu
    private ViewModelState(){
        super();
        lobbies = new HashMap<Integer,Integer>();
        nicknames = new ArrayList<>();
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
    }

    // region Setters used by controller to edit the Model, REMEMBER TO CALL this.notifyObservers(); at end of method
    public void updateLobbyList(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers();
    }

    public void updateJoinLobby(JoinLobbyResponseData joinLobby){
        this.currentLobbyId = joinLobby.getLobbyId();
        this.nicknames = joinLobby.getNicknames();

        // Change automatically the view displayed
        TuiPrinter.getInstance().setType(ViewType.LOBBY);
        this.notifyObservers();
    }

    public void updateLeaveGame(LeaveGameResponseData leaveGame){
        this.currentLobbyId = -1;
        this.nicknames = new ArrayList<>();

        // Change automatically the view displayed
        TuiPrinter.getInstance().setType(ViewType.MENU);
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

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }
    // endregion
}
