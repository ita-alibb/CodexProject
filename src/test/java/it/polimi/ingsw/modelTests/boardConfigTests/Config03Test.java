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
public class Config03Test
{
    /**
     * Place a sequence of cards on the playing board and each time check
     * if the boards has correct resources, items, available slots, covered slots,
     * patterns, ecc...
     */
    @Test
    @DisplayName("Board Config #2")
    public void buildConfig()
    {
        //region Starter Card

        // Get the starter card for the playing board:
        // Starter card id = 84 (Starter card #5), front face.
        StarterCardFace starterCard = StarterCard.getCardWithId(84).getFrontFace();

        // Create the playing board with the selected starter card.
        PlayingBoard board = new PlayingBoard(starterCard);

        // Check resources and items.
        checkResources(board.getInfo(), 0, 1, 1, 1);
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
        // Fungi resource card id = 3 (card #4), front face.
        KingdomCardFace card = ResourceCard.getCardWithId(3).getFrontFace();

        // The location on the board.
        BoardSlot cardLocation = new BoardSlot(-1, 1);

        // Place card and store gained points.
        int gainedPoints = board.placeCard(cardLocation, card);

        // Check points.
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-2,0));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 1, 1);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 40,41,42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #2

        // Get card to place.
        // Insect resource card id = 31 (card #32), front face.
        card = ResourceCard.getCardWithId(31).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-2, 0);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-2,0));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-3,-1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 1, 3);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

        //region Place Card #3

        // Get card to place.
        // Insect resource card id = 35 (card #36), front face.
        card = ResourceCard.getCardWithId(35).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-3, -1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-3,-1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-2,-2));
        availableSlots.add(new BoardSlot(-4,0));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 1, 1, 3);
        checkItems(board.getInfo(), 0, 0, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

        //region Place Card #4

        // Get card to place.
        // Fungi gold card id = 48 (card #9), front face.
        card = GoldCard.getCardWithId(48).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-4, 0);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-4,0));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-2,-2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 1, 1, 3);
        checkItems(board.getInfo(), 0, 0, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

        //region Place Card #5

        // Get card to place.
        // Fungi gold card id = 46 (card #7), front face.
        card = GoldCard.getCardWithId(46).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(1, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(-2,-2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 3, 1, 1, 3);
        checkItems(board.getInfo(), 0, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 2, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are placeable, except ids 49.
        checkFungiGoldCardsArePlaceableExcept(board.getInfo(), 49);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

        //region Place Card #6

        // Get card to place.
        // Fungi gold card id = 47 (card #8), front face.
        card = GoldCard.getCardWithId(47).getFrontFace();

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
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 1, 3);
        checkItems(board.getInfo(), 1, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

        //region Place Card #7

        // Get card to place.
        // Insect gold card id = 78 (card #39), front face.
        card = GoldCard.getCardWithId(78).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(0, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(0,2));
        // The available slots.
        availableSlots.clear();
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 1, 1, 1, 3);
        checkItems(board.getInfo(), 2, 1, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 2);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 2, 0, 0);

        // All fungi gold cards are not placeable.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo());
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are placeable, except id 79.
        checkInsectGoldCardsArePlaceableExcept(board.getInfo(), 79);

        //endregion

    }
}
