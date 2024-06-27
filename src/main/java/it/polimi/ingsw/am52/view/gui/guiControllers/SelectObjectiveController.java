package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Objects;

/**
 * The controller for the Select Objective view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class SelectObjectiveController extends ModelObserver {
    public Button continueButton;
    @FXML
    private ImageView secretObjective1;
    @FXML
    private ImageView secretObjective2;


    private ImageView selected;

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML chat-view.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.END_GAME);

        List<Integer> objectives = ViewModelState.getInstance().getPlayerObjectives();

        secretObjective1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(objectives.get(0)+87)))));
        secretObjective2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(objectives.get(1)+87)))));
    }

    /**
     * Method to select the face
     * @param event the clieck event on one of the image view
     */
    public void setSelected(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (selected!=null)selected.setEffect(null);
        selected = (ImageView) event.getSource();
        selected.setEffect(glow);
        System.out.println(selected.getId());
    }

    /**
     * Action bound to Continue Button click
     */
    public void continueClicked(){
        ResponseStatus res = null;
        if(selected== secretObjective1){
            res =ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(0));
        }else if(selected== secretObjective2){
            res = ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(1));
        }

        if (res == null || res.getErrorCode() != 0) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant load the game due to error: " + (res == null ? "Select a card" : res.getErrorMessage()));
            alertBox.show();
            StageController.changeScene("fxml/menu-view.fxml", "Codex Naturalis", this);
        } else {
            StageController.changeScene("fxml/select-starter-card.fxml", "Select start Card", this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateEndGame() {
        Platform.runLater(() -> {
            StageController.changeScene("fxml/end-game.fxml","Codex Naturalis" ,  this);
        });
    }
}
