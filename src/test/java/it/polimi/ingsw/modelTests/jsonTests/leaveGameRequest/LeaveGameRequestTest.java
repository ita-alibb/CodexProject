package it.polimi.ingsw.modelTests.jsonTests.leaveGameRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.request.LeaveGameRequest;
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
        // The method name.
        final String methodName = JsonDeserializer.LEAVE_GAME_METHOD;

        // Create the request object.
        LeaveGameRequest request = new LeaveGameRequest(null);
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

        // Check the method is "leaveGame".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);
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
        //  "data": {
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
            assertEquals(JsonDeserializer.LEAVE_GAME_METHOD, request.getMethod());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
