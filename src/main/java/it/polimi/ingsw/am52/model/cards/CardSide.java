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
     * Static method to convert an integer to a GameState object
     * @param i The index of the states of a game
     * @return The object in the position i
     */
    public static CardSide fromInteger(int i) {
        return switch (i) {
            case 0 -> FRONT;
            case 1 -> BACK;
            default -> null;
        };
    }
}
