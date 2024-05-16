package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the leaveGame method.
 * The player can send a message to the server, that message will
 * be displayed to all other players of the lobby.
 * @author Livio B.
 */
public class LeaveGameData implements Serializable {

    //region Constructor

    /**
     * Default constructor, for json deserialization purpose only.
     */
    public LeaveGameData() {
    }

    //endregion
}
