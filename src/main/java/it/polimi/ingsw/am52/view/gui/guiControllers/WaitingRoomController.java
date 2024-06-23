package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

public class WaitingRoomController extends ModelObserver implements Initializable {
    @FXML
    private Label numberOfPlayers;


    public WaitingRoomController() {
        // register the printer to
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY, EventType.LEAVE_GAME);
    }

    public void setNumberOfPlayers(){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                numberOfPlayers.setText(Integer.toString(ViewModelState.getInstance().getNicknames().size()));
                if(ViewModelState.getInstance().getPhase() == GamePhase.INIT){
                    StageController.changeScene("fxml/select-objective.fxml","Select secret objective" , numberOfPlayers);
                }
            }
        });


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY, EventType.LEAVE_GAME);
        setNumberOfPlayers();


    }




    @Override
    protected void updateLeaveGame() {
        setNumberOfPlayers();

    }


    @Override
    protected void updateJoinLobby() {
        setNumberOfPlayers();
    }
}
