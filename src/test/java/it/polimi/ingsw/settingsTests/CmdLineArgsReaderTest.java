package it.polimi.ingsw.settingsTests;

import it.polimi.ingsw.am52.settings.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

        // Test inverted order of the two arguments.
        argsString = "5635 127.0.0.1";
        args = getArgs(argsString);
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

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
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

        // Test (wrong) value (MAX+1).
        argsString = "127.0.0.1 " + (ServerSettings.PORT_MAX + 1);
        args = getArgs(argsString);
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

    }

    private static void checkReadThrows(String[] args) {
        assertThrows(IllegalArgumentException.class, () -> CmdLineArgsReader.readCmdLineArgs(args));
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
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

        // Test wrong multiple options (client mode).
        argsString = "localhost 2236 --help -r";
        args = getArgs(argsString);
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);
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
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_PORT_MODE
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
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );

        // Test server running mode with one invalid argument (port number),
        // use one less than the lower limit.
        argsString = String.format("%d", ServerSettings.PORT_MIN - 1);
        args = getArgs(argsString);
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

        // Test server running mode with one invalid argument (port number),
        // use one over than the upper limit.
        argsString = String.format("%d", ServerSettings.PORT_MAX + 1);
        args = getArgs(argsString);
        // Check the read method throws IllegalArgumentException.
        checkReadThrows(args);

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
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_PORT_MODE
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
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_PORT_MODE
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
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_PORT_MODE
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
                ServerSettings.DEF_VERBOSITY,
                ServerSettings.DEF_PORT_MODE
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
                ServerSettings.DEF_VERBOSITY,
                PortMode.FIXED
        );
    }

    /**
     * The user can set log verbosity of the server: <ul>
     *     <li>-v/--verbosity lvl: set the verbosity to level lvl, in range [1, 4]</li>
     *     <li>no options: verbosity level is 3 (info)</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for this option.
     */
    @Test
    @DisplayName("-v/--verbosity test")
    public void verbosityTest() {

        // Test server running mode (no arguments), without any option.
        // The default verbosity level is info (3).
        String argsString = "";
        String[] args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.INFO, args);

        // Test server running mode with the port argument, without any option.
        // The default verbosity level is info (3).
        argsString = "1335";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.INFO, args);

        // Test server running mode (no arguments).
        // Set verbosity level to 1;
        argsString = "-v 1";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.ERROR, args);

        // Test server running mode (no arguments).
        // Set verbosity level to 1;
        argsString = "--verbosity 1";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.ERROR, args);

        // Test server running mode, one argument (port number).
        // Set verbosity level to 1;
        argsString = "4599 -v 1";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.ERROR, args);

        // Test server running mode, one argument (port number).
        // Set verbosity level to 1;
        argsString = "4599 --verbosity 1";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.ERROR, args);

        // Test server running mode (no arguments).
        // Set verbosity level to 2;
        argsString = "-v 2";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.WARNING, args);

        // Test server running mode, one argument (port number).
        // Set verbosity level to 2;
        argsString = "4599 -v 2";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.WARNING, args);

        // Test server running mode (no arguments).
        // Set verbosity level to 3;
        argsString = "-v 3";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.INFO, args);

        // Test server running mode, one argument (port number).
        // Set verbosity level to 2;
        argsString = "4599 -v 3";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.INFO, args);

        // Test server running mode (no arguments).
        // Set verbosity level to 4;
        argsString = "-v 4";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test server running mode, one argument (port number).
        // Set verbosity level to 4;
        argsString = "4599 -v 4";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test throws IllegalArgumentException if the option is specified,
        // but not its argument.
        argsString = "-v";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws IllegalArgumentException if the option is specified,
        // but not its argument.
        argsString = "367 -v";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "-v 0";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "367 -v 0";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "-v 5";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "367 -v 5";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "-v info";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test throws if the argument is not valid.
        argsString = "367 -v info";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test with other options.
        // Set verbosity and auto mode;
        argsString = "-v 4 -a";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test with other options.
        // Set verbosity and auto mode;
        argsString = "4599 -v 4 -a";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test with other options.
        // Set verbosity and auto mode;
        argsString = "-a -v 4";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test with other options.
        // Set verbosity and auto mode;
        argsString = "4599 -a -v 4";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Test with other options.
        // Set verbosity and auto mode;
        argsString = "4599 -a --verbosity 4";
        args = getArgs(argsString);
        checkVerbosity(VerbosityLevel.VERBOSE, args);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check all settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(4599),
                ServerSettings.DEF_MAX_LOBBIES,
                VerbosityLevel.VERBOSE,
                PortMode.AUTO
        );
    }

    private static void checkVerbosity(VerbosityLevel expected, String[] args) {
        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        assertEquals(expected, cmdArgs.getSettings().getServerSettings().getVerbosity());
    }

    /**
     * The user can set the maximum number of concurrent games on the server: <ul>
     *     <li>-l/--limit max: set max number of concurrent games, in range [1, MAX_LOBBIES]</li>
     *     <li>no options: the limit is DEF_MAX_LOBBIES (=1000)</li>
     * </ul>.
     * Test if the CmdLineArgsReader.readCmdLineArgs() works correctly
     * for this option.
     */
    @Test
    @DisplayName("-l/--limit test")
    public void limitTest() {

        // Test server running mode (no arguments), without any option.
        // The default max lobbies is DEF_MAX_LOBBIES.
        String argsString = "";
        String[] args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.DEF_MAX_LOBBIES, args);

        // Test server running mode with the port argument, without any option.
        // The default max lobbies is DEF_MAX_LOBBIES.
        argsString = "1335";
        args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.DEF_MAX_LOBBIES, args);

        // Test server running mode (no arguments).
        // Set limit to 1;
        argsString = "-l 1";
        args = getArgs(argsString);
        checkMaxLobbies(1, args);

        // Test server running mode (no arguments).
        // Set limit to 1;
        argsString = "--limit 1";
        args = getArgs(argsString);
        checkMaxLobbies(1, args);

        // Test server running mode, one argument (port number).
        // Set limit to 1;
        argsString = "1244 -l 1";
        args = getArgs(argsString);
        checkMaxLobbies(1, args);

        // Test server running mode, one argument (port number).
        // Set limit to 1;
        argsString = "1244 --limit 1";
        args = getArgs(argsString);
        checkMaxLobbies(1, args);

        // Test server running mode (no arguments).
        // Set limit to 653;
        argsString = "-l 653";
        args = getArgs(argsString);
        checkMaxLobbies(653, args);

        // Test server running mode, one argument (port number).
        // Set limit to 1;
        argsString = "1244 -l 653";
        args = getArgs(argsString);
        checkMaxLobbies(653, args);

        // Test server running mode (no arguments).
        // Set limit to MIN_LOBBIES;
        argsString = "-l " + ServerSettings.MIN_LOBBIES;
        args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.MIN_LOBBIES, args);

        // Test server running mode, one argument (port number).
        // Set limit to MIN_LOBBIES;
        argsString = "1244 -l " + ServerSettings.MIN_LOBBIES;
        args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.MIN_LOBBIES, args);

        // Test server running mode (no arguments).
        // Set limit to MAX_LOBBIES;
        argsString = "-l " + ServerSettings.MAX_LOBBIES;
        args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.MAX_LOBBIES, args);

        // Test server running mode, one argument (port number).
        // Set limit to MAX_LOBBIES;
        argsString = "1244 -l " + ServerSettings.MAX_LOBBIES;
        args = getArgs(argsString);
        checkMaxLobbies(ServerSettings.MAX_LOBBIES, args);

        // Test out of range values.
        // Set to MIN_LOBBIES - 1.
        argsString = "-l " + (ServerSettings.MIN_LOBBIES - 1);
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test out of range values.
        // Set to MIN_LOBBIES - 1.
        argsString = "4475 -l " + (ServerSettings.MIN_LOBBIES - 1);
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test out of range values.
        // Set to MAX_LOBBIES + 1.
        argsString = "-l " + (ServerSettings.MAX_LOBBIES + 1);
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test out of range values.
        // Set to MAX_LOBBIES + 1.
        argsString = "4475 -l " + (ServerSettings.MAX_LOBBIES + 1);
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test not integer values.
        argsString = "-l min";
        args = getArgs(argsString);
        checkReadThrows(args);
        argsString = "-l 6.3";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test missing option argument.
        argsString = "-l";
        args = getArgs(argsString);
        checkReadThrows(args);
        argsString = "3364 -l min";
        args = getArgs(argsString);
        checkReadThrows(args);

        // Test with other options.
        argsString = "-l 35 -r";
        args = getArgs(argsString);
        checkMaxLobbies(35, args);
        argsString = "-r -l 35";
        args = getArgs(argsString);
        checkMaxLobbies(35, args);
        argsString = "9963 -r --limit 35";
        args = getArgs(argsString);
        checkMaxLobbies(35, args);

        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        // Check all settings.
        checkServerCmdLineArgs(
                cmdArgs,
                OptionalInt.of(9963),
                35,
                VerbosityLevel.INFO,
                PortMode.FIXED
        );
    }

    private static void checkMaxLobbies(int expected, String[] args) {
        // Parse the command line args.
        CmdLineArgs cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);

        assertEquals(expected, cmdArgs.getSettings().getServerSettings().getMaxLobbies());
    }

    private static void checkServerCmdLineArgs(CmdLineArgs args, OptionalInt port, int maxLobbies, VerbosityLevel verbosity, PortMode portMode) {

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
        // Check verbosity.
        assertEquals(verbosity, serverSettings.getVerbosity());
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
