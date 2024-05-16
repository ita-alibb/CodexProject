package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

public class EndGameResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create an EndGame response
     * @param data  The endGame data
     */
    public EndGameResponse(EndGameResponseData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The "endGame" method
     */
    @Override
    public String getMethod() {
        return "endGame";
    }

    /**
     * @return The data associated to this message
     */
    public EndGameResponseData getData() {
        return (EndGameResponseData) super.data;
    }

    //endregion
}
