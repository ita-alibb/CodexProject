package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.model.cards.Kingdom;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.cards.ResourceCard;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Objective class, animal diagonal game objective.
 */
public class InsectDiagonalTest
{
    /**
     * Test the PatternFinder of the diagonal pattern of
     * the insect kingdom:<ul>
     *     <li>method calculatePoints() (calls the method findPatterns())</li>
     * </ul>
     * The test consists in placing some cards on a dummy playing board and
     * calculate the gained point for the diagonal pattern (three insect
     * cards aligned from bottom-left to top-right):<ul>
     *     <li>Empty playing board</li>
     *     <li>One diagonal with 2 cards</li>
     *     <li>One diagonal with 3 cards</li>
     *     <li>One diagonal with 5 cards</li>
     *     <li>One diagonal with 6 cards (two patterns)</li>
     *     <li>One diagonal with 7 cards (two patterns)</li>
     *     <li>Replace a card on the diagonal with a card of different kingdom.</li>
     * </ul>
     */
    @Test
    @DisplayName("Insect Diagonal Pattern on Diagonal Board")
    public void testOnDiagonal()
    {
        // Get the objective instance to test (insect diagonal,
        // objective #2).
        Objective target = Objective.getObjectiveWithId(3);

        // Get an insect card.
        KingdomCardFace insectCard = ResourceCard.getCardWithId(30).getFrontFace();

        // Get an insect card (different kingdom).
        KingdomCardFace fungiCard = ResourceCard.getCardWithId(0).getFrontFace();

        // Create a dummy playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Test the pattern finder for an empty playing board.
        int gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place an insect card on the board.
        board.addCard(new BoardSlot(2,2), insectCard);

        // Test the pattern finder: expected 0 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place an insect card on the board, creating a diagonal with
        // two cards (left hand diagonal).
        board.addCard(new BoardSlot(1,3), insectCard);

        // Test the pattern finder: expected 0 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // Place an insect card on the board, creating a diagonal with
        // three cards.
        board.addCard(new BoardSlot(0,4), insectCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);

        // Place an insect card on the board, creating a diagonal with
        // four cards.
        board.addCard(new BoardSlot(3,1), insectCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);

        // Place two insect cards on the board, creating a diagonal with
        // six cards.
        board.addCard(new BoardSlot(-1,5), insectCard);
        board.addCard(new BoardSlot(-2,6), insectCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Place an insect card on the board, creating a diagonal with
        // seven cards.
        board.addCard(new BoardSlot(-3,7), insectCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Remove the card at the middle of the diagonal, creating
        // two diagonals with three cards each.
        board.removeCard(new BoardSlot(0, 4));

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Place an insect card that connects the two diagonals.
        board.addCard(new BoardSlot(0, 4), fungiCard);

        // Test the pattern finder: expected 2x2=4 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(4, gainedPoints);

        // Replace the first card of the first diagonal.
        board.replaceCard(new BoardSlot(3, 1), fungiCard);

        // Test the pattern finder: expected 2x1=2 points.
        gainedPoints = target.calculatePoints(board);
        assertEquals(2, gainedPoints);
    }

    /**
     * Test the PatternFinder of the diagonal pattern of
     * the insect kingdom:<ul>
     *     <li>method calculatePoints() (calls the method findPatterns())</li>
     * </ul>
     * The test consists in creating a rectangular board with all cards
     * of the same kingdom and calculate the gained point for the diagonal
     * pattern (three insect cards aligned from bottom-left to top-right):<ul>
     *     <li>1x1 rectangle</li>
     *     <li>2x2 rectangle</li>
     *     <li>3x3 rectangle</li>
     *     <li>3x6 rectangle</li>
     *     <li>6x6 rectangle</li>
     * </ul>
     */
    @Test
    @DisplayName("Insect Diagonal Pattern on Rectangular Board")
    public void testOnRectangularBoard() {

        // Get the objective instance to test (insect diagonal,
        // objective #0).
        Objective target = Objective.getObjectiveWithId(3);

        // The ref. position of the rectangle on the board.
        BoardSlot origin = new BoardSlot(1, 1);

        // Create a square board with only one card (1x1).
        DummyPlayingBoard board = DummyPlayingBoard.getUniformKingdomBoard(
                origin, 1, 1, Kingdom.INSECT_KINGDOM
        );

        // Expected zero points.
        assertEquals(0, target.calculatePoints(board));

        // Create a square board (2x2).
        board = DummyPlayingBoard.getUniformKingdomBoard(
                origin, 2, 2, Kingdom.INSECT_KINGDOM
        );

        // Expected zero points.
        assertEquals(0, target.calculatePoints(board));

        // Create a square board (3x3).
        board = DummyPlayingBoard.getUniformKingdomBoard(
                origin, 3, 3, Kingdom.INSECT_KINGDOM
        );

        // Expected 2 points (the main diagonal).
        assertEquals(2, target.calculatePoints(board));

        // Create a rectangular board (3x6).
        /*
         *    123456
         *  3 X X X
         *  2  X X X
         *  1 X X X
         */
        board = DummyPlayingBoard.getUniformKingdomBoard(
                origin, 3, 6, Kingdom.INSECT_KINGDOM
        );

        // Expected 4 points (2 diagonal patterns).
        assertEquals(4, target.calculatePoints(board));

        // Create a rectangular board (6x6).
        /*
         *    123456
         *  6  X X X
         *  5 X X X
         *  4  X X X
         *  3 X X X
         *  2  X X X
         *  1 X X X
         */
        board = DummyPlayingBoard.getUniformKingdomBoard(
                origin, 6, 6, Kingdom.INSECT_KINGDOM
        );

        // Expected 8 points (4 diagonal patterns).
        assertEquals(8, target.calculatePoints(board));


    }
}
