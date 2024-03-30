package it.polimi.ingsw.modelTests.boardConfigTests;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.PlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static it.polimi.ingsw.modelTests.boardConfigTests.CheckBoardInfo.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for PlayingBoard class.
 */
public class Config01Test
{
    /**
     * Place a sequence of cards on the playing board and each time check
     * if the boards has correct resources, items, available slots, covered slots,
     * patterns, ecc...
     */
    @Test
    @DisplayName("Board Config #1")
    public void buildConfig()
    {
        //region Starter Card

        // Get the starter card for the playing board:
        // Starter card id = 85 (Starter card #6), front face.
        StarterCardFace starterCard = StarterCard.getCardWithId(85).getFrontFace();

        // Create the playing board with the selected starter card.
        PlayingBoard board = new PlayingBoard(starterCard);

        // Check resources and items.
        checkResources(board.getInfo(), 1, 1, 1, 0);
        checkItems(board.getInfo(), 0, 0, 0);

        // The placed slots (zero, starter card does not count).
        Set<BoardSlot> placedSlots = new HashSet<>();
        // The available slots.
        Set<BoardSlot> availableSlots = new HashSet<>();
        availableSlots.add(new BoardSlot(1, 1));
        availableSlots.add(new BoardSlot(-1, 1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);
        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // Check all Gold cards are NOT placeable.
        checkGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #1

        // Get card to place.
        // Fungi resource card id = 0 (card #1), front face.
        KingdomCardFace card = ResourceCard.getCardWithId(0).getFrontFace();

        // The location on the board.
        BoardSlot cardLocation = new BoardSlot(1, 1);

        // Place card and store gained points.
        int gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 1, 1, 0);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #2

        // Get card to place.
        // Fungi resource card id = 1 (card #2), front face.
        card = ResourceCard.getCardWithId(1).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(2, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(2,2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,1));
        availableSlots.add(new BoardSlot(3,3));
        availableSlots.add(new BoardSlot(1,3));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 1, 1, 0);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #3

        // Get card to place.
        // Fungi gold card id = 46 (card #47), front face.
        card = GoldCard.getCardWithId(46).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(3, 3);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(3,3));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,1));
        availableSlots.add(new BoardSlot(2,4));
        availableSlots.add(new BoardSlot(1,3));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 4, 1, 1, 0);
        checkItems(board.getInfo(), 0, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #4

        // Get card to place.
        // Fungi gold card id = 40 (card #41), front face.
        card = GoldCard.getCardWithId(40).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(2, 4);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(1, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(2,4));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,1));
        availableSlots.add(new BoardSlot(3,5));
        availableSlots.add(new BoardSlot(1,3));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 4, 1, 1, 0);
        checkItems(board.getInfo(), 1, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #5

        // Get card to place.
        // Plant resource card id = 10 (card #11), front face.
        card = ResourceCard.getCardWithId(10).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(3, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points.
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(3,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,5));
        availableSlots.add(new BoardSlot(1,3));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 4, 3, 1, 0);
        checkItems(board.getInfo(), 1, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #6

        // Get card to place.
        // Fungi resource card id = 4 (card #5), front face.
        card = ResourceCard.getCardWithId(4).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(0, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(0,2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,5));
        availableSlots.add(new BoardSlot(1,3));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 4, 4, 1, 0);
        checkItems(board.getInfo(), 2, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 2, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #7

        // Get card to place.
        // Plant gold card id = 55 (card #56), front face.
        card = GoldCard.getCardWithId(55).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(1, 3);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(6, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,3));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,5));
        availableSlots.add(new BoardSlot(0,4));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 4, 1, 0);
        checkItems(board.getInfo(), 1, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #8

        // Get card to place.
        // Plant gold card id = 58 (card #59), front face.
        card = GoldCard.getCardWithId(58).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(0, 4);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(0,4));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(3,5));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 4, 1, 0);
        checkItems(board.getInfo(), 1, 2, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 2, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #9

        // Get card to place.
        // Plant gold card id = 57 (card #58), front face.
        card = GoldCard.getCardWithId(57).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(3, 5);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(3,5));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(4,6));
        availableSlots.add(new BoardSlot(2,6));
        availableSlots.add(new BoardSlot(-1,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 4, 1, 0);
        checkItems(board.getInfo(), 1, 2, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 0, 2, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #10

        // Get card to place.
        // Animal resource card id = 23 (card #24), front face.
        card = ResourceCard.getCardWithId(23).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-1, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(4,6));
        availableSlots.add(new BoardSlot(2,6));
        availableSlots.add(new BoardSlot(-2,2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 3, 3, 0);
        checkItems(board.getInfo(), 1, 2, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 0, 2, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are placeable, except ids 60,63,69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(),60, 63, 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #11

        // Get card to place.
        // Animal resource card id = 21 (card #22), front face.
        card = ResourceCard.getCardWithId(21).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-2, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-2,2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(4,6));
        availableSlots.add(new BoardSlot(2,6));
        availableSlots.add(new BoardSlot(-3,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 3, 5, 0);
        checkItems(board.getInfo(), 1, 2, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 2, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 2, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 0, 2, 0);

        // All fungi gold cards are placeable, except ids 42,45,49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 42, 45, 49);
        // All plant gold cards are placeable, except ids 50,53,59.
        checkPlantGoldCardsArePlaceableExcept(board.getInfo(),50, 53, 59);
        // All animal gold cards are placeable, except ids 60,63.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(),60, 63);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion
    }
}
