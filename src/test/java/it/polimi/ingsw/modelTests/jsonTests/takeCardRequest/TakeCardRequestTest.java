package it.polimi.ingsw.modelTests.jsonTests.takeCardRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.TakeCardData;
import it.polimi.ingsw.am52.json.TakeCardRequest;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the TakeCardRequest class.
 */
public class TakeCardRequestTest
{
    /**
     * Test for the serialization of TakeCardRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 2;
        // The lobby id.
        final int lobbyId = 477;

        // The method name.
        final String methodName = "takeCard";

        // Take card data.
        final int cardId = 31;
        final String type = "resource";

        // Create the data object of the request.
        TakeCardData data = new TakeCardData(cardId, type);

        // Create the request object.
        TakeCardRequest request = new TakeCardRequest(playerId, lobbyId, data);
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

        // Check there are two and only two fields, named "method" and "data".
        checkNodeFieldNames(jsonNode, "playerId", "lobbyId", "method", "data");

        // Check the method is "selectObjective".
        checkNodeFiledStringValue(jsonNode, "method", methodName);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get("data");

        // Check there are only two and only two fields "cardId" and "type".
        checkNodeFieldNames(dataNode, "cardId", "type");

        // Check the filed values.
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledStringValue(dataNode, "type", type);

    }

    /**
     * Test for the deserialization of TakeCardRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseTakeCard01Test() {
        // This file has the following json text:
        // {
        //  "method": "takeCard",
        //  "playerId": 3,
        //  "lobbyId": 889,
        //  "data": {
        //    "cardId": 69,
        //    "type": "gold"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/takeCardRequest";
        final String jsonFileName = "takeCard01.json";
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
            TakeCardRequest request = (TakeCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals("takeCard", request.getMethod());
            assertEquals(3, request.getPlayerId());
            assertEquals(889, request.getLobbyId());
            assertEquals(69, request.getData().getCardId());
            assertEquals("gold", request.getData().getType());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of TakeCardRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseTakeCard02Test() {
        // This file has the following json text:
        // {
        //  "method": "takeCard",
        //  "playerId": 4,
        //  "lobbyId": 2335,
        //  "data": {
        //    "type": "resource"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/takeCardRequest";
        final String jsonFileName = "takeCard02.json";
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
            TakeCardRequest request = (TakeCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals("takeCard", request.getMethod());
            assertEquals(4, request.getPlayerId());
            assertEquals(2335, request.getLobbyId());
            assertEquals(-1, request.getData().getCardId());
            assertEquals("resource", request.getData().getType());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
