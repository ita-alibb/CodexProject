package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.BoardMap;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class OpponentBardController {
    @FXML
    private GridPane playingBoardGrid;

    private void placeCard(BoardSlot slot, CardIds card){
        String side = card.cardFace == 0? "fronts" : "backs";
        ImageView image = new ImageView(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(side,card.cardId+1)))));
        image.setFitHeight(64);
        image.setFitWidth(87);
        playingBoardGrid.add(image, slot.getHoriz()+5, slot.getVert()* -1 +5);
        GridPane.setHalignment(image, HPos.CENTER);
        GridPane.setValignment(image, VPos.CENTER);
    }

    public void setOpponentBoard(String nickname){
        BoardMap<BoardSlot, CardIds> board = ViewModelState.getInstance().getBoard(nickname);
        board.forEach(this::placeCard);
    }
}
