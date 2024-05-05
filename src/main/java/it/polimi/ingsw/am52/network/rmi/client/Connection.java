package it.polimi.ingsw.am52.network.rmi.client;

import it.polimi.ingsw.am52.json.CreateLobbyData;
import it.polimi.ingsw.am52.json.JoinLobbyData;
import it.polimi.ingsw.am52.json.LeaveGameData;
import it.polimi.ingsw.am52.json.response.Response;
import it.polimi.ingsw.am52.json.response.Status;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Connection TODO: temp name
 * Is the class that has instance on Client-side and is forwarded through the network to be called by the server to forward responses
 *
 */
public class Connection extends UnicastRemoteObject implements ConnectionRMI{
    public ActionsRMI view;

    /**
     * Generic constructor
     * @throws RemoteException to implement the Remote interface
     */
    public Connection() throws RemoteException {
    }

    public void setView(ActionsRMI view) throws Exception {
        if (this.view != null){
            throw new Exception("Already initialized");
        }
        this.view = view;
    }

    /**
     * This method is called by the server every X seconds, to let the server know that this client is still connected.
     * @throws RemoteException As defined in remote interfaces
     */
    @Override
    public void heartBeat() throws RemoteException {
        //heartbeat method, hopefully throws exception if the clients shut down and so this class instance is not valid anymore
    }

    /**
     * This method is called by the Server when it has to broadcast a message, it just saves the response in the queue, then in another thread the response is processed.
     * This removes the synchronous response and so speed up the Server.
     * @param response the response forwarded
     * @throws RemoteException As defined in Remote interface
     */
    @Override
    public void addQueue(Response<?> response) throws RemoteException {
        System.out.println(response.getString());
        // add the response to the queue and ends method, so the server can resume.
    }

    // region Implementation of the execute method
    /**
     * Method that delegates the execution to the VirtualView that is instantiated in the Server.
     * Use Override to trigger the correct method in the virtual view
     * @param data the request
     * @return the Response of the call
     */
    public Response<Integer> execute(CreateLobbyData data) {
        try {
            return this.view.createLobby(data);
        } catch (RemoteException e) {
            return new Response<>(new Status(), 2, -1 );
        }
    }

    public Response<String> execute(JoinLobbyData data) {
        try {
            return this.view.joinLobby(data);
        } catch (RemoteException e) {
            return new Response<>(new Status(), 2, e.getMessage() );
        }
    }

    public Response<String> execute(LeaveGameData data) {
        try {
            return this.view.leaveGame(data);
        } catch (RemoteException e) {
            return new Response<>(new Status(), 2, e.getMessage() );
        }
    }

    //end region
}
