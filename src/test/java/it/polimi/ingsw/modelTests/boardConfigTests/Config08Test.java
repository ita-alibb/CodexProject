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
public class Config08Test
{
    /**
     * Place a sequence of cards on the playing board and each time check
     * if the boards has correct resources, items, available slots, covered slots,
     * patterns, ecc...
     */
    @Test
    @DisplayName("Board Config #8")
    public void buildConfig()
    {
        //region Starter Card

        // Get the starter card for the playing board:
        // Starter card id = 80 (Starter card #1), back face.
        StarterCardFace starterCard = StarterCard.getCardWithId(80).getBackFace();

        // Create the playing board with the selected starter card.
        PlayingBoard board = new PlayingBoard(starterCard);

        // Check resources and items.
        checkResources(board.getInfo(), 1, 1, 1, 1);
        checkItems(board.getInfo(), 0, 0, 0);

        // The placed slots (zero, starter card does not count).
        Set<BoardSlot> placedSlots = new HashSet<>();
        // The available slots.
        Set<BoardSlot> availableSlots = new HashSet<>();
        availableSlots.add(new BoardSlot(1, 1));
        availableSlots.add(new BoardSlot(1, -1));
        availableSlots.add(new BoardSlot(-1, -1));
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
        // Fungi resource card id = 1 (card #2), front face.
        KingdomCardFace card = ResourceCard.getCardWithId(1).getFrontFace();

        // The location on the board.
        BoardSlot cardLocation = new BoardSlot(1, 1);

        // Place card and store gained points.
        int gainedPoints = board.placeCard(cardLocation, card);

        // Check points.
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,-1));
        availableSlots.add(new BoardSlot(-1,-1));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(2,0));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(0,2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 0, 1, 1);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 41, 44, 49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 41, 44, 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #2

        // Get card to place.
        // Fungi resource card id = 2 (card #3), front face.
        card = ResourceCard.getCardWithId(2).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(1, -1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,-1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(-1,-1));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 0, 0, 1);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 41, 43, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 41, 43, 44);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #3

        // Get card to place.
        // Fungi gold card id = 49 (card #10), front face.
        card = GoldCard.getCardWithId(49).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-1, -1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(5, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,-1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,0));
        availableSlots.add(new BoardSlot(-2,-2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 0, 0, 0);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 41, 42, 43, 44, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 41, 42, 43, 44, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #4

        // Get card to place.
        // Fungi gold card id = 46 (card #7), front face.
        card = GoldCard.getCardWithId(46).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-2, 0);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-2,0));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,-2));
        availableSlots.add(new BoardSlot(-3,-1));
        availableSlots.add(new BoardSlot(-3,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 0, 0, 0);
        checkItems(board.getInfo(), 0, 1, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 41, 42, 43, 44, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 41, 42, 43, 44, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #5

        // Get card to place.
        // Fungi gold card id = 48 (card #9), front face.
        card = GoldCard.getCardWithId(48).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-2, -2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-2,-2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-3,1));
        availableSlots.add(new BoardSlot(-1,-3));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 0, 0, 0);
        checkItems(board.getInfo(), 0, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 41, 42, 43, 44, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 41, 42, 43, 44, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #6

        // Get card to place.
        // Fungi resource card id = 0 (card #1), back face.
        card = ResourceCard.getCardWithId(0).getBackFace();

        // The location on the board.
        cardLocation = new BoardSlot(-3, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-3,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-1,-3));
        availableSlots.add(new BoardSlot(-2,2));
        availableSlots.add(new BoardSlot(-4,2));
        availableSlots.add(new BoardSlot(-4,0));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 6, 0, 0, 0);
        checkItems(board.getInfo(), 0, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 4, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 41, 42, 43, 44, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 41, 42, 43, 44, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #7

        // Get card to place.
        // Plant resource card id = 10 (card #11), back face.
        card = ResourceCard.getCardWithId(10).getBackFace();

        // The location on the board.
        cardLocation = new BoardSlot(-1, -3);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,-3));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,2));
        availableSlots.add(new BoardSlot(-4,2));
        availableSlots.add(new BoardSlot(-4,0));
        availableSlots.add(new BoardSlot(-2,-4));
        availableSlots.add(new BoardSlot(0,-4));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 6, 1, 0, 0);
        checkItems(board.getInfo(), 0, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 3, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 4, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 42, 43, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 42, 43, 45);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #8

        // Get card to place.
        // Plant resource card id = 11 (card #12), back face.
        card = ResourceCard.getCardWithId(11).getBackFace();

        // The location on the board.
        cardLocation = new BoardSlot(2, -2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(2,-2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,2));
        availableSlots.add(new BoardSlot(-4,2));
        availableSlots.add(new BoardSlot(-4,0));
        availableSlots.add(new BoardSlot(-2,-4));
        availableSlots.add(new BoardSlot(0,-4));
        availableSlots.add(new BoardSlot(3,-1));
        availableSlots.add(new BoardSlot(3,-3));
        availableSlots.add(new BoardSlot(1,-3));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 5, 2, 0, 0);
        checkItems(board.getInfo(), 0, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 6, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 40, 42, 43, 45.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 40, 42, 43, 45);
        // All plant gold cards are not placeable, except id 51.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo(), 51);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

    }
}
