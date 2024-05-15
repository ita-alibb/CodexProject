package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The response class for method TakeCard
 */
public class TakeCardResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create a select objective request with the specified data.
     * @param selectObjective   The data to select your secret objective
     */
    public TakeCardResponse(TakeCardResponseData selectObjective) {
        super(selectObjective);
    }

    //endregion

    //region Overrides

    /**
     * @return The "selectSecretObjective" method
     */
    @Override
    public String getMethod() {
        return "takeCard";
    }

    /**
     *
     * @return The data associated to this message.
     */
    @Override
    public TakeCardResponseData getData() {
        return (TakeCardResponseData) super.data;
    }
    //endregion
}
