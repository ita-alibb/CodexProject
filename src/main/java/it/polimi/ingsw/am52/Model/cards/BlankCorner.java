package it.polimi.ingsw.am52.Model.cards;

/**
 * Immutable class that represents a visible card corner with no associated
 * resource or item.
 */
public class BlankCorner extends CardCorner {

    //region Static final Fields

    /**
     * The blank corner.
     */
    public static final BlankCorner BLANK_CORNER = new BlankCorner();

    //endregion

    //region Constructor

    /**
     * Creates a card corner without any associated resource or item.
     */
    private BlankCorner() {

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
        return new ItemsCounter();
    }

    //endregion
}
