package it.polimi.ingsw.am52.network.rmi;

import it.polimi.ingsw.am52.json.request.CreateLobbyData;
import it.polimi.ingsw.am52.json.request.JoinLobbyData;
import it.polimi.ingsw.am52.json.request.LeaveGameData;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of all Actions that the client can do in the server after connection
 * This is the interface exposed in the network
 * Implemented by {@link it.polimi.ingsw.am52.controller.VirtualView}
 */
public interface ActionsRMI extends Remote {

    /**
     * Method to perform the createLobby Request
     *
     * @param data the request
     */
    JoinLobbyResponseData createLobby(CreateLobbyData data) throws RemoteException;

    /**
     * Method to perform the joinLobby Request
     *
     * @param data the request
     */
    JoinLobbyResponseData joinLobby(JoinLobbyData data) throws RemoteException;

    /**
     * Method to perform the leaveGame Request
     *
     * @param data the request
     */
    LeaveGameResponseData leaveGame(LeaveGameData data) throws RemoteException;
}
