package it.polimi.ingsw.am52.json;

/**
 * The object representing the data for the login method.
 * The login method needs the nickname of the player and
 * the id of the lobby where o login.
 */
public class LoginData {

    //region Private Fields

    /**
     * The nickname of the player asking for login.
     */
    private final String nickname;

    /**
     * The id of the lobby to login into.
     */
    private final int lobbyId;

    //endregion

    //region Constructors

    /**
     * Default constructor, for json deserialization purpose only.
     */
    protected LoginData() {
        this("",-1);
    }

    /**
     * Create a login data object.
     * @param nickname The nickname of the player.
     * @param lobbyId The id of the lobby where to login.
     */
    public LoginData(String nickname, int lobbyId) {
        // Assign private fields.
        this.nickname = nickname;
        this.lobbyId = lobbyId;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return The id of the where to login.
     */
    public int getLobbyId() {
        return lobbyId;
    }

    //endregion
}
