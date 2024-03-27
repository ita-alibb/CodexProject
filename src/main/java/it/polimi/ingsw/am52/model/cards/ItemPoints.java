package it.polimi.ingsw.am52.Model.cards;

/**
 * Gives the player a number of points depending of the number visible items
 * on the playing board.
 */
public class ItemPoints extends CardPoints {

    /**
     * The specific item for the point calculation.
     */
    private final Item item;

    /**
     * The bonus points for each item.
     */
    public static final int ITEM_BONUS = 1;

    /**
     * An object that calculates the gained points based on the number
     * of visible feathers on the playing board.
     */
    public static final ItemPoints FEATHER_POINTS = new ItemPoints(Item.FEATHER);

    /**
     * An object that calculates the gained points based on the number
     * of visible inks on the playing board. This class cannot be instantiated, you can get
     * the available objects are available by public static (final) fields.
     */
    public static final ItemPoints INK_POINTS = new ItemPoints(Item.INK);

    /**
     * An object that calculates the gained points based on the number
     * of visible vellum on the playing board.
     */
    public static final ItemPoints VELLUM_POINTS = new ItemPoints(Item.VELLUM);

    /**
     * Private constructor. Creates an object that calculates the points based on the
     * number of visible instances (on the playing board) of the specified item.
     * @param item The item used to calculate points.
     */
    private ItemPoints(Item item) {
        this.item = item;
    }

    /**
     * The gained points depend on the number of visible items (associated to this card)
     * on the playing board.
     * @param items All visible items available on the playing board.
     * @return The number of points gained by the player when the cad is placed on the
     * playing board.
     */     
    @Override
    public int calculatePoints(ItemsProvider items, int linkedCorners) {

        // Initialize the visible items to zero.
        int nItems = switch (this.item) {
            case FEATHER -> // feathers
                    items.getItems().getFeatherCount();
            case INK -> // inks
                    items.getItems().getInkCount();
            case VELLUM -> // vellum
                    items.getItems().getVellumCount();
        };

        // Get the number of the desired item.

        // Calculate the gained points, by multiplying the number
        // of visible points to the bonus points.
        return ITEM_BONUS * nItems;
    }
    
}
