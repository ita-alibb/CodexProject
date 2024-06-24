package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.network.server.ServerConnection;
import it.polimi.ingsw.am52.settings.*;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.tui.TuiApplication;

import java.rmi.RemoteException;

/**
 * The main class of the application.
 */
public class Codex {

    public static final String APP_NAME = "Codex";

    /**
     * The entry point of the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        try {

            // Parse command line args.
            CmdLineArgs cmdLineArgs = CmdLineArgsReader.readCmdLineArgs(args);

            // Check if the user required to display the help.
            if (cmdLineArgs.showHelp()) {
                ShowHelp();
                return;
            }

            // Get application settings.
            ApplicationSettings appSettings = cmdLineArgs.getSettings();

            // Check application mode: Client or Server.
            switch (appSettings.getMode()) {
                case Mode.SERVER:
                    runServer(appSettings.getServerSettings());
                    break;
                case Mode.CLIENT:
                    runClient(appSettings.getClientSettings());
                    break;
            }

        } catch (Exception ex) {
            // Log any exception message.
            System.out.println(
                    String.format("Error:%n  %s%n", ex.getMessage())
            );
            System.out.println(
                    String.format("Run \"%s %s/%s\" for help/usage.",
                            APP_NAME,
                            CmdLineArgsReader.getHelpOption().getShortFlag(),
                            CmdLineArgsReader.getHelpOption().getLongFlag())
            );
        }

    }

    /**
     * Run in server mode, with specified settings.
     * @param settings The server settings.
     */
    private static void runServer(ServerSettings settings) throws RemoteException {

        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        ServerConnection connection = new ServerConnection(settings);

        connection.run();
    }

    /**
     * Run in client mode, with specified settings.
     * @param settings The server settings.
     */
    private static void runClient(ClientSettings settings) {
        switch (settings.getMode()) {
            case ClientMode.TEXTUAL:
                runTuiClient(settings.getServerIp(), settings.getPort(),settings.getNetworkMode());
                break;
            case ClientMode.GRAPHICAL:
                runGuiClient(settings.getServerIp(), settings.getPort(),settings.getNetworkMode());
                break;
        }
    }

    private static void runGuiClient(String serverIp, int port, NetworkMode connection) {
        GuiApplication.run(serverIp, port, connection);
    }

    /**
     * Start client application with specified settings.
     * @param serverIp The server Ip.
     * @param port The port number.
     * @param connection The type of connection (TCP or RMI)
     */
    private static void runTuiClient(String serverIp, int port, NetworkMode connection) {
        TuiApplication.run(serverIp, port, connection);
    }

    /**
     * Display the help of the application on the console.
     */
    private static void ShowHelp() {
        StringBuilder helpText = new StringBuilder();
        helpText.append(String.format("%s usage.%n", APP_NAME));
        helpText.append(getDisplayHelpText());
        helpText.append(getServerHelpText());
        helpText.append(getClientHelpText());

        System.out.println(helpText.toString());
    }

    /**
     *
     * @return The help message for the "Help" section.
     */
    private static String getDisplayHelpText() {
        StringBuilder helpText = new StringBuilder();
        helpText.append(String.format("Display help/usage instructions:%n"));
        helpText.append(String.format("  %s %s%n", APP_NAME, CmdLineArgsReader.getHelpOption().getDescription()));
        return helpText.toString();
    }

    /**
     *
     * @return The help message for the "Server" section.
     */
    private static String getServerHelpText() {
        StringBuilder helpText = new StringBuilder();
        helpText.append(String.format("Run application as game server:%n"));
        helpText.append(String.format("  %s [options]%n", APP_NAME));
        helpText.append(String.format("  Options:%n"));
        for (Option option : CmdLineArgsReader.getServerOptions()) {
            helpText.append(String.format("    %s%n", option.getDescription()));
        }
        return helpText.toString();
    }

    /**
     *
     * @return The help message for the "Client" section.
     */
    private static String getClientHelpText() {
        StringBuilder helpText = new StringBuilder();
        helpText.append(String.format("Run application as client:%n"));
        helpText.append(String.format("  %s <serverIp> <port> [options]%n", APP_NAME));
        helpText.append(String.format("  Params:%n"));
        helpText.append(String.format("    <serverIp>: the ip address of the server (or \"localhost\"%n"));
        helpText.append(String.format("    <port>: the port number of the server%n"));
        helpText.append(String.format("  Options:%n"));
        for (Option option : CmdLineArgsReader.getClientOptions()) {
            helpText.append(String.format("    %s%n", option.getDescription()));
        }
        return helpText.toString();
    }
}
