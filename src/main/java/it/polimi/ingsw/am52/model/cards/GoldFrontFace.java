package it.polimi.ingsw.am52.model.cards;

import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.am52.model.cards.BlankCorner.*;
import static it.polimi.ingsw.am52.model.cards.ItemCorner.*;
import static it.polimi.ingsw.am52.model.cards.ConstantPoints.*;
import static it.polimi.ingsw.am52.model.cards.ItemPoints.*;
import static it.polimi.ingsw.am52.model.cards.CornerPoints.*;

/**
 * The base class of all front faces of Gold Cards. Every Gold card
 * needs some resources in order to be placed on the playing board.
 * Every Gold card, when placed with its front side visible, gives
 * some points to the player.
 */
public abstract class GoldFrontFace extends KingdomCardFace {

    //region Private Fields

    /**
     * The required resources needed to place the card on the
     * playing board.
     */
    private final ResourcesCounter requiredResources;

    //endregion

    //region Private Static Fields

    /**
     * The (single instance) list of all cards.
     */
    private static ImmutableList<GoldFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get all front faces of Gold cards. The list is sorted as the
     * card pictures on the ref. manual.
     * @return A list with all front faces of the Gold cards.
     */
    public static ImmutableList<GoldFrontFace> getCards() {

        // If the static field already exists, then return it.
        if (GoldFrontFace.cards != null) {
            return GoldFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<GoldFrontFace> cards = new ArrayList<>();

        // Populate the list with all card faces of all Kingdom.
        // 1) Fungi
        FungiGoldFrontFace.getFungiCards().addTo(cards);
        // 2) Plant
        PlantGoldFrontFace.getPlantCards().addTo(cards);
        // 3) Animal
        AnimalGoldFrontFace.getAnimalCards().addTo(cards);
        // 4) Insect
        InsectGoldFrontFace.getInsectCards().addTo(cards);

        // Save the reference into the static field, as an immutable list.
        GoldFrontFace.cards = new ImmutableList<>(cards);

        // Return the static field.
        return GoldFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Gold card front face, with the specified corners, the specified
     * points and required resources.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     * @param plants The required number of plants.
     * @param animals The required number of animals.
     * @param fungi The required number of fungi.
     * @param insects The required number of insects.     
     */
    protected GoldFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points, int plants, int animals, int fungi, int insects) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
        this.requiredResources = new ResourcesCounter(fungi, plants, animals, insects);
    }

    //endregion

    //region Getters

    /**
     *
     * @return The resources required by this card face, in order to
     * be placed on the playing board.
     */
    public ResourcesCounter getRequiredResources() {
        return this.requiredResources;
    }

    //endregion

    //region Overrides

    @Override
    public boolean canPlace(ResourcesProvider availableResources) {
        ResourcesCounter available = availableResources.getResources();
        ResourcesCounter required = getRequiredResources();
        return (available.getPlantCount() >= required.getPlantCount() &&
                available.getAnimalCount() >= required.getAnimalCount() &&
                available.getFungiCount() >= required.getFungiCount() &&
                available.getInsectCount() >= required.getInsectCount());
    }

    @Override
    public CardSide getCardSide() {
        return CardSide.FRONT;
    }

    //endregion
}

/**
 * A Gold card front face, of the Fungi Kingdom.
 */
class FungiGoldFrontFace extends GoldFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<GoldFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Gold cards of the
     * Insect Kingdom.
     * @return The card list.
     */
    public static ImmutableList<GoldFrontFace> getFungiCards() {

        // If the static field already exists, then return it.
        if (FungiGoldFrontFace.cards != null) {
            return FungiGoldFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<GoldFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Fungi gold cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 41 */cards.add(new FungiGoldFrontFace(BLANK_CORNER, FEATHER_CORNER, BLANK_CORNER, null, FEATHER_POINTS, 0, 1, 2,0));
        /* 42 */cards.add(new FungiGoldFrontFace(INK_CORNER, BLANK_CORNER, null, BLANK_CORNER, INK_POINTS, 1, 0, 2, 0));
        /* 43 */cards.add(new FungiGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, VELLUM_CORNER, VELLUM_POINTS, 0, 0, 2, 1));
        /* 44 */cards.add(new FungiGoldFrontFace(BLANK_CORNER, BLANK_CORNER, null, BLANK_CORNER, CORNER_POINTS, 3, 1, 0, 0));
        /* 45 */cards.add(new FungiGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 1, 0, 3, 0));
        /* 46 */cards.add(new FungiGoldFrontFace(null, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 0, 0, 3, 1));
        /* 47 */cards.add(new FungiGoldFrontFace(null, null, INK_CORNER, BLANK_CORNER, THREE_POINTS, 0, 0, 3, 0));
        /* 48 */cards.add(new FungiGoldFrontFace(BLANK_CORNER, null, null, FEATHER_CORNER, THREE_POINTS, 0, 0, 3, 0));
        /* 49 */cards.add(new FungiGoldFrontFace(VELLUM_CORNER, BLANK_CORNER, null, null, THREE_POINTS, 0, 0, 3, 0));
        /* 50 */cards.add(new FungiGoldFrontFace(null, null, BLANK_CORNER, BLANK_CORNER, FIVE_POINTS, 0, 0, 5, 0));

        FungiGoldFrontFace.cards = new ImmutableList<>(cards);

        return FungiGoldFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Gold card front face, of the Fungi Kingdom, with the specified corners,
     * the specified points and required resources.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     * @param plants The required number of plants.
     * @param animals The required number of animals.
     * @param fungi The required number of fungi.
     * @param insects The required number of insects.
     */
    protected FungiGoldFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
                                 CardPoints points, int plants, int animals, int fungi, int insects) {
        super(topRight, bottomRight, bottomLeft, topLeft, points, plants, animals, fungi, insects);
    }

    //endregion

    //region Overrides

    @Override
    public Kingdom getKingdom() {
        return Kingdom.FUNGI_KINGDOM;
    }

    //endregion
}

