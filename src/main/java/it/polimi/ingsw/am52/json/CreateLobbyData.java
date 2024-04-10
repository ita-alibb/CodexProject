package it.polimi.ingsw.am52.json;

/**
 * The object representing the data for the createLobby method.
 */
public class CreateLobbyData {

    //region Private Fields

    /**
     * The nickname of the player asking for login.
     */
    private final String nickname;

    /**
     * The name of the lobby to create.
     */
    private final String lobbyName;

    /**
     * The number of players for the match.
     */
    private final int numPlayers;

    //endregion

    //region Constructor

    /**
     * Default constructor, for json deserialization purpose only.
     */
    protected CreateLobbyData() {
        this.nickname = "";
        this.lobbyName = "";
        this.numPlayers = -1;
    }

    /**
     * Create a data object for the createLobby method.
     * @param nickname The nickname of the player.
     * @param lobbyName The id of the lobby where to login.
     * @param numberOfPlayers The number of players of the match.
     */
    public CreateLobbyData(String nickname, String lobbyName, int numberOfPlayers) {
        // Assign private fields.
        this.nickname = nickname;
        this.lobbyName = lobbyName;
        this.numPlayers = numberOfPlayers;
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
     * @return The name of the new lobby to create.
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     *
     * @return The number of player of the game for the lobby to create.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    //endregion
}
