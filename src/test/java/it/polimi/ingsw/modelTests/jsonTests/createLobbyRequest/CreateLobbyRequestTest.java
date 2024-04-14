package it.polimi.ingsw.modelTests.jsonTests.createLobbyRequest;

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
 * Unit test for the CreateLobbyRequest class.
 */
public class CreateLobbyRequestTest
{
    /**
     * Test for the serialization of CreateLobbyRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The method name.
        final String methodName = JsonDeserializer.CREATE_LOBBY_METHOD;

        // Create lobby data.
        final String nickname = "Livio";
        final String lobbyName = "Family Lobby";
        final int numPlayers = 4;

        // Create the data object of the request.
        CreateLobbyData createLobbyData = new CreateLobbyData(nickname, lobbyName, numPlayers);

        // Create the request object.
        CreateLobbyRequest request = new CreateLobbyRequest(createLobbyData);
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
        checkNodeFieldNames(jsonNode, JsonDeserializer.METHOD_FIELD, JsonDeserializer.DATA_FIELD);

        // Check the method is "createLobby".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there are two and only two fields, named "nickname" and "lobbyId".
        checkNodeFieldNames(dataNode, "nickname", "numPlayers", "lobbyName");

        // Check the filed values.
        checkNodeFiledStringValue(dataNode, "nickname", nickname);
        checkNodeFiledStringValue(dataNode, "lobbyName", lobbyName);
        checkNodeFiledIntValue(dataNode, "numPlayers", numPlayers);

    }

    /**
     * Test for the deserialization of CreateLobbyRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseCreateLobby01Test() {
        // This file has the following json text:
        // {
        //  "method": "createLobby",
        //  "data": {
        //    "nickname": "Livio",
        //    "lobbyName": "Family Lobby",
        //    "numPlayers": 4
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/createLobbyRequest";
        final String jsonFileName = "createLobby01.json";
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
            CreateLobbyRequest request = (CreateLobbyRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.CREATE_LOBBY_METHOD, request.getMethod());
            assertEquals("Livio", request.getData().getNickname());
            assertEquals("Family Lobby", request.getData().getLobbyName());
            assertEquals(4, request.getData().getNumPlayers());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of CreateLobbyRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseLogin02Test() {
        // This file has the following json text:
        // {
        //  "method": "createLobby",
        //  "data": {
        //    "nickname": "Livio"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/createLobbyRequest";
        final String jsonFileName = "createLobby02.json";
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
            CreateLobbyRequest request = (CreateLobbyRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.CREATE_LOBBY_METHOD, request.getMethod());
            assertEquals("Livio", request.getData().getNickname());
            assertEquals("", request.getData().getLobbyName());
            assertEquals(-1, request.getData().getNumPlayers());
            assert(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
