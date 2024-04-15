package it.polimi.ingsw.am52.settings;

import java.util.ArrayList;
import java.util.List;

public class CmdLineArgsReader {

    //region Public Static Final Fields

    /**
     * The minimum allowed port number.
     */
    public static final int PORT_MIN = 1024;

    /**
     * The maximum allowed port number.
     */
    public static final int PORT_MAX = 65035;

    public static final ClientMode DEFAULT_CLIENT_MODE = ClientMode.GRAPHICAL;
    public static final NetworkMode DEFAULT_NETWORK_MODE = NetworkMode.SOCKET;

    //endregion

    //region Public Static Methods

    /**
     * Processes the command line arguments and return an instance of
     * the application settings, configured with the specified settings.
     * @param args The array of string representing the command line arguments.
     * @return The application startup settings.
     * @throws IllegalArgumentException If there are invalid format or options.
     */
    public static CmdLineArgs readCmdLineArgs(String[] args) {
        // Check if the user required to show the help, without running the
        // application. The -h/--help flags shall be the only flag in
        // the command line args.
        if (args.length == 1 &&
                (args[0].equals("-h") || args[0].equals("--help"))) {
            return CmdLineArgs.getShowHelpSettings();
        }

        return new CmdLineArgs(readSettings(args));
    }

    //endregion

    //region Private Static Methods

    /**
     * Processes the command line arguments and return an instance of
     * the application settings, configured with the specified settings.
     * @param args The array of string representing the command line arguments.
     * @return The application settings.
     * @throws IllegalArgumentException If there are invalid format or options.
     */
    private static ApplicationSettings readSettings(String[] args) {
        // Get the list of params.
        List<String> params = readParams(args);

        // Create the list of options. The options are all elements
        // in args[], after the initial params.
        List<String> options = new ArrayList<>();
        for (int i = params.size(); i != args.length; i++ ) {
            options.add(args[i]);
        }

        // Check number of params:
        // 0,1 = Server Mode
        // 2 = Client Mode
        // > 2 = error
        return switch (params.size()) {
            case 0, 1 -> readServerSettings(params, options);
            case 2 -> readClientSettings(params, options);
            default -> throw new IllegalArgumentException(
                    String.format("Too many input arguments (%d).", params.size()));
        };
    }

    private static ApplicationSettings readServerSettings(List<String> params, List<String> options) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param params The arguments entered for the application.
     * @param options The options entered at the command line, after the initial params.
     * @return The application startup settings.
     * @throws IllegalArgumentException If there are invalid format or options.
     */
    private static ApplicationSettings readClientSettings(List<String> params, List<String> options) {

        // Note: I don't check the length of the params list, because this is a private method
        //       and I assume it is always called with params.size() == 2.

        // The ip address (it is a string in the settings class).
        final String ip = params.getFirst();

        // Parse the value of the port number.
        int port = parsePortNumber(params.getLast());

        // Default settings, these are app settings if no options
        // are entered at the command line.
        ClientMode  clientMode = DEFAULT_CLIENT_MODE;
        NetworkMode networkMode = DEFAULT_NETWORK_MODE;

        // Parse all remaining options.
        for (String option : options) {
            // Validate option flag.
            if (!validateFlag(option, Mode.CLIENT)) {
                throw new IllegalArgumentException(
                        String.format("Illegal option %s for the application (mode %s).",
                                option, Mode.CLIENT)
                );
            }

            // Switch option flag.
            switch (option) {
                // -r/--rmi: enable RMI network.
                case "-r":
                case "--rmi":
                    networkMode = NetworkMode.RMI;
                    break;
                // -t/--tui: run in textual mode.
                case "-t":
                case "--tui":
                    clientMode = ClientMode.TEXTUAL;
                    break;
            }
        }

        // Return the client settings.
        return new ApplicationSettings(new ClientSettings(ip, port, clientMode, networkMode));
    }

    /**
     *
     * @param portText The text of the argument representing the port number.
     * @return The value of the port number.
     * @throws IllegalArgumentException If teh value of the port number is outside the allowed range
     * (from 1024 to 65035).
     */
    private static int parsePortNumber(String portText) {
        // The port number.
        int port;
        try {
            // Parse the text, I expect an integer value.
            port = Integer.parseInt(portText);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    String.format("Invalid value \"%s\" for argument \"port\"", portText)
            );
        }

        // Check if the port number is in the allowed range.
        if (port >= PORT_MIN && port <= PORT_MAX) {
            return port;
        }

        // The port number is outside the allowed range.
        throw new IllegalArgumentException(
                String.format("Port number %d out of valid range [%d, %d]",
                        port, PORT_MIN, PORT_MAX)
        );
    }

    /**
     * Processes the command line arguments and return the list of parameters.
     * The parameters are the initial args that do not begin with "-" or "--".
     * @param args The array of string representing the command line arguments.
     * @return The list of parameters.
     */
    private static List<String> readParams(String[] args) {
        // Create an empty list, that will be populated with the
        // parameters.
        List<String> params = new ArrayList<>();

        // Iterate starting from the first arg, and take only the args
        // that do NOT begin with "-" or "--", breaking at the first
        // arg that begins with "-" or "--" (or at the end of the array).
        for (String candidate : args) {
            // If arg begins with "-" or "--", then exit the loop.
            if (candidate.startsWith("-") || candidate.startsWith("--")) {
                break;
            }
            params.add(candidate);
        }
        //Return the list.
        return params;
    }

    /**
     * Check if the option flag is a valid flag for the application,
     * depending on the running mode (Server or Client).
     * @param flag The flag string, in the "-x" (short) or "--xyz" (long) format.
     * @param mode The running mode of the application.
     * @return True if the flag is a valid flag, false otherwise.
     */
    private static boolean validateFlag(String flag, Mode mode) {
        // Switch on the running mode, then call the appropriate validation method.
        return switch (mode) {
            // Run as server:
            case SERVER -> validateServerFlag(flag);
            // Run as client:
            case CLIENT -> validateClientFlag(flag);
        };
    }

    private static boolean validateServerFlag(String flag) {
        throw new UnsupportedOperationException();
    }

    /**
     * Check if the option flag is a valid flag for the client app.
     * @param flag The flag string, in the "-x" (short) or "--xyz" (long) format.
     * @return True if the flag is a valid flag, false otherwise.
     */
    private static boolean validateClientFlag(String flag) {
        return switch (flag) {
            case "-r", "--rmi", "-t", "--tui" -> true;
            default -> false;
        };
    }

    //endregion
}
