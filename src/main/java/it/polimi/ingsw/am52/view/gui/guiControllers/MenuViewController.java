package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class MenuViewController implements Initializable {
    private Map<Integer,Integer> lobbies;
    @FXML
    private ListView<String> guiLobbies;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button newLobby;
    @FXML
    private Button joinLobbyButton;
    @FXML
    private Button updateLobbyButton;

   public void createLobby(ActionEvent event){
       StageController.changeScene("fxml/create-lobby.fxml", "Create Lobby",event);
   }
    public void joinLobby(ActionEvent event){
         String id = guiLobbies.getSelectionModel().getSelectedItem().split(" ")[1];
        StageController.changeScene("fxml/join-lobby.fxml", "Join Lobby", event);

        JoinLobbyController.setId(parseInt(id));
        System.out.println(id);
    }

    public void updateLobby(ActionEvent event){
        ClientConnection.getLobbyList();
        guiLobbies.getItems().clear();
        lobbies = ViewModelState.getInstance().getLobbies();
        List<String> list = lobbies.entrySet()
                .stream()
                .map(entry -> "Id: " +entry.getKey().toString() + " Number of players:" + entry.getValue().toString())
                .toList();
        guiLobbies.getItems().addAll(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnection.getLobbyList();
        lobbies = ViewModelState.getInstance().getLobbies();
        List<String> list = lobbies.entrySet()
                .stream()
                .map(entry -> "Id: " +entry.getKey().toString() + " Number of players:" + entry.getValue().toString())
                .toList();
        guiLobbies.getItems().addAll(list);
        StackPane.setAlignment(newLobby, javafx.geometry.Pos.TOP_RIGHT);
        StackPane.setAlignment(joinLobbyButton, javafx.geometry.Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(updateLobbyButton, javafx.geometry.Pos.BOTTOM_CENTER);
        guiLobbies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                joinLobbyButton.setVisible(true); // Show the button when an item is selected
            } else {
                joinLobbyButton.setVisible(false); // Hide the button when no item is selected
            }
        });



    }
}
