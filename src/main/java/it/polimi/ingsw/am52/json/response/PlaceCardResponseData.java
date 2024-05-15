package it.polimi.ingsw.am52.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.dto.BoardSlotInfo;
import it.polimi.ingsw.am52.json.dto.BoardSlotSerializer;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.io.Serializable;
import java.util.List;

/**
 * The object representing the data for the placeCard response
 */
public class PlaceCardResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The id of the card
     */
    private final int cardId;

    /**
     * The visible face of the card
     */
    private final int face;

    /**
     * The position where the card has been placed
     */
    @JsonProperty("placedSlot")
    private BoardSlot placedSlot;

    /**
     * The new available positions
     */
    @JsonSerialize(using = BoardSlotSerializer.class)
    @JsonProperty("availableSlots")
    private final List<BoardSlot> availableSlots;

    /**
     * The player who place the card and the update on his score. These two elements are necessary together to map the scores on the scoreboard in the client
     */
    private final String player;
    private final int score;

    //endregion

    //region Constructors

    /**
     * The empty constructor needed for Jackson library
     */
    public PlaceCardResponseData() {
        super();
        this.cardId = -1;
        this.face = -1;
        this.placedSlot = null;
        this.availableSlots = null;
        this.player = "";
        this.score = 0;
    }

    /**
     * Create a placeCard data object
     * @param status            The status of the response
     * @param cardId            The ID of the placed card
     * @param face              The face of the placed card
     * @param slot              The position of the placed card
     * @param availableSlots    The available slots after the placement
     */
    public PlaceCardResponseData(ResponseStatus status, int cardId, int face, BoardSlot slot, List<BoardSlot> availableSlots, String player, int score) {
        super(status);
        this.cardId = cardId;
        this.face = face;
        this.placedSlot = slot;
        this.availableSlots = availableSlots;
        this.player = player;
        this.score = score;
    }

    /**
     * Create a placeCard data object
     * @param status    The status of the response
     */
    public PlaceCardResponseData(ResponseStatus status) {
        super(status);
        this.cardId = -1;
        this.face = -1;
        this.placedSlot = null;
        this.availableSlots = null;
        this.player = "";
        this.score = 0;
    }

    /**
     * Create a placeCard data object, for broadcast
     * @param status    The status of the response
     * @param cardId    The ID of the placed card
     * @param face      The face of the placed card
     * @param slot      The position of the placed card
     */
    public PlaceCardResponseData(ResponseStatus status, int cardId, int face, BoardSlot slot, String player, int score) {
        super(status);
        this.cardId = cardId;
        this.face = face;
        this.placedSlot = slot;
        this.availableSlots = null;
        this.player = player;
        this.score = score;
    }

    //endregion

    //region Getters

    /**
     * @return The ID of the card
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     * @return The face of the card
     */
    public int getFace() {
        return this.face;
    }

    /**
     * @return The position of the placed card
     */
    @JsonSerialize(as = BoardSlotInfo.class)
    public BoardSlot getPlacedSlot() {
        return this.placedSlot;
    }

    /**
     * @return The available positions
     */
    public List<BoardSlot> getAvailableSlots() {
        return this.availableSlots;
    }

    /**
     * @return The nickname of the player
     */
    public String getPlayer() {
        return this.player;
    }

    /**
     * @return The score of the player
     */
    public int getScore() {
        return this.score;
    }

    //endregion

    //region Setter

    /**
     * Setter for deserializing purpose
     * @param placedSlot    The slot where the card has been placed
     */
    @JsonSetter("placedSlot")
    public void setPlacedSlot(BoardSlot placedSlot) {
        this.placedSlot = new BoardSlot(placedSlot.getHoriz(), placedSlot.getVert());
    }


    //endregion
}
