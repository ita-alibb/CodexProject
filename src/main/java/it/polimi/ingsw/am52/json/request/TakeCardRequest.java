package it.polimi.ingsw.am52.json.request;

/**
 * The object representing the data for the takeCard method.
 * The method takeCard requires the card id and the type of the card
 * (resource or gold).
 * @author Livio B.
 */
public class TakeCardRequest extends PlayerRequest<TakeCardData> {

    //region constructor

    /**
     * Creates the request object for the takeCard method, with the specified
     * data.
     * @param playerId The player id.
     * @param lobbyId The lobby id.
     * @param data The data for the takeCard method.
     */
    public TakeCardRequest(int playerId, int lobbyId, TakeCardData data) {
        super(playerId, lobbyId, data);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "takeCard" method.
     */
    @Override
    public String getMethod() {
        return "takeCard";
    }

    //endregion
}
