package it.polimi.ingsw.am52.json.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.json.dto.*;

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
    private final int face;

    /**
     * The slot where the card is supposed to be placed
     */
    private BoardSlot placedSlot;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected PlaceCardData() {
        this(-1, -1, null);
    }

    /**
     * Create the object of the PlaceCard data.
     * @param cardId        The card id
     * @param visibleFace   The visible face (front or back)
     * @param placedSlot    The slot where the card is supposed to be placed
     */
    public PlaceCardData(int cardId, int visibleFace, BoardSlot placedSlot) {
        // Assign private fields.
        this.cardId = cardId;
        this.face = visibleFace;
        this.placedSlot = placedSlot;
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

    /**
     * @return The slot where the card has been placed
     */
    @JsonSerialize(as = BoardSlotInfo.class)
    public BoardSlot getPlacedSlot() {
        return this.placedSlot;
    }

    /**
     * Setter for deserializing purpose
     */
    @JsonSetter("placedSlot")
    public void setPlacedSlot(BoardSlot placedSlot) {
        this.placedSlot = new BoardSlot(placedSlot.getHoriz(), placedSlot.getVert());
    }

    //endregion
}
