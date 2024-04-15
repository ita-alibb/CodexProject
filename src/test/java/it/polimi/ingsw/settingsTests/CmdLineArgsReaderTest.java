package it.polimi.ingsw.settingsTests;

import it.polimi.ingsw.am52.settings.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for CmdLineArgsReader class.
 */
public class CmdLineArgsReaderTest
{
    /**
     * To run the application in client mode, the user must specify
     * two arguments after the application name:<ul>
     *     <li>the ip address</li>
     *     <li>the port number (in the range [1024, 65035]</li>
     * </ul>
     * This performs the following tests, on the readCmdLineArgs() method of
     * the CmdLineArgsReader class:<ul>
     *     <li>With two arguments the settings mode is client.</li>
     *     <li>With more than two arguments a IllegalArgumentException is thrown.</li>
     *     <li>Check the limit of the valid port number (IllegalArgumentException if out of range)</li>
     * </ul>
     */
    @Test
    @DisplayName("Client arguments test")
    public void clientArgumentsTest() {

        // Test client running mode (two valid arguments, no options).
        String argsString = "127.0.0.1 5635";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        ApplicationSettings settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        ClientSettings clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.GRAPHICAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetworkMode());

        // Test client running mode with an extra invalid argument (36).
        argsString = "127.0.0.1 5635 36";
        args = getArgs(argsString);

        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test inverted order of the two arguments.
        argsString = "5635 127.0.0.1";
        args = getArgs(argsString);

        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test lower limit of the port number.
        int port = ServerSettings.PORT_MIN;
        argsString = "127.0.0.1 " + port;
        args = getArgs(argsString);
        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check port value.
        checkClientPortNumber(ServerSettings.PORT_MIN, cmdArgs);

        // Test upper limit of the port number.
        port = ServerSettings.PORT_MAX;
        argsString = "127.0.0.1 " + port;
        args = getArgs(argsString);
        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check port value.
        checkClientPortNumber(ServerSettings.PORT_MAX, cmdArgs);

