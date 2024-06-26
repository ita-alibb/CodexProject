package it.polimi.ingsw.am52.network.server;

import it.polimi.ingsw.am52.controller.ServerController;
import it.polimi.ingsw.am52.controller.VirtualView;
import it.polimi.ingsw.am52.network.server.logging.Log;
import it.polimi.ingsw.am52.network.server.logging.LogMessage;
import it.polimi.ingsw.am52.network.server.rmi.Accepter;
import it.polimi.ingsw.am52.network.server.rmi.ClientHandlerRMI;
import it.polimi.ingsw.am52.network.client.RemoteConnection;
import it.polimi.ingsw.am52.network.server.tcp.ClientHandlerTCP;
import it.polimi.ingsw.am52.settings.PortMode;
import it.polimi.ingsw.am52.settings.ServerSettings;
import it.polimi.ingsw.am52.settings.VerbosityLevel;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ServerConnection class, it handles all the connection establishing in the Server.
 * Initialize both TCP and RMI connections and takes reference to all clientHandler running instances.
 */
public class ServerConnection extends UnicastRemoteObject implements Accepter, Runnable{

    /**
     * The stub name.
     */
    public static final String STUB_NAME = "SERVER_CONNECTION";

    /**
     * The root label for the registry.
     */
    public static final String REGISTRY_ROOT = "VirtualView:";

    /**
     * The unique ID counter
     */
    private int clientId = 0;

    /**
     * The serverSocket
     */
    private final ServerSocket serverSocket;
    /**
     * The TCP port number of the server.
     */
    private final int tcpPort;

    /**
     * The registry in which the RMI objects are exposed
     */
    private final Registry registry;

    /**
     * The executor service that contains the running Clients
     */
    private final ExecutorService clientConnections;

    /**
     * The log instance of the server.
     */
    private final Log serverLog;

