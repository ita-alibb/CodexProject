package it.polimi.ingsw.am52.model.cards;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am52.model.cards.BlankCorner.BLANK_CORNER;
import static it.polimi.ingsw.am52.model.cards.ResourceCorner.ANIMAL_CORNER;
import static it.polimi.ingsw.am52.model.cards.ResourceCorner.FUNGI_CORNER;
import static it.polimi.ingsw.am52.model.cards.ResourceCorner.INSECT_CORNER;
import static it.polimi.ingsw.am52.model.cards.ResourceCorner.PLANT_CORNER;

/**
 * The front face of a Starter card. Every starter card has
 * one or more permanent resources on its front face.
 */
public abstract class StarterCardFrontFace extends StarterCardFace {

    //region Private Fields

    /**
     * The permanent resources of the front face.
     */
    private final ResourcesCounter permanentResources;

    //endregion

    //region Private Static Fields

    /**
     * The (single instance) list of all starter card font faces.
     */
    private static ImmutableList<StarterCardFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get all front faces of Starter cards. The list is sorted as the
     * card pictures on the ref. manual.
     * @return A list with all front faces of the Starter cards.
     */
    public static ImmutableList<StarterCardFrontFace> getCards() {

        // If the static field already exists, then return it.
        if (StarterCardFrontFace.cards != null) {
            return StarterCardFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<StarterCardFrontFace> cards = new ArrayList<>();

        // Populate the list with all six front faces of the starter cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 81 */cards.add(new StarterCardFrontFace(PLANT_CORNER, BLANK_CORNER, INSECT_CORNER, BLANK_CORNER, Resource.INSECT) { });
        /* 82 */cards.add(new StarterCardFrontFace(BLANK_CORNER, FUNGI_CORNER, BLANK_CORNER, ANIMAL_CORNER, Resource.FUNGI) { });
        /* 83 */cards.add(new StarterCardFrontFace(BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, Resource.PLANT, Resource.FUNGI) { });
        /* 84 */cards.add(new StarterCardFrontFace(BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, Resource.ANIMAL, Resource.INSECT) { });
        /* 85 */cards.add(new StarterCardFrontFace(BLANK_CORNER, null, null, BLANK_CORNER, Resource.ANIMAL, Resource.INSECT, Resource.PLANT) { });
        /* 86 */cards.add(new StarterCardFrontFace(BLANK_CORNER, null, null, BLANK_CORNER, Resource.PLANT, Resource.ANIMAL, Resource.FUNGI) { });
        
        // Save the List in the static field, as an immutable List of cards.
        StarterCardFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return StarterCardFrontFace.cards;
    }

    //endregion
    
    //region Constructor

    /**
     * Creates a front card fae of the starter card wit the specified corners
     * and one additional resource.
     * @param topRight The top-right corner of the card face.
     * @param bottomRight The bottom-right corner of the card face.
     * @param bottomLeft The bottom-left corner of the card face.
     * @param topLeft The top-left corner of the card face.
     * @param permanent The permanent resource.
     */
    private StarterCardFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft,
            CardCorner topLeft, Resource permanent) {
        super(topRight, bottomRight, bottomLeft, topLeft);

        this.permanentResources = new ResourcesCounter(permanent);
    }
    
    /**
     * Creates a front card fae of the starter card wit the specified corners
     * and two additional resources.
     * @param topRight The top-right corner of the card face.
     * @param bottomRight The bottom-right corner of the card face.
     * @param bottomLeft The bottom-left corner of the card face.
     * @param topLeft The top-left corner of the card face.
     * @param permanent1 The permanent resource.
     * @param permanent2 The permanent resource.
     */
    private StarterCardFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft,
            CardCorner topLeft, Resource permanent1, Resource permanent2) {
        super(topRight, bottomRight, bottomLeft, topLeft);
        
        this.permanentResources = ResourcesCounter.add(new ResourcesCounter(permanent1), new ResourcesCounter(permanent2));
    }
    
    /**
     * Creates a front card fae of the starter card wit the specified corners
     * and three additional resources.
     * @param topRight The top-right corner of the card face.
     * @param bottomRight The bottom-right corner of the card face.
     * @param bottomLeft The bottom-left corner of the card face.
     * @param topLeft The top-left corner of the card face.
     * @param permanent1 The permanent resource.
     * @param permanent2 The permanent resource.
     * @param permanent3 The permanent resource.
     */
    private StarterCardFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft,
            CardCorner topLeft, Resource permanent1, Resource permanent2, Resource permanent3) {
        super(topRight, bottomRight, bottomLeft, topLeft);

        this.permanentResources = ResourcesCounter.add(
                ResourcesCounter.add(new ResourcesCounter(permanent1), new ResourcesCounter(permanent2)),
                new ResourcesCounter(permanent3));
    }
    
    //endregion

    //region Getters

    /**
     * Every starter card has one or more permanent resources on its
     * front face.
     * @return The permanent resources of the front face.
     */
    public ResourcesCounter getPermanentResources() {
        return this.permanentResources;
    }

    //endregion

    //region Overrides

    /**
     * The front faces of starter cards have one or more permanent resources.
     * This method takes into consideration both resources visible on the corners
     * and the permanent resource.
     * @return The resources associated to this card face.
     */
    @Override
    public ResourcesCounter getResources() {

        // Invoke super-class implementation in order to calculate
        // visible resources on the card corners.
        ResourcesCounter cornerResources = super.getResources();

        // Add the permanent resource and return the result.
        return ResourcesCounter.add(cornerResources, getPermanentResources());
    }

    @Override
    public CardSide getCardSide() {
        return CardSide.FRONT;
    }

    //endregion
}
