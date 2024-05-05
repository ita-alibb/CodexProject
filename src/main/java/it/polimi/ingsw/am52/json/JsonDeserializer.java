package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;

/**
 * Utility class with static methods for deserialization
 * of json requests or responses.
 * @author Livio B.
 */
public class JsonDeserializer {

    //region Public Static Final Fields

    /**
     * The name of the filed used to specify the method in the json request / response.
     */
    public static final String METHOD_FIELD = "method";

    /**
     * The name of the field used to specify the id of player.
     */
    public static final String PLAYER_ID_FIELD = "playerId";

    /**
     * The name of the field used to specify the id of the lobby.
     */
    public static final String LOBBY_ID_FIELD = "lobbyId";

    /**
     * The name of the field used to specify data of the request.
     */
    public static final String DATA_FIELD = "data";

    /**
     * The label of the login method.
     */
    public static final String JOIN_LOBBY_METHOD = "joinLobby";
    /**
     * The label of the createLobby method.
     */
    public static final String CREATE_LOBBY_METHOD = "createLobby";
    /**
     * The label of the leaveGame method.
     */
    public static final String LEAVE_GAME_METHOD = "leaveGame";
    /**
     * The label of the selectObjective method.
     */
    public static final String SELECT_OBJECTIVE_METHOD = "selectObjective";
    /**
     * The label of the placeStarterCard method.
     */
    public static final String PLACE_STARTER_CARD_METHOD = "placeStarterCard";
    /**
     * The label of the placeCard method.
     */
    public static final String PLACE_CARD_METHOD = "placeCard";
    /**
     * The label of the drawCard method.
     */
    public static final String DRAW_CARD_METHOD = "drawCard";
    /**
     * The label of the takeCard method.
     */
    public static final String TAKE_CARD_METHOD = "takeCard";

    //endregion

    //region Public Static Methods

    /**
     * Parse a json text representing a request from the client and return the appropriate
     * java object.
     * @param jsonText The json text of the request.
     * @return The java object of the request.
     * @throws IOException If an error occur in the deserialization process.
     * @author Livio B.
     */
    public static ClientRequest deserializeRequest(String jsonText) throws IOException {
        // Create the object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialize into a json node object.
        JsonNode jsonNode = objectMapper.readTree(jsonText);

        // Get the iterator to iterate over all field names.
        Iterator<String> iter = jsonNode.fieldNames();
        // Iterate over all names.
        while (iter.hasNext()) {
            // Check for field "method".
            if (iter.next().equals(METHOD_FIELD)) {
                // Deserialize based on the method.
                return deserializeRequest(jsonNode, jsonNode.get(METHOD_FIELD).asText());
            }
        }
        // Field "method" not found in json.
        throw new IOException(String.format("Field \"{%s\" not found in json object.", METHOD_FIELD));
    }

    //endregion

    //region Private Static Methods

    /**
     * Deserialize the jsonNode into the appropriate java request object.
     * @param jsonNode The json node to deserialize.
     * @param method The method of the request.
     * @return The java object of the request.
     * @throws IOException If an error occurs in the deserialization process.
     */
    private static ClientRequest deserializeRequest(JsonNode jsonNode, String method) throws IOException {

        // Switch on method of the request.
        switch (method) {
            // Cases with only "method" and DATA_FIELD fields (inherit form ClientRequest).
            case JOIN_LOBBY_METHOD:
            case CREATE_LOBBY_METHOD:
                return deserializeClientRequest(jsonNode, method);

            // Cases with also "playerId" and "lobbyId" fields (inherit from PlayerRequest).
            case LEAVE_GAME_METHOD:
            case SELECT_OBJECTIVE_METHOD:
            case PLACE_CARD_METHOD:
            case PLACE_STARTER_CARD_METHOD:
            case DRAW_CARD_METHOD:
            case TAKE_CARD_METHOD:
                return deserializePlayerRequest(jsonNode, method);
            // Unknown method.
            default:
                throw new IOException(String.format("Unknown method \"%s\".", method));
        }
    }

    /**
     * Parse requests that inherit from PlayerRequest, that have"playerId" and "lobbyId" fields.
     * @param jsonNode The json node to deserialize.
     * @param method The method of the request.
     * @return The request object.
     * @throws IOException If an error occurs during the deserialization process.
     */
    private static ClientRequest deserializePlayerRequest(JsonNode jsonNode, String method) throws IOException {

        // Get the "playerId" and "lobbyId" fields' values.
        int playerId , lobbyId;
        try {
            // Get them from the json node.
            playerId = jsonNode.get(PLAYER_ID_FIELD).asInt();
            lobbyId = jsonNode.get(LOBBY_ID_FIELD).asInt();
        } catch (Exception ex) {
            // Throws on error.
            throw new IOException(
                    String.format("Missing field \"%s\" and/or \"%s\" in the player request json text.",
                            PLAYER_ID_FIELD, LOBBY_ID_FIELD));
        }

        // Get an ObjectMapper for deserialization.
        ObjectMapper objectMapper = new ObjectMapper();

        // Switch on method (to select the type of the request data).
        return switch (method) {
            case LEAVE_GAME_METHOD -> {
                LeaveGameData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), LeaveGameData.class);
                yield new LeaveGameRequest(playerId, lobbyId, data);
            }
            case SELECT_OBJECTIVE_METHOD -> {
                SelectObjectiveData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), SelectObjectiveData.class);
                yield new SelectObjectiveRequest(playerId, lobbyId, data);
            }
            case PLACE_STARTER_CARD_METHOD -> {
                PlaceCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceCardData.class);
                yield new PlaceStarterCardRequest(playerId, lobbyId, data);
            }
            case PLACE_CARD_METHOD -> {
                PlaceCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceCardData.class);
                yield new PlaceCardRequest(playerId, lobbyId, data);
            }
            case DRAW_CARD_METHOD -> {
                DrawCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), DrawCardData.class);
                yield new DrawCardRequest(playerId, lobbyId, data);
            }
            case TAKE_CARD_METHOD -> {
                TakeCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), TakeCardData.class);
                yield new TakeCardRequest(playerId, lobbyId, data);
            }
            // Unknown method.
            default -> throw new IOException(String.format("Unknown method \"%s\".", method));
        };
    }

    /**
     * Parse requests that inherit from ClientRequest, with only "data" field.
     * @param jsonNode The json node to deserialize.
     * @param method The method of the request.
     * @return The request object.
     * @throws IOException If an error occurs during the deserialization process.
     */
    private static ClientRequest deserializeClientRequest(JsonNode jsonNode, String method) throws IOException {
        // Get a ObjectMapper.
        ObjectMapper objectMapper = new ObjectMapper();

        // Switch on method (to select the type of the request data).
        return switch (method) {
            case JOIN_LOBBY_METHOD -> {
                JoinLobbyData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), JoinLobbyData.class);
                yield new JoinLobbyRequest(data);
            }
            case CREATE_LOBBY_METHOD -> {
                CreateLobbyData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), CreateLobbyData.class);
                yield new CreateLobbyRequest(data);
            }
            // Unknown method.
            default -> throw new IOException(String.format("Unknown method \"%s\".", method));
        };
    }

    //endregion
}
