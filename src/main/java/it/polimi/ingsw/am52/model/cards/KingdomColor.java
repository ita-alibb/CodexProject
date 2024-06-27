package it.polimi.ingsw.am52.model.cards;

/**
 * Each kingdom has an associated color. This enum contains
 * all available colors.
 */
public enum KingdomColor {
    /**
     * The color of the Plant kingdom.
     */
    GREEN,
    /**
     * The color of the Animal kingdom.
     */
    BLUE,
    /**
     * The color of the Fungi kingdom.
     */
    RED,
    /**
     * The color of the Insect kingdom.
     */
    VIOLET;

    /**
     * Static method to convert to an integer a KingdomColor object
     * @return The int value
     */
    public String toString() {
        return switch (this) {
            case GREEN -> "green";
            case BLUE -> "blue";
            case RED -> "red";
            case VIOLET -> "violet";
        };
    }
}
