package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the leaveGame method.
 * The player can send a message to the server, that message will
 * be displayed to all other players of the lobby.
 * @author Livio B.
 */
public class LeaveGameData implements Serializable {

    //region Private Fields

    /**
     * The goodbye message.
     */
    private final String message;

    //endregion

    //region Constructor

    /**
     * Default constructor, for json deserialization purpose only.
     */
    protected LeaveGameData() {
        this.message = "";
    }

    /**
     * Create a login data object.
     * @param message The nickname of the player.
     */
    public LeaveGameData(String message) {
        // Assign private fields.
        this.message = message;
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
