package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.network.rmi.client.ConnectionRMI;

import java.rmi.RemoteException;

public class ConnectionController {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static ConnectionRMI getConnectionRMI() {
        return connectionRMI;
    }

   private static ConnectionRMI connectionRMI;
    public static void setusername(String username) {
        ConnectionController.username = username;
    }
    public static void connect() throws RemoteException {
        connectionRMI = new ConnectionRMI();
    }
}
