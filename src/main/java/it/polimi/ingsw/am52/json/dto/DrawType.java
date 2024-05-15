package it.polimi.ingsw.am52.json.dto;

/**
 * The DrawType enum
 */
public enum DrawType {
    /**
     * The type of card to take Resource
     */
    RESOURCE,
    /**
     * The type of card to take GOLD.
     */
    GOLD;

    /**
     * Static method to convert an integer to a DrawType object
     * @param i The index of the type of cards
     * @return The object in the position i
     */
    public static DrawType fromInteger(int i) {
        return switch (i) {
            case 0 -> RESOURCE;
            case 1 -> GOLD;
            default -> null;
        };
    }
}
