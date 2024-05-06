package it.polimi.ingsw.am52.json.request;

/**
 * The object representing the data for the createLobby method.
 * The createLobby method requires the nickname of the player, the
 * name of the lobby and the number of player for the game.
 */
public class CreateLobbyData implements java.io.Serializable {

    //region Private Fields

    /**
     * The nickname of the player asking for login.
     */
    private final String nickname;

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
        this.numPlayers = -1;
    }

    /**
     * Create a data object for the createLobby method.
     * @param nickname The nickname of the player.
     * @param numberOfPlayers The number of players of the match.
     */
    public CreateLobbyData(String nickname, int numberOfPlayers) {
        // Assign private fields.
        this.nickname = nickname;
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
     * @return The number of player of the game for the lobby to create.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    //endregion
}
