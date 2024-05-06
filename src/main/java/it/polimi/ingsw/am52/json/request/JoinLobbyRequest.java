package it.polimi.ingsw.am52.json.request;

/**
 * The login request object. The method of this request is "joinLobby" and the
 * data is a LoginData object.
 */
public class JoinLobbyRequest extends ClientRequest<JoinLobbyData> {

    //region Constructors

    /**
     * Creates a login request with the specified login data.
     * @param joinLobbyData The login data.
     */
    public JoinLobbyRequest(JoinLobbyData joinLobbyData) {
        super(joinLobbyData);
    }

    /**
     * Creates a login request with the specified player's nickname
     * and lobby id.
     * @param nickname The nickname of the player.
     * @param lobbyId The id of the lobby where to login.
     */
    public JoinLobbyRequest(String nickname, int lobbyId) {
        this(new JoinLobbyData(nickname, lobbyId));
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "joinLobby" method.
     */
    @Override
    public String getMethod() {
        return "joinLobby";
    }

    //endregion
}
