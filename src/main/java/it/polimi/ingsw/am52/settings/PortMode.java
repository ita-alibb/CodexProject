package it.polimi.ingsw.am52.settings;

/**
 * The mode used to find a port number for the connection.
 */
public enum PortMode {
    AUTO,
    FIXED;

    public static PortMode parse(String value) {
        return switch (value.toLowerCase()) {
            case "auto" -> PortMode.AUTO;
            case "fixed" -> PortMode.FIXED;
            default -> throw new IllegalArgumentException();
        };
    }
}
