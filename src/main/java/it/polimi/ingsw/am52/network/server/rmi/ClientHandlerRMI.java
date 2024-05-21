package it.polimi.ingsw.am52.network.server.rmi;

import it.polimi.ingsw.am52.controller.VirtualView;
import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.network.server.ClientHandler;
import it.polimi.ingsw.am52.network.client.RemoteConnection;

import java.rmi.RemoteException;

/**
 * Implementation of {@link ClientHandler} for RMI connections
 */
public class ClientHandlerRMI implements ClientHandler,Runnable {
    /**
     * The client id
     */
    private final int clientId;

    /**
     * The client object exposed by the client
     */
    private final RemoteConnection client;

    /**
     * The virtual view assigned to the client, it is called directly by the Client because it is exposed in the network
     */
    private final VirtualView view;

    /**
     * Class constructor
     * @param clientId the unique clientId generated by the server
     * @param client the client instance received through the network
     * @param virtualView the virtual view instantiated by the server
     */
    public ClientHandlerRMI(int clientId, RemoteConnection client, VirtualView virtualView) {
        this.clientId = clientId;
        this.client = client;
        this.view = virtualView;
    }

    /**
     * Get the clientId
     * @return the client id
     */
    @Override
    public int getClientId() {
        return this.clientId;
    }

    /**
     * Thread run to check that the client is still connected, if exception is thrown then the client is disconnected
     */
    @Override
    public void run() {
        while (true) {
            try {
                this.client.heartBeat();
                Thread.sleep(10000);
            } catch (RemoteException | InterruptedException e) {
                // TODO: disconnection
                System.out.println("Client disconnected: " + this.clientId);
                break;
            }
        }

        this.view.disconnect(this);
    }

    /**
     * Method used to forward a response to the Client.
     * Calls a method in the client's object to process the response in the client, it will be processed in another thread to speed-up de server
     * @param response the message to send
     */
    @Override
    public void sendMessage(JsonMessage<BaseResponseData> response) {
        try {
            this.client.sendMessage(response.getData());
        } catch (RemoteException e) {
            // TODO: better logging
            System.out.println("Error on sending for client " + this.clientId + "exception: " + e.getMessage());
        }
    }
}