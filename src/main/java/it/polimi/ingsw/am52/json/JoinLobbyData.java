package it.polimi.ingsw.am52.json;

import java.io.Serializable;

/**
 * The object representing the data for the joinLobby method.
 * The joinLobby method needs the nickname of the player and
 * the id of the lobby where o joinLobby.
 */
public class JoinLobbyData implements Serializable {

    //region Private Fields

    /**
     * The nickname of the player asking for joinLobby.
     */
    private final String nickname;

    /**
     * The id of the lobby to joinLobby into.
     */
    private final int lobbyId;

    //endregion

    //region Constructors

    /**
     * Default constructor, for json deserialization purpose only.
     */
    protected JoinLobbyData() {
        this("",-1);
    }

    /**
     * Create a joinLobby data object.
     * @param nickname The nickname of the player.
     * @param lobbyId The id of the lobby where to joinLobby.
     */
    public JoinLobbyData(String nickname, int lobbyId) {
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
