package it.polimi.ingsw.am52.model.objectives;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.am52.util.ImmutableList;
import it.polimi.ingsw.am52.model.decks.RandomDealer;
import it.polimi.ingsw.am52.exceptions.ObjectivesException;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;

/**
 * Represent an Objective card of the game. It is not part
 * of the Card class hierarchy because the objective cards are not
 * true playing cards (they are not placed on the playing board,
 * they are not drawn from a deck, ecc...), but they represent
 * just a game target that gives points to the player, if the
 * player is able to reach the target at the end of the ame.
 * You can get a list of all possible game objectives by calling
 * the static method getObjectives().
 */
public class Objective {

    //region Private Static Fields

    /**
     * The first index of the collection of available objectives.
     */
    private static final int START_INDEX = 0;


    /**
     * The last index of the collection of available objectives.
     */
    private static final int LAST_INDEX = 15;

    /**
     * The list of all available objectives.
     */
    private static ImmutableList<Objective> objectives = null;

    //endregion

    //region Private Fields

    /**
     * The numerical identifier of this objective.
     */
    private final int objectiveId;

    /**
     * Points gained for each pattern.
     */
    private final int bonusPoints;

    /**
     * The object able to find the pattern on the playing board.
     */
    private final PatternFinder finder;

    //endregion

    //region Public Static Methods

    /**
     * Get the objective that has the specified identifier. Each objective has its own numerical identifier.
     * @param objectiveId The objective identifier.
     * @return The objective that has the specified identifier.
     */
    public static Objective getObjectiveWithId(int objectiveId) {

        // Check if the card identifier is valid.
        if (objectiveId < START_INDEX || objectiveId > LAST_INDEX) {
            throw new ObjectivesException(
                    String.format("Id of objective out of bounds [%d,%d].", START_INDEX, LAST_INDEX)
            );
        }

        // Return the card with the specified identifier.
        return Objective.getObjectives().get(objectiveId - START_INDEX);
    }

    /**
     * The game has 16 different Objective cards. This method
     * returns a list of all objective, sorted as reported
     * in the ref. game manual.
     * @return The list of all 16 objectives available in the game.
     */
    public static ImmutableList<Objective> getObjectives() {

        // If the static field already exists, then return it.
        if (Objective.objectives != null) {
            return Objective.objectives;
        }

        // The static field does not exist, so I have to create it.

        // Create a new empty List of card faces.
        List<Objective> objectives = new ArrayList<>();

        // Populate the list with all objectives.

        /*
         * The comment at the beginning of each row is the page number of 
         * the corresponding Objective card in the ref. manual
         * (CODEX_cards_gold_front.pdf).
         */

        // 1) Four diagonal patterns, each with 2 bonus points.
        /*  87 */objectives.add(new Objective(0, 2, DiagonalPattern.FUNGI_DIAGONAL));
        /*  88 */objectives.add(new Objective(1, 2, DiagonalPattern.PLANT_DIAGONAL));
        /*  89 */objectives.add(new Objective(2, 2, DiagonalPattern.ANIMAL_DIAGONAL));
        /*  90 */objectives.add(new Objective(3, 2, DiagonalPattern.INSECT_DIAGONAL));

        // 2) Four tower patterns, each with 3 bonus points.
        /*  91 */objectives.add(new Objective(4, 3, TowerPattern.FUNGI_TOWER));
        /*  92 */objectives.add(new Objective(5, 3, TowerPattern.PLANT_TOWER));
        /*  93 */objectives.add(new Objective(6, 3, TowerPattern.ANIMAL_TOWER));
        /*  94 */objectives.add(new Objective(7, 3, TowerPattern.INSECT_TOWER));

        // 3) Four resource objectives, each with 2 bonus points.
        /*  95 */objectives.add(new Objective(8, 2, ResourcePattern.FUNGI_RESOURCES));
        /*  96 */objectives.add(new Objective(9, 2, ResourcePattern.PLANT_RESOURCES));
        /*  97 */objectives.add(new Objective(10, 2, ResourcePattern.ANIMAL_RESOURCES));
        /*  98 */objectives.add(new Objective(11, 2, ResourcePattern.INSECT_RESOURCES));

        // 4) Four item objectives.
        /*  99 */objectives.add(new Objective(12, 3, ItemPattern.ALL_ITEMS));
        /* 100 */objectives.add(new Objective(13, 2, ItemPattern.FEATHER_ITEMS));
        /* 100 */objectives.add(new Objective(14, 2, ItemPattern.INK_ITEMS));
        /* 100 */objectives.add(new Objective(15, 2, ItemPattern.VELLUM_ITEMS));

        // Save the reference into the static field, as an immutable list.
        Objective.objectives = new ImmutableList<>(objectives);

        // Return the static field.
        return Objective.objectives;

    }

    /**
     * Creates a random dealer that contains all objectives of the game.
     * @return The dealer of with all objectives.
     */
    public static RandomDealer<Objective> getObjectiveDealer() {
        // Create a new dealer instance with all objectives and return it.
        return new RandomDealer<>(getObjectives().toList());
    }

    /**
     * Creates a random dealer that contains all objectives of the game and use
     * an integer seed to instantiate the random generator, thus the dealer is
     * always initialized with its objectives in the same order.
     * @param seed The seed used to initialize the random generator.
     * @return The dealer of with all objectives.
     */
    public static RandomDealer<Objective> getObjectiveDealer(int seed) {
        // Create a new dealer instance with all objectives and return it.
        return new RandomDealer<>(getObjectives().toList(), seed);
    }

    //endregion

    //region Constructors

    /**
     * Instantiate an Objective that gives points based on the specified
     * pattern.
     * @param id The numerical identifier of the objective.
     * @param bonus The bonus point for each pattern found on the playing board.
     * @param finder The object able to find the pattern on the playing board.
     */
    private Objective(int id, int bonus, PatternFinder finder) {
        // Initialize the private final fields.
        this.objectiveId = id;
        this.bonusPoints = bonus;
        this.finder = finder;
    }

    //endregion

    //region Public Methods

    public int calculatePoints(BoardInfo board) {
        return this.finder.findPatterns(board) * this.bonusPoints;
    }

    //endregion

    //region Getters

    /**
     * 
     * @return The numerical identifier of this objective.
     */
    public int getObjectiveId() {
        return this.objectiveId;
    }

    /**
     * 
     * @return The bonus points for each pattern present on the playing board.
     */
    public int getBonusPoints() {
        return this.bonusPoints;
    }

    //endregion

}
