package it.polimi.ingsw.am52.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

public class TakeCardResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The taken card id
     */
    private final int takenCardId;

    /**
     * The new shown card id
     */
    private final int shownCardId;

    /**
     * The type of card taken by the player, int representing enum {@link it.polimi.ingsw.am52.json.dto.DrawType}.
     */
    private final int type;

    /**
     * The id of the next card in the deck pile
     */
    private final int nextCardId;
    //endregion

    //region Constructor

    /**
     * The empty constructor needed for Jackson library
     */
    public TakeCardResponseData() {
        super();
        this.takenCardId = -1;
        this.shownCardId = -1;
        this.type = -1;
        this.nextCardId = -1;
    }

    /**
     * Create a selectObjective data object
     * @param status    The status of the response
     * @param takenCardId the id of the card chosen to be taken
     * @param shownCardId the id of the card shown
     * @param type The type of the card, resource or gold.
     * @param nextCardId   The id of the next card in the pile
     */
    public TakeCardResponseData(ResponseStatus status, int takenCardId, int shownCardId, int type, int nextCardId) {
        super(status);
        this.takenCardId = takenCardId;
        this.shownCardId = shownCardId;
        this.type = type;
        this.nextCardId = nextCardId;
    }

    /**
     * Create a selectObjective data object
     * @param status    The status of the response
     */
    public TakeCardResponseData(ResponseStatus status) {
        super(status);
        this.takenCardId = -1;
        this.shownCardId = -1;
        this.type = -1;
        this.nextCardId = -1;
    }

    //endregion

    //region Getters

    /**
     * @return The id of the card chosen to be taken
     */
    public int getTakenCardId() {
        return this.takenCardId;
    }

    /**
     * @return The id of the card shown
     */
    public int getShownCardId() {
        return this.shownCardId;
    }

    /**
     *
     * @return The type of the card, resource or gold.
     */
    public int getType() {
        return this.type;
    }

    /**
     * @return the next card id, used to show the correct back on the deck pile
     */
    public int getNextCardId() {
        return this.nextCardId;
    }
    //endregion
}