    /**
     * Initialize the ServerConnection,
     * Creates the ServerSocket and exports the ServerConnection to the network.
     * @param settings The server startup settings
     * @throws RemoteException If the socket creation fails.
     * @throws IllegalArgumentException If any settings' value is not valid (detail in the exception message)
     */
    public ServerConnection(ServerSettings settings) throws RemoteException, IllegalArgumentException {
        try {

            // Create the server log instance.
            this.serverLog = new Log(settings.getVerbosity());

            // Log the server settings.
            displayStartupSettings(settings);

            // Create the socket for the TCP connection
            this.serverSocket = openTcpServerSocket(settings.getSocketPort(), settings.getPortMode());

            // Store the tcp port number (it may be automatically allocated).
            this.tcpPort = this.serverSocket.getLocalPort();

            this.registry = LocateRegistry.createRegistry(settings.getRmiPort());

            // Bind the remote object's stub in the registry
            this.registry.rebind(STUB_NAME, this);

            //not hardcoded, taken from config. Every thread is a client
            this.clientConnections = Executors.newFixedThreadPool(settings.getMaxLobbies());

            // Display TCP port number (this is not a log message, but an application output).
            System.out.println(
                    String.format("Server TCP listening on port n. %d", this.tcpPort)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void displayStartupSettings(ServerSettings settings) {
        printInfoMessage(String.format("(settings) Max lobbies = %d", settings.getMaxLobbies()));
        printInfoMessage(String.format("(settings) Socket port = %d", settings.getSocketPort()));
        printInfoMessage(String.format("(settings) Rmi port = %d", settings.getRmiPort()));
        printInfoMessage(String.format("(settings) Socket port mode = %s", settings.getPortMode()));
        printInfoMessage(String.format("(settings) Log verbosity = %s", settings.getVerbosity()));
    }

    /**
     * Create a socket for the TCP connection.
     * @param socketPort The port number the socket shall be bound to
     * @param portMode The method for selecting the port number (fixed or auto)
     * @return The server socket instance
     * @throws IOException If the socket creation failed
     * @throws IllegalArgumentException If the specified port number is invalid.
     */
    private ServerSocket openTcpServerSocket(int socketPort, PortMode portMode) throws IOException {

        ServerSocket socket = null;
        try {

            printVerboseMessage(String.format("Opening TCP socket on specified port %d", socketPort));

            // First, attempt to open connection on specified port number.
            socket = new ServerSocket(socketPort);
        }
        catch (IllegalArgumentException argEx) {
            String errMessage = String.format("Invalid TCP socket port %d.", socketPort);

            printErrorMessage(errMessage);

            throw new IllegalArgumentException(errMessage, argEx);
        }
        catch (IOException ioEx) {
            if (portMode == PortMode.FIXED) {

                String errMessage = String.format("Failed to open TCP connection on port %d.", socketPort);

                printErrorMessage(errMessage);

                throw new IOException(errMessage, ioEx);
            }
            else {
                // Open a connection bound to an automatically allocated port (pass zero as argument).
                try {

                    printVerboseMessage("Opening TCP socket on automatically allocated port");

                    socket = new ServerSocket(0);
                }
                catch (IOException ex) {

                    String errMessage = "Failed to open TCP connection bound to an automatically allocated port.";

                    printErrorMessage(errMessage);

                    throw new IOException(errMessage, ioEx);
                }
            }
        }

        printInfoMessage(String.format("TCP connection bounded to port n. %d", socket.getLocalPort()));

        return socket;
    }

    /**
     * Endless loop method, listening to client to connect to the ServerSocket.
     * When a client tries to connect to the server a new unique ID is generated and a new ClientHandlerTCP is created.
     * The Handler object is on server side, TODO: on client side there is another class that handles the send of request and response.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                try {
                    int newId = this.getUniqueClientId();
                    ClientHandler clientHandler = new ClientHandlerTCP(newId, socket, new VirtualView(newId));

                    // add Handler to the Server, if fails close connection
                    ServerController.getInstance().addHandler(clientHandler);
                    this.clientConnections.execute(clientHandler);

                    // Log Info message
                    printInfoMessage(String.format("Client %d connected via TCP", newId));
                }
                catch (Exception e) {
                    socket.close();
                    throw e;
                }
            } catch (Exception ex) {
                String errMessage = String.format("Error creating new Client Handler. Exception: %s, Exception msg: %s",
                        ex.getClass().getName(), ex.getMessage());
                printErrorMessage(errMessage);
            }
        }
    }

    /**
     * Method exposed to the network by the Server and called by the client.
     * It generates a new ID, creates the ClientHandleRMI to handle the RMI client.
     * Expose the linked VirtualView in the network with the id of the client.
     * Add the client to the server and runs it.
     * @param client The client object that will be called to forward the message of broadcast
     * @throws RemoteException Implementation of Remote interface
     */
    @Override
    public int accept(RemoteConnection client) throws RemoteException {
        int newId = this.getUniqueClientId();

        VirtualView virtualView = new VirtualView(newId);

        ClientHandler clientHandler = new ClientHandlerRMI(newId, client, virtualView);

        // Export the VirtualView to the client, it will call the Virtual view directly
        this.registry.rebind(REGISTRY_ROOT+newId, virtualView);

        // add Handler to the Server, if fails close connection
        ServerController.getInstance().addHandler(clientHandler);
        this.clientConnections.execute(clientHandler);

        System.out.println("Client " + newId + " connected via RMI");
        return newId;
    }

    /**
     * Method to get unique client ids
     */
    private synchronized int getUniqueClientId() {
        return ++clientId;
    }

    /**
     * Send a message to the log. The message has a verbosity level set to Verbose.
     * @param message The text of the message.
     */
    private void printVerboseMessage(String message) {
        printLogMessage(message, VerbosityLevel.VERBOSE);
    }


    /**
     * Send a message to the log. The message has a verbosity level set to Information.
     * @param message The text of the message.
     */
    private void printInfoMessage(String message) {
        printLogMessage(message, VerbosityLevel.INFO);
    }


    /**
     * Send a message to the log. The message has a verbosity level set to Warning.
     * @param message The text of the message.
     */
    private void printWarningMessage(String message) {
        printLogMessage(message, VerbosityLevel.WARNING);
    }


    /**
     * Send a message to the log. The message has a verbosity level set to Error.
     * @param message The text of the message.
     */
    private void printErrorMessage(String message) {
        printLogMessage(message, VerbosityLevel.ERROR);
    }

    /**
     * Send a message to the log, specifying the message's verbosity level.
     * @param message The text of the message.
     * @param level The verbosity level of the message.
     */
    private void printLogMessage(String message, VerbosityLevel level) {
        LogMessage logMessage = new LogMessage(message, level);
        this.serverLog.printMessage(logMessage);
    }
}
