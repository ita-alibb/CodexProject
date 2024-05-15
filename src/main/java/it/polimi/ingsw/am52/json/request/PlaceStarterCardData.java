package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the placeStarterCard and
 * placeCard methods.
 * The placeStarterCard and placeCard methods require the id of the
 * card and the visible face.
 */
public class PlaceStarterCardData implements Serializable {

    //region Private Fields

    /**
     * The id of the card.
     */
    private final int cardId;

    /**
     * The visible face of the card.
     */
    private final int face;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected PlaceStarterCardData() {
        this(-1, -1);
    }

    /**
     * Create the object of the PlaceStarterCard data.
     * @param cardId The card id.
     * @param visibleFace The visible face (front or back).
     */
    public PlaceStarterCardData(int cardId, int visibleFace) {
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
    public int getFace() {
        return this.face;
    }

    //endregion
}
