package it.polimi.ingsw.modelTests.jsonTests.drawCardRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.request.DrawCardData;
import it.polimi.ingsw.am52.json.request.DrawCardRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the DrawCardRequest class.
 */
public class DrawCardRequestTest
{
    /**
     * Test for the serialization of DrawCardRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 4;
        // The lobby id.
        final int lobbyId = 1065;

        // The method name.
        final String methodName = JsonDeserializer.DRAW_CARD_METHOD;

        // Draw card data.
        final String deck = "resource";

        // Create the data object of the request.
        DrawCardData data = new DrawCardData(deck);

        // Create the request object.
        DrawCardRequest request = new DrawCardRequest(playerId, lobbyId, data);
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

        // Check the method is "selectObjective".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);
        // Check the value of the "playerId" field.
        checkNodeFiledIntValue(jsonNode, JsonDeserializer.PLAYER_ID_FIELD, playerId);
        // Check the value of the "lobbyId" field.
        checkNodeFiledIntValue(jsonNode, JsonDeserializer.LOBBY_ID_FIELD, lobbyId);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there is one and only one field "deck".
        checkNodeFieldNames(dataNode, "deck");

        // Check the filed values.
        checkNodeFiledStringValue(dataNode, "deck", deck);

    }

    /**
     * Test for the deserialization of DrawCardRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseDrawCard01Test() {
        // This file has the following json text:
        // {
        //  "method": "drawCard",
        //  "playerId": 2,
        //  "lobbyId": 89,
        //  "data": {
        //    "deck": "gold"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/drawCardRequest";
        final String jsonFileName = "drawCard01.json";
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
            DrawCardRequest request = (DrawCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.DRAW_CARD_METHOD, request.getMethod());
            assertEquals(2, request.getPlayerId());
            assertEquals(89, request.getLobbyId());
            assertEquals("gold", request.getData().getDeck());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of DrawCardRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseDrawCard02Test() {
        // This file has the following json text:
        // {
        //  "method": "drawCard",
        //  "playerId": 2,
        //  "lobbyId": 89,
        //  "data": {
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/drawCardRequest";
        final String jsonFileName = "drawCard02.json";
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
            DrawCardRequest request = (DrawCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.DRAW_CARD_METHOD, request.getMethod());
            assertEquals(2, request.getPlayerId());
            assertEquals(89, request.getLobbyId());
            assertEquals("", request.getData().getDeck());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
