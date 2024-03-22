package it.polimi.ingsw.am52.Model.cards;

import it.polimi.ingsw.am52.Exceptions.CardException;
import it.polimi.ingsw.am52.Util.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class that represents a Starter card. The starter is a special card,
 * because is the first card placed on the playing board (on the root slot),
 * it does not give points, it does not take part in the calculation of
 * the points based on patterns. The identifier of starter cards go from 80
 * to 85 (6 starter cards).
 */
public abstract class StarterCard extends Card {

    //region Private Static Fields

    /**
     * The start index of the starter cards (6 cards, from 80 to 85).
     */
    protected static final int START_INDEX = 80;

    /**
     * The last index of the starter cards (6 cards, from 80 to 85).
     */
    protected static final int LAST_INDEX = 85;

    /**
     * The (single instance) collection with all six starter cards.
     */
    private static ImmutableList<StarterCard> cards;

    //endregion

    //section Public Static Methods

    /**
     * The game has six different starer cards. This method
     * returns a list of all starter cards, sorted as reported
     * in the ref. game manual.
     * @return The list of all six starter cards.
     */
    public static ImmutableList<StarterCard> getCards() {

        // If the static field already exists, then return it.
        if (StarterCard.cards != null) {
            return StarterCard.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<StarterCard> cards = new ArrayList<>();

        // Populate the list with all six starter cards.
        // Iterate over the List of starter card Front-faces (Back-faces list
        // has the same size)
        for (int i = 0; i != StarterCardFrontFace.getCards().size(); i++) {
            // At each iteration add a new card to the list.
            cards.add(
                    // Create an anonymous class with the i-th front/back
                    // face.
                    new StarterCard(
                            START_INDEX + i,
                            StarterCardFrontFace.getCards().get(i),
                            StarterCardBackFace.getCards().get(i)
                    ) {}
            );
        }

        // Save the List in the static field, as an immutable List of cards.
        StarterCard.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return StarterCard.cards;
    }

    /**
     * Pick the card that has the specified identifier. Each card has its own numerical identifier.
     * @param cardId The card identifier.
     * @return The card that has the specified identifier.
     */
    public static StarterCard getCardWithId(int cardId) {

        // Check if the card identifier is valid.
        if (cardId < START_INDEX || cardId > LAST_INDEX) {
            throw new CardException(
                    String.format("Id of starter card out of bounds [%d,%d].", START_INDEX, LAST_INDEX)
            );
        }

        // Return the card with the specified identifier.
        return StarterCard.getCards().get(cardId - START_INDEX);
    }

    //endregion

    //region Constructors

    /**
     * Instantiate a starter card with the specified front and back faces.
     *
     * @param frontFace The front face of the card.
     * @param backFace  The back face of the card.
     */
    public StarterCard(int CardId, StarterCardFace frontFace, StarterCardFace backFace) {
        super(CardId, frontFace, backFace);
    }

    //endregion

    /**
     * Method used to check if a Face is of a Card
     * @param  face The Face to check
     * @return The (optional) card side if the CardFace is present on one of the two side.
     */
    public Optional<CardSide> getSide(StarterCardFace face) {
        if (face.equals(this.getFrontFace()) ){
            return Optional.of(CardSide.FRONT);
        } else if (face.equals(this.getBackFace())) {
            return Optional.of(CardSide.BACK);
        }

        return Optional.empty();
    }

    //region Overrides

    @Override
    public StarterCardFace getFrontFace() {
        return (StarterCardFace) super.getFrontFace();
    }

    @Override
    public StarterCardFace getBackFace() {
        return (StarterCardFace) super.getBackFace();
    }

    @Override
    public String toString() {
        return String.format("Starter Card #%d", getCardId());
    }

    //endregion
}
