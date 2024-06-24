package it.polimi.ingsw.am52.view.gui.guiControllers;


import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.cards.CornerLocation;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class PlayingBoardController extends ModelObserver {
    public Button chatButton;
    @FXML
    private ImageView resourceCardsDeck;
    @FXML
    private ImageView visibleResourceCard1;
    @FXML
    private ImageView visibleResourceCard2;
    @FXML
    private ImageView goldCardsDeck;
    @FXML
    private ImageView visibleGoldCard1;
    @FXML
    private ImageView visibleGoldCard2;
    @FXML
    private ImageView commonObjective1;
    @FXML
    private ImageView commonObjective2;
    @FXML
    private ImageView secretObjective;
    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;
    @FXML
    private ImageView playerCard3;
    @FXML
    private VBox scoringBoard;
    @FXML
    private GridPane playingBoardGrid;

    private ImageView pickedCard;

    private static CardSide startercardside ;
    private CardSide selectedCardSide = CardSide.FRONT;

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.PLACE_CARD,EventType.TAKE_CARD,EventType.DRAW_CARD);

        ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), startercardside);
        String side= startercardside == CardSide.FRONT ? "fronts" : "backs";
        selected = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/%s/%s.png".formatted(side,ViewModelState.getInstance().getStarterCard()+1)))));
        placeCard(5,5);
        setVisibleGoldCards();
        setVisibleResourceCards();
        setCommonObjectives();
        setSecretObjective();
        setPlayerCards();
        System.out.println(ViewModelState.getInstance().getVisibleResourceCards());
        System.out.println(ViewModelState. getInstance().getVisibleGoldCards());
        setScoringBoard();
    }

    public static void setStartercardside(CardSide startercardside) {
        PlayingBoardController.startercardside = startercardside;
    }

    private ImageView selected;
    private String  selectedSide;

    public void cardSelection(MouseEvent event){
            setSelectedCardSide(event);
            setSelected(event);
            getCorner(event);
            System.out.println(ViewModelState.getInstance().getPhase());
    }

    public CornerLocation getCorner(MouseEvent event){
        ImageView selectedBoardCard = (ImageView) event.getSource();
        double x = event.getX();
        double y = event.getY();
        double width = selectedBoardCard.getBoundsInLocal().getWidth();
        double height = selectedBoardCard.getBoundsInLocal().getHeight();
        boolean isTop = y < height / 2;
        boolean isLeft = x < width / 2;

        if (isTop && isLeft) {
            return CornerLocation.TOP_LEFT;
        } else if (isTop && !isLeft) {
            return CornerLocation.TOP_RIGHT;
        } else if (!isTop && isLeft) {
            return CornerLocation.BOTTOM_LEFT;
        } else {
            return CornerLocation.BOTTOM_RIGHT;
        }
    }

    public void setSelectedCardSide(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();
        if(selectedCard == selected){
            selectedCardSide = selectedCardSide == CardSide.FRONT ? CardSide.BACK : CardSide.FRONT;
            String cardSide = selectedCardSide == CardSide.FRONT ? "fronts": "backs";
            System.out.println(cardSide);
            List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();
            if (selectedCard == playerCard1){
                playerCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/%s/%s.png".formatted(cardSide, playerHand.get(0)+1)))));
            } else if (selectedCard == playerCard2) {
                playerCard2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/%s/%s.png".formatted(cardSide, playerHand.get(1)+1)))));
            } else if (selectedCard == playerCard3) {
                playerCard3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/%s/%s.png".formatted(cardSide, playerHand.get(2)+1)))));
            }
        }else{
            setPlayerCards();
        }
    }

    public void pickupCard(MouseEvent event){
        if (ViewModelState.getInstance().isClientTurn() && ViewModelState.getInstance().getPhase() == GamePhase.DRAWING) {
            pickUpCardServer(event);
            setPickedCard(event);
            System.out.println(ViewModelState.getInstance().getPhase());
        }
    }

    public void setPickedCard(MouseEvent event){
        if(pickedCard != event.getSource()){
            Glow glow = new Glow(0.8);
            if (pickedCard!=null)pickedCard.setEffect(null);
            pickedCard = (ImageView) event.getSource();
            pickedCard.setEffect(glow);
            System.out.println(pickedCard.getId());
        }
    }

    public void pickUpCardServer(MouseEvent event){
        if (pickedCard == event.getSource()) {
            List<Integer> resourceCards = ViewModelState.getInstance().getVisibleResourceCards();
            List<Integer> goldCards = ViewModelState.getInstance().getVisibleGoldCards();
            if (pickedCard== visibleResourceCard1){
                ClientConnection.takeCard(resourceCards.get(0), DrawType.RESOURCE );
                System.out.println(ViewModelState.getInstance().getPlayerHand().getFirst());
                setVisibleResourceCards();
                System.out.println(resourceCards.getFirst());
            } else if (pickedCard== visibleResourceCard2) {
                ClientConnection.takeCard(resourceCards.get(1), DrawType.RESOURCE );
                setVisibleResourceCards();
            } else if (pickedCard== visibleGoldCard1) {
                ClientConnection.takeCard(goldCards.get(0), DrawType.GOLD );
                setVisibleGoldCards();
            } else if (pickedCard== visibleGoldCard2) {
                ClientConnection.takeCard(goldCards.get(1), DrawType.GOLD );
                setVisibleGoldCards();
            } else if (pickedCard== resourceCardsDeck) {
                ClientConnection.drawCard(DrawType.RESOURCE );
            } else if (pickedCard== goldCardsDeck) {
                ClientConnection.drawCard(DrawType.GOLD );
            }
            setPlayerCards();
        }
    }

    public void placeCardServer(MouseEvent event){
        if(ViewModelState.getInstance().isClientTurn() && ViewModelState.getInstance().getPhase() == GamePhase.PLACING && selected!=null){
            List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();
            int rowIndex = GridPane.getRowIndex((ImageView) event.getSource());
            int colIndex = GridPane.getColumnIndex((ImageView) event.getSource());
            BoardSlot boardSlot = new BoardSlot(colIndex-5,rowIndex*-1+5);
            BoardSlot selectedBoardSlot = boardSlot.getSlotAt(getCorner(event));
            System.out.println(getCorner(event));
            System.out.println(selectedBoardSlot.getVert());
            System.out.println(selectedBoardSlot.getHoriz());
            selected.setEffect(null);

            ResponseStatus response;
            if (ViewModelState.getInstance().getAvailableSlots().contains(selectedBoardSlot)) {
                if (selected == playerCard1){
                    response = ClientConnection.placeCard(playerHand.get(0), selectedCardSide, selectedBoardSlot);
                } else if (selected == playerCard2) {
                    response = ClientConnection.placeCard(playerHand.get(1),selectedCardSide,selectedBoardSlot);
                }else{
                    response = ClientConnection.placeCard(playerHand.get(2),selectedCardSide,selectedBoardSlot);
                }

                if (response.getErrorCode() == 0) {
                    placeCard(selectedBoardSlot.getVert()* -1 +5, selectedBoardSlot.getHoriz()+5);
                } else {
                    System.out.println(response.getErrorCode());
                    System.out.println(response.getErrorMessage());
                    Alert alertBox = new Alert(Alert.AlertType.ERROR);
                    alertBox.setContentText("Can't place: " + response.getErrorMessage());
                }
            }
        }
    }

    public void setPlayerCards(){
        List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();
        System.out.println(playerHand);

        if (playerHand.size() > 0) {
            playerCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(playerHand.get(0)+1)))));
        } else {
            playerCard1.setImage(null);
        }

        if (playerHand.size() > 1) {
            playerCard2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(playerHand.get(1)+1)))));
        } else {
            playerCard2.setImage(null);
        }

        if (playerHand.size() > 2) {
            playerCard3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(playerHand.get(2)+1)))));
        } else {
            playerCard3.setImage(null);
        }
    }

    public void setVisibleGoldCards(){
        List<Integer> visibleGoldCards = ViewModelState.getInstance().getVisibleGoldCards();
        visibleGoldCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(visibleGoldCards.get(0)+1)))));
        visibleGoldCard2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(visibleGoldCards.get(1)+1)))));
    }

    public void setVisibleResourceCards(){
        List<Integer> visibleResourceCards = ViewModelState.getInstance().getVisibleResourceCards();
        visibleResourceCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(visibleResourceCards.get(0)+1)))));
        visibleResourceCard2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(visibleResourceCards.get(1)+1)))));
    }

    private void setVisibleDecks() {
        if (!ViewModelState.getInstance().getResourceDeck()) {
            this.resourceCardsDeck.setImage(null);
        }
        if (!ViewModelState.getInstance().getGoldDeck()) {
            this.goldCardsDeck.setImage(null);
        }
    }

    public void setCommonObjectives(){
        List<Integer> commonObjectives = ViewModelState.getInstance().getCommonObjectives();
        commonObjective1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(commonObjectives.get(0)+87)))));
        commonObjective2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(commonObjectives.get(1)+87)))));
    }

    public void setSecretObjective(){
        int secretObjectiveCard = ViewModelState.getInstance().getSecretObjective();
        secretObjective.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("cards/fronts/%s.png".formatted(secretObjectiveCard+87)))));
    }

    public void placeCard(int row, int column){
        ImageView image = new ImageView(selected.getImage());
        image.setFitHeight(64);
        image.setFitWidth(87);
        playingBoardGrid.add(image, column, row);
        GridPane.setHalignment(image, HPos.CENTER);
        GridPane.setValignment(image, VPos.CENTER);
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, this::placeCardServer);
    }

    public void setScoringBoard(){
        Map<String, Integer> scores = ViewModelState.getInstance().getScoreboard();
        scoringBoard.getChildren().clear();
        scores.forEach((k,v)->{
            Label newLabel = new Label(  k +": " + v.toString());
            newLabel.setStyle("-fx-font-size: 20px;");
            if (Objects.equals(k, ViewModelState.getInstance().getCurrentPlayer()))newLabel.setStyle("-fx-text-fill: blue;");
            scoringBoard.getChildren().add(newLabel);
            if (!Objects.equals(k, ViewModelState.getInstance().getClientNickname())){
                Button showBoard = new Button("Show Board");
                scoringBoard.getChildren().add(showBoard);
                showBoard.setOnAction(event -> {
                    try {
                        openOpponentBoard(k);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void openOpponentBoard(String nickname) throws IOException {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Opponent Board");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/opponent-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        OpponentBardController controller = fxmlLoader.getController();
        controller.setOpponentBoard(nickname);

        modalStage.setScene(scene);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> modalStage.close());

        modalStage.showAndWait();
    }

    @FXML
    private void toggleChat() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Chat");

        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("fxml/chat-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            System.out.println("Cannot load chat view" + e.getMessage());
            return;
        }
        ChatViewController controller = fxmlLoader.getController();
        modalStage.setScene(scene);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> modalStage.close());

        modalStage.showAndWait();
    }

    public void setSelected(MouseEvent event){
        if(selected != (ImageView) event.getSource()){
            Glow glow = new Glow(0.8);
            if (selected!=null)selected.setEffect(null);
            selected = (ImageView) event.getSource();
            selected.setEffect(glow);
            selectedCardSide = CardSide.FRONT;
            System.out.println(selected.getId());
            System.out.println(ViewModelState.getInstance().getPhase());
        }
    }

    @Override
    protected void updatePlaceCard() {
        Platform.runLater(() -> {
            setScoringBoard();
            setPlayerCards();
            if (ViewModelState.getInstance().getPhase() != GamePhase.PLACING) if (selected!=null)selected.setEffect(null);
        });
    }

    @Override
    protected void updateTakeCard() {
        Platform.runLater(() -> {
            setVisibleResourceCards();
            setVisibleGoldCards();
            setPlayerCards();
            setScoringBoard();
        });
    }

    @Override
    protected void updateDrawCard() {
        Platform.runLater(() -> {
            setVisibleDecks();
            setPlayerCards();
            setScoringBoard();
        });
    }
}
