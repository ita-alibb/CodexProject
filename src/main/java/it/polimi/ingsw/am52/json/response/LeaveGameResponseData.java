package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The object representing the data for the leaveGame response.
 */
public class LeaveGameResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The name of the player who left.
     */
    private final String username;

    /**
     * The map with &lt;lobbyID, playersToStart&gt;
     */
    private final Map<Integer,Integer> lobbies;
    //endregion

    //region Constructor

    /**
     * The Empty constructor needed for Jackson library
     */
    public LeaveGameResponseData() {
        super();
        this.username = "";
        this.lobbies = new HashMap<>();
    }

    /**
     * Create a logout data object.
     * @param status    The status of the response
     * @param username  The nickname of the player.
     * @param lobbies The updated status of lobbies.
     */
    public LeaveGameResponseData(ResponseStatus status, String username, Map<Integer,Integer> lobbies) {
        // Assign private fields.
        super(status);
        this.username = username;
        this.lobbies = lobbies;
    }

    /**
     * Create a logout data object for Error case.
     * @param status The status of the response
     */
    public LeaveGameResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.username = "";
        this.lobbies = new HashMap<>();
    }

    //endregion

    //region Getters

    /**
     *
     * @return The username of the player who left.
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return The id of the lobby joined.
     */
    public Map<Integer,Integer> getLobbies() {
        return this.lobbies;
    }
    //endregion
}
