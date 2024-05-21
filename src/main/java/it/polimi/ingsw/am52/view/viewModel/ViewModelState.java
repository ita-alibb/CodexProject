package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.ListLobbyResponseData;

import java.util.ArrayList;
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
    }

    public synchronized static ViewModelState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelState();
        }
        return INSTANCE;
    }

    // region Setters used by controller to edit the Model, REMEMBER TO CALL this.notifyObservers(); at end of method
    public void update(ListLobbyResponseData listLobby){
        this.lobbies = listLobby.getLobbies();
        this.notifyObservers();
    }

    public void update(JoinLobbyResponseData joinLobby){
        this.currentLobbyId = joinLobby.getLobbyId();
        this.nicknames = joinLobby.getNicknames();
        this.notifyObservers();
    }

    public void update(LeaveGameResponseData leaveGame){
        this.currentLobbyId = -1;
        this.nicknames = new ArrayList<>();
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
