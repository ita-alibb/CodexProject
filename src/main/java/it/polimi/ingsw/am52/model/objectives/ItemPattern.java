package it.polimi.ingsw.am52.model.objectives;

import it.polimi.ingsw.am52.model.cards.ItemsCounter;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;

/**
 * Find a pattern based on the number of visible items on the
 * playing board.
 */
public class ItemPattern extends PatternFinder {

    //region Public Static Final Fields

    /**
     * Find the pattern composed of all three items (1 Feather, 1 Ink, 1 Vellum).
     */
    public static final ItemPattern ALL_ITEMS = new ItemPattern(new ItemsCounter(1, 1, 1));

    /**
     * Find the pattern composed of two feathers.
     */
    public static final ItemPattern FEATHER_ITEMS = new ItemPattern(new ItemsCounter(2, 0, 0));

    /**
     * Find the pattern composed of two inks.
     */
    public static final ItemPattern INK_ITEMS = new ItemPattern(new ItemsCounter(0, 2, 0));

    /**
     * Find the pattern composed of two vellum.
     */
    public static final ItemPattern VELLUM_ITEMS = new ItemPattern(new ItemsCounter(0, 0, 2));

    //endregion

    //region Private Fields

    /**
     * The required items for each pattern.
     */
    private final ItemsCounter requiredItems;

    //endregion
    
    //region Constructors

    /**
     * Create a pattern finder that find all instances of the pattern
     * based on the required visible items on the playing board.
     * @param requiredItems The required items for each pattern.
     */
    private ItemPattern(ItemsCounter requiredItems) {
        // Initialize the private final field.
        this.requiredItems = requiredItems;
    }
    
    //endregion

    //region Overrides

    /**
     * Find the pattern based on the visible items on the playing board.
     */
    @Override
    public int findPatterns(BoardInfo board) {

        // Initialize the counter of patterns to zero.
        int patternCount = 0;

        // Get available items from the playing board.
        ItemsCounter availableItems = board.getItems();

        // Each time I subtract the required items from the available
        // items, and the available remain not-negative, I found a pattern.
        while (true) {

            // Subtract required items from the available items.
            availableItems = ItemsCounter.subtract(availableItems, this.requiredItems);

            // Check if available items become negative.
            if (hasNegativeItems(availableItems)) {
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
     * Check if the counter of any item is negative.
     * @param items The items counter.
     * @return True if there is a negative item counter.
     */
    private static boolean hasNegativeItems(ItemsCounter items) {

        return /* feathers */(items.getFeatherCount()) < 0 ||
               /* inks */    (items.getInkCount()) < 0 ||
               /* vellum */  (items.getVellumCount()) < 0;
    }

    //endregion
}
