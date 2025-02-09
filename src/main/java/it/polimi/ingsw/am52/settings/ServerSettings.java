package it.polimi.ingsw.am52.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * The settings of the server application.
 * @author Livio B.
 */
public class ServerSettings {

    //region Private Fields

    /**
     * The number of maximum concurrency games.
     */
    private final int maxLobbies;
    /**
     * The number of the socket port.
     */
    private final int socketPort;
    /**
     * The number of the rmi port.
     */
    private final int rmiPort;
    /**
     * The level of verbosity for logging.
     */
    private final VerbosityLevel verbosity;

    /**
     * The mode used to select the port number for the connection.
     */
    private final PortMode portMode;

    //endregion

    //region Public Static Final Fields

    /**
     * The minimum allowed port number.
     */
    public static final int PORT_MIN = 1024;

    /**
     * The maximum allowed port number.
     */
    public static final int PORT_MAX = 65535;

    /**
     * The default socket port number.
     */
    public static final int DEF_SOCKET_PORT = 1024;

    /**
     * The default rmi port number.
     */
    public static final int DEF_RMI_PORT = 1025;

    /**
     * The maximum allowed "maximum number" of concurrent games
     */
    public static final int MAX_LOBBIES = 10000;

    /**
     * The minimum allowed "maximum number" of concurrent games
     */

    public static final int MIN_LOBBIES = 1;

    /**
     * The default number of maximum concurrency games.
     */
    public static final int DEF_MAX_LOBBIES = 1000;
    /**
     * The default verbosity level.
     */
    public static final VerbosityLevel DEF_VERBOSITY = VerbosityLevel.INFO;

    /**
     * The default mode for selecting the port for the connection.
     */
    public static final PortMode DEF_PORT_MODE = PortMode.AUTO;

    //endregion

    //region Private Static Fields

    /**
     * The server settings with all settings set to their default values.
     */
    private static final ServerSettings defaultSettings = new ServerSettings(DEF_MAX_LOBBIES, DEF_VERBOSITY);

    //endregion

    //region Public Static Methods

    /**
     *
     * @return The default settings of the server.
     * @author Livio B.
     */
    public static ServerSettings defaultSettings() {
        return defaultSettings;
    }

    /**
     * Parse a json text and return the ServerSettings object. The json text should
     * have the following fields (case-sensitive):
     * <ul>
     *     <li>"maxLobbies": integer, the maximum number of concurrent games (min=1).</li>
     *     <li>"port": integer, the port number bound by the server (min=1024, max=65535).</li>
     *     <li>"verbosity": string, the verbosity level for logging ("verbose", "info", "warning", "error").</li>
     * </ul>
     * If a field is missing, or it has an invalid value, the default value of that setting
     * is used.
     * <P>
     * The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
     * @param json The json text with the server settings.
     * @return The object containing the specified server settings.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @author Livio B.
     */
    public static ServerSettings parseFromJson(String json) throws JsonProcessingException {

        // Create the object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Get the json node object.
        JsonNode jsonNode = objectMapper.readTree(json);

        // Set all settings to default values, they may be overwritten by values
        // in the json object.
        int maxLobbies = DEF_MAX_LOBBIES;   // Def. max lobbies
        int socketPort = DEF_SOCKET_PORT;   // Def. port number for socket
        int rmiPort = DEF_RMI_PORT;         // Def. port number for rmi
        VerbosityLevel verbosity = DEF_VERBOSITY;   // Def. server logging verbosity
        PortMode portMode = DEF_PORT_MODE;  // Def. port mode.

        // Get the iterator able to iterate over all json fields, and overwrite settings, if found.
        Iterator<String> iter = jsonNode.fieldNames();
        // Iterate over all json fields.
        while (iter.hasNext()) {
            // The field text string.
            final String field = iter.next();
            // Switch the text of the filed: the valid filed names are
            // "maxLobbies", "port", "verbosity", and "portMode".
            // Invalid field names are ignored. The values of maxLobbies and port are just
            // converted to integer values (or their default values) without
            // any check on valid values, because all values are validate inside the
            // constructor of this class.
            switch (field) {
                case "maxLobbies":
                    // Get maxLobbies value from json, or use default value.
                    maxLobbies = jsonNode.get(field).asInt(DEF_MAX_LOBBIES);
                    break;
                case "socketPort":
                    // Get port value from json, or use default value.
                    socketPort = jsonNode.get(field).asInt(DEF_SOCKET_PORT);
                    break;
                case "rmiPort":
                    // Get port value from json, or use default value.
                    rmiPort = jsonNode.get(field).asInt(DEF_RMI_PORT);
                    break;
                case "verbosity":
                    // Parse the verbosity value of the "verbosity" field.
                    verbosity = parseVerbosity(jsonNode.get(field).asText());
                    break;
                case "portMode":
                    // Parse the port mode value of the "portMode" field.
                    portMode = parsePortMode(jsonNode.get(field).asText());
                default:
                    // Ignore invalid (or additional) field names.
                    break;
            }
        }

        // If the port number has NOT been set, and the port mode has been set to AUTO.
        return new ServerSettings(maxLobbies, socketPort, rmiPort, verbosity, portMode);
    }

