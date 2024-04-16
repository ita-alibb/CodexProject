package it.polimi.ingsw.am52.settings;

import it.polimi.ingsw.am52.util.ImmutableList;

import java.util.*;

public class CmdLineArgsReader {

    //region Private Static Fields

    /**
     * The list of the options available for the server mode.
     */
    private static ImmutableList<Option> serverOptions = null;

    /**
     * The list of the options available for the client mode.
     */
    private static ImmutableList<Option> clientOptions = null;

    //endregion

    //region Public Static Methods

    /**
     *
     * @return The list of available options for the server mode.
     */
    public static ImmutableList<Option> getServerOptions() {

        if (serverOptions == null) {

            List<Option> options = new ArrayList<>();
            options.add(new AutoOption());
            options.add(new LimitOption());
            options.add(new VerbosityOption());
            options.add(new RmiOption());

            serverOptions = new ImmutableList<>(options);
        }

        return serverOptions;
    }

    /**
     *
     * @return The list of available options for the server mode.
     */
    public static ImmutableList<Option> getClientOptions() {

        if (clientOptions == null) {

            List<Option> options = new ArrayList<>();
            options.add(new TuiOption());
            options.add(new RmiOption());

            clientOptions = new ImmutableList<>(options);
        }

        return clientOptions;
    }

