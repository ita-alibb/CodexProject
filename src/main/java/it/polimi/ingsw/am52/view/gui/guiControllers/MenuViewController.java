package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * The controller for the Menu view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class MenuViewController extends ModelObserver {
    public Button newLobby;
    public Button joinLobbyButton;
    public Button updateLobbyButton;
    @FXML
    private ListView<String> guiLobbies;

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML chat-view.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.LIST_LOBBY);

        var lobbies = ViewModelState.getInstance().getLobbies();
        List<String> list = lobbies.entrySet()
                .stream()
                .map(entry -> "Id: " +entry.getKey().toString() + " Number of players:" + entry.getValue().toString())
                .toList();
        guiLobbies.getItems().addAll(list);
        guiLobbies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Hide the button when no item is selected
            joinLobbyButton.setVisible(newValue != null); // Show the button when an item is selected
        });
    }

    /**
     * Action bound to the new lobby button
     */
    public void createLobby(){
       StageController.changeScene("fxml/create-lobby.fxml", "Create Lobby", this);
    }

    /**
     * Action bound to the join lobby button
     */
    public void joinLobby(){
        String id = guiLobbies.getSelectionModel().getSelectedItem().split(" ")[1];
        StageController.changeScene("fxml/join-lobby.fxml", "Join Lobby", this);

        JoinLobbyController.setId(parseInt(id));
    }

    /**
     * Action bound to the updateLobby button
     */
    public void updateLobby(){
        var res = ClientConnection.getLobbyList();
        if (res.getErrorCode() != 0) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant load the game due to error " + res.getErrorMessage());
            alertBox.show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateListLobby() {
        Platform.runLater(() -> {
            guiLobbies.getItems().clear();
            var lobbies = ViewModelState.getInstance().getLobbies();
            List<String> list = lobbies.entrySet()
                    .stream()
                    .map(entry -> "Id: " +entry.getKey().toString() + " Number of players:" + entry.getValue().toString())
                    .toList();
            guiLobbies.getItems().addAll(list);
        });
    }
}
