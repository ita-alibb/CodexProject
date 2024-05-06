package it.polimi.ingsw.modelTests.jsonTests.joinLobbyRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.request.JoinLobbyData;
import it.polimi.ingsw.am52.json.request.JoinLobbyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static it.polimi.ingsw.modelTests.jsonTests.JsonUtil.*;

/**
 * Unit test for the LoginRequest class.
 */
public class JoinLobbyRequestTest
{
    /**
     * Test for the serialization of LoginRequest class.
     */
    @Test
    @DisplayName("Test json serialization, method toJson()")
    public void toJsonTest()
    {
        // The method name.
        final String methodName = JsonDeserializer.JOIN_LOBBY_METHOD;

        // Login data.
        final String nickname = "livio";
        final int lobbyId = 1;

        // Create the data object of the request.
        JoinLobbyData login = new JoinLobbyData(nickname, lobbyId);

        // Create the request object.
        JoinLobbyRequest request = new JoinLobbyRequest(login);
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

        // Check the method is "joinLobby".
        checkNodeFiledStringValue(jsonNode, JsonDeserializer.METHOD_FIELD, methodName);

        // Inspect the data object.
        JsonNode dataNode = jsonNode.get(JsonDeserializer.DATA_FIELD);

        // Check there are two and only two fields, named "nickname" and "lobbyId".
        checkNodeFieldNames(dataNode, "nickname", "lobbyId");

        // Check the filed values.
        checkNodeFiledStringValue(dataNode, "nickname", nickname);
        checkNodeFiledIntValue(dataNode, "lobbyId", lobbyId);

    }

    /**
     * Test for the deserialization of LoginRequest class.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseLogin01Test() {
        // This file has the following json text:
        // {
        //  "method": "joinLobby",
        //  "data": {
        //    "nickname": "Livio",
        //    "lobbyId": 668
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/joinLobbyRequest";
        final String jsonFileName = "joinLobby01.json";
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
            JoinLobbyRequest request = (JoinLobbyRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.JOIN_LOBBY_METHOD, request.getMethod());
            assertEquals("Livio", request.getData().getNickname());
            assertEquals(668, request.getData().getLobbyId());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

    /**
     * Test for the deserialization of LoginRequest class, parsing
     * a json object with a missing field in the data object.
     */
    @Test
    @DisplayName("Test json deserialization")
    public void parseLogin02Test() {
        // This file has the following json text:
        // {
        //  "method": "joinLobby",
        //  "data": {
        //    "nickname": "Livio"
        //  }
        //}

        // Path and filename of the json settings file.
        final String path = "src/test/java/it/polimi/ingsw/modelTests/jsonTests/joinLobbyRequest";
        final String jsonFileName = "joinLobby02.json";
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
            JoinLobbyRequest request = (JoinLobbyRequest)JsonDeserializer.deserializeRequest(jsonText);
            assertEquals(JsonDeserializer.JOIN_LOBBY_METHOD, request.getMethod());
            assertEquals("Livio", request.getData().getNickname());
            assertEquals(-1, request.getData().getLobbyId());
            assert(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assert(false);
        }

    }

}
