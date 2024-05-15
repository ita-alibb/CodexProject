package it.polimi.ingsw.modelTests.jsonTests.placeCardResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.response.PlaceCardResponse;
import it.polimi.ingsw.am52.json.response.PlaceCardResponseData;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the process of serialization and deserialization of the class PlaceCardResponse
 */
public class PlaceCardResponseTest {
    /**
     * Test for the serialization of PlaceCardResponse class
     */
    @Test
    @DisplayName("Test json deserialization, method toJson()")
    public void toJsonTest() {
        //The player id
        final int playerId = 4;
        //The lobby id
        final int lobbyId = 1223;

        //The method name
        final String methodName = JsonDeserializer.PLACE_CARD_METHOD;

        //Place card data
        final int cardId = 81;
        final int face = 0;
        final BoardSlot placedSlot = new BoardSlot(1, 1);
        final List<BoardSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new BoardSlot(2, 2));
        availableSlots.add(new BoardSlot(3, 3));
        availableSlots.add(new BoardSlot(-1, -3));
        final String player = "Lorenzo";
        final int score = 12;
        final ResponseStatus status = new ResponseStatus();

        //Create the data object of the response
        PlaceCardResponseData data = new PlaceCardResponseData(status, cardId, face, placedSlot, availableSlots, player, score);

        //Create the response object
        PlaceCardResponse response = new PlaceCardResponse(data);
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

        //Check the method is "placeCard"
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        //Inspect the data object
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        //Check if there are 8 fields:
        //  -cardId
        //  -face
        //  -placedSlot
        //  -availableSlots
        //  -player
        //  -score
        //  -status
        //  -isBroadcast
        checkNodeFieldNames(dataNode, "status", "isBroadcast", "cardId", "face", "placedSlot", "availableSlots", "player", "score");

        //Check the field values
        checkNodeFiledIntValue(dataNode, "cardId", cardId);
        checkNodeFiledIntValue(dataNode, "face", face);

        JsonNode placedSlotNode = dataNode.get("placedSlot");
        checkNodeFieldNames(placedSlotNode, "h", "v");
        checkNodeFiledIntValue(placedSlotNode, "h", placedSlot.getHoriz());
        checkNodeFiledIntValue(placedSlotNode, "v", placedSlot.getVert());

        JsonNode availableSlotsNode = dataNode.get("availableSlots");
        int i = 0;
        for (JsonNode availableSlot : availableSlotsNode) {
            checkNodeFiledIntValue(availableSlot, "h", availableSlots.get(i).getHoriz());
            checkNodeFiledIntValue(availableSlot, "v", availableSlots.get(i).getVert());
            i++;
        }

        checkNodeFiledStringValue(dataNode, "player", player);
        checkNodeFiledIntValue(dataNode, "score", score);
    }

    @Test
    @DisplayName("Test json deserialization")
    public void parsePlaceCardResponse01Test() {
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
        //    "cardId" : 64,
        //    "face" : 1,
        //    "player" : "Andrea",
        //    "score" : 18,
        //    "placedSlot" : {
        //      "h" : 1,
        //      "v" : 1
        //    },
        //    "availableSlots" : [
        //      {
        //        "h" : 2,
        //        "v" : 2
        //      },
        //      {
        //        "h" : 3,
        //        "v" : 3
        //      },
        //      {
        //        "h" : -1,
        //        "v" : -3
        //      }
        //    ]
        //  },
        //  "method" : "placeCard"
        //}

        //Path and filename of the json settings file
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/placeCardResponse/";
        final String jsonFileName = "placeCard01.json";
        final Path jsonFilePath = Path.of(path, jsonFileName);
        //Check if the file exists
        assertTrue(Files.exists(jsonFilePath));

        //Read the text file from file
        String jsonText = null;
        try {
            jsonText = Files.readString(jsonFilePath);
        } catch (IOException e) {
            assert false;
        }

        //Parse the json text
        try {
            PlaceCardResponse response = (PlaceCardResponse) JsonDeserializer.deserializeResponse(jsonText);
            assertEquals(JsonDeserializer.PLACE_CARD_METHOD, response.getMethod());

            //Extract the data from the response
            var dataNode = response.getData();

            //Check all the data
            assertEquals(64, dataNode.getCardId());
            assertEquals(1, dataNode.getFace());
            assertEquals("Andrea", dataNode.getPlayer());
            assertEquals(18, dataNode.getScore());

            final BoardSlot placedSlot = dataNode.getPlacedSlot();
            assertEquals(1, placedSlot.getHoriz());
            assertEquals(1, placedSlot.getVert());

            final List<BoardSlot> availableSlots = dataNode.getAvailableSlots();
            assertEquals(3, availableSlots.size());
            assertEquals(2, availableSlots.get(0).getHoriz());
            assertEquals(2, availableSlots.get(0).getVert());
            assertEquals(3, availableSlots.get(1).getHoriz());
            assertEquals(3, availableSlots.get(1).getVert());
            assertEquals(-1, availableSlots.get(2).getHoriz());
            assertEquals(-3, availableSlots.get(2).getVert());

            final ResponseStatus status = dataNode.getStatus();
            assertEquals(GamePhase.LOBBY, status.gamePhase);
            assertEquals("", status.currPlayer);
            assertEquals("", status.errorMessage);
            assertEquals(0, status.errorCode);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
}
