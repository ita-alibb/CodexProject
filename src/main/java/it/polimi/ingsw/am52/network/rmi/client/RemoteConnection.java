package it.polimi.ingsw.am52.network.rmi.client;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that the Client expose to the Server.
 * It contains methods to keep the client alive and receive responses
 */
public interface RemoteConnection extends Remote {
    /**
     * This method is called by the server every X seconds, to let the server know that this client is still connected.
     * @throws RemoteException As defined in remote interfaces
     */
    void heartBeat() throws RemoteException;

    /**
     * This method is called by the Server when it has to broadcast a message, it just saves the response in the queue, then in another thread the response is processed.
     * This removes the synchronous response and so speed up the Server.
     * @param response the response forwarded
     * @throws RemoteException As defined in Remote interface
     */
    void sendMessage(BaseResponseData response) throws RemoteException;
}
