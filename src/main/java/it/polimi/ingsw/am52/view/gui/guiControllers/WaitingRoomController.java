package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitingRoomController extends ModelObserver {
    @FXML
    private Label numberOfPlayers;

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY, EventType.LEAVE_GAME, EventType.INIT_GAME);
        Platform.runLater(() -> {
            numberOfPlayers.setText(Integer.toString(ViewModelState.getInstance().getNicknames().size()));
        });
    }

    @Override
    protected void updateInitGame() {
        Platform.runLater(() -> {
                StageController.changeScene("fxml/select-objective.fxml","Select secret objective" , numberOfPlayers);
        });
    }

    @Override
    protected void updateLeaveGame() {
        Platform.runLater(() -> {
            numberOfPlayers.setText(Integer.toString(ViewModelState.getInstance().getNicknames().size()));
        });
    }

    @Override
    protected void updateJoinLobby() {
        Platform.runLater(() -> {
            numberOfPlayers.setText(Integer.toString(ViewModelState.getInstance().getNicknames().size()));
        });
    }
}