    /**
     * Load the server setting from a json file.  The json text should
     * have the following fields (case-sensitive):
     * <ul>
     *     <li>"maxLobbies": integer, the maximum number of concurrent games (min=1).</li>
     *     <li>"port": integer, the port number bound by the server (min=1024, max=65535).</li>
     *     <li>"verbosity": string, the verbosity level for logging ("verbose", "info", "warning", "error").</li>
     * </ul>
     * If a field is missing, or it has an invalid value, the default value of that setting
     ** is used.
     * <P>
     * The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
     * @param fileName The name of the file to load.
     * @return The object containing the server settings specified n the file.
     * @throws IOException If the file does not exist.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @author Livio B.
     */
    public static ServerSettings loadJsonFile(String fileName) throws IOException {
        // Create a Path object with the file name and invoke the overload that
        // takes the path.
        return loadJsonFile(Path.of(fileName));
    }

    /**
     * Load the server setting from a json file.  The json text should
     * have the following fields (case-sensitive):
     * <ul>
     *     <li>"maxLobbies": integer, the maximum number of concurrent games (min=1).</li>
     *     <li>"port": integer, the port number bound by the server (min=1024, max=65535).</li>
     *     <li>"verbosity": string, the verbosity level for logging ("verbose", "info", "warning", "error").</li>
     * </ul>
     * If a field is missing, or it has an invalid value, the default value of that setting
     ** is used.
     * <P>
     * The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
     * @param filePath The path of the file to load.
     * @return The object containing the server settings specified n the file.
     * @throws IOException If the file does not exist.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @author Livio B.
     */
    public static ServerSettings loadJsonFile(Path filePath) throws IOException{
        // Parse json (Jackson).
        return parseFromJson(Files.readString(filePath));
    }

    //endregion

    // region Constructors

    /**
     * Create an object with the specified server settings. The port number is not specified and
     * the port mode is AUTO.
     * @param maxLobbies The maximum number of concurrency games on the server. If the
     *                   specified value is less than 1, the DEF_MAX_LOBBIES value is assigned.
     * @param verbosity The verbosity level for logging.
     * @author Livio B.
     */
    public ServerSettings(int maxLobbies, VerbosityLevel verbosity) {
        this.maxLobbies = (maxLobbies < MIN_LOBBIES || maxLobbies > MAX_LOBBIES) ? DEF_MAX_LOBBIES : maxLobbies;
        this.socketPort = DEF_SOCKET_PORT;
        this.rmiPort = DEF_RMI_PORT;
        this.verbosity = verbosity;
        this.portMode = PortMode.AUTO;
    }

    /**
     * Create an object with the specified server settings.
     * @param maxLobbies The maximum number of concurrency games on the server. If the
     *                   specified value is less than 1, the DEF_MAX_LOBBIES value is assigned.
     * @param socketPort The number of port used for the socket connection. If the value is less than
     *                   1024 or greater than 65535, the DEF_SOCKET_PORT value is assigned.
     * @param rmiPort The number of port used for the socket connection. If the value is less than
     *      *         1024 or greater than 65535, the DEF_RMI_PORT value is assigned.
     * @param verbosity The verbosity level for logging.
     * @param portMode The mode used to select the port number for the connection.
     * @author Livio B.
     */
    public ServerSettings(int maxLobbies, int socketPort, int rmiPort, VerbosityLevel verbosity, PortMode portMode) {

        this.maxLobbies = (maxLobbies < MIN_LOBBIES || maxLobbies > MAX_LOBBIES) ? DEF_MAX_LOBBIES : maxLobbies;
        this.socketPort = (socketPort < ServerSettings.PORT_MIN || socketPort > ServerSettings.PORT_MAX) ? DEF_SOCKET_PORT : socketPort;
        this.rmiPort = (rmiPort < ServerSettings.PORT_MIN || rmiPort > ServerSettings.PORT_MAX) ? DEF_RMI_PORT : rmiPort;
        this.verbosity = verbosity;
        this.portMode = portMode;

        if (this.portMode == PortMode.FIXED &&
                (this.socketPort == this.rmiPort)) {
            throw new IllegalArgumentException(
                    String.format("Rmi port (%d) and Socket port (%d) cannot be equal in port mode %s.",
                            this.rmiPort, this.socketPort, this.portMode)
            );
        }
    }

    //endregion

    //region Getters

    /**
     *
     * @return The maximum number of concurrent games on the server.
     * @author Livio B.
     */
    public int getMaxLobbies() {
        return this.maxLobbies;
    }

