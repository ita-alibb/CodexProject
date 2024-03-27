package it.polimi.ingsw.am52.model.objectives;

import it.polimi.ingsw.am52.exceptions.ObjectivesException;
import it.polimi.ingsw.am52.model.cards.ResourcesCounter;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;

/**
 * Find a pattern based on the number of visible resources on the
 * playing board.
 */
public class ResourcePattern extends PatternFinder {

    //region Public Static Final Fields

    /**
     * Find the pattern composed of three fungi.
     */
    public static final ResourcePattern FUNGI_RESOURCES = new ResourcePattern(new ResourcesCounter(3, 0, 0, 0));

    /**
     * Find the pattern composed of three plants.
     */
    public static final ResourcePattern PLANT_RESOURCES = new ResourcePattern(new ResourcesCounter(0, 3, 0, 0));

    /**
     * Find the pattern composed of three animals.
     */
    public static final ResourcePattern ANIMAL_RESOURCES = new ResourcePattern(new ResourcesCounter(0, 0, 3, 0));

    /**
     * Find the pattern composed of three insects.
     */
    public static final ResourcePattern INSECT_RESOURCES = new ResourcePattern(new ResourcesCounter(0, 0, 0, 3));

    //endregion

    //region Private Fields

    /**
     * The required resources for each pattern.
     */
    private final ResourcesCounter requiredResources;

    //endregion
    
    //region Constructors

    /**
     * Create a pattern finder that find all instances of the pattern
     * based on the required visible resources on the playing board.
     * @param requiredResources The required resources for each pattern.
     */
    private ResourcePattern(ResourcesCounter requiredResources) {
        // Initialize the private final field.
        this.requiredResources = requiredResources;
    }
    
    //endregion

    //region Overrides

    /**
     * Find the pattern based on the visible resources on the playing board.
     */
    @Override
    public int findPatterns(BoardInfo board) throws ObjectivesException {

        // Check if the resource counter is ok.
        if (hasNegativeItems(board.getResources())) {
            throw new ObjectivesException(
                    String.format("The playing board has negative resources: %s", board.getResources()));
        }

        // Initialize the counter of patterns to zero.
        int patternCount = 0;

        // Get available resources from the playing board.
        ResourcesCounter availableResources = board.getResources();

        // Each time I subtract the required resources from the available
        // resources, and the available remain not-negative, I found a pattern.
        while (true) {

            // Subtract required resources from the available resources.
            availableResources = ResourcesCounter.subtract(availableResources, this.requiredResources);

            // Check if available resources become negative.
            if (hasNegativeItems(availableResources)) {
                break;
            }

            // Not negative -> A new pattern is found!
            patternCount++;
        }

        // Return the counter of found patterns.
        return patternCount;
    }

    //endregion

    //region Private Static Methods

    /**
     * Check if the counter of any resource is negative.
     * @param items The items counter.
     * @return True if there is a negative item counter.
     */
    private static boolean hasNegativeItems(ResourcesCounter items) {

        return /* fungi */  (items.getFungiCount()) < 0 ||
               /* plants */ (items.getPlantCount()) < 0 ||
               /* animal */ (items.getAnimalCount()) < 0 ||
               /* insects */(items.getInsectCount()) < 0;
    }

    //endregion
}
