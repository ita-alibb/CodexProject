package it.polimi.ingsw.am52.network.server.rmi;

import it.polimi.ingsw.am52.network.client.RemoteConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Accepter extends Remote {
    int accept(RemoteConnection client) throws RemoteException;
}
