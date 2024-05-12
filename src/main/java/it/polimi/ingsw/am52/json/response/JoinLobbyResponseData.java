package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the joinLobby response.
 */
public class JoinLobbyResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The id of the lobby joined.
     */
    private final int lobbyId;

    //endregion

    //region Constructors

    /**
     * The Empty constructor needed for Jackson library
     */
    public JoinLobbyResponseData() {
        super();
        this.lobbyId = -1;
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     * @param lobbyId The id of the lobby joined.
     */
    public JoinLobbyResponseData(ResponseStatus status, int lobbyId) {
        // Assign private fields.
        super(status);
        this.lobbyId = lobbyId;
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     */
    public JoinLobbyResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.lobbyId = -1;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The id of the lobby joined.
     */
    public int getLobbyId() {
        return lobbyId;
    }

    //endregion
}
