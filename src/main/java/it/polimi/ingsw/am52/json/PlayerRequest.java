package it.polimi.ingsw.am52.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The base class of all client request's messages regarding a player
 * action for the game. All these requests have a "playerId" and "lobbyId"
 * properties. In this way, the server can identify the lobby and the player
 * making the request.
 * @author Livio B.
 */
public abstract class PlayerRequest<TData> extends ClientRequest<TData> {

    // region Private Fields

    /**
     * The id of the player making the request.
     */
    private final int playerId;

    /**
     * The id of the lobby where the player is playing the game.
     */
    private final int lobbyId;

    // endregion

    //region Constructor

    /**
     * Create the player request object with specified data.
     * @param playerId The id of the player who is making the request.
     * @param lobbyId The id of the lobby where the player is playing the game.
     * @param data The request data.
     * @author Livio B.
     */
    protected PlayerRequest(int playerId, int lobbyId, TData data) {
        super(data);
        // Assign the data of the request.
        this.playerId = playerId;
        this.lobbyId = lobbyId;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The player id.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     *
     * @return The lobby id.
     */
    public int getLobbyId() {
        return lobbyId;
    }


    //endregion

}
