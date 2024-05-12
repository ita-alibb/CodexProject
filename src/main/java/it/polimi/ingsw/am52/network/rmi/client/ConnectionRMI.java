package it.polimi.ingsw.am52.network.rmi.client;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.request.CreateLobbyData;
import it.polimi.ingsw.am52.json.request.JoinLobbyData;
import it.polimi.ingsw.am52.json.request.LeaveGameData;
import it.polimi.ingsw.am52.json.request.SelectObjectiveData;
import it.polimi.ingsw.am52.json.response.InitGameResponseData;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.json.response.SelectObjectiveResponseData;
import it.polimi.ingsw.am52.network.rmi.Accepter;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Connection
 * Is the class that has instance on Client-side and is forwarded through the network to be called by the server to forward responses
 *
 */
public class ConnectionRMI extends UnicastRemoteObject implements RemoteConnection, ActionsRMI {

    /**
     * The VirtualView exposed by the server
     */
    public final ActionsRMI view;

    /**
     * Generic constructor
     * @throws RemoteException to implement the Remote interface
     */
    public ConnectionRMI() throws RemoteException {
        try {
            // establish connection to server
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",5556);
            Accepter stub = (Accepter) registry.lookup("SERVER_CONNECTION");

            // register myself in server
            int id = stub.accept(this);

            //get the server's virtual view so client can call it directly
            // set the Proxy virtualView in client side connection (so client can call it)
            this.view = (ActionsRMI) registry.lookup("VirtualView:"+id);

            System.out.println("initialized");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called by the server every X seconds, to let the server know that this client is still connected.
     * @throws RemoteException As defined in remote interfaces
     */
    @Override
    public void heartBeat() throws RemoteException {
        System.out.println("heartbeat");
        //heartbeat method, hopefully throws exception if the clients shut down and so this class instance is not valid anymore
    }

    /**
     * This method is called by the Server when it has to broadcast a message, the execution is delegated to another thread to be processed.
     * This removes the synchronous response and so speed up the Server.
     * @param response the response forwarded
     * @throws RemoteException As defined in Remote interface
     */
    @Override
    public void sendMessage(BaseResponseData response) throws RemoteException {
        System.out.println("response received" + response.getStatus().errorCode);
        // add the response to the queue and ends method, so the server can resume.
        new Thread(() -> {
            // TODO: use the response to update the game

            //test to remove
            try {
                Thread.sleep(1000 * 60);
                System.out.println(" Aspettato tempo ma risposta e' gia' arrivata giusto?");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // end test

        }).start();
    }

    // region Implementation of the rmi method

    /**
     * Method to perform the createLobby Request
     *
     * @param data the request
     */
    @Override
    public JoinLobbyResponseData createLobby(CreateLobbyData data) throws RemoteException {
        try {
            return this.view.createLobby(data);
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the joinLobby Request
     *
     * @param data the request
     */
    @Override
    public JoinLobbyResponseData joinLobby(JoinLobbyData data) throws RemoteException {
        try {
            return this.view.joinLobby(data);
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the leaveGame Request
     *
     * @param data the request
     */
    @Override
    public LeaveGameResponseData leaveGame(LeaveGameData data) throws RemoteException {
        try {
            return this.view.leaveGame(data);
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to fetch all information needed on game initialization
     */
    @Override
    public InitGameResponseData initGame() throws RemoteException {
        try {
            return this.view.initGame();
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the selectObjective Request
     *
     * @param data The request
     */
    @Override
    public SelectObjectiveResponseData selectObjective(SelectObjectiveData data) throws RemoteException {
        try {
            return this.view.selectObjective(data);
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    //end region
}
