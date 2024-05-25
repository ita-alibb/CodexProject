package it.polimi.ingsw.am52.model.cards;


/**
 * The side of the card face, front or back side.
 */
public enum CardSide {
    /**
     * The front side of the card.
     */
    FRONT,
    /**
     * The back side of the card.
     */
    BACK;

    /**
     * Static method to convert an integer to a CardSide object
     * @param i The index
     * @return The object in the position i
     */
    public static CardSide fromInteger(int i) {
        return switch (i) {
            case 0 -> FRONT;
            case 1 -> BACK;
            default -> null;
        };
    }

    /**
     * Static method to convert to an integer a CardSide object
     * @return The int value
     */
    public int toInteger() {
        return switch (this) {
            case FRONT -> 0;
            case BACK -> 1;
        };
    }
}
