package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The object representing the data for the listLobby response.
 */
public class ListLobbyResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The map with < lobbyID, playersToStart >
     */
    private final Map<Integer,Integer> lobbies;

    //endregion

    //region Constructors

    /**
     * The Empty constructor needed for Jackson library
     */
    public ListLobbyResponseData() {
        super();
        this.lobbies = new HashMap<>();
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     * @param lobbies The lobbies.
     */
    public ListLobbyResponseData(ResponseStatus status, Map<Integer,Integer> lobbies) {
        // Assign private fields.
        super(status);
        this.lobbies = lobbies;
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     */
    public ListLobbyResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.lobbies = new HashMap<>();
    }

    //endregion

    //region Getters

    /**
     *
     * @return The id of the lobby joined.
     */
    public Map<Integer,Integer> getLobbies() {
        return this.lobbies;
    }

    //endregion
}