/**
 * A Gold card front face, of the Plant Kingdom.
 */
class PlantGoldFrontFace extends GoldFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<GoldFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Gold cards of the
     * Insect Kingdom.
     * @return The card list.
     */
    public static ImmutableList<GoldFrontFace> getPlantCards() {

        // If the static field already exists, then return it.
        if (PlantGoldFrontFace.cards != null) {
            return PlantGoldFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<GoldFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Plant gold cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 51 */cards.add(new PlantGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, FEATHER_CORNER, FEATHER_POINTS, 2, 0, 0,1));
        /* 52 */cards.add(new PlantGoldFrontFace(VELLUM_CORNER, BLANK_CORNER, null, BLANK_CORNER, VELLUM_POINTS, 2, 0, 1, 0));
        /* 53 */cards.add(new PlantGoldFrontFace(null, BLANK_CORNER, INK_CORNER, BLANK_CORNER, INK_POINTS, 2, 1, 0, 0));
        /* 54 */cards.add(new PlantGoldFrontFace(BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, null, CORNER_POINTS, 3, 0, 0, 1));
        /* 55 */cards.add(new PlantGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 3, 1, 0, 0));
        /* 56 */cards.add(new PlantGoldFrontFace(null, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 3, 0, 1, 0));
        /* 57 */cards.add(new PlantGoldFrontFace(null, null, FEATHER_CORNER, BLANK_CORNER, THREE_POINTS, 3, 0, 0, 0));
        /* 58 */cards.add(new PlantGoldFrontFace(BLANK_CORNER, null, null, VELLUM_CORNER, THREE_POINTS, 3, 0, 0, 0));
        /* 59 */cards.add(new PlantGoldFrontFace(INK_CORNER, BLANK_CORNER, null, null, THREE_POINTS, 3, 0, 0, 0));
        /* 60 */cards.add(new PlantGoldFrontFace(BLANK_CORNER, null, null, BLANK_CORNER, FIVE_POINTS, 5, 0, 0, 0));

        // Save the List in the static field, as an immutable List of cards.
        PlantGoldFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return PlantGoldFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Gold card front face, of the Plant Kingdom, with the specified corners,
     * the specified points and required resources.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     * @param plants The required number of plants.
     * @param animals The required number of animals.
     * @param fungi The required number of fungi.
     * @param insects The required number of insects. 
     */
    protected PlantGoldFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points, int plants, int animals, int fungi, int insects) {
        super(topRight, bottomRight, bottomLeft, topLeft, points, plants, animals, fungi, insects);
    }

    //endregion

    //region Overrides

    @Override
    public Kingdom getKingdom() {
        return Kingdom.PLANT_KINGDOM;
    }

    //endregion
}

/**
 * A Gold card front face, of the Animal Kingdom.
 */
