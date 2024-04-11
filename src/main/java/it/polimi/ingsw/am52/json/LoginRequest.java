package it.polimi.ingsw.am52.json;

/**
 * The login request object. The method of this request is "login" and the
 * data is a LoginData object.
 */
public class LoginRequest extends ClientRequest<LoginData> {

    //region Constructors

    /**
     * Creates a login request with the specified login data.
     * @param loginData The login data.
     */
    public LoginRequest(LoginData loginData) {
        super(loginData);
    }

    /**
     * Creates a login request with the specified player's nickname
     * and lobby id.
     * @param nickname The nickname of the player.
     * @param lobbyId The id of the lobby where to login.
     */
    public LoginRequest(String nickname, int lobbyId) {
        this(new LoginData(nickname, lobbyId));
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "login" method.
     */
    @Override
    public String getMethod() {
        return "login";
    }

    //endregion
}