        // Test (wrong) value (MIN-1).
        argsString = "127.0.0.1 " + (ServerSettings.PORT_MIN - 1);
        args = getArgs(argsString);
        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test (wrong) value (MAX+1).
        argsString = "127.0.0.1 " + (ServerSettings.PORT_MAX + 1);
        args = getArgs(argsString);
        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

    }

    private static void checkClientPortNumber(int expected, CmdLineArgs args) {
        assertEquals(expected, args.getSettings().getClientSettings().getPort());
    }

    /**
     * To show the help of the application, the -h or --help flags
     * shall be the only flag specified after the app name at the
     * command line. Test this behavior for the readCmdLineArgs()
     * method.
     */
    @Test
    @DisplayName("-h/--help test")
    public void showHelpTest() {

        // Test single option short flag "-h".
        String argsString = "-h";
        String[] args = getArgs(argsString);

        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        assertTrue(cmdArgs.showHelp());

        // Test single option long flag "--help".
        argsString = "--help";
        args = getArgs(argsString);

        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        assertTrue(cmdArgs.showHelp());

        // Test wrong multiple options (client mode).
        argsString = "localhost 2236 -h -t";
        args = getArgs(argsString);

        try {
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert (false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test wrong multiple options (client mode).
        argsString = "localhost 2236 --help -r";
        args = getArgs(argsString);

        try {
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert (false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }
    }

    /**
     * The user can set the application to run in client mode with
     * the following options: <ul>
     *     <li>-r/--rmi: use RMI network</li>
     *     <li>no options: Socket network</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for these options.
     */
    @Test
    @DisplayName("-r/--rmi test")
    public void rmiOptionTest() {

        // Test no option.
        String argsString = "127.0.0.1 5635";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        ApplicationSettings settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        ClientSettings clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.GRAPHICAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetworkMode());

        // Test single option short flag "-r" (RMI).
        argsString = "127.0.0.1 5635 -r";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.GRAPHICAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());

        // Test single option long flag "--rmi" (RMI).
        argsString = "127.0.0.1 5635 --rmi";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.GRAPHICAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());

        // Test option long flag "-r" (RMI) with also "-t".
        argsString = "127.0.0.1 5635 -r -t";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());

        // Test option long flag "-r" (RMI) with also "-t", reverse order.
        argsString = "127.0.0.1 5635 -t -r";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());

    }

    /**
     * The user can set the application to run in client mode with
     * textual mode: <ul>
     *     <li>-t/--tui: use RMI network</li>
     *     <li>no options: graphical mode</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for these options.
     */
    @Test
    @DisplayName("-t/--tui test")
    public void tuiOptionTest() {

        // Test no option.
        String argsString = "127.0.0.1 5635";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        ApplicationSettings settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        ClientSettings clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.GRAPHICAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetworkMode());

        // Test single option short flag "-t" (TUI).
        argsString = "127.0.0.1 5635 -t";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetworkMode());

        // Test single option long flag "--tui" (TUI).
        argsString = "127.0.0.1 5635 --tui";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetworkMode());

        // Test option long flag "-r" (RMI) with also "-t" (TUI).
        argsString = "127.0.0.1 5635 -r -t";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());

        // Test option long flag "-r" (RMI) with also "-t" (TUI), reverse order.
        argsString = "127.0.0.1 5635 -t -r";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check showHelp() returns false.
        assertFalse(cmdArgs.showHelp());
        // Get the startup settings.
        settings = cmdArgs.getSettings();
        // Check client mode.
        assertEquals(Mode.CLIENT, settings.getMode());
        // Get the client settings.
        clientSettings = settings.getClientSettings();
        // Check ip address.
        assertEquals("127.0.0.1", clientSettings.getIp());
        // Check port number.
        assertEquals(5635, clientSettings.getPort());
        // Check client mode.
        assertEquals(ClientMode.TEXTUAL, clientSettings.getMode());
        // Check network mode.
        assertEquals(NetworkMode.RMI, clientSettings.getNetworkMode());
    }
    /**
     * To run the application in server mode, the user specify the app name
     * and an optional argument:<ul>
     *     <li>optional: the port number (in the range [1024, 65035]</li>
     * </ul>
     * This performs the following tests, on the readCmdLineArgs() method of
     * the CmdLineArgsReader class:<ul>
     *     <li>With no (zero) arguments, the settings mode is server.</li>
     *     <li>With one argument, the settings mode is server.</li>
     *     <li>Check the limit of the valid port number (IllegalArgumentException if out of range)</li>
     * </ul>
     */
    @Test
    @DisplayName("Server arguments test")
    public void serverArgumentsTest() {

        // Test server running mode (no arguments).
        String argsString = "";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.empty(),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_MODE_PORT
        );

        // Test server running mode with one valid argument (port number).
        argsString = "1196";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one valid argument (port number),
        // use the lower limit.
        argsString = String.format("%d", ServerSettings.PORT_MIN);
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(ServerSettings.PORT_MIN),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one valid argument (port number),
        // use the upper limit.
        argsString = String.format("%d", ServerSettings.PORT_MAX);
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(ServerSettings.PORT_MAX),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one invalid argument (port number),
        // use one less than the lower limit.
        argsString = String.format("%d", ServerSettings.PORT_MIN - 1);
        args = getArgs(argsString);

        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test server running mode with one invalid argument (port number),
        // use one over than the upper limit.
        argsString = String.format("%d", ServerSettings.PORT_MAX + 1);
        args = getArgs(argsString);

        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

    }

    /**
     * The user can set the server to automatically find an available
     * port: <ul>
     *     <li>-a/--auto: set "auto" port mode</li>
     *     <li>no options: if the port number id specified, "fixed" mode, otherwise "auto"</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for this option.
     */
    @Test
    @DisplayName("-a/--auto test")
    public void autoOptionTest() {

        // Test server running mode (no arguments), but the
        // port mode "auto" option (short flag "-a").
        String argsString = "-a";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.empty(),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_MODE_PORT
        );

        // Test server running mode (no arguments), but the
        // port mode "auto" option (short flag "-a").
        argsString = "--auto";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.empty(),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_MODE_PORT
        );

        // Test server running mode with one valid argument (port number),
        // and the port mode "auto" (short flag "-a").
        argsString = "1196 -a";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                PortMode.AUTO
        );

        // Test server running mode with one valid argument (port number),
        // and the port mode "auto" (long flag "--auto").
        argsString = "1196 --auto";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                ServerSettings.DEF_NETWORK,
                ServerSettings.DEF_VERBOSITY,
                PortMode.AUTO
        );

    }

    /**
     * The user can set the server to use the RMI network: <ul>
     *     <li>-r/--rmi: use RMI</li>
     *     <li>no options: use socket</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for this option.
     */
    @Test
    @DisplayName("-r/--rmi test")
    public void networkModeTest() {

        // Test server running mode (no arguments), but the
        // network is set to RMI (short flag "-r").
        String argsString = "-r";
        String[] args = getArgs(argsString);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.empty(),
                ServerSettings.DEF_MAX_LOBBIES,
                NetworkMode.RMI,
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_MODE_PORT
        );

        // Test server running mode (no arguments), but the
        // port mode "auto" option (long flag "--rmi").
        argsString = "--rmi";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.empty(),
                ServerSettings.DEF_MAX_LOBBIES,
                NetworkMode.RMI,
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_MODE_PORT
        );

        // Test server running mode with one valid argument (port number),
        // without options.
        argsString = "1196";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                NetworkMode.SOCKET,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one valid argument (port number),
        // and the rmi flag (short flag "-r").
        argsString = "1196 -r";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                NetworkMode.RMI,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one valid argument (port number),
        // and the rmi flag (long flag "--rmi").
        argsString = "1196 --rmi";
        args = getArgs(argsString);

        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(1196),
                ServerSettings.DEF_MAX_LOBBIES,
                NetworkMode.RMI,
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );
    }

    private static void checkServerCmdLineArgs(CmdLineArgs args, OptionalInt port, int maxLobbies, NetworkMode network, VerbosityLevel verbodity, PortMode portMode) {

        // Check showHelp() returns false.
        assertFalse(args.showHelp());
        // Get the startup settings.
        ApplicationSettings settings = args.getSettings();
        // Check client mode.
        assertEquals(Mode.SERVER, settings.getMode());
        // Get the client settings.
        ServerSettings serverSettings = settings.getServerSettings();
        // Check port number.
        assertEquals(port, serverSettings.getPort());
        // Check max lobbies.
        assertEquals(maxLobbies, serverSettings.getMaxLobbies());
        // Check network mode.
        assertEquals(network, serverSettings.getNetworkMode());
        // Check verbosity.
        assertEquals(verbodity, serverSettings.getVerbosity());
        // Check port mode.
        assertEquals(portMode, serverSettings.getPortMode());

    }

    private static String[] getArgs(String cmdLineText) {
        if (cmdLineText == null || cmdLineText.isEmpty() || cmdLineText.isBlank()) {
            return new String[] { };
        }
        return cmdLineText.split(" ");
    }
}
