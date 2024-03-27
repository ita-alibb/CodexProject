package it.polimi.ingsw.am52.model.cards;

import java.util.Objects;

/**
 * The kingdom of a card. There are four kingdoms, each with
 * its associated resource and color. A kingdom object can be
 * obtained by calling the appropriate static method of this class.
 */
public final class Kingdom {

    //region Private Fields.

    /**
     * The name of the kingdom.
     */
    private final String kingdomName;
    /**
     * The color associated to the kingdom.
     */
    private final KingdomColor color;
    /**
     * The resource associated to the kingdom.
     */
    private final Resource resource;

    /**
     * The plant kingdom.
     */
    public static final Kingdom PLANT_KINGDOM = new Kingdom("Plant", KingdomColor.GREEN, Resource.PLANT);

    /**
     * The animal kingdom.
     */
    public static final Kingdom ANIMAL_KINGDOM = new Kingdom("Animal", KingdomColor.BLUE, Resource.ANIMAL);

    /**
     * The fungi kingdom.
     */
    public static final Kingdom FUNGI_KINGDOM = new Kingdom("Fungi", KingdomColor.RED, Resource.FUNGI);

    /**
     * The insect kingdom.
     */
    public static final Kingdom INSECT_KINGDOM = new Kingdom("Insect", KingdomColor.VIOLET, Resource.INSECT);

    //endregion

    //region Constructor

    /**
     * Private constructor, creates a Kingdom instance with the specified
     * parameters.
     * @param kingdomName The kingdom name.
     * @param color The kingdom color.
     * @param resource The resource associated to the kingdom.
     */
    private Kingdom(String kingdomName, KingdomColor color, Resource resource) {
        this.kingdomName = kingdomName;
        this.color = color;
        this.resource = resource;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The name of this kingdom.
     */
    public String getKingdomName() {
        return this.kingdomName;
    }

    /**
     *
     * @return The color associated to this kingdom.
     */
    public KingdomColor getColor() {
        return this.color;
    }

    /**
     *
     * @return The resource associated to this kingdom.
     */
    public Resource getResource() {
        return this.resource;
    }

    //endregion

    //region Overrides

    /**
     * Compare this Kingdom object with another Kingdom object for equality.
     * @param other The object to compare with.
     * @return True if the other object is equal to this object, false otherwise.
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Kingdom k)) {
            return false;
        }
        return (k.getColor() == this.getColor()) &&
                (Objects.equals(k.getKingdomName(), this.getKingdomName())) &&
                (k.getResource() == this.getResource());
    }

    /**
     * Creates a hash code for this Kingdom object.
     * @return The hash code of this Kingdom object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(kingdomName, color, resource);
    }

    /**
     *
     * @return A text representation of this Kingdom object.
     */
    @Override
    public String toString() {
        return String.format("{%s Kingdom, %s, %s}", getKingdomName(), getColor(), getResource());
    }

    //endregion
}
