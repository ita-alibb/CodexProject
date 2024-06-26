package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class SelectStarterCardController extends ModelObserver {
    public Button continueButton;
    @FXML
    private ImageView frontStarterCard;
    @FXML
    private ImageView backStarterCard;

    private ImageView selected;

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.END_GAME);

        int starterCard = ViewModelState.getInstance().getStarterCard();
        frontStarterCard.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(starterCard+1)))));
        backStarterCard.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/backs/%s.png".formatted(starterCard+1)))));
    }

    public void setSelected(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (selected!=null)selected.setEffect(null);
        selected = (ImageView) event.getSource();
        selected.setEffect(glow);
        System.out.println(selected.getId());
    }

    public void continueClicked(){
        if(selected == frontStarterCard){
            ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), CardSide.FRONT);
            PlayingBoardController.setStartercardside(CardSide.FRONT);
        }else if(selected == backStarterCard){
            ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), CardSide.BACK);
            PlayingBoardController.setStartercardside(CardSide.BACK);
        }
        StageController.changeScene("fxml/playing-board.fxml", "Game", this);
    }

    @Override
    protected void updateEndGame() {
        Platform.runLater(() -> {
            StageController.changeScene("fxml/end-game.fxml","Codex Naturalis" , this);
        });
    }
}
