package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The controller for the Waiting Room view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class WaitingRoomController extends ModelObserver {
    @FXML
    private VBox playersNickname;

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML chat-view.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY, EventType.LEAVE_GAME, EventType.INIT_GAME, EventType.END_GAME);
        setNicknames();
    }

    /**
     * Method used to update the nicknames
     */
    public void setNicknames(){
        Platform.runLater(() -> {
            playersNickname.getChildren().clear();
            List<String> nicknames = ViewModelState.getInstance().getNicknames();
            nicknames.forEach(nickname -> {
                Label newLabel = new Label(nickname);
                newLabel.setStyle("-fx-font-size: 30px;");
                playersNickname.getChildren().add(newLabel);
            });
        });
    }

    /**
     * Method bound to the leave game button
     */
    public void leaveLobby() {
        ResponseStatus res = ClientConnection.leaveLobby();
        if (res.getErrorCode() != 0) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant leave the game due to error " + res.getErrorMessage());
            alertBox.show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateInitGame() {
        Platform.runLater(() -> {
                StageController.changeScene("fxml/select-objective.fxml","Select secret objective" , this);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateLeaveGame() {
        if (ViewModelState.getInstance().getCurrentLobbyId() != -1) {
            setNicknames();
        } else {
            Platform.runLater(() -> {
                StageController.changeScene("fxml/menu-view.fxml","Codex Naturalis" , this);
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateJoinLobby() {
        setNicknames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateEndGame() {
        Platform.runLater(() -> {
            StageController.changeScene("fxml/end-game.fxml","Codex Naturalis" , this);
        });
    }
}
