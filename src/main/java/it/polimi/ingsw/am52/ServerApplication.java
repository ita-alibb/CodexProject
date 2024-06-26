package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.network.server.ServerConnection;
import it.polimi.ingsw.am52.settings.ServerSettings;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerApplication {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", Inet4Address.getLocalHost().getHostAddress());
            System.out.print("ServerIP: " + Inet4Address.getLocalHost().getHostAddress() + "\n");
        } catch (UnknownHostException e) {
            System.out.println("Cannot get IP address");
            System.exit(1);
        }

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
