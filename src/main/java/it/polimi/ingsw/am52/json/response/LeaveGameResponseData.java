package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the leaveGame response.
 */
public class LeaveGameResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The name of the player who left.
     */
    private final String username;

    //endregion

    //region Constructor

    /**
     * The Empty constructor needed for Jackson library
     */
    public LeaveGameResponseData() {
        super();
        this.username = "";
    }

    /**
     * Create a login data object.
     * @param status    The status of the response
     * @param username  The nickname of the player.
     */
    public LeaveGameResponseData(ResponseStatus status, String username) {
        // Assign private fields.
        super(status);
        this.username = username;
    }

    /**
     * Create a login data object for Error case.
     * @param status The status of the response
     */
    public LeaveGameResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.username = "";
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

    //endregion
}
