package it.polimi.ingsw.am52.json;

/**
 * The object representing the data for the drawCard method.
 * The method requires the deck from which the card has been drawn.
 * @author Livio B.
 */
public class DrawCardRequest extends PlayerRequest<DrawCardData> {

    //region constructor

    /**
     * Creates the request object for the drawCard method, with the specified
     * data.
     * @param playerId The player id.
     * @param lobbyId The lobby id.
     * @param data The data for the drawCard method.
     */
    public DrawCardRequest(int playerId, int lobbyId, DrawCardData data) {
        super(playerId, lobbyId, data);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "drawCard" method.
     */
    @Override
    public String getMethod() {
        return "drawCard";
    }

    //endregion
}
