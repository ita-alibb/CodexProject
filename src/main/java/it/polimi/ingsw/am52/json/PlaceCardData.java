package it.polimi.ingsw.am52.json;

import java.io.Serializable;

/**
 * The object representing the data for the placeStarterCard and
 * placeCard methods.
 * The placeStarterCard and placeCard methods require the id of the
 * card and the visible face.
 */
public class PlaceCardData implements Serializable {

    //region Private Fields

    /**
     * The id of the card.
     */
    private final int cardId;

    /**
     * The visible face of the card.
     */
    private final String face;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected PlaceCardData() {
        this(-1, "");
    }

    /**
     * Create the object of the PlaceCard data.
     * @param cardId The card id.
     * @param visibleFace The visible face (front or back).
     */
    public PlaceCardData(int cardId, String visibleFace) {
        // Assign private fields.
        this.cardId = cardId;
        this.face = visibleFace;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The id of the card.
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     *
     * @return The face of the card.
     */
    public String getFace() {
        return this.face;
    }

    //endregion
}
