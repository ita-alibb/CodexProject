package it.polimi.ingsw.am52.settings;

/**
 * Represent the socket port option for server.
 */
public class SocketPortOption extends Option {

    public static final String SHORT_FLAG = "-s";
    public static final String LONG_FLAG = "--socketPort";

    public SocketPortOption() {
        super(SHORT_FLAG, LONG_FLAG, true);
    }

    /**
     * Parse the text of the socket argument, and return the number of the port
     * to use for the socket server. The text of the argument must be an integer
     * value, in the range [1024, 65535].
     * @param text The text to parse
     * @return The number of the port.
     * @throws IllegalArgumentException If the text is not formatted for an integer,
     * or the value is out of the allowed range.
     */
    @Override
    public Integer parseValueText(String text) throws IllegalArgumentException {

        try {
            // Try to parse the text as an integer.
            final int rmiPort = Integer.parseInt(text);

            // The text was an integer, so now check if the value is valid
            if (rmiPort < ServerSettings.PORT_MIN || rmiPort > ServerSettings.PORT_MAX) {
                throw new IllegalArgumentException(
                        String.format(
                                "Invalid value %d for RMI port number. Range is [%d, %d].",
                                rmiPort, ServerSettings.PORT_MIN, ServerSettings.PORT_MAX
                        )
                );
            }

            return rmiPort;

        } catch (NumberFormatException ex) {
            // The text does not represent an integer value.
            throw new IllegalArgumentException(
                    String.format("Invalid value \"%s\" for verbosity option.", text),ex
            );
        }
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: set socket port number for the server.",
                getShortFlag(), getLongFlag());
    }
}
