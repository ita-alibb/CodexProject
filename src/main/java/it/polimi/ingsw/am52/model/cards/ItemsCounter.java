package it.polimi.ingsw.am52.model.cards;

/**
 * Immutable class that maintains the count of all available items.
 */
public class ItemsCounter {

    //region Private Fields

    /**
     * The count of feather items.
     */
    private final int featherCount;
    /**
     * The count of ink items.
     */
    private final int inkCount;
    /**
     * The count of vellum items.
     */
    private final int vellumCount;

    //endregion

    //region Static Add Method

    /**
     * Add together two items counters and return a new resource
     * counter with the sum of each resource.
     * @param i1 The first item counter.
     * @param i2 The second item counter.
     * @return A new item counter with the sum of each item.
     */
    public static ItemsCounter add(ItemsCounter i1, ItemsCounter i2) {
        return new ItemsCounter(
                i1.getFeatherCount() + i2.getFeatherCount(),
                i1.getInkCount() + i2.getInkCount(),
                i1.getVellumCount() + i2.getVellumCount()
        );
    }

    /**
     * Subtract together two items counters and return a new items
     * counter with the result of the calculation.
     * @param i1 The first items counter.
     * @param i2 The second items counter.
     * @return A new items counter with the result of subtracting the second
     * counter from the first counter.
     */
    public static ItemsCounter subtract(ItemsCounter i1, ItemsCounter i2) {
        return new ItemsCounter(
                i1.getFeatherCount() - i2.getFeatherCount(),
                i1.getInkCount() - i2.getInkCount(),
                i1.getVellumCount() - i2.getVellumCount()
        );
    }

    //endregion

    //region Constructors

    /**
     * Creates an items counter with no-items.
     */
    public ItemsCounter() {
        this(0, 0, 0);
    }

    /**
     * Creates an item counter with the counter of the specified item
     * set to one (1), and all other counter set to zero (0).
     * @param item The item with its counter set to one (1).
     */
    public ItemsCounter(Item item) {
        this.featherCount = item == Item.FEATHER ? 1 : 0;
        this.inkCount = item == Item.INK ? 1 : 0;
        this.vellumCount = item == Item.VELLUM ? 1 : 0;
    }

    /**
     * Creates an item counter with the specified items.
     * @param feathers The count of feather items.
     * @param inks The count of ink items.
     * @param vellum The count of vellum items.
     */
    public ItemsCounter(int feathers, int inks, int vellum) {
        this.featherCount = feathers;
        this.inkCount = inks;
        this.vellumCount = vellum;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The count of feather items.
     */
    public int getFeatherCount() {
        return this.featherCount;
    }

    /**
     *
     * @return The count of ink items.
     */
    public int getInkCount() {
        return this.inkCount;
    }

    /**
     *
     * @return The count of vellum items.
     */
    public int getVellumCount() {
        return this.vellumCount;
    }

    //endregion

    //region ToString

    @Override
    public String toString() {
        return String.format("[Feathers: %d; Inks: %d; Vellum: %d]", 
                getFeatherCount(), getInkCount(), getVellumCount());
    }

    //endregion
}
