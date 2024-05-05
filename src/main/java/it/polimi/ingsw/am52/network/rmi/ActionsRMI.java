package it.polimi.ingsw.am52.network.rmi;

import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of all Actions that the client can do in the server after connection
 * This is the interface exposed in the network
 * Implemented by {@link it.polimi.ingsw.am52.controller.VirtualView}
 */
public interface ActionsRMI extends Remote {
    /**
     * Method to perform the joinLobby Request
     *
     * @param data the request
     */
    Response<String> joinLobby(JoinLobbyData data) throws RemoteException;

    /**
     * Method to perform the createLobby Request
     *
     * @param data the request
     */
    Response<Integer> createLobby(CreateLobbyData data) throws RemoteException;

    /**
     * Method to perform the leaveGame Request
     *
     * @param data the request
     */
    Response<String> leaveGame(LeaveGameData data) throws RemoteException;
}
