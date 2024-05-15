package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The placeStarterCard response object. The data associated to this object is a placeStarterCardResponseData
 */
public class PlaceStarterCardResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Create a placeStarterCard response with the specified data.
     * @param placeStarterCard  The data to select your starter card
     */
    public PlaceStarterCardResponse(PlaceStarterCardResponseData placeStarterCard) {
        super(placeStarterCard);
    }

    //endregion

    //region Overrides

    /**
     * @return The "placeStarterCard" method
     */
    @Override
    public String getMethod() {
        return "placeStarterCard";
    }

    /**
     *
     * @return The data associated to this message.
     */
    @Override
    public PlaceStarterCardResponseData getData() {
        return (PlaceStarterCardResponseData) super.data;
    }
    //endregion
}
