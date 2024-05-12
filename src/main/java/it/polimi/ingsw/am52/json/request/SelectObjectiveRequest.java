package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The select objective request object. The method is "selectObjective" and the
 * data is a SelectObjectiveData object.
 */
public class SelectObjectiveRequest extends JsonMessage<SelectObjectiveData> {

    //region Constructor

    /**
     * Creates a select objective request with the specified data.
     * @param leaveData The login data.
     */
    public SelectObjectiveRequest(SelectObjectiveData leaveData) {
        super(leaveData);
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
