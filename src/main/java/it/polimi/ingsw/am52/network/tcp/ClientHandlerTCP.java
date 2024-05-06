package it.polimi.ingsw.am52.network.tcp;

import it.polimi.ingsw.am52.controller.VirtualView;
import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.network.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Implementation of {@link ClientHandler} for TCP connections
 */
public class ClientHandlerTCP implements ClientHandler {
    /**
     * The clientId
     */
    private final int clientId;

    /**
     * The client socket
     */
    private final Socket socket;

    /**
     * The client's out stream
     */
    private final PrintWriter out;

    /**
     * The client's in stream
     */
    private final BufferedReader in;

    /**
     * The client's VirtualView used to trigger the Controller
     */
    private final VirtualView view;

    /**
     * The class Constructor
     * @param clientId the unique clientId generated by the server
     * @param socket the client socket accepted by the server
     * @param virtualView the virtual view instantiated by the server
     * @throws Exception if the initialization of the in/out stream fails
     */
    public ClientHandlerTCP(int clientId,Socket socket, VirtualView virtualView) throws Exception {
        this.clientId = clientId;
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.view = virtualView;
    }

    /**
     * Get the clientId
     * @return the client id
     */
    @Override
    public int getClientId() {
        return clientId;
    }

    /**
     * Listening thread to all incoming messages from the client's socket.
     * When a message is received a new thread is created to handle the request in the virtual view.
     * After the execution of the thread the response is forwarded to the client, by this ClientHandler, by sendResponse method.
     */
    @Override
    public void run() {
        String jsonMsg;

        try {
            while((jsonMsg = in.readLine()) != null){
                System.out.println("received: " + jsonMsg + " from:" + this.getClientId());

                String finalJsonMsg = jsonMsg;
                new Thread(() -> {
                    try {
                        var res = this.view.execute(finalJsonMsg);

                        if (res != null){
                            try {
                                this.sendMessage(res);
                            } catch (Exception e) {
                                throw new RuntimeException(e); // TODO: Error handling
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        System.out.println("Handler "+ this.getClientId() + "throw exception:" + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            // break the loop and finally call the disconnection
        }
        this.view.disconnect(this);
    }

    /**
     * Method to send the response through the network, used both in broadcast and response of a request
     * @param response the message to send
     */
    @Override
    public void sendMessage(JsonMessage<BaseResponseData> response) {
        try {
            this.out.println(response.toJson());
        } catch (Exception e) {
            System.out.println("Exception on Serializing response: " + e.getMessage());
        }
    }
}
