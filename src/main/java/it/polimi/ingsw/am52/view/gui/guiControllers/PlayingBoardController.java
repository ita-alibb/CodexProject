package it.polimi.ingsw.am52.view.gui.guiControllers;


import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.cards.CornerLocation;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
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

/**
 * The controller for the PlayingBoard view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class PlayingBoardController extends ModelObserver {
    public Button chatButton;

    public static final BoardSlot CENTER_SLOT = new BoardSlot(8, 10);

    // region Pins
    private static final int[][] pinPos = {{1335,439},
        {1390,439},
        {1445,439},
        {1472,390},
        {1417,390},
        {1367,390},
        {1308,390},
        {1308,341},
        {1367,339},
        {1417,339},
        {1472,339},
        {1472,291},
        {1417,291},
        {1367,291},
        {1308,291},
        {1308,244},
        {1367,244},
        {1417,244},
        {1472,244},
        {1463,194},
        {1387,175},
        {1308,193},
        {1308,142},
        {1308,93},
        {1337,56},
        {1387,44},
        {1445,56},
        {1472,93},
        {1463,142},
        {1390,105},
    };

    public ImageView redPin;
    public ImageView greenPin;
    public ImageView bluePin;
    public ImageView violetPin;
    // endregion

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

    private static CardSide starterCardSide;

    private CardSide selectedCardSide = CardSide.FRONT;

    private ImageView selected;

    /**
     * Static method to set the initial card on the playerBoard, used by the {@link SelectStarterCardController}
     * @param starterCardSide the card side of the starter card
     */
    public static void setStarterCardSide(CardSide starterCardSide) {
        PlayingBoardController.starterCardSide = starterCardSide;
    }

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML playing-board.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.PLACE_CARD,EventType.TAKE_CARD,EventType.DRAW_CARD, EventType.END_GAME);

        String side= starterCardSide == CardSide.FRONT ? "fronts" : "backs";
        var selected = new ImageView(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(side,ViewModelState.getInstance().getStarterCard()+1)))));
        placeCard(CENTER_SLOT.getHoriz(),CENTER_SLOT.getVert(), selected);

        setVisibleDecks();
        setVisibleGoldCards();
        setVisibleResourceCards();
        setCommonObjectives();
        setSecretObjective();
        setPlayerCards();
        setScoringBoard();
    }

    /**
     * Method used to manage the currently selected card and side
     * @param event The event bound with the click of one of the 3 player's hand card {@link #playerCard1} {@link #playerCard2} {@link #playerCard3}
     */
    public void cardSelection(MouseEvent event){
            //select the card side if the card is the same
            setSelectedCardSide(event);

            //select the selected card if the card is different
            setSelected(event);
    }

    /**
     * Method used to change the side of the selected card
     * @param event The event of click on card, if the card clicked is already selected then the {@link #selectedCardSide} will be toggled
     */
    public void setSelectedCardSide(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();

        // We re-selected the same card?
        if(selectedCard == selected){
            selectedCardSide = selectedCardSide == CardSide.FRONT ? CardSide.BACK : CardSide.FRONT;
            String cardSide = selectedCardSide == CardSide.FRONT ? "fronts": "backs";
            List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();

            //Change only the image for selected card
            if (selectedCard == playerCard1){
                playerCard1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(cardSide, playerHand.get(0)+1)))));
            } else if (selectedCard == playerCard2) {
                playerCard2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(cardSide, playerHand.get(1)+1)))));
            } else if (selectedCard == playerCard3) {
                playerCard3.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(cardSide, playerHand.get(2)+1)))));
            }
        }else{
            //if we select another card than we reload the default view for the cards in hand
            setPlayerCards();
        }
    }

    /**
     * Set the selected card. If the card is the same nothing is done
     * @param event The click event on the card
     */
    public void setSelected(MouseEvent event){
        //if the card is the same then no need to set effect
        if(selected != (ImageView) event.getSource()){
            // remove effect on the previously selected
            if (selected!=null) {
                selected.setEffect(null);
            }

            //add effect to the new selected
            Glow glow = new Glow(0.8);
            selected = (ImageView) event.getSource();
            selected.setEffect(glow);
            selectedCardSide = CardSide.FRONT;
        }
    }

    /**
     * Method used to add one card image to the {@link #playingBoardGrid}
     * @param row the row of the boardSlot in which place the card
     * @param column the column of the board slot in which place the card
     * @param newCard the image of the card placed
     */
    public void placeCard(int column, int row, ImageView newCard){
        //add the card in the grid
        ImageView image = new ImageView(newCard.getImage());
        image.setFitHeight(64);
        image.setFitWidth(87);
        playingBoardGrid.add(image, column, row);
        GridPane.setHalignment(image, HPos.CENTER);
        GridPane.setValignment(image, VPos.CENTER);

        //add the event on the new card to trigger the server call
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, this::placeCardServer);
    }

    /**
     * Method bound to every new card placed. Fires the placeCard on server side via {@link ClientConnection#placeCard(int, CardSide, BoardSlot)}
     * Gets the new boardSlot from the click event and the card to place is the currently selected
     * After execution reset the selected card
     * @param event The click event on one particular card
     */
    public void placeCardServer(MouseEvent event){
        if(ViewModelState.getInstance().isClientTurn() && ViewModelState.getInstance().getPhase() == GamePhase.PLACING && selected!=null){
            List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();
            int rowIndex = GridPane.getRowIndex((ImageView) event.getSource());
            int colIndex = GridPane.getColumnIndex((ImageView) event.getSource());
            BoardSlot boardSlot = new BoardSlot(colIndex-CENTER_SLOT.getHoriz(),rowIndex*-1+CENTER_SLOT.getVert());
            BoardSlot selectedBoardSlot = boardSlot.getSlotAt(getCorner(event));

            ResponseStatus response;
            if (ViewModelState.getInstance().getAvailableSlots().contains(selectedBoardSlot)) {
                if (selected == playerCard1){
                    response = ClientConnection.placeCard(playerHand.get(0), selectedCardSide, selectedBoardSlot);
                } else if (selected == playerCard2) {
                    response = ClientConnection.placeCard(playerHand.get(1),selectedCardSide, selectedBoardSlot);
                }else if (selected == playerCard3){
                    response = ClientConnection.placeCard(playerHand.get(2),selectedCardSide, selectedBoardSlot);
                } else {
                    Alert alertBox = new Alert(Alert.AlertType.ERROR);
                    alertBox.setContentText("Select a card!");
                    alertBox.show();
                    return;
                }

                if (response == null) {
                    Alert alertBox = new Alert(Alert.AlertType.ERROR);
                    alertBox.setContentText("Error on placeCard phase");
                    alertBox.show();
                    return;
                } else if (response.getErrorCode() != 0) {
                    Alert alertBox = new Alert(Alert.AlertType.ERROR);
                    alertBox.setContentText("Error on placeCard: " + response.getErrorMessage());
                    alertBox.show();
                    return;
                }

                // reset the selected card if everything goes right
                selected.setEffect(null);
                selected = null;
            } else {
                Alert alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setContentText("Not a valid slot selected!");
                alertBox.show();
            }
        }
    }

    /**
     * Method used to get the relative location of the board with respect to the selected corner
     * @param event the click event on the corner
     * @return the relative location of the boardSlot
     */
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

    /**
     * Method used to pick up a card.
     * If the turn is correct it triggers the pickup on server if it is the second time clicking the same card
     * @param event The event bounded to all possible drawable cards {@link #visibleGoldCard1} {@link #visibleGoldCard2} {@link #goldCardsDeck} {@link #resourceCardsDeck} {@link #visibleResourceCard2} {@link #visibleResourceCard2}
     */
    public void pickupCard(MouseEvent event){
        if (ViewModelState.getInstance().isClientTurn() && ViewModelState.getInstance().getPhase() == GamePhase.DRAWING) {
            if(pickedCard == event.getSource()) {
                pickUpCardServer(event);
            } else {
                setPickedCard(event);
            }
        }
    }

    /**
     * Method needed to add double-click picking
     * If the card is clicked for the second time nothing happens, otherwise it is selected
     * @param event The event on one of the drawable cards.
     */
    public void setPickedCard(MouseEvent event){
        Glow glow = new Glow(0.8);
        if (pickedCard!=null)pickedCard.setEffect(null);
        pickedCard = (ImageView) event.getSource();
        pickedCard.setEffect(glow);
    }

    /**
     * Method to fire the draw or take card on server side via {@link ClientConnection#drawCard(DrawType)} or {@link ClientConnection#takeCard(int, DrawType)}
     * Gets the type of draw from the input event
     * After execution reset the picked card
     * @param event The click event on one particular drawable  card
     */
    public void pickUpCardServer(MouseEvent event){
        if (pickedCard == event.getSource()) {
            List<Integer> resourceCards = ViewModelState.getInstance().getVisibleResourceCards();
            List<Integer> goldCards = ViewModelState.getInstance().getVisibleGoldCards();
            ResponseStatus response = null;
            if (pickedCard== visibleResourceCard1){
                response = ClientConnection.takeCard(resourceCards.get(0), DrawType.RESOURCE );
                setVisibleResourceCards();
            } else if (pickedCard== visibleResourceCard2) {
                response = ClientConnection.takeCard(resourceCards.get(1), DrawType.RESOURCE );
                setVisibleResourceCards();
            } else if (pickedCard== visibleGoldCard1) {
                response = ClientConnection.takeCard(goldCards.get(0), DrawType.GOLD );
                setVisibleGoldCards();
            } else if (pickedCard== visibleGoldCard2) {
                response = ClientConnection.takeCard(goldCards.get(1), DrawType.GOLD );
                setVisibleGoldCards();
            } else if (pickedCard== resourceCardsDeck) {
                response = ClientConnection.drawCard(DrawType.RESOURCE );
            } else if (pickedCard== goldCardsDeck) {
                response = ClientConnection.drawCard(DrawType.GOLD );
            }

            if (response == null || response.getErrorCode() != 0) {
                Alert alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setContentText("Error on draw phase");
                alertBox.show();
                return;
            }

            // reset the picked card if everything goes right
            pickedCard.setEffect(null);
            pickedCard = null;
        }
    }

    /**
     * Method to show the correct Image for all cards in player hand
     */
    public void setPlayerCards(){
        List<Integer> playerHand = ViewModelState.getInstance().getPlayerHand();
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        playerCard3.setImage(null);

        if (!playerHand.isEmpty()) {
            playerCard1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(playerHand.get(0)+1)))));
        }
        if (playerHand.size() > 1) {
            playerCard2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(playerHand.get(1)+1)))));
        }
        if (playerHand.size() > 2) {
            playerCard3.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(playerHand.get(2)+1)))));
        }
    }

    /**
     * Method to set the image of the visible gold cards
     */
    public void setVisibleGoldCards(){
        List<Integer> visibleGoldCards = ViewModelState.getInstance().getVisibleGoldCards();
        visibleGoldCard1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(visibleGoldCards.get(0)+1)))));
        visibleGoldCard2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(visibleGoldCards.get(1)+1)))));
    }

    /**
     * Method to set the image of the visible resource cards
     */
    public void setVisibleResourceCards(){
        List<Integer> visibleResourceCards = ViewModelState.getInstance().getVisibleResourceCards();
        visibleResourceCard1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(visibleResourceCards.get(0)+1)))));
        visibleResourceCard2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(visibleResourceCards.get(1)+1)))));
    }

    /**
     * Method to set the visible top card on decks
     */
    private void setVisibleDecks() {
        var idResource = ViewModelState.getInstance().getResourceDeckNextId();
        if (idResource == -1) {
            this.resourceCardsDeck.setImage(null);
        } else {
            this.resourceCardsDeck.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/backs/%s.png".formatted(idResource+1)))));
        }

        var idGold = ViewModelState.getInstance().getGoldDeckNextId();
        if (idGold == -1) {
            this.goldCardsDeck.setImage(null);
        } else {
            this.goldCardsDeck.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/backs/%s.png".formatted(idGold+1)))));
        }
    }

    /**
     * Method to set the correct image on common objectives
     */
    public void setCommonObjectives(){
        List<Integer> commonObjectives = ViewModelState.getInstance().getCommonObjectives();
        commonObjective1.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(commonObjectives.get(0)+87)))));
        commonObjective2.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(commonObjectives.get(1)+87)))));
    }

    /**
     * Method to set the correct image on secret objective
     */
    public void setSecretObjective(){
        int secretObjectiveCard = ViewModelState.getInstance().getSecretObjective();
        secretObjective.setImage(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/fronts/%s.png".formatted(secretObjectiveCard+87)))));
    }


    /**
     * Method to update the Scoring Board and the plateau
     */
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

            switch (ViewModelState.getInstance().getClientColor(k)) {
                case RED : {
                    redPin.setLayoutX(pinPos[v][0]+7);
                    redPin.setLayoutY(pinPos[v][1]+7);
                    break;
                }
                case BLUE : {
                    bluePin.setLayoutX(pinPos[v][0]-7);
                    bluePin.setLayoutY(pinPos[v][1]+7);
                    break;
                }
                case GREEN : {
                    greenPin.setLayoutX(pinPos[v][0]+7);
                    greenPin.setLayoutY(pinPos[v][1]-7);
                    break;
                }
                case VIOLET : {
                    violetPin.setLayoutX(pinPos[v][0]-7);
                    violetPin.setLayoutY(pinPos[v][1]-7);
                    break;
                }
            }
        });
    }

    /**
     * Method bound to "ShowBoard" button to open the board of an opponent
     * @param nickname the nickname of the opponent board
     * @throws IOException if it is impossible to load the fxml
     */
    private void openOpponentBoard(String nickname) throws IOException {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Opponent Board");

        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("fxml/opponent-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        OpponentBardController controller = fxmlLoader.getController();
        controller.setOpponentBoard(nickname);

        modalStage.setScene(scene);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> modalStage.close());

        modalStage.showAndWait();
    }

    /**
     * Method bound to "Chat" button, used to open the chat Modal
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updatePlaceCard() {
        Platform.runLater(() -> {
            if (ViewModelState.getInstance().isClientTurn()) {
                Map.Entry<BoardSlot, CardIds> newCard = ViewModelState.getInstance().getBoard().lastEntry();
                String cardSide = (newCard.getValue().cardFace == 0) ? "fronts" : "backs";

                ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(GuiApplication.class.getResourceAsStream("images/cards/%s/%s.png".formatted(cardSide,newCard.getValue().cardId+1)))));

                placeCard(newCard.getKey().getHoriz() + PlayingBoardController.CENTER_SLOT.getHoriz(),
                        newCard.getKey().getVert()* -1 + PlayingBoardController.CENTER_SLOT.getVert(),
                        cardImage
                        );

                setPlayerCards();
            }

            setScoringBoard();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateTakeCard() {
        Platform.runLater(() -> {
            setVisibleDecks();
            setVisibleResourceCards();
            setVisibleGoldCards();
            setPlayerCards();
            setScoringBoard();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateDrawCard() {
        Platform.runLater(() -> {
            setVisibleDecks();
            setPlayerCards();
            setScoringBoard();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateEndGame() {
        Platform.runLater(() -> {
            StageController.changeScene("fxml/end-game.fxml","Winners" , this);
        });
    }
}