class AnimalGoldFrontFace extends GoldFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<GoldFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Gold cards of the
     * Animal Kingdom.
     * @return The card list.
     */
    public static ImmutableList<GoldFrontFace> getAnimalCards() {

        // If the static field already exists, then return it.
        if (AnimalGoldFrontFace.cards != null) {
            return AnimalGoldFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<GoldFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Animal gold cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 61 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, INK_CORNER, INK_POINTS, 0, 2, 0, 1));
        /* 62 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, VELLUM_CORNER, BLANK_CORNER, null, VELLUM_POINTS, 1, 2, 0, 0));
        /* 63 */cards.add(new AnimalGoldFrontFace(null, BLANK_CORNER, FEATHER_CORNER, BLANK_CORNER, FEATHER_POINTS, 0, 2, 1, 0));
        /* 64 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, BLANK_CORNER, null, BLANK_CORNER, CORNER_POINTS, 0, 3, 0, 1));
        /* 65 */cards.add(new AnimalGoldFrontFace(null, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 0, 3, 1, 0));
        /* 66 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, null, CORNER_POINTS, 1, 3, 0, 0));
        /* 67 */cards.add(new AnimalGoldFrontFace(null, null, VELLUM_CORNER, BLANK_CORNER, THREE_POINTS, 0, 3, 0, 0));
        /* 68 */cards.add(new AnimalGoldFrontFace(INK_CORNER, null, null, BLANK_CORNER, THREE_POINTS, 0, 3, 0, 0));
        /* 69 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, FEATHER_CORNER, null, null, THREE_POINTS, 0, 3, 0, 0));
        /* 70 */cards.add(new AnimalGoldFrontFace(BLANK_CORNER, BLANK_CORNER, null, null, FIVE_POINTS, 0, 5, 0, 0));

        // Save the List in the static field, as an immutable List of cards.
        AnimalGoldFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return AnimalGoldFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Gold card front face, of the Animal Kingdom, with the specified corners,
     * the specified points and required resources.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     * @param plants The required number of plants.
     * @param animals The required number of animals.
     * @param fungi The required number of fungi.
     * @param insects The required number of insects. 
     */
    protected AnimalGoldFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points, int plants, int animals, int fungi, int insects) {
        super(topRight, bottomRight, bottomLeft, topLeft, points, plants, animals, fungi, insects);
    }

    //endregion

    //region Overrides

    @Override
    public Kingdom getKingdom() {
        return Kingdom.ANIMAL_KINGDOM;
    }

    //endregion
}

/**
 * A Gold card front face, of the Insect Kingdom.
 */
class InsectGoldFrontFace extends GoldFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<GoldFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Gold cards of the
     * Insect Kingdom.
     * @return The card list.
     */
    public static ImmutableList<GoldFrontFace> getInsectCards() {

        // If the static field already exists, then return it.
        if (InsectGoldFrontFace.cards != null) {
            return InsectGoldFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<GoldFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Insect gold cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 71 */cards.add(new InsectGoldFrontFace(FEATHER_CORNER, BLANK_CORNER, null, BLANK_CORNER, FEATHER_POINTS, 1, 0, 0, 2));
        /* 72 */cards.add(new InsectGoldFrontFace(null, BLANK_CORNER, VELLUM_CORNER, BLANK_CORNER, VELLUM_POINTS, 0, 1, 0, 2));
        /* 73 */cards.add(new InsectGoldFrontFace(BLANK_CORNER, INK_CORNER, BLANK_CORNER, null, INK_POINTS, 0, 0, 1, 2));
        /* 74 */cards.add(new InsectGoldFrontFace(BLANK_CORNER, BLANK_CORNER, null, BLANK_CORNER, CORNER_POINTS, 0, 1, 0, 3));
        /* 75 */cards.add(new InsectGoldFrontFace(BLANK_CORNER, null, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 1, 0, 0, 3));
        /* 76 */cards.add(new InsectGoldFrontFace(null, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, CORNER_POINTS, 0, 0, 1, 3));
        /* 77 */cards.add(new InsectGoldFrontFace(null, null, BLANK_CORNER, INK_CORNER, THREE_POINTS, 0, 0, 0, 3));
        /* 78 */cards.add(new InsectGoldFrontFace(VELLUM_CORNER, null, null, BLANK_CORNER, THREE_POINTS, 0, 0, 0, 3));
        /* 79 */cards.add(new InsectGoldFrontFace(null, BLANK_CORNER, FEATHER_CORNER, null, THREE_POINTS, 0, 0, 0, 3));
        /* 80 */cards.add(new InsectGoldFrontFace(BLANK_CORNER, null, null, BLANK_CORNER, FIVE_POINTS, 0, 0, 0, 5));

        // Save the List in the static field, as an immutable List of cards.
        InsectGoldFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return InsectGoldFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Gold card front face, of the Insect Kingdom, with the specified corners,
     * the specified points and required resources.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     * @param plants The required number of plants.
     * @param animals The required number of animals.
     * @param fungi The required number of fungi.
     * @param insects The required number of insects. 
     */
    protected InsectGoldFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points, int plants, int animals, int fungi, int insects) {
        super(topRight, bottomRight, bottomLeft, topLeft, points, plants, animals, fungi, insects);
    }

    //endregion

    //region Overrides

    @Override
    public Kingdom getKingdom() {
        return Kingdom.INSECT_KINGDOM;
    }

    //endregion
}