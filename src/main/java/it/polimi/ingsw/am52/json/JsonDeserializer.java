package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.core.JsonProcessingException;
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
            if (iter.next().equals("method")) {
                // Deserialize based on the method.
                return deserializeRequest(jsonNode, jsonNode.get("method").asText());
            }
        }
        // Field "method" not found in json.
        throw new IOException("Field \"method\" not found in json object.");
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
            // Cases with only "method" and "data" fields (inherit form ClientRequest).
            case "login":
            case "createLobby":
                return deserializeClientRequest(jsonNode, method);

            // Cases with also "playerId" and "lobbyId" fields (inherit from PlayerRequest).
            case "leaveGame":
            case "selectObjective":
            case "placeStarterCard":
            case "placeCard":
            case "drawCard":
            case "takeCard":
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
        // Init them.
        int playerId = -1;
        int lobbyId = -1;
        try {
            // Get them from the json node.
            playerId = jsonNode.get("playerId").asInt();
            lobbyId = jsonNode.get("lobbyId").asInt();
        } catch (Exception ex) {
            // Throws on error.
            throw new IOException("Missing field \"playerId\" and/or \"lobbyId\" in the player request json text.");
        }

        // Get an ObjectMapper for deserialization.
        ObjectMapper objectMapper = new ObjectMapper();

        // Switch on method (to select the type of the request data).
        return switch (method) {
            case "leaveGame" -> {
                LeaveGameData data = objectMapper.readValue(jsonNode.get("data").toString(), LeaveGameData.class);
                yield new LeaveGameRequest(playerId, lobbyId, data);
            }
            case "selectObjective" -> {
                SelectObjectiveData data = objectMapper.readValue(jsonNode.get("data").toString(), SelectObjectiveData.class);
                yield new SelectObjectiveRequest(playerId, lobbyId, data);
            }
            case "placeStarterCard" -> {
                PlaceCardData data = objectMapper.readValue(jsonNode.get("data").toString(), PlaceCardData.class);
                yield new PlaceStarterCardRequest(playerId, lobbyId, data);
            }
            case "placeCard" -> {
                PlaceCardData data = objectMapper.readValue(jsonNode.get("data").toString(), PlaceCardData.class);
                yield new PlaceCardRequest(playerId, lobbyId, data);
            }
            case "drawCard" -> {
                DrawCardData data = objectMapper.readValue(jsonNode.get("data").toString(), DrawCardData.class);
                yield new DrawCardRequest(playerId, lobbyId, data);
            }
            case "takeCard" -> {
                TakeCardData data = objectMapper.readValue(jsonNode.get("data").toString(), TakeCardData.class);
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
            case "login" -> {
                LoginData data = objectMapper.readValue(jsonNode.get("data").toString(), LoginData.class);
                yield new LoginRequest(data);
            }
            case "createLobby" -> {
                CreateLobbyData data = objectMapper.readValue(jsonNode.get("data").toString(), CreateLobbyData.class);
                yield new CreateLobbyRequest(data);
            }
            // Unknown method.
            default -> throw new IOException(String.format("Unknown method \"%s\".", method));
        };
    }

    //endregion
}
