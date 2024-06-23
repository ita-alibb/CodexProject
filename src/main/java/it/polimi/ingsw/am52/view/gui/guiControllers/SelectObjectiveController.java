package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectObjectiveController implements Initializable {
    @FXML
    private ImageView secretObjective1;
    @FXML
    private ImageView secretObjective2;


    private ImageView selected;

    public void setSelected(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (selected!=null)selected.setEffect(null);
        selected = (ImageView) event.getSource();
        selected.setEffect(glow);
        System.out.println(selected.getId());
    }

    public void confirm(ActionEvent event){
        if(selected== secretObjective1){
            ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(0));
            StageController.changeScene("fxml/select-starter-card.fxml", "Select start Card",event);
        }else if(selected== secretObjective2){
            ClientConnection.selectObjective(ViewModelState.getInstance().getPlayerObjectives().get(1));
            StageController.changeScene("fxml/select-starter-card.fxml", "Select start Card",event);
        }



    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> objectives = ViewModelState.getInstance().getPlayerObjectives();
        System.out.println("images/cards/fronts/%s.png".formatted(objectives.get(0)));

        secretObjective1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(objectives.get(0)+87)))));
        secretObjective2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(objectives.get(1)+87)))));


    }
}
