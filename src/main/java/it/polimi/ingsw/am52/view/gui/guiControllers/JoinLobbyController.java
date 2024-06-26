package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class JoinLobbyController extends ModelObserver {
    private static int id;
    public TextField nickNameTextField;
    public Button joinLobbyButton;

    public static void setId(int id){
        JoinLobbyController.id = id;
    }

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY);

        StackPane.setAlignment(joinLobbyButton, javafx.geometry.Pos.BOTTOM_RIGHT);
    }

    public void joinLobby() {
        if (ViewModelState.getInstance().getLobbies().containsKey(id)) {
            ResponseStatus networkResponse = ClientConnection.joinLobby(nickNameTextField.getText(), id);
            if (networkResponse.getErrorCode() != 0) {
                Alert alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setContentText("Cant load the game due to error " + networkResponse.getErrorMessage());
                alertBox.show();
                StageController.changeScene("fxml/menu-view.fxml", "Codex Naturalis", this);
            }
        }
    }

    @Override
    protected void updateJoinLobby() {
        Platform.runLater(()-> {
            StageController.changeScene("fxml/waiting-room.fxml", "Waiting room", this);
        });
    }
}
