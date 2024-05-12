package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the takeCard method.
 * The method takeCard requires the card id and the type of the card
 * (resource or gold).
 * @author Livio B.
 */
public class TakeCardRequest extends JsonMessage<TakeCardData> {

    //region constructor

    /**
     * Creates the request object for the takeCard method, with the specified
     * data.
     * @param data The data for the takeCard method.
     */
    public TakeCardRequest(TakeCardData data) {
        super(data);
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
