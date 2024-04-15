package it.polimi.ingsw.settingsTests;

import it.polimi.ingsw.am52.settings.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetwork());

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
        int port = CmdLineArgsReader.PORT_MIN;
        argsString = "127.0.0.1 " + port;
        args = getArgs(argsString);
        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check port value.
        checkClientPortNumber(CmdLineArgsReader.PORT_MIN, cmdArgs);

        // Test upper limit of the port number.
        port = CmdLineArgsReader.PORT_MAX;
        argsString = "127.0.0.1 " + port;
        args = getArgs(argsString);
        // Parse the command line args.
        cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
        // Check port value.
        checkClientPortNumber(CmdLineArgsReader.PORT_MAX, cmdArgs);

        // Test (wrong) value (MIN-1).
        argsString = "127.0.0.1 " + (CmdLineArgsReader.PORT_MIN - 1);
        args = getArgs(argsString);
        try {
            // Parse the command line args.
            cmdArgs = CmdLineArgsReader.readCmdLineArgs(args);
            assert(false);
        } catch (IllegalArgumentException ex) {
            assert(true);
        }

        // Test (wrong) value (MAX+1).
        argsString = "127.0.0.1 " + (CmdLineArgsReader.PORT_MAX + 1);
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
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.SOCKET, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

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
        assertEquals(NetworkMode.RMI, clientSettings.getNetwork());

    }

    private static String[] getArgs(String cmdLineText) {
        return cmdLineText.split(" ");
    }
}
