package it.polimi.ingsw.am52.network.rmi;

import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;

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

    /**
     * Method to fetch all information needed on game initialization
     *
     */
    InitGameResponseData initGame() throws RemoteException;

    /**
     * Method to perform the selectObjective Request
     * @param data  The request
     */
    SelectObjectiveResponseData selectObjective(SelectObjectiveData data) throws RemoteException;

    /**
     * Method to perform the placeStarterCard request
     * @param data  The request
     */
    PlaceStarterCardResponseData placeStarterCard(PlaceStarterCardData data) throws RemoteException;

    /**
     * Method to perform the placeCard request
     * @param data  The request
     */
    PlaceCardResponseData placeCard(PlaceCardData data) throws RemoteException;
}
