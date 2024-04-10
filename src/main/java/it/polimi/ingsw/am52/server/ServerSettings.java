package it.polimi.ingsw.am52.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * The settings of the server.
 * @author Livio B.
 */
public class ServerSettings {

    //region Private Fields

    /**
     * The number of maximum concurrency games.
     */
    private final int maxLobbies;
    /**
     * The number of the port.
     */
    private final int port;
    /**
     * The level of verbosity for logging.
     */
    private final VerbosityLevel verbosity;

    //endregion

    //region Public Static Final Fields

    /**
     * The default number of maximum concurrency games.
     */
    public static final int DEF_MAX_LOBBIES = 1000;
    /**
     * The default port number.
     */
    public static final int DEF_PORT = 2000;
    /**
     * The default verbosity level.
     */
    public static final VerbosityLevel DEF_VERBOSITY = VerbosityLevel.INFO;

    //endregion

    //region Private Static Fields

    /**
     * The server settings with all settings set to their default values.
     */
    private static final ServerSettings defaultSettings = new ServerSettings(DEF_MAX_LOBBIES, DEF_PORT, DEF_VERBOSITY);

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
     * @param json The json text with the server settings.
     * @return The object containing the specified server settings.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @implNote The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
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
        int port = DEF_PORT;    // Def. server port number
        VerbosityLevel verbosity = DEF_VERBOSITY;   // Def. server logging verbosity

        // Get the iterator able to iterate over all json fields, and overwrite settings, if found.
        Iterator<String> iter = jsonNode.fieldNames();
        // Iterate over all json fields.
        while (iter.hasNext()) {
            // The field text string.
            final String field = iter.next();
            // Switch the text of the filed: the valid filed names are
            // "maxLobbies", "port", and "verbosity". Invalid field names
            // are ignored. The values of maxLobbies and port are just
            // converted to integer values (or their default values) without
            // any check on valid values, because all values are validate inside the
            // constructor of this class.
            switch (field) {
                case "maxLobbies":
                    // Get maxLobbies value from json, or use default value.
                    maxLobbies = jsonNode.get(field).asInt(DEF_MAX_LOBBIES);
                    break;
                case "port":
                    // Get port value from json, or use default value.
                    port = jsonNode.get(field).asInt(DEF_PORT);
                    break;
                case "verbosity":
                    // Parse the verbosity value of the "verbosity" field.
                    verbosity = parseVerbosity(jsonNode.get(field).asText());
                    break;
                default:
                    // Ignore invalid (or additional) field names.
                    break;
            }
        }

        // Return the object with the settings. The constructor validates the
        // parameters passed to it.
        return new ServerSettings(maxLobbies, port, verbosity);
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
     * @param fileName The name of the file to load.
     * @return The object containing the server settings specified n the file.
     * @throws IOException If the file does not exist.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @implNote The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
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
     * @param filePath The path of the file to load.
     * @return The object containing the server settings specified n the file.
     * @throws IOException If the file does not exist.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @implNote The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
     * @author Livio B.
     */
    public static ServerSettings loadJsonFile(Path filePath) throws IOException{
        // Parse json (Jackson).
        return ServerSettings.parseFromJson(Files.readString(filePath));
    }

    //endregion

    // region Constructor

    /**
     * Create an object with the specified server settings.
     * @param maxLobbies The maximum number of concurrency games on the server. If the
     *                   specified value is less than 1, the DEF_MAX_LOBBIES value is assigned.
     * @param port The number of port used for the socket connection. If the value is less than
     *             1024 or greater than 65535, the DEF_PORT value is assigned.
     * @param verbosity The verbosity level for logging.
     * @author Livio B.
     */
    public ServerSettings(int maxLobbies, int port, VerbosityLevel verbosity) {
        this.maxLobbies = (maxLobbies < 1) ? DEF_MAX_LOBBIES : maxLobbies;
        this.port = (port < 1024 || port > 65535) ? DEF_PORT : port;
        this.verbosity = verbosity;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The maximum number of concurrent games on the server.
     * @author Livio B.
     */
    public int getMaxLobbies() {
        return maxLobbies;
    }

    /**
     *
     * @return The port bound to the server.
     * @author Livio B.
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return The verbosity level for logging.
     * @author Livio B.
     */
    public VerbosityLevel getVerbosity() {
        return verbosity;
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
     * Convert a string to an integer that represents the port number bound by
     * the server. If the string is not a valid integer, or the value is
     * less than 1024 or greater than 65535, the DEF_PORT value is returned.
     * @param value The text to parse.
     * @return The integer parsed from text, representing the port number bound by
     *         the server.
     * @author Livio B.
     */
    private static int parsePortNumber(String value) {
        try {
            int port = Integer.parseInt(value);
            return (port < 1024 || port > 65535 ) ? DEF_PORT : port;
        } catch (Exception e) {
            return DEF_PORT;
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

    //endregion

    //region Overrides

    /**
     *
     * @return The text representation of this server settings instance, formatted as:
     * <ul>
     *         [setting1:value1;setting2:value2;...]
     * </ul>
     * @author Livio B.
     */
    @Override
    public String toString() {
        return String.format("[maxLobbies:%d;port:%d;verbosity:%s]", this.maxLobbies, this.port, this.verbosity);
    }

    //endregion
}
