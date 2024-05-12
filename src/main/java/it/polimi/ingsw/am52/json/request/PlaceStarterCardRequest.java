package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the placeStarterCard method.
 * The player shall choose which side of its starter card should be
 * visible on its playing board.
 * @author Livio B.
 */
public class PlaceStarterCardRequest extends JsonMessage<PlaceCardData> {

    //region constructor

    /**
     * Creates the request object for the placeStarterCard method, with the specified
     * data.
     * @param data The data for the placeStarterCard method.
     */
    public PlaceStarterCardRequest(PlaceCardData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "placeStarterCard" method.
     */
    @Override
    public String getMethod() {
        return "placeStarterCard";
    }

    //endregion
}
