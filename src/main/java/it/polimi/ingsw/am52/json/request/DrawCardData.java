package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the drawCard method.
 * The drawCard method requires the deck from which the card
 * has been drawn.
 */
public class DrawCardData implements Serializable {

    //region Private Fields

    /**
     * The deck from which the card is has been drawn.
     */
    private final int deck;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected DrawCardData() {
        this(-1);
    }

    /**
     * Create the object of the PlaceCard data.
     * @param deck The deck from which the card has been drawn.
     */
    public DrawCardData(int deck) {
        // Assign private fields.
        this.deck = deck;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The deck from which the card has been drawn.
     */
    public int getDeck() {
        return this.deck;
    }

    //endregion
}
