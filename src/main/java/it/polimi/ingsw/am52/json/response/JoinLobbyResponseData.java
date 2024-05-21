package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The object representing the data for the joinLobby response.
 */
public class JoinLobbyResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The id of the lobby joined.
     */
    private final int lobbyId;

    /**
     * The list of username in the lobby.
     */
    private final List<String> nicknames;
    //endregion

    //region Constructors

    /**
     * The Empty constructor needed for Jackson library
     */
    public JoinLobbyResponseData() {
        super();
        this.lobbyId = -1;
        this.nicknames = new ArrayList<>();
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     * @param lobbyId The id of the lobby joined.
     * @param nicknames The nicknames in the lobby
     */
    public JoinLobbyResponseData(ResponseStatus status, int lobbyId, List<String> nicknames) {
        // Assign private fields.
        super(status);
        this.lobbyId = lobbyId;
        this.nicknames = nicknames;

    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     */
    public JoinLobbyResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.lobbyId = -1;
        this.nicknames = new ArrayList<>();
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

    /**
     *
     * @return The nicknames
     */
    public List<String> getNicknames() {
        return nicknames;
    }

    //endregion
}
