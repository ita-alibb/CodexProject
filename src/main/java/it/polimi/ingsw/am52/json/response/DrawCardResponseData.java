package it.polimi.ingsw.am52.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the drawCard response
 */
public class DrawCardResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The card which has been drawn
     */
    private final int cardId;

    /**
     * The deck the player draws from
     */
    private final int deck;

    /**
     * Checks if the deck above is empty or not
     */
    @JsonProperty("isEmpty")
    private final boolean isEmpty;

    //endregion

    //region Constructor

    /**
     * The empty constructor, needed for Jackson library
     */
    public DrawCardResponseData() {
        super();
        this.cardId = -1;
        this.deck = -1;
        this.isEmpty = false;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     * @param cardId    The ID of the drawn card
     * @param deck      The name of the deck
     * @param isEmpty   The value that checks if there are any cards left or not
     */
    public DrawCardResponseData(ResponseStatus status, int cardId, int deck, boolean isEmpty) {
        super(status);
        this.cardId = cardId;
        this.deck = deck;
        this.isEmpty = isEmpty;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     */
    public DrawCardResponseData(ResponseStatus status) {
        super(status);
        this.cardId = -1;
        this.deck = -1;
        this.isEmpty = false;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     * @param deck      The name of the deck
     * @param isEmpty   The value that checks if there are any cards left or not
     */
    public DrawCardResponseData(ResponseStatus status, int deck, boolean isEmpty) {
        super(status);
        this.cardId = -1;
        this.deck = deck;
        this.isEmpty = isEmpty;
    }

    //endregion

    //region Getters

    /**
     * @return The ID of the drawn card
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     * @return The deck a player draws from
     */
    public int getDeck() {
        return this.deck;
    }

    /**
     * @return false if the deck is not empty, otherwise true
     */
    @JsonProperty("isEmpty")
    public boolean isEmpty() {
        return this.isEmpty;
    }

    //endregion
}
