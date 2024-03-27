package it.polimi.ingsw.am52.Model.cards;

import it.polimi.ingsw.am52.Exceptions.CardException;
import it.polimi.ingsw.am52.Util.ImmutableList;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class of all Resource cards. You can get a list
 * of all resource cards by invoking the static method getCards().
 * The identifiers of the Resource cards go from 0 to 39 (40 cards).
 */
public abstract class ResourceCard extends KingdomCard {

    //region Private Static Fields

    /**
     * The start index of the resource cards (40 cards, from 0 to 39).
     */
    protected static final int START_INDEX = 0;

    /**
     * The last index of the starter cards (6 cards, from 0 to 39).
     */
    protected static final int LAST_INDEX = 39;

    /**
     * The (single instance) collection with all 40 Resource cards.
     */
    private static ImmutableList<ResourceCard> cards;

    //endregion

    //region Public Static Methods

    /**
     * The game has 40 different Resource cards. This method
     * returns a list of all Resource cards, sorted as reported
     * in the ref. game manual.
     * @return The list of all 40 Resource cards.
     */
    public static ImmutableList<ResourceCard> getCards() {

        // If the static field already exists, then return it.
        if (ResourceCard.cards != null) {
            return ResourceCard.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceCard> cards = new ArrayList<>();

        // Populate the list with all six starter cards.
        // Iterate over the List of starter card Front-faces (Back-faces list
        // has the same size). The iteration is divided into 4 loops,
        // each with 10 iterations. Each loop construct the set of cards
        // with the same back face of the same kingdom.
        // 1) Fungi resource cards (form 0 to 9)
        for (int i = 0; i != 10; i++) {
            // At each iteration add a new card to the list.
            cards.add(
                    // Create an anonymous class with the i-th front/back
                    // face.
                    new ResourceCard(
                            i,
                            ResourceFrontFace.getCards().get(i),
                            KingdomBackFace.FUNGI_BACK_FACE
                    ) {}
            );
        }
        // 2) Plant resource cards (form 10 to 19)
        for (int i = 10; i != 20; i++) {
            // At each iteration add a new card to the list.
            cards.add(
                    // Create an anonymous class with the i-th front/back
                    // face.
                    new ResourceCard(
                            i,
                            ResourceFrontFace.getCards().get(i),
                            KingdomBackFace.PLANT_BACK_FACE
                    ) {}
            );
        }
        // 3) Animal resource cards (form 20 to 29)
        for (int i = 20; i != 30; i++) {
            // At each iteration add a new card to the list.
            cards.add(
                    // Create an anonymous class with the i-th front/back
                    // face.
                    new ResourceCard(
                            i,
                            ResourceFrontFace.getCards().get(i),
                            KingdomBackFace.ANIMAL_BACK_FACE
                    ) {}
            );
        }
        // 4) Insect resource cards (form 30 to 39)
        for (int i = 30; i != 40; i++) {
            // At each iteration add a new card to the list.
            cards.add(
                    // Create an anonymous class with the i-th front/back
                    // face.
                    new ResourceCard(
                            i,
                            ResourceFrontFace.getCards().get(i),
                            KingdomBackFace.INSECT_BACK_FACE
                    ) {}
            );
        }

        // Save the List in the static field, as an immutable List of cards.
        ResourceCard.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return ResourceCard.cards;
    }

    /**
     * Pick the card that has the specified identifier. Each card has its own numerical identifier.
     * @param cardId The card identifier.
     * @return The card that has the specified identifier.
     */
    public static ResourceCard getCardWithId(int cardId) {

        // Check if the card identifier is valid.
        if (cardId < START_INDEX || cardId > LAST_INDEX) {
            throw new CardException(
                    String.format("Id of resource card out of bounds [%d,%d].", START_INDEX, LAST_INDEX)
            );
        }

        // Return the card with the specified identifier.
        return ResourceCard.getCards().get(cardId - START_INDEX);
    }

    //endregion

    //region Constructors

    /**
     * Instantiate a Resource card with the specified front and back faces.
     *
     * @param frontFace The front face of the card.
     * @param backFace  The back face of the card.
     */
    public ResourceCard(int cardId, ResourceFrontFace frontFace, KingdomBackFace backFace) {
        super(cardId, frontFace, backFace);
    }

    //endregion

    //region Overrides

    @Override
    public ResourceFrontFace getFrontFace() {
        return (ResourceFrontFace)super.getFrontFace();
    }

    @Override
    public String toString() {
        return String.format("Resource Card #%d", getCardId());
    }

    //endregion

}
