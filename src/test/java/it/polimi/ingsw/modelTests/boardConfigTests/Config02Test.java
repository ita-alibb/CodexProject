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
public class Config02Test
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
        // Animal resource card id = 26 (card #27), front face.
        KingdomCardFace card = ResourceCard.getCardWithId(26).getFrontFace();

        // The location on the board.
        BoardSlot cardLocation = new BoardSlot(1, -1);

        // Place card and store gained points.
        int gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,-1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(1,1));
        availableSlots.add(new BoardSlot(-1,-1));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 1, 1);
        checkItems(board.getInfo(), 1, 0, 0);

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
        // Animal resource card id = 29 (card #30), front face.
        card = ResourceCard.getCardWithId(29).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(1, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(1, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(-1,-1));
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 0, 2, 1);
        checkItems(board.getInfo(), 1, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 40,42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable, except ids 60,62.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo(), 60, 62);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #3

        // Get card to place.
        // Animal resource card id = 25 (card #26), front face.
        card = ResourceCard.getCardWithId(25).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-1, -1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,-1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,0));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 3, 0);
        checkItems(board.getInfo(), 1, 0, 1);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 40, 41.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are placeable, except ids 60, 63, 69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(), 60, 63, 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #4

        // Get card to place.
        // Animal gold card id = 61 (card #62), front face.
        card = GoldCard.getCardWithId(61).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(-1, 1);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(2, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(-1,1));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,0));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 1, 1, 3, 0);
        checkItems(board.getInfo(), 1, 0, 2);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 2);

        // All fungi gold cards are not placeable.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo());
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are placeable, except ids 60, 63, 69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(), 60, 63, 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #5

        // Get card to place.
        // Animal resource card id = 6 (card #6), front face.
        card = ResourceCard.getCardWithId(6).getFrontFace();

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
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(-2,0));
        availableSlots.add(new BoardSlot(-1,3));
        availableSlots.add(new BoardSlot(1,3));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 3, 1);
        checkItems(board.getInfo(), 1, 0, 3);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 3, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 2);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are placeable, except id 69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(), 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #6

        // Get card to place.
        // Animal gold card id = 40 (card #1), front face.
        card = GoldCard.getCardWithId(40).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(2, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(2, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(2,2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(-2,0));
        availableSlots.add(new BoardSlot(-1,3));
        availableSlots.add(new BoardSlot(3,3));
        availableSlots.add(new BoardSlot(3,1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 2, 1);
        checkItems(board.getInfo(), 2, 0, 3);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 6, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 2, 0, 2);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable, except id 60, 61, 62.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo(), 60, 61, 62);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #7

        // Get card to place.
        // Animal resource card id = 21 (card #22), front face.
        card = ResourceCard.getCardWithId(21).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(3, 3);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(3,3));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(-2,0));
        availableSlots.add(new BoardSlot(-1,3));
        availableSlots.add(new BoardSlot(3,1));
        availableSlots.add(new BoardSlot(4,2));
        availableSlots.add(new BoardSlot(4,4));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 4, 1);
        checkItems(board.getInfo(), 2, 0, 3);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 6, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 2, 0, 2);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are placeable, except id 69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(), 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion

        //region Place Card #8

        // Get card to place.
        // Animal gold card id = 67 (card #28), front face.
        card = GoldCard.getCardWithId(67).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(4, 2);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(3, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(4,2));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(2,-2));
        availableSlots.add(new BoardSlot(0,-2));
        availableSlots.add(new BoardSlot(-2,0));
        availableSlots.add(new BoardSlot(-1,3));
        availableSlots.add(new BoardSlot(4,4));
        availableSlots.add(new BoardSlot(5,3));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 1, 3, 1);
        checkItems(board.getInfo(), 2, 1, 3);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 6, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 2, 0);
        checkMultiItemPatterns(board.getInfo(), 3);
        checkSingleItemPatterns(board.getInfo(), 2, 0, 2);

        // All fungi gold cards are not placeable, except ids 40, 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 40, 41, 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are placeable, except id 69.
        checkAnimalGoldCardsArePlaceableExcept(board.getInfo(), 69);
        // All insect gold cards are not placeable.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo());

        //endregion
    }
}
