package it.polimi.ingsw.modelTests.jsonTests.endGameResponse;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.response.EndGameResponse;
import it.polimi.ingsw.am52.json.response.EndGameResponseData;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the EndGameResponse class
 */
public class EndGameResponseTest {
    /**
     * Test for the serialization of EndGameResponse class
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest() {
        //The player id
        final int playerId = 4;
        //The lobby id
        final int lobbyId = 1664;

        //The method name
        final String methodName = JsonDeserializer.END_GAME_METHOD;

        //Winners' names data
        final List<String> winners = new ArrayList<>();
        winners.add("Lorenzo");
        winners.add("Andrea");
        final ResponseStatus status = new ResponseStatus();

        //Create the object of the response
        EndGameResponseData data = new EndGameResponseData(status, winners);

        //Create the response object
        EndGameResponse response = new EndGameResponse(data);
        String jsonText = null;

        //Serialize the response object
        try {
            jsonText = response.toJson();
        } catch (IOException e) {
            assert false;
        }

        //Deserialize the json text, in order to check if there are all fields and their values
        JsonNode jsonNode = getJsonNode(jsonText);

        //Check if there are four and only four fields, named "playerId", "lobbyId", "method" anc "data"
        checkNodeFieldNames(jsonNode, JsonDeserializer.METHOD_FIELD, JsonDeserializer.DATA_FIELD);

        //Check the method is "endGame"
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        //Inspect the data object
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        //Check if there are 3 fields, named "status", "idBroadcast" and "winners"
        checkNodeFieldNames(dataNode, "status", "isBroadcast", "winners", "disconnectedPlayerNickname");

        //Check the filed values
        JsonNode winnersNode = dataNode.get("winners");
        int i = 0;
        for (String winner : winners) {
            JsonNode winnerNode = winnersNode.get(i);
            assertEquals(winner, winnerNode.asText());
            i++;
        }

        //Inspect the status node
        JsonNode statusNode = dataNode.get("status");
        checkNodeFieldNames(statusNode, "gamePhase", "currPlayer", "errorCode", "errorMessage");
        checkNodeFiledStringValue(statusNode, "currPlayer", "");
        checkNodeFiledIntValue(statusNode, "errorCode", 0);
        checkNodeFiledStringValue(statusNode, "errorMessage", "");
        assertEquals("NULL", statusNode.get("gamePhase").asText());
    }

    /**
     * Test for deserialization of EndGameResponse class
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseEndGame01Test() {
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
        //    "winners" : [
        //      "Livio",
        //      "Lorenzo",
        //      "Andrea",
        //      "William"
        //    ]
        //  },
        //  "method" : "endGame"
        //}

        //Path and filename of json settings file
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/endGameResponse";
        final String jsonFileName = "endGame01.json";
        final Path jsonFilePath = Path.of(path,jsonFileName);
        //Check if the file exists
        assertTrue(Files.exists(jsonFilePath));

        //Read the text from file
        final List<String> expectedWinners = new ArrayList<>();
        expectedWinners.add("Livio");
        expectedWinners.add("Lorenzo");
        expectedWinners.add("Andrea");
        expectedWinners.add("William");
        String jsonText = null;
        try {
            jsonText = Files.readString(jsonFilePath);
        } catch (IOException e) {
            assert false;
        }

        //Parse the json text
        try {
            EndGameResponse response = (EndGameResponse) JsonDeserializer.deserializeResponse(jsonText);
            assertEquals(JsonDeserializer.END_GAME_METHOD, response.getMethod());

            var dataNode = response.getData();

            //Check all data
            assertFalse(dataNode.getIsBroadcast());
            final List<String> winners = dataNode.getWinners();
            int i = 0;
            for (String winner : winners) {
                assertEquals(winner, expectedWinners.get(i));
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
}