    /**
     *
     * @return The port number for socket connection.
     * @author Livio B.
     */
    public int getSocketPort() {
        return this.socketPort;
    }

    /**
     *
     * @return The port number for rmi connection.
     * @author Livio B.
     */
    public int getRmiPort() {
        return this.rmiPort;
    }

    /**
     *
     * @return The verbosity level for logging.
     * @author Livio B.
     */
    public VerbosityLevel getVerbosity() {
        return this.verbosity;
    }

    /**
     *
     * @return The mode used to select the port number for the connection.
     * @author Livio B.
     */
    public PortMode getPortMode() {
        return  this.portMode;
    }

    //endregion

    //region Private Static Methods

    /**
     * Convert a string to an integer that represents the max number of concurrent
     * games on the server. If the string is not a valid integer, or the value is
     * less than 1, the DEF_MAX_LOBBIES value is returned.
     * @param value The text to parse.
     * @return The integer parsed from text, representing the max number of concurrent
     *         games on the server.
     * @author Livio B.
     */
    private static int parseMaxLobbies(String value) {
        try {
            int maxLobbies = Integer.parseInt(value);
            return (maxLobbies < 1 ) ? DEF_MAX_LOBBIES : maxLobbies;
        } catch (Exception e) {
            return DEF_MAX_LOBBIES;
        }
    }

    /**
     * Convert a string to an integer that represents the socket port number bound by
     * the server. If the string is not a valid integer, or the value is
     * less than 1024 or greater than 65535, the DEF_SOCKET_PORT value is returned.
     * @param value The text to parse.
     * @return The integer parsed from text, representing the port number bound by
     *         the server.
     * @author Livio B.
     */
    private static int parseSocketPortNumber(String value) {
        return parsePortNumber(value, DEF_SOCKET_PORT);
    }

    /**
     * Convert a string to an integer that represents the rmi port number bound by
     * the server. If the string is not a valid integer, or the value is
     * less than 1024 or greater than 65535, the DEF_RMI_PORT value is returned.
     * @param value The text to parse.
     * @return The integer parsed from text, representing the port number bound by
     *         the server.
     * @author Livio B.
     */
    private static int parseRmiPortNumber(String value) {
        return parsePortNumber(value, DEF_RMI_PORT);
    }

    /**
     * Convert a string to an integer that represents the port number bound by
     * the server. If the string is not a valid integer, or the value is
     * less than 1024 or greater than 65535, the default value is returned.
     * @param value The text to parse.
     * @param defaultValue The default value.
     * @return The integer parsed from text, representing the port number bound by
     *         the server.
     * @author Livio B.
     */
    private static int parsePortNumber(String value, int defaultValue) {
        try {
            int port = Integer.parseInt(value);
            return (port < ServerSettings.PORT_MIN || port > ServerSettings.PORT_MAX ) ? defaultValue : port;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Convert a string into a VerbosityLevel enum value. If the string cannot
     * be converted, the DEF_VERBOSITY is returned. The valid string values are
     * (case-sensitive):
     * <ul>
     *     <li>"verbose"</li>
     *     <li>"info"</li>
     *     <li>"warning"</li>
     *     <li>"error"</li>
     * </ul>
     * @param value The text to parse.
     * @return The verbosity level value.
     * @author Livio B.
     */
    private static VerbosityLevel parseVerbosity(String value) {
        try {
            // Delegate the parsing stuff to the VerbosityLevel enum.
            return VerbosityLevel.parse(value);
        } catch (Exception e) {
            // On parsing error, return default server verbosity level.
            return DEF_VERBOSITY;
        }
    }

    /**
     * Convert a string into a POrtMode enum value. If the string cannot
     * be converted, the DEF_MODE_PORT is returned. The valid string values are
     * (case-sensitive):
     * <ul>
     *     <li>"auto"</li>
     *     <li>"fixed"</li>
     * </ul>
     * @param value The text to parse.
     * @return The port mode value.
     * @author Livio B.
     */
    private static PortMode parsePortMode(String value) {
        try {
            // Delegate the parsing stuff to the VerbosityLevel enum.
            return PortMode.parse(value);
        } catch (Exception e) {
            // On parsing error, return default server verbosity level.
            return DEF_PORT_MODE;
        }
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The text representation of this server settings instance, formatted as:
     * <ul>
     *         <li>[setting1:value1;setting2:value2;...]</li>
     * </ul>
     * @author Livio B.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Server settings:%n"));
        sb.append(String.format("  Max lobbies: %d%n", getMaxLobbies()));
        sb.append(String.format("  Socket Port: %s%n", getSocketPort()));
        sb.append(String.format("  RMI Port: %s%n", getRmiPort()));
        sb.append(String.format("  Port mode: %s%n", getPortMode()));
        sb.append(String.format("  Log verbosity: %s%n", getVerbosity()));

        return sb.toString();
    }

    //endregion
}