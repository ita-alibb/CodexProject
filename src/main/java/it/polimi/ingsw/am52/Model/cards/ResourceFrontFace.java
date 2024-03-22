package it.polimi.ingsw.am52.Model.cards;

import it.polimi.ingsw.am52.Util.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.am52.Model.cards.BlankCorner.BLANK_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ResourceCorner.PLANT_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ResourceCorner.ANIMAL_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ResourceCorner.FUNGI_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ResourceCorner.INSECT_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ItemCorner.FEATHER_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ItemCorner.INK_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ItemCorner.VELLUM_CORNER;
import static it.polimi.ingsw.am52.Model.cards.ConstantPoints.ONE_POINTS;

/**
 * The base class of all front faces of Resource Cards. Resource cards
 * do not need any resource in order to be placed on the playing board.
 * Some resource card gives points when placed.
 */
public abstract class ResourceFrontFace extends KingdomCardFace {

    //region Private Static Fields

    /**
     * The (single instance) list of all cards.
     */
    private static ImmutableList<ResourceFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get all front faces of Resource cards. The list is sorted as the
     * card pictures on the ref. manual.
     * @return A list with all front faces of the Resource cards.
     */
    public static ImmutableList<ResourceFrontFace> getCards() {

        // If the static field already exists, then return it.
        if (ResourceFrontFace.cards != null) {
            return ResourceFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceFrontFace> cards = new ArrayList<>();

        // Populate the list with all card faces of all Kingdom.
        // 1) Fungi
        FungiResourceFrontFace.getFungiCards().addTo(cards);
        // 2) Plant
        PlantResourceFrontFace.getPlantCards().addTo(cards);
        // 3) Animal
        AnimalResourceFrontFace.getAnimalCards().addTo(cards);
        // 4) Insect
        InsectResourceFrontFace.getInsectCards().addTo(cards);

        // Save the reference into the static field, as an immutable list.
        ResourceFrontFace.cards = new ImmutableList<>(cards);

        // Return the static field.
        return ResourceFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Resource card front face, without any associated point,
     * with the specified corners.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     */
    protected ResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft,
            CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    /**
     * Creates a Resource card front face, with the specified corners and the specified
     * points.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected ResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
    }

    //endregion

    //region Overrides

    @Override
    public CardSide getCardSide() {
        return CardSide.FRONT;
    }

    //endregion
}

/**
 * A Resource card front face, of the Fungi Kingdom.
 */
class FungiResourceFrontFace extends ResourceFrontFace {

    //region Privete Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<ResourceFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Resource cards of the
     * Fungi Kingdom.
     * @return The card list.
     */
    public static ImmutableList<ResourceFrontFace> getFungiCards() {

        // If the static field already exists, then return it.
        if (FungiResourceFrontFace.cards != null) {
            return FungiResourceFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Fungi resource cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 01 */cards.add(new FungiResourceFrontFace(BLANK_CORNER, null, FUNGI_CORNER, FUNGI_CORNER));
        /* 02 */cards.add(new FungiResourceFrontFace(FUNGI_CORNER, BLANK_CORNER, null, FUNGI_CORNER));
        /* 03 */cards.add(new FungiResourceFrontFace(null, FUNGI_CORNER, FUNGI_CORNER, BLANK_CORNER));
        /* 04 */cards.add(new FungiResourceFrontFace(FUNGI_CORNER, FUNGI_CORNER, BLANK_CORNER, null));
        /* 05 */cards.add(new FungiResourceFrontFace(FEATHER_CORNER, FUNGI_CORNER, PLANT_CORNER, null));
        /* 06 */cards.add(new FungiResourceFrontFace(FUNGI_CORNER, ANIMAL_CORNER, null, INK_CORNER));
        /* 07 */cards.add(new FungiResourceFrontFace(INSECT_CORNER, BLANK_CORNER, VELLUM_CORNER, FUNGI_CORNER));
        /* 08 */cards.add(new FungiResourceFrontFace(FUNGI_CORNER, null, BLANK_CORNER, BLANK_CORNER, ONE_POINTS));
        /* 09 */cards.add(new FungiResourceFrontFace(null, BLANK_CORNER, BLANK_CORNER, FUNGI_CORNER, ONE_POINTS));
        /* 10 */cards.add(new FungiResourceFrontFace(BLANK_CORNER, BLANK_CORNER, FUNGI_CORNER, null, ONE_POINTS));

        // Save the List in the static field, as an immutable List of cards.
        FungiResourceFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return FungiResourceFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Resource card front face, of the Fungi Kingdom,
     * without any associated point, with the specified corners.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * player when the card is placed on the playing board.
     */
    protected FungiResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    /**
     * Creates a Resource card front face, of the Fungi Kingdom, with the
     * specified corners and the specified points.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected FungiResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft, CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
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
 * A Resource card front face, of the Plant Kingdom.
 */
class PlantResourceFrontFace extends ResourceFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<ResourceFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Resource cards of the
     * Plant Kingdom.
     * @return The card list.
     */
    public static ImmutableList<ResourceFrontFace> getPlantCards() {

        // If the static field already exists, then return it.
        if (PlantResourceFrontFace.cards != null) {
            return PlantResourceFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Plant resource cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 11 */cards.add(new PlantResourceFrontFace(BLANK_CORNER, null, PLANT_CORNER, PLANT_CORNER));
        /* 12 */cards.add(new PlantResourceFrontFace(PLANT_CORNER, BLANK_CORNER, null, PLANT_CORNER));
        /* 13 */cards.add(new PlantResourceFrontFace(null, PLANT_CORNER, PLANT_CORNER, BLANK_CORNER));
        /* 14 */cards.add(new PlantResourceFrontFace(PLANT_CORNER, PLANT_CORNER, BLANK_CORNER, null));
        /* 15 */cards.add(new PlantResourceFrontFace(INSECT_CORNER, PLANT_CORNER, FEATHER_CORNER, null));
        /* 16 */cards.add(new PlantResourceFrontFace(PLANT_CORNER, INK_CORNER, null, FUNGI_CORNER));
        /* 17 */cards.add(new PlantResourceFrontFace(null, ANIMAL_CORNER, PLANT_CORNER, VELLUM_CORNER));
        /* 18 */cards.add(new PlantResourceFrontFace(BLANK_CORNER, null, PLANT_CORNER, BLANK_CORNER, ONE_POINTS));
        /* 19 */cards.add(new PlantResourceFrontFace(BLANK_CORNER, PLANT_CORNER, null, BLANK_CORNER, ONE_POINTS));
        /* 20 */cards.add(new PlantResourceFrontFace(PLANT_CORNER, BLANK_CORNER, BLANK_CORNER, null, ONE_POINTS));

        // Save the List in the static field, as an immutable List of cards.
        PlantResourceFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return PlantResourceFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Resource card front face, of the Plant Kingdom,
     * without any associated point, with the specified corners.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * player when the card is placed on the playing board.
     */
    protected PlantResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    /**
     * Creates a Resource card front face, of the Plant Kingdom, with the
     * specified corners and the specified points.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected PlantResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft, CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
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
 * A Resource card front face, of the Animal Kingdom.
 */
class AnimalResourceFrontFace extends ResourceFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<ResourceFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Resource cards of the
     * Animal Kingdom.
     * @return The card list.
     */
    public static ImmutableList<ResourceFrontFace> getAnimalCards() {

        // If the static field already exists, then return it.
        if (AnimalResourceFrontFace.cards != null) {
            return AnimalResourceFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Fungi resource cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 21 */cards.add(new AnimalResourceFrontFace(ANIMAL_CORNER, null, BLANK_CORNER, ANIMAL_CORNER));
        /* 22 */cards.add(new AnimalResourceFrontFace(BLANK_CORNER, ANIMAL_CORNER, ANIMAL_CORNER, null));
        /* 23 */cards.add(new AnimalResourceFrontFace(null, BLANK_CORNER, ANIMAL_CORNER, ANIMAL_CORNER));
        /* 24 */cards.add(new AnimalResourceFrontFace(ANIMAL_CORNER, ANIMAL_CORNER, null, BLANK_CORNER));
        /* 25 */cards.add(new AnimalResourceFrontFace(INSECT_CORNER, ANIMAL_CORNER, INK_CORNER, null));
        /* 26 */cards.add(new AnimalResourceFrontFace(ANIMAL_CORNER, VELLUM_CORNER, null, PLANT_CORNER));
        /* 27 */cards.add(new AnimalResourceFrontFace(null, FUNGI_CORNER, ANIMAL_CORNER, FEATHER_CORNER));
        /* 28 */cards.add(new AnimalResourceFrontFace(BLANK_CORNER, BLANK_CORNER, ANIMAL_CORNER, null, ONE_POINTS));
        /* 29 */cards.add(new AnimalResourceFrontFace(null, ANIMAL_CORNER, BLANK_CORNER, BLANK_CORNER, ONE_POINTS));
        /* 30 */cards.add(new AnimalResourceFrontFace(ANIMAL_CORNER, null, BLANK_CORNER, BLANK_CORNER, ONE_POINTS));

        // Save the List in the static field, as an immutable List of cards.
        AnimalResourceFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return AnimalResourceFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Resource card front face, of the Animal Kingdom,
     * without any associated point, with the specified corners.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * player when the card is placed on the playing board.
     */
    protected AnimalResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    /**
     * Creates a Resource card front face, of the Animal Kingdom, with the
     * specified corners and the specified points.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected AnimalResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft, CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
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
 * A Resource card front face, of the Insect Kingdom.
 */
class InsectResourceFrontFace extends ResourceFrontFace {

    //region Private Static Fields

    /**
     * The (single instance) collection of all card faces.
     */
    private static ImmutableList<ResourceFrontFace> cards = null;

    //endregion

    //region Public Static Methods

    /**
     * Get the list of all front faces for the Resource cards of the
     * Insect Kingdom.
     * @return The card list.
     */
    public static ImmutableList<ResourceFrontFace> getInsectCards() {

        // If the static field already exists, then return it.
        if (InsectResourceFrontFace.cards != null) {
            return InsectResourceFrontFace.cards;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<ResourceFrontFace> cards = new ArrayList<>();

        // Populate the list with all ten front faces of the Fungi resource cards.

        /*
         The number in the comment at the beginning of the line refers
         to the page of the pdf file where the card face is represented:
         CODEX_cards_gold_front.pdf
         */
        /* 31 */cards.add(new InsectResourceFrontFace(INSECT_CORNER, null, BLANK_CORNER, INSECT_CORNER));
        /* 32 */cards.add(new InsectResourceFrontFace(BLANK_CORNER, INSECT_CORNER, INSECT_CORNER, null));
        /* 33 */cards.add(new InsectResourceFrontFace(null, BLANK_CORNER, INSECT_CORNER, INSECT_CORNER));
        /* 34 */cards.add(new InsectResourceFrontFace(INSECT_CORNER, INSECT_CORNER, null, BLANK_CORNER));
        /* 35 */cards.add(new InsectResourceFrontFace(FEATHER_CORNER, INSECT_CORNER, ANIMAL_CORNER, null));
        /* 36 */cards.add(new InsectResourceFrontFace(INSECT_CORNER, FUNGI_CORNER, null, VELLUM_CORNER));
        /* 37 */cards.add(new InsectResourceFrontFace(PLANT_CORNER, null, INK_CORNER, INSECT_CORNER));
        /* 38 */cards.add(new InsectResourceFrontFace(null, BLANK_CORNER, BLANK_CORNER, INSECT_CORNER, ONE_POINTS));
        /* 39 */cards.add(new InsectResourceFrontFace(BLANK_CORNER, INSECT_CORNER, null, BLANK_CORNER, ONE_POINTS));
        /* 40 */cards.add(new InsectResourceFrontFace(INSECT_CORNER, BLANK_CORNER, BLANK_CORNER, null, ONE_POINTS));

        // Save the List in the static field, as an immutable List of cards.
        InsectResourceFrontFace.cards = new ImmutableList<>(cards);

        // Return the reference to the static field.
        return InsectResourceFrontFace.cards;
    }

    //endregion

    //region Constructors

    /**
     * Creates a Resource card front face, of the Insect Kingdom,
     * without any associated point, with the specified corners.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * player when the card is placed on the playing board.
     */
    protected InsectResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    /**
     * Creates a Resource card front face, of the Insect Kingdom, with the
     * specified corners and the specified points.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected InsectResourceFrontFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft, CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft, points);
    }

    //endregion

    //region Overrides

    @Override
    public Kingdom getKingdom() {
        return Kingdom.INSECT_KINGDOM;
    }

    //endregion
}