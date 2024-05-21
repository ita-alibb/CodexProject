package it.polimi.ingsw.am52.network.server;

import it.polimi.ingsw.am52.controller.ServerController;
import it.polimi.ingsw.am52.controller.VirtualView;
import it.polimi.ingsw.am52.network.server.rmi.Accepter;
import it.polimi.ingsw.am52.network.server.rmi.ClientHandlerRMI;
import it.polimi.ingsw.am52.network.client.RemoteConnection;
import it.polimi.ingsw.am52.network.server.tcp.ClientHandlerTCP;

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
     * The unique ID counter
     */
    private int clientId = 0;

    /**
     * The serverSocket
     */
    private final ServerSocket serverSocket;

    /**
     * The registry in which the RMI objects are exposed
     */
    private final Registry registry;

    /**
     * The executor service that contains the running Clients
     */
    private final ExecutorService clientConnections;

    /**
     * Initialize the ServerConnection,
     * Creates the ServerSocket and exports the ServerConnection to the network.
     * @param portTCP the port in which the TCP socket is opened
     * @param portRMI the port in which the RMI registry is created
     * @param numberOfClients the limit of accepted clients
     */
    public ServerConnection(int portTCP, int portRMI, int numberOfClients) throws RemoteException {
        try {
            this.serverSocket = new ServerSocket(portTCP);

            this.registry = LocateRegistry.createRegistry(portRMI);

            // Bind the remote object's stub in the registry
            this.registry.rebind("SERVER_CONNECTION", this);


            //not hardcoded, taken from config. Every thread is a client
            this.clientConnections = Executors.newFixedThreadPool(numberOfClients);

            System.err.println("Server ready");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                    System.out.println("Client " + newId + " connected via TCP");
                }
                catch (Exception e) {
                    socket.close();
                    throw e;
                }
            } catch (Exception ex) {
                //TODO: Log message
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
        this.registry.rebind("VirtualView:"+newId, virtualView);

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
}
