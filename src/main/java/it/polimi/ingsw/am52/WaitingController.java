package it.polimi.ingsw.am52;

import it.polimi.ingsw.am52.json.request.CreateLobbyData;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.network.rmi.client.ConnectionRMI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WaitingController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private ListView<Integer> mylobbies;

    public void display(String username){
        nameLabel.setText("Welcome " + username);
    }

    public void newLobby() throws IOException {
        ConnectionRMI connection = ConnectionController.getConnectionRMI();
        JoinLobbyResponseData res =  connection.createLobby(new CreateLobbyData(ConnectionController.getUsername(),2));
        mylobbies.getItems().add(res.getLobbyId());
        System.out.println(connection.listLobby().getLobbies());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConnectionController.connect();
            ConnectionRMI connection = ConnectionController.getConnectionRMI();
            connection.createLobby(new CreateLobbyData("Paoletto",2));
            Map<Integer,Integer> lobbies = connection.listLobby().getLobbies();
            List<Integer> keysList = new ArrayList<>(lobbies.keySet());
            mylobbies.getItems().addAll(keysList);
            System.out.println(lobbies);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }



    }
}
