package it.polimi.ingsw.modelTests.jsonTests.placeStarterCardRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the PlaceStarterCardRequest class.
 */
public class PlaceStarterCardRequestTest
{
    /**
     * Test for the serialization of PlaceStarterCardRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 4;
        // The lobby id.
        final int lobbyId = 1223;

        // The method name.
        final String methodName = JsonDeserializer.PLACE_STARTER_CARD_METHOD;

        // Place card data.
        final int cardId = 81;
        final String face = "front";

        // Create the data object of the request.
        PlaceCardData data = new PlaceCardData(cardId, face);

        // Create the request object.
        PlaceStarterCardRequest request = new PlaceStarterCardRequest(playerId, lobbyId, data);
        String jsonText = null;

        // Serialize the request object.
        try {
            jsonText = request.toJson();
        } catch (JsonProcessingException e) {
            assert(false);
        }

        // Deserialize the json text, in order to check if there are
        // all fields and their values.
        JsonNode jsonNode = getJsonNode(jsonText);

        // Check there are four and only four fields, named "playerId", lobbyId",
        // "method", and "data".
        checkNodeFieldNames(jsonNode,
                JsonDeserializer.PLAYER_ID_FIELD,
                JsonDeserializer.LOBBY_ID_FIELD,
                JsonDeserializer.METHOD_FIELD,
                JsonDeserializer.DATA_FIELD);

        // Check the method is "placeStarterCard".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);
        // Check the value of the "playerId" field.
        checkNodeFiledIntValue(jsonNode, JsonDeserializer.PLAYER_ID_FIELD, playerId);
        // Check the value of the "lobbyId" field.
        checkNodeFiledIntValue(jsonNode, JsonDeserializer.LOBBY_ID_FIELD, lobbyId);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there are two and only two fields "cardId" and "face".
        checkNodeFieldNames(dataNode, "cardId", "face");

        // Check the filed values.
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledStringValue(dataNode, "face", face);

    }

    /**
     * Test for the deserialization of PlaceStarterCardRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceStarterCard01Test() {
        // This file has the following json text:
        // {
        //  "method": "placeStarterCard",
        //  "playerId": 1,
        //  "lobbyId": 136,
        //  "data": {
        //    "cardId": 85,
        //    "face": "back"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeStarterCardRequest";
        final String jsonFileName = "placeStarterCard01.json";
        final Path jsonFilePath = Path.of(path, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Read the text from file.
        String jsonText = null;
        try {
            jsonText = Files.readString(jsonFilePath);
        } catch (IOException e) {
            assert(false);
        }

        // Parse the json text.
        try {
            PlaceStarterCardRequest request = (PlaceStarterCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.PLACE_STARTER_CARD_METHOD, request.getMethod());
            assertEquals(1, request.getPlayerId());
            assertEquals(136, request.getLobbyId());
            assertEquals(85, request.getData().getCardId());
            assertEquals("back", request.getData().getFace());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of PlaceStarterCardRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceStarterCard02Test() {
        // This file has the following json text:
        // {
        //  "method": "placeStarterCard",
        //  "playerId": 1,
        //  "lobbyId": 136,
        //  "data": {
        //    "face": "back"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeStarterCardRequest";
        final String jsonFileName = "placeStarterCard02.json";
        final Path jsonFilePath = Path.of(path, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Read the text from file.
        String jsonText = null;
        try {
            jsonText = Files.readString(jsonFilePath);
        } catch (IOException e) {
            assert(false);
        }

        // Parse the json text.
        try {
            PlaceStarterCardRequest request = (PlaceStarterCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.PLACE_STARTER_CARD_METHOD, request.getMethod());
            assertEquals(1, request.getPlayerId());
            assertEquals(136, request.getLobbyId());
            assertEquals(-1, request.getData().getCardId());
            assertEquals("back", request.getData().getFace());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
