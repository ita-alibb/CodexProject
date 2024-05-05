package it.polimi.ingsw.am52.network.rmi;

import it.polimi.ingsw.am52.network.rmi.client.ConnectionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Accepter extends Remote {
    int accept(ConnectionRMI client) throws RemoteException;
}
