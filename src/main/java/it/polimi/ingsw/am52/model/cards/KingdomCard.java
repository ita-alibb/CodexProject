package it.polimi.ingsw.am52.Model.cards;

import it.polimi.ingsw.am52.Exceptions.CardException;

/**
 * The base class of all Kingdom cards (Gold and Resource cards).
 * Every kingdom card has a Kingdom associated to it, and on the
 * back side, there is a permanent resource equal to its kingdom resource
 * (Fungi, Plant, Animal, or Insect).
 */
public abstract class KingdomCard extends Card {

    //region Public Static Methods


    /**
     * Pick the card that has the specified identifier. Each card has its own numerical identifier.
     * @param cardId The card identifier.
     * @return The card that has the specified identifier.
     */
    public static KingdomCard getCardWithId(int cardId) {

        // Check if the card identifier is valid.
        if (cardId < ResourceCard.START_INDEX || cardId > GoldCard.LAST_INDEX) {
            throw new CardException(
                    String.format("Id of kingdom card out of bounds [%d,%d].", ResourceCard.START_INDEX, GoldCard.LAST_INDEX)
            );
        }

        // Return Resource or Gold card, depending on the card identifier value.
        return cardId <= ResourceCard.LAST_INDEX ?
                ResourceCard.getCards().get(cardId - ResourceCard.START_INDEX) :
                GoldCard.getCards().get(cardId - GoldCard.START_INDEX);
    }

    //endregion

    //region Constructors

    /**
     * Instantiate a playing card with the specified front and back faces.
     *
     * @param frontFace The front face of the card.
     * @param backFace  The back face of the card.
     */
    public KingdomCard(int cardId, KingdomCardFace frontFace, KingdomBackFace backFace) {
        super(cardId, frontFace, backFace);
    }

    //endregion

    //region Getters

    /**
     *
     * @return The kingdom of this card.
     */
    public Kingdom getKingdom() {
        return getFrontFace().getKingdom();
    }

    @Override
    public KingdomCardFace getFrontFace() {
        return (KingdomCardFace)super.getFrontFace();
    }

    @Override
    public KingdomBackFace getBackFace() {
        return (KingdomBackFace)super.getBackFace();
    }

    //endregion
}
