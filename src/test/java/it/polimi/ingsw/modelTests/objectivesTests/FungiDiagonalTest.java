package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.Model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.Model.cards.ResourceCard;
import it.polimi.ingsw.am52.Model.objectives.Objective;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class FungiDiagonalTest
{
    /**
     * Test the PatternFinder of the diagonal pattern of
     * the fungi kingdom:<ul>
     *     <li>method calculatePoints() (calls the method findPatterns)</li>
     * The test consists in placing some cards on a dummy playing board and
     * </ul>
     * calculate the gained point for the diagonal pattern with three fungi
     * cards aligned from bottom-left to top-right:<ul>
     *     <li>Empty playing board</li>
     *     <li>One diagonal with 2 cards</li>
     *     <li>One diagonal with 3 cards</li>
     *     <li>One diagonal with 5 cards</li>
     *     <li>One diagonal with 6 cards (two patterns)</li>
     *     <li>One diagonal with 7 cards (two patterns)</li>
     * </ul>
     */
    @Test
    @DisplayName("Fungi Diagonal Pattern")
    public void testApp()
    {
        //Get the objective instance to test (Fungi diagonal,
        // objective #0).
        Objective target = Objective.getObjectiveWithId(0);

        // Get a fungi card.
        KingdomCardFace fungiCard = ResourceCard.getCardWithId(0).getFrontFace();

        // Get a plant card (different kingdom).
        KingdomCardFace plantCard = ResourceCard.getCardWithId(10).getFrontFace();

        // Create a dummy playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Test the pattern finder for an empty playing board.
        int gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place a fungi card on the board.
        board.addCard(new BoardSlot(2,2), fungiCard);

        // Test the pattern finder: expected 0 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place a fungi card on the board, creating a diagonal with
        // two cards.
        board.addCard(new BoardSlot(3,3), fungiCard);

        // Test the pattern finder: expected 0 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place a fungi card on the board, creating a diagonal with
        // three cards.
        board.addCard(new BoardSlot(4,4), fungiCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);

        // Place a fungi card on the board, creating a diagonal with
        // four cards.
        board.addCard(new BoardSlot(1,1), fungiCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);

        // Place two fungi cards on the board, creating a diagonal with
        // six cards.
        board.addCard(new BoardSlot(5,5), fungiCard);
        board.addCard(new BoardSlot(6,6), fungiCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Place a fungi card on the board, creating a diagonal with
        // seven cards.
        board.addCard(new BoardSlot(7,7), fungiCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Remove the card at the middle of the diagonal, creating
        // two diagonals with three cards each.
        board.removeCard(new BoardSlot(4, 4));

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Place a plant card that connects the two diagonals.
        board.addCard(new BoardSlot(4, 4), plantCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Replace the first card of the first diagonal.
        board.replaceCard(new BoardSlot(1, 1), plantCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);
    }
}
