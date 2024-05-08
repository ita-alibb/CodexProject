package it.polimi.ingsw.am52.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ClientSettings {

    //region Public Final Fields

    /**
     * The minimum allowed port number.
     */
    public static final int PORT_MIN = ServerSettings.PORT_MIN;

    /**
     * The maximum allowed port number.
     */
    public static final int PORT_MAX = ServerSettings.PORT_MAX;

    /**
     * The default graphical mode of the client.
     */
    public static final ClientMode DEFAULT_CLIENT_MODE = ClientMode.GRAPHICAL;

    /**
     * The default network mode of the client.
     */
    public static final NetworkMode DEFAULT_NETWORK_MODE = NetworkMode.SOCKET;

    //endregion

    //region Private Fields

    /**
     * The ip address of the server to connect with.
     */
    private final String serverIp;
    /**
     * The number of port for the connection.
     */
    private final int port;

    /**
     * The running mode of the client (GUI or TUI)
     */
    private final ClientMode mode;

    /**
     * The network mode (Socket or RMI).
     */
    private final NetworkMode networkMode;

    //endregion

    //region Constructor

    /**
     * Creates an object with the specified client settings.
     * @param ip The ip address of the server to connect with.
     * @param port The number of port used for the connection.
     * @param mode The running mode of the client (GUI or TUI).
     * @param network The network mode (Socket or RMI).
     * @throws IllegalArgumentException If the port number is outside the allowed range.
     */
    public ClientSettings(String ip, int port, ClientMode mode, NetworkMode network) throws IllegalArgumentException {

        if (port < PORT_MIN || port > PORT_MAX) {
            throw new IllegalArgumentException(
                    String.format("The port value %d is outside the allowed range [%d,%d].",
                            port, PORT_MIN, PORT_MAX)
            );
        }

        this.serverIp = ip;
        this.port = port;
        this.mode = mode;
        this.networkMode = network;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The ip address of the server where to connect.
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     *
     * @return The number of port for the connection.
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return The client mode: Graphical or Textual.
     */
    public ClientMode getMode() {
        return mode;
    }

    /**
     *
     * @return The network: Socket (json) or RMI.
     */
    public NetworkMode getNetworkMode() {
        return networkMode;
    }


    //endregion

    //region Public Static Methods

    /**
     * Load the client setting from a json file.  The json text should
     * have the following fields (case-sensitive):
     * <ul>
     *     <li>"serverId": integer, the maximum number of concurrent games (min=1).</li>
     *     <li>"port": integer, the port number bound by the server (min=1024, max=65535).</li>
     *     <li>"mode": string, the verbosity level for logging ("verbose", "info", "warning", "error").</li>
     *     <li>"network": string, the network connection ("socket", or "rmi")</li>
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
    public static ClientSettings loadJsonFile(String fileName) throws IOException {
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
    public static ClientSettings loadJsonFile(Path filePath) throws IOException{
        // Parse json (Jackson).
        return parseFromJson(Files.readString(filePath));
    }

    /**
     * Parse a json text and return the ClientSettings object. The json text should
     * have the following fields (case-sensitive):
     * <ul>
     *     <li>"serverIp": string, the ip address of the server (or "localhost").</li>
     *     <li>"port": integer, the port number where the server is listening for connection (min=1024, max=65535).</li>
     *     <li>"mode": string, the graphical mode ("gui", "tui").</li>
     *     <li>"network": string, the network connection to use ("socket", "rmi")</li>
     * </ul>
     * The fields "serverIp" and "port" are mandatory, "mode" and "network" are optional.
     * If an optional field is missing, or it has an invalid value, the default value of that setting
     * is used.
     * @param json The json text with the client settings.
     * @return The object containing the specified client settings.
     * @throws IllegalArgumentException If "port" or "serverIp" fields are missing or invalid.
     * @throws JsonProcessingException If the text is not a valid json-formatted text.
     * @implNote The json text can have duplicate fields and in that case the value assigned for
     * the duplicated setting is one of the specified values. If the json text has extra fields,
     * they are ignored.
     * @author Livio B.
     */
    public static ClientSettings parseFromJson(String json) throws JsonProcessingException, IllegalArgumentException {

        // Create the object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Get the json node object.
        JsonNode jsonNode = objectMapper.readTree(json);

        // Get the serverId value (can throw an exception). The value is not validated.
        final String serverIp = getSereverIpValueFromJsonNode(jsonNode);

        // Get the port number value (can throw an exception). The value is not validated.
        final int port = getPortValueFromJsonNode(jsonNode);

        // Set all settings to default values, they may be overwritten by values
        // in the json object.
        ClientMode mode = DEFAULT_CLIENT_MODE;   // Def. graphical mode
        NetworkMode network = DEFAULT_NETWORK_MODE;  // Def. network mode.

        // Get the iterator able to iterate over all json fields, and overwrite settings, if found.
        Iterator<String> iter = jsonNode.fieldNames();
        // Iterate over all json fields.
        while (iter.hasNext()) {
            // The field text string.
            final String field = iter.next();
            // Switch the text of the filed: the valid filed names are
            // "mode" and "network".
            // Invalid field names are ignored.
            switch (field) {
                case "mode":
                    // Get ClientMode value from json, or use default value.
                    mode = parseMode(jsonNode.get(field).asText());
                    break;
                case "network":
                    // Get NetworkMode value from json, or use default value.
                    network = parseNetwork(jsonNode.get(field).asText());
                    break;
                default:
                    // Ignore invalid (or additional) field names.
                    break;
            }
        }

        // Return the client settings object with specified values.
        return new ClientSettings(serverIp, port, mode, network);
    }

    //endregion

    /**
     * Get the server ip (String) from a json node object, representing the client settings.
     * @param node The json node object.
     * @return The string representing the ip address of the server.
     */
    private static String getSereverIpValueFromJsonNode(JsonNode node) {
        // The name of the server ip field in the json object.
        final String fieldName = "serverIp";

        // Check if there is the field.
        if (node.has(fieldName)) {
            try {
                // Return the field value as text.
                return node.get(fieldName).asText();
            } catch (Exception e) {
                // In case the asText() method throws, rethrow an exception
                throw new IllegalArgumentException(
                        String.format("Illegal value for field %s in json client settings.", fieldName)
                );
            }
        }

        // If there isn't the server ip field, throws an exception.
        throw new IllegalArgumentException(
                String.format("Field %s not found in json client settings.", fieldName)
        );
    }

    /**
     * Get the port number (int) from a json node object, representing the client settings.
     * @param node The json node object.
     * @return The integer representing the port number.
     */
    private static int getPortValueFromJsonNode(JsonNode node) {
        // The name of the port field in the json object.
        final String fieldName = "port";

        // Check if there is the field.
        if (node.has(fieldName)) {
            try {
                // Return the field value as int.
                return node.get(fieldName).asInt();
            } catch (Exception e) {
                // In case the asText() method throws, rethrow an exception
                throw new IllegalArgumentException(
                        String.format("Illegal value for field %s in json client settings.", fieldName)
                );
            }
        }

        // If there isn't the port field, throws an exception.
        throw new IllegalArgumentException(
                String.format("Field %s not found in json client settings.", fieldName)
        );
    }

    /**
     * Convert a string into a ClientMode enum value. If the string cannot
     * be converted, the DEF_CLIENT_MODE is returned. The valid string values are
     * (case-sensitive):
     * <ul>
     *     <li>"gui"</li>
     *     <li>"tui"</li>
     * </ul>
     * @param value The text to parse.
     * @return The ClientMode value.
     * @author Livio B.
     */
    private static ClientMode parseMode(String value) {
        try {
            // Delegate the parsing stuff to the VerbosityLevel enum.
            return ClientMode.parse(value);
        } catch (Exception e) {
            // On parsing error, return default client graphical mode.
            return DEFAULT_CLIENT_MODE;
        }
    }

    /**
     * Convert a string into a NetworkMode enum value. If the string cannot
     * be converted, the DEF_NETWORK_MODE is returned. The valid string values are
     * (case-sensitive):
     * <ul>
     *     <li>"socket"</li>
     *     <li>"rmi"</li>
     * </ul>
     * @param value The text to parse.
     * @return The NetworkMode value.
     * @author Livio B.
     */
    private static NetworkMode parseNetwork(String value) {
        try {
            // Delegate the parsing stuff to the VerbosityLevel enum.
            return NetworkMode.parse(value);
        } catch (Exception e) {
            // On parsing error, return default client network mode.
            return DEFAULT_NETWORK_MODE;
        }
    }
}
