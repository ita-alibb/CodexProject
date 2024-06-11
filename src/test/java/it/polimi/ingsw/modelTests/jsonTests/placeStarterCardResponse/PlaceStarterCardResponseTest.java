package it.polimi.ingsw.modelTests.jsonTests.placeStarterCardResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.response.PlaceStarterCardResponse;
import it.polimi.ingsw.am52.json.response.PlaceStarterCardResponseData;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the process of serialization and deserialization of the class PlaceStarterCardResponse
 */
public class PlaceStarterCardResponseTest {
    /**
     * Test for the serialization of PlaceStarterCardResponse class
     */
    @Test
    @DisplayName("Test json deserialization, method toJson()")
    public void toJsonTest() {
        //The player id
        final int playerId = 4;
        //The lobby id
        final int lobbyId = 1223;

        //The method name
        final String methodName = JsonDeserializer.PLACE_STARTER_CARD_METHOD;

        //Place starter card data
        final int cardId = 81;
        final int face = 0;
        final String nickname = "Andrea";
        final List<BoardSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new BoardSlot(1, 1));
        availableSlots.add(new BoardSlot(2, 2));
        final ResponseStatus status = new ResponseStatus();

        //Create the data object of the response
        PlaceStarterCardResponseData data = new PlaceStarterCardResponseData(status, cardId, face, nickname, availableSlots);

        //Create the response object
        PlaceStarterCardResponse response = new PlaceStarterCardResponse(data);
        String jsonText = null;

        //Serialize the response object
        try {
            jsonText = response.toJson();
        } catch (JsonProcessingException e) {
            assert false;
        }

        //Deserialize the json text, in order to check if there are all fields and their values
        JsonNode jsonNode = getJsonNode(jsonText);

        //Check there are four and only four fields, named "playerId", "lobbyId", "method" and "data"
        checkNodeFieldNames(jsonNode, JsonDeserializer.METHOD_FIELD, JsonDeserializer.DATA_FIELD);

        //Check the method is "placeStarterCard"
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        //Inspect the data object
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        //Check if there are 3 fields, "cardId", "face" and "availableSlots"
        checkNodeFieldNames(dataNode, "status", "isBroadcast", "cardId", "face", "availableSlots", "nickname");

        //Check the field values
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledIntValue(dataNode, "face", face);
        checkNodeFiledStringValue(dataNode, "nickname", nickname);

        //Check the slots values
        JsonNode slotsNode = dataNode.get("availableSlots");

        int i = 0;
        for (JsonNode slot : slotsNode) {
            checkNodeFiledIntValue(slot, "h", availableSlots.get(i).getHoriz());
            checkNodeFiledIntValue(slot, "v", availableSlots.get(i).getVert());
            i++;
        }
    }

    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceStarterCardResponse01Test() {
        //This file has the following json text:
        //  "data" : {
        //    "status" : {
        //      "gamePhase" : "LOBBY",
        //      "currPlayer" : "",
        //      "errorCode" : 0,
        //      "errorMessage" : ""
        //    },
        //    "isBroadcast" : false,
        //    "cardId" : 81,
        //    "face" : 0,
        //    "availableSlots" : [
        //      {
        //        "h" : 1,
        //        "v" : 1
        //      },
        //      {
        //        "h" : 2,
        //        "v" : 2
        //      }
        //    ]
        //  },
        //  "method" : "placeStarterCard"

        //Path and filename of the json settings file
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeStarterCardResponse/";
        final String jsonFileName = "placeStarterCardResponse01.json";
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
            PlaceStarterCardResponse response = (PlaceStarterCardResponse) JsonDeserializer.deserializeResponse(jsonText);
            assertEquals(JsonDeserializer.PLACE_STARTER_CARD_METHOD, response.getMethod());

            //Extract the data from the response
            var dataNode = (PlaceStarterCardResponseData) response.getData();

            //Check all the data
            assertEquals(81, dataNode.getCardId());
            assertEquals(0, dataNode.getFace());
            assertEquals("Andrea", dataNode.getNickname());

            final List<BoardSlot> availableSlots = dataNode.getBoardSlots();
            assertEquals(2, availableSlots.size());
            assertEquals(1, availableSlots.get(0).getHoriz());
            assertEquals(1, availableSlots.get(0).getVert());
            assertEquals(2, availableSlots.get(1).getHoriz());
            assertEquals(2, availableSlots.get(1).getVert());

            final ResponseStatus status = dataNode.getStatus();
            assertEquals(GamePhase.LOBBY, status.getGamePhase());
            assertEquals("", status.getCurrPlayer());
            assertEquals("", status.getErrorMessage());
            assertEquals(0, status.getErrorCode());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
}
