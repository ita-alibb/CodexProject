package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.model.cards.CornerLocation;
import it.polimi.ingsw.am52.model.cards.Kingdom;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.cards.ResourceCard;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.RelativeLocation;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am52.model.playingBoards.RelativeLocation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Objective class, animal tower game objective.
 */
public class AnimalTowerTest {

    //region Private Static Fields

    /**
     * The origin of the random number generator.
     */
    private static final int ORIGIN = -20;
    /**
     * The bound of the random number generator.
     */
    private static final int BOUND = 21;
    /**
     * The objective to test: Tower Pattern of Animal Kingdom (objective Id = 6).
     */
    private static final Objective TARGET = Objective.getObjectiveWithId(6);
    /**
     * The card used in the tower: card with Animal kingdom.
     */
    private static final KingdomCardFace TOWER_CARD = ResourceCard.getCardWithId(20).getFrontFace();
    /**
     * The card used in the base: card with Fungi kingdom.
     */
    private static final KingdomCardFace BASE_CARD = ResourceCard.getCardWithId(0).getFrontFace();

    //endregion

    /**
     * The animal tower pattern is composed of two cards of the animal
     * kingdom aligned in vertical direction, with a fungi kingdom
     * card placed in the top right corner of the tower.
     * This test consists of trying to find the pattern in the following
     * scenario:<ul>
     *     <li>Empty playing board, expected zero points</li>
     *     <li>Only one card of the tower, expected zero points</li>
     *     <li>The two cards of the tower, expected zero points</li>
     *     <li>The full correct pattern, expected three points</li>
     *     <li>The full pattern but with one more card at the top of the tower,
     *     expected three points</li>
     *     <li>The full pattern but with one more card at the bottom of the tower,
     *     expected three points</li>
     *     <li>The full pattern but with one more card at the top and at the
     *     bottom of the tower, expected three points</li>
     * </ul>
     */
    @Test
    @DisplayName("Animal Tower Pattern: Basic Test")
    public void testTowerLength() {

        // Define a reference slot position, with random horizontal and vertical
        // coordinates.
        final BoardSlot refSlot = DummyPlayingBoard.getRandomSlot(ORIGIN, BOUND);

        // 1) Create a dummy (empty) playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Test the pattern finder for an empty playing board.
        int gainedPoints = TARGET.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // 2) Place an animal card in the reference slot.
        board.addCard(refSlot, TOWER_CARD);

        // Test the pattern finder: expected 0 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // 3) Place an animal card at the bottom of the first card (create the tower
        // without the base).
        board.addCard(refSlot.getSlotAt(BOTTOM), TOWER_CARD);

        // Test the pattern finder: expected 0 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(0, gainedPoints);

        // 4) Place a fungi card in the top-right corner, to create
        // a complete tower pattern (tower + base).
        board.addCard(refSlot.getSlotAt(TOP_RIGHT), BASE_CARD);

        // Test the pattern finder: expected 3 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(3, gainedPoints);

        // 5) Place an additional animal card at the top of the tower.
        board.addCard(refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM), TOWER_CARD);

        // Test the pattern finder: expected 3 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(3, gainedPoints);

