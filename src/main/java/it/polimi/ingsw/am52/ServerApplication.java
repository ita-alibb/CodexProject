package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.network.server.ServerConnection;
import it.polimi.ingsw.am52.settings.ServerSettings;

import java.rmi.RemoteException;

public class ServerApplication {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");

        ServerConnection connection = null;
        try {
            ServerSettings settings = new ServerSettings(
                    10,
                    5555,
                    5556,
                    ServerSettings.DEF_VERBOSITY,
                    ServerSettings.DEF_PORT_MODE
            );
            connection = new ServerConnection(settings);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        connection.run();
    }
}
