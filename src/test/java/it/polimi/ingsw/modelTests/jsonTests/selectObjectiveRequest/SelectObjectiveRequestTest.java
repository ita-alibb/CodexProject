package it.polimi.ingsw.modelTests.jsonTests.selectObjectiveRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.request.SelectObjectiveData;
import it.polimi.ingsw.am52.json.request.SelectObjectiveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the SelectObjectiveRequest class.
 */
public class SelectObjectiveRequestTest
{
    /**
     * Test for the serialization of SelectObjectiveRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The player id.
        final int playerId = 1;
        // The lobby id.
        final int lobbyId = 556;

        // The method name.
        final String methodName = JsonDeserializer.SELECT_OBJECTIVE_METHOD;

        // Select objective data.
        final int objectiveId = 13;

        // Create the data object of the request.
        SelectObjectiveData data = new SelectObjectiveData(objectiveId);

        // Create the request object.
        SelectObjectiveRequest request = new SelectObjectiveRequest(data);
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

        // Check the method is "selectObjective".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there are one and only one filed "objectiveId".
        checkNodeFieldNames(dataNode, "objectiveId");

        // Check the filed values.
        checkNodeFiledIntValue(dataNode, "objectiveId", objectiveId);

    }

    /**
     * Test for the deserialization of SelectObjectiveRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseSelectObjective01Test() {
        // This file has the following json text:
        // {
        //  "method": "selectObjective",
        //  "data": {
        //    "objectiveId": 9
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/selectObjectiveRequest";
        final String jsonFileName = "selectObjective01.json";
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
            SelectObjectiveRequest request = (SelectObjectiveRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.SELECT_OBJECTIVE_METHOD, request.getMethod());
            assertEquals(9, request.getData().getObjectiveId());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of SelectObjectiveRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseSelectObjective02Test() {
        // This file has the following json text:
        // {
        //  "method": "selectObjective",
        //  "data": {
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/selectObjectiveRequest";
        final String jsonFileName = "selectObjective02.json";
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
            SelectObjectiveRequest request = (SelectObjectiveRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.SELECT_OBJECTIVE_METHOD, request.getMethod());
            assertEquals(-1, request.getData().getObjectiveId());
            assert(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
