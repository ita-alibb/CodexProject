package it.polimi.ingsw.am52.model.cards;

/**
 * Immutable class that represents a card corner with an associated item.
 * You can get an instance of the desired corner by calling the appropriate
 * static getter method.
 */
public class ItemCorner extends CardCorner {

    //region Private Fields.

    /**
     * The resource associated to this card corner.
     */
    private final Item item;

    //endregion

    //region Static final Fields

    /**
     * The feather corner.
     */
    public static final ItemCorner FEATHER_CORNER = new ItemCorner(Item.FEATHER);

    /**
     * The ink corner.
     */
    public static final ItemCorner INK_CORNER = new ItemCorner(Item.INK);

    /**
     * The vellum corner.
     */
    public static final ItemCorner VELLUM_CORNER = new ItemCorner(Item.VELLUM);

    //endregion

    //region Constructor

    /**
     * Creates a card corner with the specified item.
     * @param item The resource associated to the corner.
     */
    private ItemCorner(Item item) {
        this.item = item;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The resource associated to this card corner.
     */
    public Item getItem() {
        return this.item;
    }

    //endregion

    //region ResourcesProvider Override

    @Override
    public ResourcesCounter getResources() {
        return new ResourcesCounter();
    }

    //endregion

    //region ItemsProvider Override

    @Override
    public ItemsCounter getItems() {
        return new ItemsCounter(getItem());
    }

    //endregion

}
