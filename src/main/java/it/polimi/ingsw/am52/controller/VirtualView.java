package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.json.response.Response;
import it.polimi.ingsw.am52.json.response.Status;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The VirtualView, it is instantiated in {@link it.polimi.ingsw.am52.network.ServerConnection} and assigned to a {@link ClientHandler}
 * All request from both RMI and TCP pass from the linked instance of this class.
 * RMI: client calls directly method implementation of {@link ActionsRMI}
 * TCP: the request arrived to the {@link it.polimi.ingsw.am52.network.tcp.ClientHandlerTCP} is executed by the linked VirtualView
 */
public class VirtualView extends UnicastRemoteObject implements ActionsRMI {
    /**
     * The clientId to which the View is referred
     */
    private final int clientId;

    /**
     * The GameController related to the View
     */
    private GameController gameController;

    /**
     * Constructor for the VirtualView, takes the same clientId as the related ClientHandler
     * @param clientId the id of the ClientHandler
     */
    public VirtualView(int clientId) throws RemoteException {
        this.clientId = clientId;
    }

    /**
     * Method used by the {@link it.polimi.ingsw.am52.network.tcp.ClientHandlerTCP} to dispatch the execution of the method, it is thrown on new thread.
     * @param jsonMsg the json message read from the socket
     * @throws NoSuchMethodException when the request method do not exist
     */
    public Response<?> execute(String jsonMsg) throws NoSuchMethodException {
        ClientRequest request;
        try {
            request = JsonDeserializer.deserializeRequest(jsonMsg);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "from id: " + clientId);
            //if the message cannot be deserialized we ignore it
            return null;
        }

        Response res;
        try {
            res = switch (request.getMethod()) {
                case JsonDeserializer.CREATE_LOBBY_METHOD -> this.createLobby((CreateLobbyData) request.getData());
                case JsonDeserializer.JOIN_LOBBY_METHOD -> this.joinLobby((JoinLobbyData) request.getData());
                case JsonDeserializer.LEAVE_GAME_METHOD -> this.leaveGame((LeaveGameData) request.getData());
                default -> throw new NoSuchMethodException("no method " + request.getMethod());
            };
        } catch (RemoteException e) {
            // Add default response error
            res = new Response<>(new Status(), 2, "Error: " + e.getMessage() );
        }

        return res;
    }

    /**
     * Method to call the disconnection, if the handler is not in a Game then it is called only in the ServerController
     * @param handler the handler to delete
     */
    public void disconnect(ClientHandler handler){
        if (this.gameController != null){
            this.gameController.disconnect(handler);
        } else {
            ServerController.getInstance().disconnect(handler);
        }
    }

    // region Actions

    //TODO: All this actions are implementation of the actual execution, and they must return the data needed in RMI, those are the actions exposed in the network for RMI

    /**
     * Method to perform the joinLobby Request
     * @param data the request
     */
    public Response<String> joinLobby(JoinLobbyData data) throws RemoteException {
        var response = ServerController.getInstance().joinLobby(this.clientId, data);

        // the client has joined a lobby, set the GameController, in this way we remove the bottleneck on ServerController by using directly the related GameController
        if (response.errorCode == 0) {
            this.gameController = ServerController.getInstance().getGameController(data.getLobbyId()).get();
        }

        this.broadcast(response);
        return response;
    }

    /**
     * Method to perform the createLobby Request
     */
    public Response<Integer> createLobby(CreateLobbyData data) throws RemoteException  {
        var response = ServerController.getInstance().createLobby(this.clientId, data);

        // the client has joined a lobby, set the GameController, in this way we remove the bottleneck on ServerController by using directly the related GameController
        //The LobbyId is in the response of the controller TODO: the response must be implemented as the requests
        if (response.errorCode == 0) {
            this.gameController = ServerController.getInstance().getGameController(response.body).get();
        }

        this.broadcast(response);
        return response;
    }

    /**
     * Method to perform the leaveGame Request
     * @param data the request
     */
    public Response<String> leaveGame(LeaveGameData data) throws RemoteException {
        // The request here is useless, the only thing needed is the clientId, the body is empty
        var response = this.gameController.leaveLobby(this.clientId);

        this.broadcast(response);

        // Particular case, when you leave then you have to set the reference to null
        if (response.errorCode == 0) {
            this.gameController = null;
        }
        return response;
    }

    //endregion

    /**
     * Method called at last step of every execution.
     * If the response is correct and the Client is in a Game the response is broadcast to every other user in the game.
     * The list of client is retrieved from the GameController
     * @param response the Response to send to the client's. TODO: the response could be personalized for every client (ex: startGame, every client has a different response)
     */
    private void broadcast(Response<?> response) {
        if (this.gameController != null && response.errorCode == 0) {
            // Get the handlers of the Game
            var handlers = this.gameController.handlerToBroadcast(this.clientId);

            try {
                for (var handler : handlers) {
                    handler.sendMessage(response);
                }
            } catch (Exception e) {
                throw new RuntimeException(e); // TODO: Error handling
            }
        }
    }
}
