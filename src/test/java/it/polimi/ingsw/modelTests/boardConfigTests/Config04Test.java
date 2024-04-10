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
public class Config04Test
{
    /**
     * Place a sequence of cards on the playing board and each time check
     * if the boards has correct resources, items, available slots, covered slots,
     * patterns, ecc...
     */
    @Test
    @DisplayName("Board Config #4")
    public void buildConfig()
    {
        //region Starter Card

        // Get the starter card for the playing board:
        // Starter card id = 80 (Starter card #1), front face.
        StarterCardFace starterCard = StarterCard.getCardWithId(80).getFrontFace();

        // Create the playing board with the selected starter card.
        PlayingBoard board = new PlayingBoard(starterCard);

        // Check resources and items.
        checkResources(board.getInfo(), 0, 1, 0, 2);
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

        // Check all Gold cards are NOT placeable, except id 70.
        checkGoldCardsNotPlaceableExcept(board.getInfo(), 70);

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
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,0));
        availableSlots.add(new BoardSlot(2,2));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 0, 0, 2);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except id 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 42);
        // All plant gold cards are not placeable.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo());
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable, except id 72.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo(), 72);

        //endregion

        //region Place Card #2

        // Get card to place.
        // Planet resource card id = 11 (card #12), front face.
        card = ResourceCard.getCardWithId(11).getFrontFace();

        // The location on the board.
        cardLocation = new BoardSlot(2, 0);

        // Place card and store gained points.
        gainedPoints = board.placeCard(cardLocation, card);

        // Check points-
        assertEquals(0, gainedPoints);

        // The covered slots.
        placedSlots.add(new BoardSlot(2,0));
        // The available slots.
        availableSlots.clear();
        availableSlots.add(new BoardSlot(-1,-1));
        availableSlots.add(new BoardSlot(-1,1));
        availableSlots.add(new BoardSlot(0,2));
        availableSlots.add(new BoardSlot(2,2));
        availableSlots.add(new BoardSlot(3,1));
        availableSlots.add(new BoardSlot(3,-1));
        // Check slots.
        checkBoardSlots(board.getInfo(), placedSlots, availableSlots);

        // Check resources and items.
        checkResources(board.getInfo(), 2, 2, 0, 2);
        checkItems(board.getInfo(), 0, 0, 0);

        // Check objectives.
        checkDiagonalPatterns(board.getInfo(), 0, 0, 0, 0);
        checkTowerPatterns(board.getInfo(), 0, 0, 0, 0);
        checkResourcePatterns(board.getInfo(), 0, 0, 0, 0);
        checkMultiItemPatterns(board.getInfo(), 0);
        checkSingleItemPatterns(board.getInfo(), 0, 0, 0);

        // All fungi gold cards are not placeable, except ids 41, 42.
        checkFungiGoldCardsNotPlaceableExcept(board.getInfo(), 41, 42);
        // All plant gold cards are not placeable, except ids 50, 51.
        checkPlantGoldCardsNotPlaceableExcept(board.getInfo(), 50, 51);
        // All animal gold cards are not placeable.
        checkAnimalGoldCardsNotPlaceableExcept(board.getInfo());
        // All insect gold cards are not placeable, except ids 70, 72.
        checkInsectGoldCardsNotPlaceableExcept(board.getInfo(), 70, 72);

        //endregion

    }
}
