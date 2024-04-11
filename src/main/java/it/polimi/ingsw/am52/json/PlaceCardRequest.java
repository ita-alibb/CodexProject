package it.polimi.ingsw.am52.json;

/**
 * The object representing the data for the placeCard method.
 * The player shall choose which side of the card should be
 * visible on its playing board.
 * @author Livio B.
 */
public class PlaceCardRequest extends PlayerRequest<PlaceCardData> {

    //region constructor

    /**
     * Creates the request object for the placeCard method, with the specified
     * data.
     * @param playerId The player id.
     * @param lobbyId The lobby id.
     * @param data The data for the placeCard method.
     */
    public PlaceCardRequest(int playerId, int lobbyId, PlaceCardData data) {
        super(playerId, lobbyId, data);
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
