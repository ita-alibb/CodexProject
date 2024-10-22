package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the takeCard method.
 * The takeCard method requires the id of the card taken and
 * the type of card (resource or gold).
 */
public class TakeCardData implements Serializable {

    //region Private Fields

    /**
     * The id of the card taken by the player.
     */
    private final int cardId;

    /**
     * The type of card taken by the player, int representing enum {@link it.polimi.ingsw.am52.json.dto.DrawType}.
     */
    private final int type;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected TakeCardData() {
        this(-1, -1);
    }

    /**
     * Create the object of the TakeCard data.
     * @param cardId The id of the card taken by the player.
     * @param type The type of the card, resource or gold.
     */
    public TakeCardData(int cardId, int type) {
        // Assign private fields.
        this.cardId = cardId;
        this.type = type;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The id of the card taken by the player.
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     *
     * @return The type of the card, resource or gold.
     */
    public int getType() {
        return this.type;
    }

    //endregion
}
