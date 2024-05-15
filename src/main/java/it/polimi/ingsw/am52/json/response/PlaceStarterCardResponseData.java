package it.polimi.ingsw.am52.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.dto.BoardSlotSerializer;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.io.Serializable;
import java.util.List;

/**
 * The object representing the data for the placeStarterCard response
 */
public class PlaceStarterCardResponseData extends BaseResponseData implements Serializable {

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
     * The available positions on the board
     */
    @JsonSerialize(using = BoardSlotSerializer.class)
    @JsonProperty("availableSlots")
    private final List<BoardSlot> boardSlots;

    //endregion

    //region Constructor

    /**
     * The empty constructor needed for Jackson library
     */
    public PlaceStarterCardResponseData() {
        super();
        this.cardId = -1;
        this.face = -1;
        this.boardSlots = null;
    }

    /**
     * Create a placeStarterCard data object
     * @param status        The status of the response
     * @param cardId        The ID of the chosen starter card
     * @param face          The face of the starter card
     * @param boardSlots    The available slots on the board of the player
     */
    public PlaceStarterCardResponseData(ResponseStatus status, int cardId, int face, List<BoardSlot>boardSlots) {
        super(status);
        this.cardId = cardId;
        this.face = face;
        this.boardSlots = boardSlots;
    }

    /**
     * Create a placeStarterCard data object
     * @param status    The status of the response
     */
    public PlaceStarterCardResponseData(ResponseStatus status) {
        super(status);
        this.cardId = -1;
        this.face = -1;
        this.boardSlots = null;
    }

    /**
     * Create a placeStarterCard data object, for broadcast
     * @param status    The status of the response
     * @param cardId    The ID of the starter card
     * @param face      The face of the starter card
     */
    public PlaceStarterCardResponseData(ResponseStatus status, int cardId, int face) {
        super(status);
        this.cardId = cardId;
        this.face = face;
        this.boardSlots = null;
    }

    //endregion

    //region Getters

    /**
     * @return The ID of the starter card
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     * @return The face of the starter card
     */
    public int getFace() {
        return this.face;
    }

    /**
     * @return The list of available slots
     */
    public List<BoardSlot> getBoardSlots() {
        return this.boardSlots;
    }

    //endregion
}
