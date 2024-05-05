package it.polimi.ingsw.am52.json;

/**
 * The select objective request object. The method is "selectObjective" and the
 * data is a SelectObjectiveData object.
 */
public class SelectObjectiveRequest extends PlayerRequest<SelectObjectiveData> {

    //region Constructor

    /**
     * Creates a select objective request with the specified data.
     * @param leaveData The login data.
     */
    public SelectObjectiveRequest(int playerId, int lobbyId, SelectObjectiveData leaveData) {
        super(playerId, lobbyId, leaveData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "selectObjective" method.
     */
    @Override
    public String getMethod() {
        return "selectObjective";
    }

    //endregion
}
