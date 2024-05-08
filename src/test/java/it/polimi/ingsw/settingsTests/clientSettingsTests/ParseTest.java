package it.polimi.ingsw.settingsTests.clientSettingsTests;

import it.polimi.ingsw.am52.settings.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for ServerSettings class.
 */
public class ParseTest
{
    public static final String PATH = "src/test/java/it/polimi/ingsw/settingsTests/clientSettingsTests";

    /**
     * Test the parseFromJson() method.
     */
    @Test
    @DisplayName("Test file settings01.json")
    public void testSettings01()
    {
        // This file has the following json text:
        // {
        //  "serverIp": "localhost",
        //  "port": 1024
        //}

        // Path and filename of the json settings file.
        final String jsonFileName = "settings01.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ClientSettings settings = assertDoesNotThrow(()->ClientSettings.loadJsonFile(jsonFilePath));

        // Check settings.
        checkClientSettings(settings,
                "localhost",
                1024,
                ClientSettings.DEFAULT_CLIENT_MODE,
                ClientSettings.DEFAULT_NETWORK_MODE);
    }

    /**
     * Test the parseFromJson() method.
     */
    @Test
    @DisplayName("Test file settings02.json")
    public void testSettings02()
    {
        // This file has the following json text:
        // {
        //  "serverIp": "localhost",
        //  "port": 1024,
        //  "mode": "tui"
        //}

        // Path and filename of the json settings file.
        final String jsonFileName = "settings02.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ClientSettings settings = assertDoesNotThrow(()->ClientSettings.loadJsonFile(jsonFilePath));

        // Check settings.
        checkClientSettings(settings,
                "localhost",
                1024,
                ClientMode.TEXTUAL,
                ClientSettings.DEFAULT_NETWORK_MODE);
    }

    /**
     * Test the parseFromJson() method.
     */
    @Test
    @DisplayName("Test file settings03.json")
    public void testSettings03()
    {
        // This file has the following json text:
        // {
        //  "serverIp": "localhost",
        //  "port": 1024,
        //  "mode": "tui",
        //  "network": "rmi"
        //}

        // Path and filename of the json settings file.
        final String jsonFileName = "settings03.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ClientSettings settings = assertDoesNotThrow(()->ClientSettings.loadJsonFile(jsonFilePath));

        // Check settings.
        checkClientSettings(settings,
                "localhost",
                1024,
                ClientMode.TEXTUAL,
                NetworkMode.RMI);
    }

    /**
     * Test the parseFromJson() method.
     */
    @Test
    @DisplayName("Test file settings04.json")
    public void testSettings04()
    {
        // This file has the following json text:
        // {
        //  "serverIp": "127.0.0.1",
        //  "port": 12336,
        //  "mode": "gui",
        //  "network": "socket"
        //}

        // Path and filename of the json settings file.
        final String jsonFileName = "settings04.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ClientSettings settings = assertDoesNotThrow(()->ClientSettings.loadJsonFile(jsonFilePath));

        // Check settings.
        checkClientSettings(settings,
                "127.0.0.1",
                12336,
                ClientMode.GRAPHICAL,
                NetworkMode.SOCKET);
    }

    private static void checkClientSettings(ClientSettings settings, String expectedIp, int expectedPort, ClientMode expectedMode, NetworkMode expectedNetwork) {
        assertEquals(expectedIp, settings.getServerIp());
        assertEquals(expectedPort, settings.getPort());
        assertEquals(expectedMode, settings.getMode());
        assertEquals(expectedNetwork, settings.getNetworkMode());
    }

}
