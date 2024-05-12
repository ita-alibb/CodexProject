package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the placeCard method.
 * The player shall choose which side of the card should be
 * visible on its playing board.
 * @author Livio B.
 */
public class PlaceCardRequest extends JsonMessage<PlaceCardData> {

    //region constructor

    /**
     * Creates the request object for the placeCard method, with the specified
     * data.
     * @param data The data for the placeCard method.
     */
    public PlaceCardRequest(PlaceCardData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "placeCard" method.
     */
    @Override
    public String getMethod() {
        return "placeCard";
    }

    //endregion
}
