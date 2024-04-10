package it.polimi.ingsw.am52.json;

/**
 * The leave game request object. The method is "leaveGame" and the
 * data is a LeaveGameData object.
 */
public class LeaveGameRequest extends PlayerRequest<LeaveGameData> {

    //region Constructor

    /**
     * Creates a leave game request with the specified data.
     * @param leaveData The login data.
     */
    public LeaveGameRequest(int playerId, int lobbyId, LeaveGameData leaveData) {
        super(playerId, lobbyId, leaveData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "leaveGame" method.
     */
    @Override
    public String getMethod() {
        return "leaveGame";
    }

    //endregion
}