        // 6) Place an additional animal card at the bottom of the tower.
        board.addCard(refSlot.getSlotAt(TOP), TOWER_CARD);
        // Remove the additional card at the top of the tower.
        board.removeCard(refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM));

        // Test the pattern finder: expected 3 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(3, gainedPoints);

        // 7) Place the additional card at the top, again.
        board.addCard(refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM), TOWER_CARD);

        // Test the pattern finder: expected 3 points.
        gainedPoints = TARGET.calculatePoints(board);
        assertEquals(3, gainedPoints);

    }

    /**
     * The animal tower pattern is composed of two cards of the animal
     * kingdom aligned in vertical direction, with a fungi kingdom
     * card placed in the top right corner of the tower.
     * This test consists of trying to find the pattern in the following
     * scenario:<ul>
     *     <li>Place the base card in the wrong corner</li>
     *     <li>Place the base card in all corners</li>
     *     <li>Place the base card all around the tower</li>
     * </ul>
     */
    @Test
    @DisplayName("Animal Tower Pattern: Base Location Test")
    public void testTowerBase() {

        // 1) Create a dummy (empty) playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Define a reference slot position, with random horizontal and vertical
        // coordinates.
        final BoardSlot refSlot = DummyPlayingBoard.getRandomSlot(ORIGIN, BOUND);

        // Create the tower placing two animal card aligned in vertical
        // direction.
        board.addCard(refSlot, TOWER_CARD);
        board.addCard(refSlot.getSlotAt(BOTTOM), TOWER_CARD);

        // 1.a) Place the fungi card in the wrong corner (bottom-right)
        BoardSlot wrongBase = refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM_RIGHT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 0 points.
        assertEquals(0, TARGET.calculatePoints(board));

        // 1.b) Remove wrong base and add a new base at the (wrong) bottom-left
        // corner.
        board.removeCard(wrongBase);
        wrongBase = refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM_LEFT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 0 points.
        assertEquals(0, TARGET.calculatePoints(board));

        // 1.c) Remove wrong base and add a new base at the (wrong) top-left
        // corner.
        board.removeCard(wrongBase);
        wrongBase = refSlot.getSlotAt(TOP_LEFT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 0 points.
        assertEquals(0, TARGET.calculatePoints(board));

        // 1.d) Remove wrong base and add a new base at the (correct) top-right
        // corner.
        board.removeCard(wrongBase);
        wrongBase = refSlot.getSlotAt(TOP_RIGHT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 3 points.
        assertEquals(3, TARGET.calculatePoints(board));

        // 2) Add a base in all corners.
        wrongBase = refSlot.getSlotAt(BOTTOM).getSlotAt(RelativeLocation.BOTTOM_RIGHT);
        board.addCard(wrongBase, BASE_CARD);
        wrongBase = refSlot.getSlotAt(BOTTOM).getSlotAt(BOTTOM_LEFT);
        board.addCard(wrongBase, BASE_CARD);
        wrongBase = refSlot.getSlotAt(RelativeLocation.TOP_LEFT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 3 points.
        assertEquals(3, TARGET.calculatePoints(board));

        // 3) Add bases in the middle of the tower, too.
        wrongBase = refSlot.getSlotAt(BOTTOM_RIGHT);
        board.addCard(wrongBase, BASE_CARD);
        wrongBase = refSlot.getSlotAt(RelativeLocation.BOTTOM_LEFT);
        board.addCard(wrongBase, BASE_CARD);

        // Test the pattern finder: expected 3 points.
        assertEquals(3, TARGET.calculatePoints(board));
    }

    /**
     * The animal tower pattern is composed of two cards of the animal
     * kingdom aligned in vertical direction, with a fungi kingdom
     * card placed in the top right corner of the tower.
     * This test consists of trying to find the pattern in the following
     * scenario:<ul>
     *     <li>Two patterns aligned in vertical direction</li>
     *     <li>Two patterns aligned in horizontal direction</li></ul>
     */
    @Test
    @DisplayName("Animal Tower Pattern: Two Sided Patterns")
    public void testDoublePattern(){

        // Get a random reference location on the board.
        final BoardSlot refSlot = DummyPlayingBoard.getRandomSlot(ORIGIN, BOUND);

        // Get a dummy playing board with a tower pattern placed on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refSlot, TOWER_CARD, BASE_CARD, CornerLocation.TOP_RIGHT
        );

        // Test the pattern has been build properly.
        assertEquals(3, TARGET.calculatePoints(board));

        // Place an additional pattern on the right of the first.
        final BoardSlot topOrigin = refSlot.getSlotAt(RIGHT);
        // Place the pattern.
        DummyPlayingBoard.placeTowerPattern(board, topOrigin, TOWER_CARD, BASE_CARD, CornerLocation.TOP_RIGHT);

        // Test the two patterns are found by the objective.
        assertEquals(6, TARGET.calculatePoints(board));

        // 2) Get a new board with a tower pattern on it.
        board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refSlot, TOWER_CARD, BASE_CARD, CornerLocation.TOP_RIGHT
        );

        // Test the pattern has been build properly.
        assertEquals(3, TARGET.calculatePoints(board));

        // Place an additional pattern on top of the first.
        final BoardSlot rightOrigin = refSlot.getSlotAt(TOP).getSlotAt(TOP);
        // Place the pattern.
        DummyPlayingBoard.placeTowerPattern(board, rightOrigin, TOWER_CARD, BASE_CARD, CornerLocation.TOP_RIGHT);

        // Test the two patterns are found by the objective.
        assertEquals(6, TARGET.calculatePoints(board));
    }

    /**
     * Test the animal tower pattern on a striped bord, with alternating kingdom
     * along the columns.
     */
    @Test
    @DisplayName("Plant Tower Pattern: Striped Board")
    public void testStripedBoard() {

        // Get a random origin of the board.
        final BoardSlot origin = DummyPlayingBoard.getRandomSlot(ORIGIN, BOUND);

        // Get a striped board, with alternating kingdom along the column,
        // having 12 rows and 2 columns.
        DummyPlayingBoard board = DummyPlayingBoard.getStripedKingdomBoard(
                origin,
                12,
                3,
                BASE_CARD.getKingdom(),
                TOWER_CARD.getKingdom()
        );

        // Check number of patterns: expected 2 patterns, 2 x 3 = 6 points.
        assertEquals(6, TARGET.calculatePoints(board));

        // Get a striped board, with alternating kingdom along the column,
        // having 12 rows and 8 columns.
        board = DummyPlayingBoard.getStripedKingdomBoard(
                origin,
                12,
                8,
                BASE_CARD.getKingdom(),
                TOWER_CARD.getKingdom()
        );

        // Check number of patterns: expected 6 patterns, 6 x 3 = 18 points.
        assertEquals(18, TARGET.calculatePoints(board));
    }

    /**
     * Test the animal tower pattern with the pattern having the wrong kingdom
     * for the base card:<ul>
     *     <li>Test with three wrong kingdom of the base (0 points)</li>
     *     <li>Test with the correct kingdom of the base (3 points)</li>
     * </ul>
     */
    @Test
    @DisplayName("Animal Tower Pattern: Wrong Base Kingdom")
    public void testWrongBaseKingdom() {

        // All the kingdoms.
        Kingdom[] kingdoms = new Kingdom[] {
                Kingdom.FUNGI_KINGDOM,
                Kingdom.PLANT_KINGDOM,
                Kingdom.ANIMAL_KINGDOM,
                Kingdom.INSECT_KINGDOM
        };

        // Iterate over each kingdom.
        for (Kingdom kingdom : kingdoms) {
            // Get a card of the appropriate kingdom.
            KingdomCardFace baseCard = switch (kingdom.getColor()) {
                case RED -> ResourceCard.getCardWithId(0).getBackFace();
                case GREEN -> ResourceCard.getCardWithId(10).getBackFace();
                case BLUE -> ResourceCard.getCardWithId(20).getBackFace();
                case VIOLET -> ResourceCard.getCardWithId(30).getBackFace();
            };
            // Get a random location on the board.
            final BoardSlot location = DummyPlayingBoard.getRandomSlot(ORIGIN, BOUND);
            // Create the pattern having the selected base card.
            DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                    location,
                    TOWER_CARD,
                    baseCard,
                    CornerLocation.TOP_RIGHT
            );

            // The expected points, depending on the kingdom of the base card used.
            final int expectedPoints = (kingdom == BASE_CARD.getKingdom()) ? 3 : 0;

            // Check points.
            assertEquals(expectedPoints, TARGET.calculatePoints(board));
        }
    }

}
