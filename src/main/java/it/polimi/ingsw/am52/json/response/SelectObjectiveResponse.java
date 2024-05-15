package it.polimi.ingsw.am52.json.response;


import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The selectSecretObjective response object. The data associated to this object is a selectObjectiveResponseData
 */
public class SelectObjectiveResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create a select objective request with the specified data.
     * @param selectObjective   The data to select your secret objective
     */
    public SelectObjectiveResponse(SelectObjectiveResponseData selectObjective) {
        super(selectObjective);
    }

    //endregion

    //region Overrides

    /**
     * @return The "selectSecretObjective" method
     */
    @Override
    public String getMethod() {
        return "selectObjective";
    }

    /**
     *
     * @return The data associated to this message.
     */
    @Override
    public SelectObjectiveResponseData getData() {
        return (SelectObjectiveResponseData) super.data;
    }
    //endregion
}
