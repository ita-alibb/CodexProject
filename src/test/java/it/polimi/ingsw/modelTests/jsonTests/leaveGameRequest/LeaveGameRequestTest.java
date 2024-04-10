package it.polimi.ingsw.modelTests.jsonTests.leaveGameRequest;

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
 * Unit test for the LeaveGameRequest class.
 */
public class LeaveGameRequestTest
{
    /**
     * Test for the LeaveRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 3;
        // The lobby id.
        final int lobbyId = 1036;

        // The method name.
        final String methodName = "leaveGame";

        // Leave game data.
        final String message = "Goodbye to all!";

        // Create the data object of the request.
        LeaveGameData leaveData = new LeaveGameData(message);

        // Create the request object.
        LeaveGameRequest request = new LeaveGameRequest(playerId, lobbyId, leaveData);
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

        // Check there are four and only four fields, named "method" and "data".
        checkNodeFieldNames(jsonNode, "playerId", "lobbyId", "method", "data");

        // Check the method is "leaveGame".
        checkNodeFiledStringValue(jsonNode, "method", methodName);
        // Check the playerId.
        checkNodeFiledIntValue(jsonNode, "playerId", playerId);
        // Check the lobbyId.
        checkNodeFiledIntValue(jsonNode, "lobbyId", lobbyId);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get("data");

        // Check there are one and only one field, named "message".
        checkNodeFieldNames(dataNode, "message");

        // Check the filed values.
        checkNodeFiledStringValue(dataNode, "message", message);

    }

    /**
     * Test for the deserialization of LeaveGameRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseLeaveGame01Test() {
        // This file has the following json text:
        // {
        //  "method": "leaveGame",
        //  "playerId": 1,
        //  "lobbyId": 335,
        //  "data": {
        //    "message": "Bye Bye!"
        //  }

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/leaveGameRequest";
        final String jsonFileName = "leaveGame01.json";
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
            LeaveGameRequest request = (LeaveGameRequest) JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(1, request.getPlayerId());
            assertEquals(335, request.getLobbyId());
            assertEquals("leaveGame", request.getMethod());
            assertEquals("Bye Bye!", request.getData().getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
