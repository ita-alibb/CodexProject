package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the createLobby response.
 */
public class CreateLobbyResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The id of the lobby created.
     */
    private final int lobbyId;

    //endregion

    //region Constructor

    /**
     * Constructor for Success case
     * @param status the status of the response
     * @param lobbyId the lobby id
     */
    private CreateLobbyResponseData(ResponseStatus status, int lobbyId) {
        super(status);
        this.lobbyId = lobbyId;
    }

    /**
     * Constructor for Error case
     * @param status the status of the response
     */
    private CreateLobbyResponseData(ResponseStatus status) {
        super(status);
        this.lobbyId = -1;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The nickname of the player.
     */
    public int getLobbyId() {
        return this.lobbyId ;
    }

    //endregion
}
