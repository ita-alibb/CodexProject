package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.json.request.CreateLobbyData;
import it.polimi.ingsw.am52.json.request.JoinLobbyData;
import it.polimi.ingsw.am52.json.request.LeaveGameData;
import it.polimi.ingsw.am52.network.rmi.Accepter;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;
import it.polimi.ingsw.am52.network.rmi.client.ConnectionRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Dummy RMI client application
 */
public class DummyClientApplication {
    private ConnectionRMI connectionRMI;

    public void run(){
        try {
            // region Initialization of RMI connection
            // set client connection
            this.connectionRMI = new ConnectionRMI();

            /*// establish connection to server
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",5556);
            Accepter stub = (Accepter) registry.lookup("SERVER_CONNECTION");

            // register myself in server
            int id = stub.accept(this.connectionRMI);

            //get the server's virtual view so client can call it directly
            ActionsRMI viewStub = (ActionsRMI) registry.lookup("VirtualView:"+id);

            // set the Proxy virtualView in client side connection (so client can call it)
            this.connectionRMI.setView(viewStub);

            System.out.println("initialized");
            // endregion*/

            // region test calls

            // connection.execute delegates execution to the proxy virtual view
            var res = this.connectionRMI.createLobby(new CreateLobbyData("Paoletto",2));
            System.out.println(res);
            System.out.println(this.connectionRMI.listLobby().getLobbies());

            Thread.sleep(10 * 1000);

            System.out.println("Uscito dallo sleep");

            /*var res1 = this.connectionRMI.leaveGame();
            System.out.println(res1);

            var res2 = this.connectionRMI.joinLobby(new JoinLobbyData("Paoletto",2));
            System.out.println(res2);*/
            System.out.println(this.connectionRMI.listLobby().getLobbies());

            // endregion
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var dummy = new DummyClientApplication();
        dummy.run();
    }
}
