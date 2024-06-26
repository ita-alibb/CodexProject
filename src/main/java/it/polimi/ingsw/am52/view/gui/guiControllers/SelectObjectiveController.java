package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Objects;

public class SelectObjectiveController extends ModelObserver {
    public Button continueButton;
    @FXML
    private ImageView secretObjective1;
    @FXML
    private ImageView secretObjective2;


    private ImageView selected;

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.END_GAME);

        List<Integer> objectives = ViewModelState.getInstance().getPlayerObjectives();

        secretObjective1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(objectives.get(0)+87)))));
        secretObjective2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(objectives.get(1)+87)))));
    }

    public void setSelected(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (selected!=null)selected.setEffect(null);
        selected = (ImageView) event.getSource();
        selected.setEffect(glow);
        System.out.println(selected.getId());
    }

    public void continueClicked(){
        if(selected== secretObjective1){
            ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(0));
            StageController.changeScene("fxml/select-starter-card.fxml", "Select start Card", this);
        }else if(selected== secretObjective2){
            ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(1));
            StageController.changeScene("fxml/select-starter-card.fxml", "Select start Card", this);
        }
    }

    @Override
    protected void updateEndGame() {
        Platform.runLater(() -> {
            StageController.changeScene("fxml/end-game.fxml","Codex Naturalis" ,  this);
        });
    }
}