    /**
     * Processes the command line arguments and return an instance of
     * the application settings, configured with the specified settings.
     * To ask for help without running the application, the user must
     * enter only the help flag (-h/--help), without any parameter or
     * additional options.
     * <ul>Server args:
     *     <li>port: optional argument, the port number.</li>
     *     <li>-a/--auto: automatic selection of the port number. If the port number
     *     has been passed as an argument, the server first try to open the connection
     *     on the specified port number. If it is not available, the server automatically
     *     iterate in order to find an available port in the range [1024,65535]</li>
     *     <li>-r/--rmi: user RMI network communication.</li>
     *     <li>-l/-limit maxLobbies: limit the maximum number of concurrent games on the
     *     server to the value maxLobbies (>=1)</li>
     *     <li>-v/--verbosity lvl: set the verbosity of the server logging, to the value lvl,
     *     in range [1,4]</li>
     * </ul>
     * <ul>Client args:
     *     <li>ip: the ip address of the server (required).</li>
     *     <li>port: the port number (required).</li>
     *     <li>-t/--tui: run in textual mode, instead of default graphical mode</li>
     *     <li>-r/--rmi: user RMI network communication.</li>
     * </ul>
     * @param args The array of string representing the command line arguments.
     * @return The application startup settings.
     * @throws IllegalArgumentException If there are invalid format for options.
     * @author Livio B.
     */
    public static CmdLineArgs readCmdLineArgs(String[] args) {
        // Check if the user required to show the help, without running the
        // application. The -h/--help flags shall be the only flag in
        // the command line args.
        if (args.length == 1 &&
                (args[0].equals("-h") || args[0].equals("--help"))) {
            return CmdLineArgs.getShowHelpSettings();
        }

        // Process command line args for application's settings.
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
     * @author Livio B.
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

    /**
     *
     * @param params The arguments entered for the application.
     * @param options The options entered at the command line, after the initial params.
     * @return The application startup settings.
     * @throws IllegalArgumentException If there are invalid format or options.
     */
    private static ApplicationSettings readServerSettings(List<String> params, List<String> options) {

        // Note: I don't check the length of the params list, because this is a private method
        //       and I assume it is always called with params.size() == (0 or 1).

        // In server mode, there is an optional parameter, that is the desired port number
        // for the connection.
        OptionalInt portNumber =
                (params.size() == 1) ? // Is there an argument?
                OptionalInt.of(parsePortNumber(params.getFirst())) : // Yes, parse it for the port value
                OptionalInt.empty(); // No, assign empty value
        // If the port number is specified, assume port mode = "fixed", otherwise it is "auto".
        PortMode portMode =
                portNumber.isEmpty() ? PortMode.AUTO : PortMode.FIXED;

        // Default settings, these are the app settings if no options
        // are entered at the command line.
        int maxLobbies = ServerSettings.DEF_MAX_LOBBIES;
        NetworkMode networkMode = ServerSettings.DEF_NETWORK;
        VerbosityLevel verbosity = ServerSettings.DEF_VERBOSITY;

        // Parse all remaining options.
        for (int i = 0; i != options.size(); i++) {
            // Get the i-th option.
            String option = options.get(i);
            // Validate option flag.
            if (!validateFlag(option, Mode.SERVER)) {
                throw new IllegalArgumentException(
                        String.format("Illegal option %s for the application (mode %s).",
                                option, Mode.SERVER)
                );
            }

            // Switch option flag.
            switch (option) {
                // -r/--rmi: enable RMI network.
                case RmiOption.SHORT_FLAG:
                case RmiOption.LONG_FLAG:
                    networkMode = NetworkMode.RMI;
                    break;

                // -r/--rmi: enable RMI network.
                case AutoOption.SHORT_FLAG:
                case AutoOption.LONG_FLAG:
                    portMode = PortMode.AUTO;
                    break;

                // -l/--limit: the maximum number of concurrent lobbies.
                case LimitOption.SHORT_FLAG:
                case LimitOption.LONG_FLAG:
                    LimitOption limitOpt = new LimitOption();
                    // This option requires a parameter.
                    String maxLobbiesText = getOptionArgument(i, options, limitOpt);
                    maxLobbies = limitOpt.parseValueText(maxLobbiesText);
                    // Skip next option in the for loop (it was the argument).
                    i++;
                    break;

                // -v/--verbosity: the verbosity level for the server's log.
                case VerbosityOption.SHORT_FLAG:
                case VerbosityOption.LONG_FLAG:
                    VerbosityOption verbosityOpt = new VerbosityOption();
                    // This option requires a parameter.
                    String verbosityText = getOptionArgument(i, options, verbosityOpt);
                    verbosity = verbosityOpt.parseValueText(verbosityText);
                    // Skip next option in the for loop (it was the argument).
                    i++;
                    break;
            }
        }

        ServerSettings serverSettings =
                portNumber.isEmpty() ?
                        new ServerSettings(maxLobbies, verbosity, networkMode) :
                        new ServerSettings(maxLobbies, portNumber.getAsInt(), verbosity, networkMode, portMode);

        return new ApplicationSettings(serverSettings);
    }

    /**
     * If an option requires an argument, this method get the string next to the option flag
     * in the args list.
     * @param optionIndex The index of the args list.
     * @param options The list of all args.
     * @param option The option.
     * @return The text of the argument for the specified option.
     * @throws IllegalArgumentException If there isn't any string after next to the specified option.
     */
    private static String getOptionArgument(int optionIndex, List<String> options, Option option) {
        if ((optionIndex+1) >= options.size()) {
            throw new IllegalArgumentException(
                    String.format("The option %s/%s requires an argument.",
                            option.getShortFlag(), option.getLongFlag())
            );
        }
        return options.get(optionIndex+1);
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
        ClientMode  clientMode = ClientSettings.DEFAULT_CLIENT_MODE;
        NetworkMode networkMode = ClientSettings.DEFAULT_NETWORK_MODE;

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
                case RmiOption.SHORT_FLAG:
                case RmiOption.LONG_FLAG:
                    networkMode = NetworkMode.RMI;
                    break;
                // -t/--tui: run in textual mode.
                case TuiOption.SHORT_FLAG:
                case TuiOption.LONG_FLAG:
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
        if (port >= ServerSettings.PORT_MIN && port <= ServerSettings.PORT_MAX) {
            return port;
        }

        // The port number is outside the allowed range.
        throw new IllegalArgumentException(
                String.format("Port number %d out of valid range [%d, %d]",
                        port, ServerSettings.PORT_MIN, ServerSettings.PORT_MAX)
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
        return switch (flag) {
            case "-r", "--rmi", "-l", "--limit", "-a", "--auto", "-v", "--verbosity" -> true;
            default -> false;
        };
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
