package it.polimi.ingsw.am52.settings;

/**
 * The network connection mode.
 */
public enum NetworkMode {
    /**
     * Socket mode.
     */
    SOCKET,
    /**
     * RMI mode.
     */
    RMI;

    public static NetworkMode parse(String value) {
        return switch (value.toLowerCase()) {
            case "socket" -> NetworkMode.SOCKET;
            case "rmi" -> NetworkMode.RMI;
            default -> throw new IllegalArgumentException();
        };
    }
}
