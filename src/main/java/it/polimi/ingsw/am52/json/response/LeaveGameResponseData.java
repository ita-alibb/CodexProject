package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The object representing the data for the leaveGame response.
 */
public class LeaveGameResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The goodbye message.
     */
    private final String message;

    //endregion

    //region Constructor

    /**
     * The Empty constructor needed for Jackson library
     */
    public LeaveGameResponseData() {
        super();
        this.message = "";
    }

    /**
     * Create a login data object.
     * @param status The status of the response
     * @param message The nickname of the player.
     */
    public LeaveGameResponseData(ResponseStatus status, String message) {
        // Assign private fields.
        super(status);
        this.message = message;
    }

    /**
     * Create a login data object for Error case.
     * @param status The status of the response
     */
    public LeaveGameResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.message = "";
    }

    //endregion

    //region Getters

    /**
     *
     * @return The goodbye message.
     */
    public String getMessage() {
        return message;
    }

    //endregion
}
