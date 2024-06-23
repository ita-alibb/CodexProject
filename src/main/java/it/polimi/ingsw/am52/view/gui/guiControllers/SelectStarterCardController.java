package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.model.cards.CardSide;
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

public class SelectStarterCardController implements Initializable {
    @FXML
    private ImageView frontStarterCard;
    @FXML
    private ImageView backStarterCard;

    private ImageView selected;

    public void setSelected(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (selected!=null)selected.setEffect(null);
        selected = (ImageView) event.getSource();
        selected.setEffect(glow);
        System.out.println(selected.getId());
    }

    public void confirm(ActionEvent event){
        if(selected== frontStarterCard){
            ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), CardSide.FRONT);
            PlayingBoardController.setStartercardside(CardSide.FRONT);
            StageController.changeScene("fxml/playing-board.fxml", "Game",event);
        }else if(selected== backStarterCard){
            ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), CardSide.BACK);
            PlayingBoardController.setStartercardside(CardSide.BACK);
            StageController.changeScene("fxml/playing-board.fxml", "Game",event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int starterCard = ViewModelState.getInstance().getStarterCard();
        System.out.println(starterCard);
        frontStarterCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(starterCard+1)))));
        backStarterCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/backs/%s.png".formatted(starterCard+1)))));

    }
}
