package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;

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
     * The name of the field used to specify data of the request.
     */
    public static final String DATA_FIELD = "data";

    /**
     * The label of the login method.
     */
    public static final String JOIN_LOBBY_METHOD = "joinLobby";
    /**
     * The label of the login method.
     */
    public static final String LIST_LOBBY_METHOD = "listLobby";
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
    /**
     * The label of the initGame method
     */
    public static final String INIT_GAME_METHOD = "initGame";
    /**
     * The label of the endGame method
     */
    public static final String END_GAME_METHOD = "endGame";

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
    public static JsonMessage deserializeRequest(String jsonText) throws IOException {
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


    public static JsonMessage<BaseResponseData> deserializeResponse(String jsonResponse) throws IOException {
        // Create the object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialize into a json node object.
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Get the iterator to iterate over all field names.
        Iterator<String> iter = jsonNode.fieldNames();
        // Iterate over all names.
        while (iter.hasNext()) {
            // Check for field "method".
            if (iter.next().equals(METHOD_FIELD)) {
                // Deserialize based on the method.
                return deserializeResponse(jsonNode, jsonNode.get(METHOD_FIELD).asText());
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
    private static JsonMessage deserializeRequest(JsonNode jsonNode, String method) throws IOException {
        // Get an ObjectMapper for deserialization.
        ObjectMapper objectMapper = new ObjectMapper();

        // Switch on method (to select the type of the request data).
        return switch (method) {
            case JOIN_LOBBY_METHOD -> {
                JoinLobbyData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), JoinLobbyData.class);
                yield new JoinLobbyRequest(data);
            }
            case LIST_LOBBY_METHOD -> {
                ListLobbyData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), ListLobbyData.class);
                yield new ListLobbyRequest(data);
            }
            case CREATE_LOBBY_METHOD -> {
                CreateLobbyData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), CreateLobbyData.class);
                yield new CreateLobbyRequest(data);
            }
            case INIT_GAME_METHOD -> {
                InitGameData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), InitGameData.class);
                yield new InitGameRequest(data);
            }
            case LEAVE_GAME_METHOD -> {
                LeaveGameData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), LeaveGameData.class);
                yield new LeaveGameRequest(data);
            }
            case SELECT_OBJECTIVE_METHOD -> {
                SelectObjectiveData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), SelectObjectiveData.class);
                yield new SelectObjectiveRequest(data);
            }
            case PLACE_STARTER_CARD_METHOD -> {
                PlaceStarterCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceStarterCardData.class);
                yield new PlaceStarterCardRequest(data);
            }
            case PLACE_CARD_METHOD -> {
                PlaceCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceCardData.class);
                yield new PlaceCardRequest(data);
            }
            case DRAW_CARD_METHOD -> {
                DrawCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), DrawCardData.class);
                yield new DrawCardRequest(data);
            }
            case TAKE_CARD_METHOD -> {
                TakeCardData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), TakeCardData.class);
                yield new TakeCardRequest(data);
            }
            case END_GAME_METHOD -> {
                EndGameData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), EndGameData.class);
                yield new EndGameRequest(data);
            }
            // Unknown method.
            default -> throw new IOException(String.format("Unknown method \"%s\".", method));
        };
    }

    /**
     * Deserialize the jsonNode into the appropriate java response object.
     * @param jsonNode The json node to deserialize.
     * @param method The method of the request.
     * @return The java object of the request.
     * @throws IOException If an error occurs in the deserialization process.
     */
    private static JsonMessage<BaseResponseData> deserializeResponse(JsonNode jsonNode, String method) throws IOException {
        // Get an ObjectMapper for deserialization.
        ObjectMapper objectMapper = new ObjectMapper();

        // Switch on method (to select the type of the request data).
        return switch (method) {
            case JOIN_LOBBY_METHOD -> {
                JoinLobbyResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), JoinLobbyResponseData.class);
                yield new JoinLobbyResponse(data);
            }
            case CREATE_LOBBY_METHOD -> {
                JoinLobbyResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), JoinLobbyResponseData.class);
                yield new CreateLobbyResponse(data);
            }
            case LIST_LOBBY_METHOD -> {
                ListLobbyResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), ListLobbyResponseData.class);
                yield new ListLobbyResponse(data);
            }
            case INIT_GAME_METHOD -> {
                InitGameResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), InitGameResponseData.class);
                yield new InitGameResponse(data);
            }
            case LEAVE_GAME_METHOD -> {
                LeaveGameResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), LeaveGameResponseData.class);
                yield new LeaveGameResponse(data);
            }
            case SELECT_OBJECTIVE_METHOD -> {
                SelectObjectiveResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), SelectObjectiveResponseData.class);
                yield new SelectObjectiveResponse(data);
            }
            case PLACE_STARTER_CARD_METHOD -> {
                PlaceStarterCardResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceStarterCardResponseData.class);
                yield new PlaceStarterCardResponse(data);
            }
            case PLACE_CARD_METHOD -> {
                PlaceCardResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), PlaceCardResponseData.class);
                yield new PlaceCardResponse(data);
            }
            case DRAW_CARD_METHOD -> {
                DrawCardResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), DrawCardResponseData.class);
                yield new DrawCardResponse(data);
            }
            case TAKE_CARD_METHOD -> {
                TakeCardResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), TakeCardResponseData.class);
                yield new TakeCardResponse(data);
            }
            case END_GAME_METHOD -> {
                EndGameResponseData data = objectMapper.readValue(jsonNode.get(DATA_FIELD).toString(), EndGameResponseData.class);
                yield new EndGameResponse(data);
            }
            //TODO: add new responses;

            // Unknown method.
            default -> throw new IOException(String.format("Unknown method \"%s\".", method));
        };
    }

    //endregion
}
