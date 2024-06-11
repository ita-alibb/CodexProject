package it.polimi.ingsw.modelTests.jsonTests.leaveGameResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.response.LeaveGameResponse;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GamePhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the process of serialization and deserialization of the class LeaveGameResponse
 */
public class LeaveGameResponseTest {
    /**
     * Test for the serialization of LeaveGameResponse class
     */
    @Test
    @DisplayName("Test json deserialization, method toJson()")
    public void toJsonTest() {
        //The player id
        final int playerId = 4;
        //The lobby id
        final int lobbyId = 1223;

        //The method name
        final String methodName = JsonDeserializer.LEAVE_GAME_METHOD;

        //Place card data
        final String username = "Lorenzo";
        final ResponseStatus status = new ResponseStatus();

        //Create the data object of the response
        LeaveGameResponseData data = new LeaveGameResponseData(status, username,  Map.ofEntries(entry(1, 0)));

        //Create the response object
        LeaveGameResponse response = new LeaveGameResponse(data);
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

        //Check the method is "leaveGame"
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        //Inspect the data object
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        //Check if there are 3 fields, named "username", "isBroadcast" and "status"
        checkNodeFieldNames(dataNode, "status", "isBroadcast", "username", "lobbies");

        //Check fields values
        checkNodeFiledStringValue(dataNode, "username", username);
    }

    @Test
    @DisplayName("Test json deserialization")
    public void parseLeaveGameResponse01Test() {
        //This file has the following json text
        //{
        //  "data" : {
        //    "status" : {
        //      "gamePhase" : "LOBBY",
        //      "currPlayer" : "",
        //      "errorCode" : 0,
        //      "errorMessage" : ""
        //    },
        //  "isBroadcast" : false,
        //  "username" : "Lorenzo"
        //  },
        //  "method" : "leaveGame"
        //}

        //Path and filename of the json settings file
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/leaveGameResponse/";
        final String jsonFileName = "leaveGame01.json";
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
            LeaveGameResponse response = (LeaveGameResponse) JsonDeserializer.deserializeResponse(jsonText);
            assertEquals(JsonDeserializer.LEAVE_GAME_METHOD, response.getMethod());

            //Extract the data from the response
            var dataNode = response.getData();

            //Check all the data
            assertEquals("Lorenzo", dataNode.getUsername());

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
