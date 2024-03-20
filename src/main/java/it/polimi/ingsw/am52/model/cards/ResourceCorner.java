package it.polimi.ingsw.am52.model.cards;

/**
 * Immutable class that represents a card corner with an associated resource.
 * You can get an instance of the desired corner by calling the appropriate
 * static getter method.
 */
public class ResourceCorner extends CardCorner {

    //region Private Fields.

    /**
     * The resource associated to this card corner.
     */
    private final Resource resource;

    //endregion

    //region Static final Fields

    /**
     * The plant corner.
     */
    public static final ResourceCorner PLANT_CORNER = new ResourceCorner(Resource.PLANT);

    /**
     * The animal corner.
     */
    public static final ResourceCorner ANIMAL_CORNER = new ResourceCorner(Resource.ANIMAL);

    /**
     * The fungi corner.
     */
    public static final ResourceCorner FUNGI_CORNER = new ResourceCorner(Resource.FUNGI);

    /**
     * The insect corner.
     */
    public static final ResourceCorner INSECT_CORNER = new ResourceCorner(Resource.INSECT);

    //endregion

    //region Constructor

    /**
     * Creates a card corner with the specified resource.
     * @param resource The resource associated to the corner.
     */
    private ResourceCorner(Resource resource) {
        this.resource = resource;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The resource associated to this card corner.
     */
    public Resource getResource() {
        return this.resource;
    }

    //endregion

    //region ResourcesProvider Override

    @Override
    public ResourcesCounter getResources() {
        return new ResourcesCounter(getResource());
    }

    //endregion

    //region ItemsProvider Override

    @Override
    public ItemsCounter getItems() {
        return new ItemsCounter();
    }

    //endregion
}
