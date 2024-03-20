package it.polimi.ingsw.am52.model.cards;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am52.model.cards.ResourceCorner.*;

public abstract class StarterCardBackFace extends StarterCardFace {

    //region Private Static Fields

    /**
     * The (single instance) list of all starter card back faces.
     */
    private static ImmutableList<StarterCardBackFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get all back faces of Starter cards. The list is sorted as the
     * card pictures on the ref. manual.
     * @return A list with all back faces of the Starter cards.
     */
    public static ImmutableList<StarterCardBackFace> getCards() {

        // If the static field already exists, then return it.
        if (StarterCardBackFace.cards != null) {
            return StarterCardBackFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<StarterCardBackFace> cards = new ArrayList<>();

        // Populate the list with all six back faces of the starter cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_back.pdf
         */
        /* 81 */cards.add(new StarterCardBackFace(PLANT_CORNER, ANIMAL_CORNER, INSECT_CORNER, FUNGI_CORNER) { });
        /* 82 */cards.add(new StarterCardBackFace(ANIMAL_CORNER, INSECT_CORNER, FUNGI_CORNER, PLANT_CORNER) { });
        /* 83 */cards.add(new StarterCardBackFace(ANIMAL_CORNER, PLANT_CORNER, FUNGI_CORNER, INSECT_CORNER) { });
        /* 84 */cards.add(new StarterCardBackFace(INSECT_CORNER, FUNGI_CORNER, ANIMAL_CORNER, PLANT_CORNER) { });
        /* 85 */cards.add(new StarterCardBackFace(FUNGI_CORNER, ANIMAL_CORNER, PLANT_CORNER, INSECT_CORNER) { });
        /* 86 */cards.add(new StarterCardBackFace(ANIMAL_CORNER, INSECT_CORNER, PLANT_CORNER, FUNGI_CORNER) { });

        // Save the List in the static field, as an immutable List of cards.
        StarterCardBackFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return StarterCardBackFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a card back face with the specified corners. In order to set a
     * non-visible corner, pass null to the desired parameter.
     * @param topRight    The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft  The bottom-left corner.
     * @param topLeft     The top-left corner.
     */
    public StarterCardBackFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }
    
    //endregion
    
    //region Overrides
    
    @Override
    public CardSide getCardSide() {
        return CardSide.BACK;
    }
    
    //endregion
}
