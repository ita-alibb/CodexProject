package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The placeCard response object. The data associated to this object is a placeCardResponseData
 */
public class PlaceCardResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create a placeCard response with the specified data.
     * @param placeCard     The data to place your card
     */
    public PlaceCardResponse(PlaceCardResponseData placeCard) {
        super(placeCard);
    }

    //endregion

    //region Overrides

    /**
     * @return The "placeCard" method
     */
    @Override
    public String getMethod() {
        return "placeCard";
    }

    //endregion
}
