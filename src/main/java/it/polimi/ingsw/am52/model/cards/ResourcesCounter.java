package it.polimi.ingsw.am52.Model.cards;

/**
 * Immutable class that maintains the count of all available resources.
 */
public class ResourcesCounter {

    //region Private Fields

    /**
     * The count of plant resources.
     */
    private final int plantCount;
    /**
     * The count of animal resources.
     */
    private final int animalCount;
    /**
     * The count of fungi resources.
     */
    private final int fungiCount;
    /**
     * The count of insect resources.
     */
    private final int insectCount;

    //endregion

    //region Public Static Methods

    /**
     * Add together two resource counters and return a new resource
     * counter with the sum of each resource.
     * @param r1 The first resource counter.
     * @param r2 The second resource counter.
     * @return A new resource counter with the sum of each resource.
     */
    public static ResourcesCounter add(ResourcesCounter r1, ResourcesCounter r2) {
        return new ResourcesCounter(
                r1.getFungiCount() + r2.getFungiCount(), r1.getPlantCount() + r2.getPlantCount(),
                r1.getAnimalCount() + r2.getAnimalCount(),
                r1.getInsectCount() + r2.getInsectCount());
    }

    /**
     * Subtract together two resources counters and return a new resources
     * counter with the result of the calculation.
     * @param r1 The first resources counter.
     * @param r2 The second resources counter.
     * @return A new resources counter with the result of subtracting the second
     * counter from the first counter.
     */
    public static ResourcesCounter subtract(ResourcesCounter r1, ResourcesCounter r2) {
        return new ResourcesCounter(
                r1.getFungiCount() - r2.getFungiCount(), r1.getPlantCount() - r2.getPlantCount(),
                r1.getAnimalCount() - r2.getAnimalCount(),
                r1.getInsectCount() - r2.getInsectCount());
    }

    //endregion

    //region Constructors

    /**
     * Creates a resource counter with no-resources.
     */
    public ResourcesCounter() {
        this(0, 0, 0, 0);
    }

    /**
     * Creates a resource counter with the counter of the specified resource
     * set to one (1), and all other counter set to zero (0).
     * @param resource The resource with its counter set to one (1).
     */
    public ResourcesCounter(Resource resource) {
        this.plantCount = resource == Resource.PLANT ? 1 : 0;
        this.animalCount = resource == Resource.ANIMAL ? 1 : 0;
        this.fungiCount = resource == Resource.FUNGI ? 1 : 0;
        this.insectCount = resource == Resource.INSECT ? 1 : 0;
    }

    /**
     * Creates a resource counter with the specified resources.
     *
     * @param fungi   The count of fungi resources.
     * @param plants  The count of plant resources.
     * @param animals The count of animal resources.
     * @param insects The count of insect resources.
     */
    public ResourcesCounter(int fungi, int plants, int animals, int insects) {
        this.plantCount = plants;
        this.animalCount = animals;
        this.fungiCount = fungi;
        this.insectCount = insects;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The count of plant resources.
     */
    public int getPlantCount() {
        return this.plantCount;
    }

    /**
     *
     * @return The count of animal resources.
     */
    public int getAnimalCount() {
        return this.animalCount;
    }

    /**
     *
     * @return The count of fungi resources.
     */
    public int getFungiCount() {
        return this.fungiCount;
    }

    /**
     *
     * @return The count of insect resources.
     */
    public int getInsectCount() {
        return this.insectCount;
    }

    //endregion

    //region ToString


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ResourcesCounter r)) {
            return false;
        }
        return (r.getAnimalCount() == this.getAnimalCount() &&
                r.getPlantCount() == this.getPlantCount() &&
                r.getInsectCount() == this.getInsectCount() &&
                r.getFungiCount() == this.getFungiCount()
        );
    }

    @Override
    public String toString() {
        return String.format("[Fungi: %d; Plants: %d; Animals: %d; Insects: %d]", 
                getFungiCount(), getPlantCount(), getAnimalCount(), getInsectCount());
    }

    //endregion
}
