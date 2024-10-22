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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The controller for the End Game view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class EndGameController extends ModelObserver {
    public Button endGameButton;
    public Label winnerLabel;
    @FXML
    private VBox winnersBox;

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML chat-view.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.LEAVE_GAME);
        winnersBox.getChildren().clear();
        List<String> nicknames = ViewModelState.getInstance().getWinners();
        if (!nicknames.isEmpty()) {
            nicknames.forEach(nickname -> {
                Label newLabel = new Label(nickname);
                newLabel.setStyle("-fx-font-size: 40px;");
                winnersBox.getChildren().add(newLabel);
            });
        } else {
            winnerLabel.setText(ViewModelState.getInstance().getDisconnectedPlayer() + " DISCONNECTED! Game ends without winners all blame on him and not on the developers! :)))))))))");
        }
    }

    /**
     * Action bound to the leave game button
     */
    public void leaveGame() {
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
    public void updateLeaveGame() {
        if (ViewModelState.getInstance().getCurrentLobbyId() == -1) {
            Platform.runLater(() -> {
                StageController.changeScene("fxml/menu-view.fxml","Codex Naturalis" , this);
            });
        }
    }
}
