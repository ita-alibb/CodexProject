package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

public class DrawCardResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create a drawCard response with the specified data
     * @param drawCard  The data to draw your card
     */
    public DrawCardResponse(DrawCardResponseData drawCard) {
        super(drawCard);
    }

    //endregion

    //region Overrides

    /**
     * @return The "drawCard" method
     */
    @Override
    public String getMethod() {
        return "drawCard";
    }

    /**
     * @return The data associated to this message
     */
    @Override
    public DrawCardResponseData getData() {
        return (DrawCardResponseData) super.data;
    }

    //endregion
}
