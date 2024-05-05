package it.polimi.ingsw.settingsTests.serverSettingsTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.am52.settings.NetworkMode;
import it.polimi.ingsw.am52.settings.PortMode;
import it.polimi.ingsw.am52.settings.ServerSettings;
import it.polimi.ingsw.am52.settings.VerbosityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for ServerSettings class.
 */
public class ParseTest
{
    public static final String PATH = "src/test/java/it/polimi/ingsw/settingsTests/serverSettingsTests";

    /**
     * Test the parseFromJson() method in case the json file has only
     * the port setting value.
     */
    @Test
    @DisplayName("Test file settings01.json")
    public void testSettings01()
    {
        // This file has just one setting:
        // {
        //   "port": 1024
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings01.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the port number in the json file.
        assertEquals(1024, settings.getPort().getAsInt());
        // The max lobbies is equal to the default value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the port setting value, with an invalid value (1023).
     */
    @Test
    @DisplayName("Test file settings02.json")
    public void testSettings02()
    {
        // This file has just one setting, the port number with an
        // invalid value:
        // {
        //   "port": 1023
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings02.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(ServerSettings.DEF_PORT, settings.getPort().getAsInt());
        // The max lobbies is equal to the default value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the port setting value (the max allowed value 65535).
     */
    @Test
    @DisplayName("Test file settings03.json")
    public void testSettings03()
    {
        // This file has just one setting:
        // {
        //   "port": 65535
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings03.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the port number in the json file.
        assertEquals(65535, settings.getPort().getAsInt());
        // The max lobbies is equal to the default value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the port setting value, with an invalid value (1023).
     */
    @Test
    @DisplayName("Test file settings04.json")
    public void testSettings04()
    {
        // This file has just one setting, the port number with an
        // invalid value:
        // {
        //   "port": 65536
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings04.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(ServerSettings.DEF_PORT, settings.getPort().getAsInt());
        // The max lobbies is equal to the default value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the maxLobbies setting value, with value 1.
     */
    @Test
    @DisplayName("Test file settings05.json")
    public void testSettings05()
    {
        // This file has just one setting, the max lobbies value:
        // {
        //   "maxLobbies": 1
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings05.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the value read from settings file.
        assertEquals(1, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the maxLobbies setting value, with an invalid value (0).
     */
    @Test
    @DisplayName("Test file settings06.json")
    public void testSettings06()
    {
        // This file has just one setting, the max lobbies value, with an
        // invalid value:
        // {
        //   "maxLobbies": 0
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings06.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the verbosity setting value.
     */
    @Test
    @DisplayName("Test file settings07.json")
    public void testSettings07()
    {
        // This file has just one setting, the verbosity value:
        // {
        //   "verbosity": "verbose"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings07.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from the settings file.
        assertEquals(VerbosityLevel.VERBOSE, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the verbosity setting value.
     */
    @Test
    @DisplayName("Test file settings08.json")
    public void testSettings08()
    {
        // This file has just one setting, the verbosity value:
        // {
        //   "verbosity": "info"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings08.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from the settings file.
        assertEquals(VerbosityLevel.INFO, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the verbosity setting value.
     */
    @Test
    @DisplayName("Test file settings09.json")
    public void testSettings09()
    {
        // This file has just one setting, the verbosity value:
        // {
        //   "verbosity": "warning"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings09.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from the settings file.
        assertEquals(VerbosityLevel.WARNING, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the verbosity setting value.
     */
    @Test
    @DisplayName("Test file settings10.json")
    public void testSettings10()
    {
        // This file has just one setting, the verbosity value:
        // {
        //   "verbosity": "error"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings10.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from the settings file.
        assertEquals(VerbosityLevel.ERROR, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the verbosity setting value, with an invalid value.
     */
    @Test
    @DisplayName("Test file settings11.json")
    public void testSettings11()
    {
        // This file has just one setting, the verbosity value:
        // {
        //   "verbosity": "high"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings11.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has an empty
     * json object.
     */
    @Test
    @DisplayName("Test file settings12.json")
    public void testSettings12()
    {
        // This file has an empty json object:
        // {
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings12.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the default port number.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the default max lobbies value.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the default value.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has an invalid
     * json text.
     */
    @Test
    @DisplayName("Test file settings13.json")
    public void testSettings13()
    {
        // This file a wrong json object:
        // {
        //    "verbosity": "warning",
        //    "maxLobbies":
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings13.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
            assert (false);
        } catch (JsonProcessingException ioEx) {
            assert(true);
        } catch (IOException ioEx) {
            assert (false);
        }
    }

    /**
     * Test the parseFromJson() method in case the json file has three settings.
     */
    @Test
    @DisplayName("Test file settings14.json")
    public void testSettings14()
    {
        // This file has three settings' values:
        // {
        //   "port": 3325,
        //   "maxLobbies": 99,
        //   "verbosity": "warning"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings14.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(3325, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(99, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(VerbosityLevel.WARNING, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has all
     * three settings, and an additional unknown field.
     */
    @Test
    @DisplayName("Test file settings15.json")
    public void testSettings15()
    {
        // This file has four settings' values:
        // {
        //   "port": 3325,
        //   "maxLobbies": 99,
        //   "verbosity": "warning",
        //   "key": "xx12"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings15.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(3325, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(99, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(VerbosityLevel.WARNING, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has all
     * three settings, but with the maxLobbies value as string "99",
     * not integer value 99.
     */
    @Test
    @DisplayName("Test file settings16.json")
    public void testSettings16()
    {
        // This file has three settings' values:
        // {
        //   "port": 3325,
        //   "maxLobbies": "99",
        //   "verbosity": "warning"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings16.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(3325, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(99, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(VerbosityLevel.WARNING, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the network field set to "socket".
     */
    @Test
    @DisplayName("Test file settings17.json")
    public void testSettings17()
    {
        // This file has one setting value:
        // {
        //  "network": "socket"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings17.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has only
     * the network field set to "socket".
     */
    @Test
    @DisplayName("Test file settings18.json")
    public void testSettings18()
    {
        // This file has one setting value:
        // {
        //  "network": "rmi"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings18.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(ServerSettings.DEF_PORT_MODE, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file has four
     * fields.
     */
    @Test
    @DisplayName("Test file settings19.json")
    public void testSettings19()
    {
        // This file has four settings' values:
        // {
        //  "port": 3365,
        //  "verbosity": "verbose",
        //  "network": "rmi",
        //  "maxLobbies": 9999
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings19.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(3365, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(9999, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(VerbosityLevel.VERBOSE, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file only the
     * field portMode set to "auto".
     */
    @Test
    @DisplayName("Test file settings20.json")
    public void testSettings20()
    {
        // This file has one setting value:
        // {
        //  "portMode": "auto"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings20.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(OptionalInt.empty(), settings.getPort());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.AUTO, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file only the
     * field portMode set to "fixed".
     */
    @Test
    @DisplayName("Test file settings21.json")
    public void testSettings21()
    {
        // This file has one setting value:
        // {
        //  "portMode": "fixed"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings21.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(ServerSettings.DEF_PORT, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file defines
     * both port number and portMode "fixed".
     */
    @Test
    @DisplayName("Test file settings22.json")
    public void testSettings22()
    {
        // This file has one setting value:
        // {
        //  "port": 2366,
        //  "portMode": "fixed"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings22.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(2366, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.FIXED, settings.getPortMode());
    }

    /**
     * Test the parseFromJson() method in case the json file defines
     * both port number and portMode "auto".
     */
    @Test
    @DisplayName("Test file settings23.json")
    public void testSettings23()
    {
        // This file has one setting value:
        // {
        //  "port": 2366,
        //  "portMode": "auto"
        // }

        // Path and filename of the json settings file.
        final String jsonFileName = "settings23.json";
        final Path jsonFilePath = Path.of(PATH, jsonFileName);
        // Check if the file exists.
        assertTrue(Files.exists(jsonFilePath));

        // Parse the server settings.
        ServerSettings settings = null;
        try {
            settings = ServerSettings.loadJsonFile(jsonFilePath);
        } catch (IOException ioEx) {
            assert(false);
        }

        // The port number is equal to the value read from file.
        assertEquals(2366, settings.getPort().getAsInt());
        // The max lobbies is equal to the value read from file.
        assertEquals(ServerSettings.DEF_MAX_LOBBIES, settings.getMaxLobbies());
        // The verbosity is equal to the value read from file.
        assertEquals(ServerSettings.DEF_VERBOSITY, settings.getVerbosity());
        // The port mode.
        assertEquals(PortMode.AUTO, settings.getPortMode());
    }

}
