package it.polimi.ingsw.am52.model.cards;

/**
 * The base class of all playing cards. Each card has two faces (sides),
 * and the player can choose to place the card on the playing board
 * with one of the two face visible. Each card has a numerical identifier.
 */
public class Card {

    //region Private Fields

    /**
     * The numerical identifier of the card.
     */
    private final int cardId;

    /**
     * The (immutable) front face of this card.
     */
    private final CardFace frontFace;

    /**
     * The (immutable) back face of this card.
     */
    private final CardFace backFace;

    //endregion

    //region Constructors

    /**
     * Instantiate a playing card with the specified front and back faces.
     * @param frontFace The front face of the card.
     * @param backFace The back face of the card.
     */
    public Card(int cardId, CardFace frontFace, CardFace backFace) {
        this.cardId = cardId;
        this.frontFace = frontFace;
        this.backFace = backFace;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The numerical identifier of this card.
     */
    public int getCardId() {
        return this.cardId;
    }

    /**
     *
     * @return The front face of this card.
     */
    public CardFace getFrontFace() {
        return this.frontFace;
    }

    /**
     *
     * @return The back face of this card.
     */
    public CardFace getBackFace() {
        return this.backFace;
    }

    //endregion
}
