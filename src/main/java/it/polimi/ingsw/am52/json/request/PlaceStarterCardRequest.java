package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the placeStarterCard method.
 * The player shall choose which side of its starter card should be
 * visible on its playing board.
 */
public class PlaceStarterCardRequest extends JsonMessage<PlaceStarterCardData> {

    //region constructor

    /**
     * Creates the request object for the placeStarterCard method, with the specified
     * data.
     * @param data The data for the placeStarterCard method.
     */
    public PlaceStarterCardRequest(PlaceStarterCardData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public PlaceStarterCardData getData() {
        return super.data;
    }

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
