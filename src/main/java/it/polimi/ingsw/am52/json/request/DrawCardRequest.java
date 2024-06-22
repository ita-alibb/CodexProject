package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the drawCard method.
 * The method requires the deck from which the card has been drawn.
 */
public class DrawCardRequest extends JsonMessage<DrawCardData> {

    //region constructor

    /**
     * Creates the request object for the drawCard method, with the specified
     * data.
     * @param data The data for the drawCard method.
     */
    public DrawCardRequest(DrawCardData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public DrawCardData getData() {
        return super.data;
    }

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
