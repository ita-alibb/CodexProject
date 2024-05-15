package it.polimi.ingsw.modelTests.jsonTests.placeCardRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.request.PlaceCardData;
import it.polimi.ingsw.am52.json.request.PlaceStarterCardData;
import it.polimi.ingsw.am52.json.request.PlaceCardRequest;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the PlaceCardRequest class.
 */
public class PlaceCardRequestTest
{
    /**
     * Test for the serialization of PlaceCardRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 2;
        // The lobby id.
        final int lobbyId = 4229;

        // The method name.
        final String methodName = JsonDeserializer.PLACE_CARD_METHOD;

        // Place card data.
        final int cardId = 31;
        final int face = 0;
        //final BoardSlot slot = new BoardSlot(1, 1);

        // Create the data object of the request.
        PlaceCardData data = new PlaceCardData(cardId, face, new BoardSlot(0, 0));

        // Create the request object.
        PlaceCardRequest request = new PlaceCardRequest(data);
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
                JsonDeserializer.METHOD_FIELD,
                JsonDeserializer.DATA_FIELD);

        // Check the method is "placeCard".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there are two and only two fields "cardId" and "face".
        checkNodeFieldNames(dataNode, "cardId", "face", "placedSlot");

        // Check the filed values.
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledIntValue(dataNode, "face", face);
        checkNodeFiledIntValue(dataNode.get("placedSlot"), "h", 0);
        checkNodeFiledIntValue(dataNode.get("placedSlot"), "v", 0);
    }

    /**
     * Test for the deserialization of PlaceCardRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceCard01Test() {
        // This file has the following json text:
        // {
        //  "method": "placeCard",
        //  "data": {
        //    "cardId": 11,
        //    "face": "front",
        //    "placedSlot": {
        //      "h" : 0,
        //      "v" : 0
        //    }
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeCardRequest";
        final String jsonFileName = "placeCard01.json";
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
            PlaceCardRequest request = (PlaceCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.PLACE_CARD_METHOD, request.getMethod());
            assertEquals(11, request.getData().getCardId());
            assertEquals(0, request.getData().getFace());
            assertEquals(0, request.getData().getPlacedSlot().getHoriz());
            assertEquals(0, request.getData().getPlacedSlot().getVert());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of PlaceCardRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceCard02Test() {
        // This file has the following json text:
        // {
        //  "method": "placeCard",
        //  "data": {
        //    "cardId": 11,
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeCardRequest";
        final String jsonFileName = "placeCard02.json";
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
            PlaceCardRequest request = (PlaceCardRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.PLACE_CARD_METHOD, request.getMethod());
            assertEquals(11, request.getData().getCardId());
            assertEquals(-1, request.getData().getFace());
            assertNull(request.getData().getPlacedSlot());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
