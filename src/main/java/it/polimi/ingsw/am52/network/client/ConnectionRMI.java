package it.polimi.ingsw.am52.network.client;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.network.server.rmi.Accepter;
import it.polimi.ingsw.am52.network.server.rmi.ActionsRMI;

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
public class ConnectionRMI extends UnicastRemoteObject implements RemoteConnection, Connection {

    private static ConnectionRMI INSTANCE;

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
        System.out.print("heartbeat");
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
        System.out.println("response received BROADCAST RMI");
        // add the response to the queue and ends method, so the server can resume.
        new Thread(() -> {
            // TODO: use the response to update the game

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
     * Method to perform the listLobby Request
     */
    @Override
    public ListLobbyResponseData listLobby() throws RemoteException {
        try {
            return this.view.listLobby();
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the leaveGame Request
     */
    @Override
    public LeaveGameResponseData leaveGame() throws RemoteException {
        try {
            return this.view.leaveGame();
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

    /**
     * Method to perform the placeStarterCard request
     * @param data  The request
     */
    @Override
    public PlaceStarterCardResponseData placeStarterCard(PlaceStarterCardData data) throws RemoteException {
        try {
            return this.view.placeStarterCard(data);
        } catch (RemoteException e) {
            // TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the placeCard request
     * @param data  The request
     */
    @Override
    public PlaceCardResponseData placeCard(PlaceCardData data) throws RemoteException {
        try {
            return this.view.placeCard(data);
        } catch (RemoteException e) {
            //TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the drawCard request
     * @param data  The request
     */
    @Override
    public DrawCardResponseData drawCard(DrawCardData data) throws RemoteException {
        try {
            return this.view.drawCard(data);
        } catch (RemoteException e) {
            //TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the takeCard request
     *
     * @param data The request
     */
    @Override
    public TakeCardResponseData takeCard(TakeCardData data) throws RemoteException {
        try {
            return this.view.takeCard(data);
        } catch (RemoteException e) {
            //TODO: handle remote exception
            return null;
        }
    }

    /**
     * Method to perform the endGame request
     */
    @Override
    public EndGameResponseData endGame() throws RemoteException {
        try {
            return this.view.endGame();
        } catch (RemoteException e) {
            //TODO: handle remote exception
            return null;
        }
    }
    //end region
}
