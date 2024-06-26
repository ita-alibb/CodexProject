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
     * The id of the next card in the deck pile
     */
    private final int nextCardId;

    //endregion

    //region Constructor

    /**
     * The empty constructor, needed for Jackson library
     */
    public DrawCardResponseData() {
        super();
        this.cardId = -1;
        this.deck = -1;
        this.nextCardId = -1;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     * @param cardId    The ID of the drawn card
     * @param deck      The name of the deck
     * @param nextCardId   The id of the next card in the pile
     */
    public DrawCardResponseData(ResponseStatus status, int cardId, int deck, int nextCardId) {
        super(status);
        this.cardId = cardId;
        this.deck = deck;
        this.nextCardId = nextCardId;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     */
    public DrawCardResponseData(ResponseStatus status) {
        super(status);
        this.cardId = -1;
        this.deck = -1;
        this.nextCardId = -1;
    }

    /**
     * Create a drawCard data object
     * @param status    The status of the response
     * @param deck      The name of the deck
     * @param nextCardId   The id of the next card in the pile
     */
    public DrawCardResponseData(ResponseStatus status, int deck, int nextCardId) {
        super(status);
        this.cardId = -1;
        this.deck = deck;
        this.nextCardId = nextCardId;
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
     * @return the next card id, used to show the correct back on the deck pile
     */
    public int getNextCardId() {
        return this.nextCardId;
    }

    //endregion
}
