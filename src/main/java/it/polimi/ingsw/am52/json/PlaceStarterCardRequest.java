package it.polimi.ingsw.am52.json;

/**
 * The object representing the data for the placeStarterCard method.
 * The player shall choose which side of its starter card should be
 * visible on its playing board.
 * @author Livio B.
 */
public class PlaceStarterCardRequest extends PlayerRequest<PlaceCardData> {

    //region constructor

    /**
     * Creates the request object for the placeStarterCard method, with the specified
     * data.
     * @param playerId The player id.
     * @param lobbyId The lobby id.
     * @param data The data for the placeStarterCard method.
     */
    public PlaceStarterCardRequest(int playerId, int lobbyId, PlaceCardData data) {
        super(playerId, lobbyId, data);
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
