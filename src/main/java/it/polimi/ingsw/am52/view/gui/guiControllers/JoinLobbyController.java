package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JoinLobbyController extends ModelObserver {
    private static int id;
    @FXML
    private TextField nickNameTextField;
    @FXML
    private Button joinLobbyButton;

    public static void setId(int id){
        JoinLobbyController.id = id;
    }

    public void joinLobby(ActionEvent event) {
        System.out.println(id);
        if (ViewModelState.getInstance().getLobbies().containsKey(id)) {
            ResponseStatus networkResponse = ClientConnection.joinLobby(nickNameTextField.getText(), id);
            if (networkResponse.getErrorCode() != 0) {
                Alert alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setContentText("Cant load the game due to error " + networkResponse.getErrorMessage());
            }
        }
    }

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY);

        StackPane.setAlignment(joinLobbyButton, javafx.geometry.Pos.BOTTOM_RIGHT);
    }


    @Override
    protected void updateJoinLobby() {
        Platform.runLater(() -> {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Waiting Room");

            FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("fxml/waiting-room.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (Exception e) {
                System.out.println("Cannot load chat view" + e.getMessage());
                return;
            }
            WaitingRoomController controller = fxmlLoader.getController();
            modalStage.setScene(scene);
            modalStage.setFullScreen(true);
            modalStage.show();
        });
    }
}
