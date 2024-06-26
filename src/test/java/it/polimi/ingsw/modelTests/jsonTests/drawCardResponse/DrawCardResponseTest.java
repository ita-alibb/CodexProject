package it.polimi.ingsw.modelTests.jsonTests.drawCardResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.response.DrawCardResponse;
import it.polimi.ingsw.am52.json.response.DrawCardResponseData;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the DrawCardResponse class
 */
public class DrawCardResponseTest {
    /**
     * Test for the serialization of DrawCardResponse class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest() {
        //The player id
        final int playerId = 4;
        //The lobby id
        final int lobbyId = 1645;

        //The method name
        final String methodName = JsonDeserializer.DRAW_CARD_METHOD;

        //Drawn card data
        final int cardId = 52;
        final int deck = 0;
        final int nextCardId = 41;
        final ResponseStatus status = new ResponseStatus();

        //Create the data object of the response
        DrawCardResponseData data = new DrawCardResponseData(status, cardId, deck, nextCardId);

        //Create the response object
        DrawCardResponse response = new DrawCardResponse(data);
        String jsonText = null;

        //Serialize the response object
        try {
            jsonText = response.toJson();
        } catch (JsonProcessingException e) {
            assert false;
        }

        //Deserialize the json text, in order to check if there are all fields and their values
        JsonNode jsonNode = getJsonNode(jsonText);

        //Check if there are four and only four fields, named "playerId", "lobbyId", "method" and "data"
        checkNodeFieldNames(jsonNode, JsonDeserializer.METHOD_FIELD, JsonDeserializer.DATA_FIELD);

        //Check the method is "drawCard"
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        //Inspect the data object
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        //Check if there are 5 fields, named "cardId", "deck", "isEmpty", "status", "isBroadcast"
        checkNodeFieldNames(dataNode, "cardId", "deck", "nextCardId", "status", "isBroadcast");

        //Check the filed values
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledIntValue(dataNode, "deck", deck);
        checkNodeFiledIntValue(dataNode, "nextCardId", nextCardId);
        checkNodeFiledBooleanValue(dataNode, "isBroadcast", false);

        //Inspect the status node
        JsonNode statusNode = dataNode.get("status");
        checkNodeFieldNames(statusNode, "gamePhase", "currPlayer", "errorCode", "errorMessage");
        checkNodeFiledStringValue(statusNode, "currPlayer", "");
        checkNodeFiledIntValue(statusNode, "errorCode", 0);
        checkNodeFiledStringValue(statusNode, "errorMessage", "");
        assertEquals("NULL", statusNode.get("gamePhase").asText());
    }

    /**
     * Test for deserialization of DrawCardResponse class
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseDrawCard01Test() {
        //This file has the following json text:
        //{
        //  "data" : {
        //    "status" : {
        //      "gamePhase" : "LOBBY",
        //      "currPlayer" : "",
        //      "errorCode" : 0,
        //      "errorMessage" : ""
        //    },
        //    "isBroadcast" : false,
        //    "cardId" : 26,
        //    "deck" : 0,
        //    "nextCardId" : 41
        //  },
        //  "method" : "drawCard"
        //}

        //Path and filename of json settings file
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/drawCardResponse";
        final String jsonFileName = "drawCard01.json";
        final Path jsonFilePath = Path.of(path, jsonFileName);
        //Check if the file exists
        assertTrue(Files.exists(jsonFilePath));

        //Read the text from file
        String jsonText = null;
        try {
            jsonText = Files.readString(jsonFilePath);
        } catch (IOException e) {
            assert false;
        }

        //Parse the json text
        try {
            DrawCardResponse response = (DrawCardResponse) JsonDeserializer.deserializeResponse(jsonText);
            assertEquals(JsonDeserializer.DRAW_CARD_METHOD, response.getMethod());

            var dataNode = response.getData();

            //Check all the data
            assertEquals(26, dataNode.getCardId());
            assertEquals(0, dataNode.getDeck());
            assertEquals(41, dataNode.getNextCardId());
            assertFalse(dataNode.getIsBroadcast());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
}
