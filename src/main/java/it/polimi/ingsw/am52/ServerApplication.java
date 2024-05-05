package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.controller.ServerController;
import it.polimi.ingsw.am52.network.ServerConnection;

import java.rmi.RemoteException;

public class ServerApplication {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");

        ServerConnection connection = null;
        try {
            connection = new ServerConnection(5555,5556, 10);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        connection.run();
    }
}
