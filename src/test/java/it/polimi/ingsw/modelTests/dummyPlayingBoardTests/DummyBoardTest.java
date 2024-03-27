package it.polimi.ingsw.modelTests.dummyPlayingBoardTests;

import it.polimi.ingsw.am52.model.cards.CornerLocation;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.cards.ResourceCard;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import static it.polimi.ingsw.am52.model.playingBoards.RelativeLocation.*;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for DummyPlayingBoard class.
 */
public class DummyBoardTest {

    /**
     * Check the constructor of the DummyPlayingBoardClass:<ul>
     *     <li>default constructor</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Constructor Test")
    public void constructorTest() {

        // Get a dummy playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Check all resources are zero.
        assertEquals(0, board.getResources().getFungiCount());
        assertEquals(0, board.getResources().getPlantCount());
        assertEquals(0, board.getResources().getAnimalCount());
        assertEquals(0, board.getResources().getInsectCount());

        // Check all items are zero.
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check there are not placed cards.
        assertEquals(0, board.getCoveredSlots().size());

    }

    /**
     * Test if the tower with top-right orientation is correctly placed on the
     * board (method getNewBoardWithTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Test Top-Right Tower")
    public void testTopRightTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.TOP_RIGHT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Check there are only three cards on the board.
        assertEquals(3, board.getCoveredSlots().size());

        // Check location of cards is correct.
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(TOP_RIGHT)));

        // Check cards themselves are correct.
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(TOP_RIGHT)));

    }

    /**
     * Test if the tower with bottom-right orientation is correctly placed on the
     * board (method getNewBoardWithTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Test Bottom-Right Tower")
    public void testBottomRightTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.BOTTOM_RIGHT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Check there are only three cards on the board.
        assertEquals(3, board.getCoveredSlots().size());

        // Check location of cards is correct.
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(TOP)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(CornerLocation.BOTTOM_RIGHT)));

        // Check cards themselves are correct.
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(TOP)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(CornerLocation.BOTTOM_RIGHT)));

    }

    /**
     * Test if the tower with bottom-right orientation is correctly placed on the
     * board (method getNewBoardWithTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Test Bottom-Left Tower")
    public void testBottomLeftTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.BOTTOM_LEFT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Check there are only three cards on the board.
        assertEquals(3, board.getCoveredSlots().size());

        // Check location of cards is correct.
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(TOP)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(CornerLocation.BOTTOM_LEFT)));

        // Check cards themselves are correct.
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(TOP)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(CornerLocation.BOTTOM_LEFT)));

    }

    /**
     * Test if the tower with bottom-right orientation is correctly placed on the
     * board (method getNewBoardWithTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Test Top-Left Tower")
    public void testTopLeftTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.TOP_LEFT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Check there are only three cards on the board.
        assertEquals(3, board.getCoveredSlots().size());

        // Check location of cards is correct.
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(CornerLocation.TOP_LEFT)));

        // Check cards themselves are correct.
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(CornerLocation.TOP_LEFT)));

    }

    /**
     * Test if the addition of a new tower works correctly (method placeTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Right-Sided Tower")
    public void testRightSidedTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.TOP_RIGHT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Place a new tower pattern on the right side of the first.
        final BoardSlot rightLocation = refLocation.getSlotAt(RIGHT);
        DummyPlayingBoard.placeTowerPattern(
                board,
                rightLocation,
                towerCard,
                baseCard,
                baseLocation
        );



        // Check there are only six cards on the board.
        assertEquals(6, board.getCoveredSlots().size());

        // Check location of cards is correct (both old cards and new cards).
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(CornerLocation.TOP_RIGHT)));
        assert(board.getCoveredSlots().contains(rightLocation));
        assert(board.getCoveredSlots().contains(rightLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(rightLocation.getSlotAt(CornerLocation.TOP_RIGHT)));

        // Check cards themselves are correct (both old cards and new cards).
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(CornerLocation.TOP_RIGHT)));
        assertEquals(towerCard, board.getCardAt(rightLocation));
        assertEquals(towerCard, board.getCardAt(rightLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(rightLocation.getSlotAt(CornerLocation.TOP_RIGHT)));

    }

    /**
     * Test if the addition of a new tower works correctly (method placeTowerPattern()), i.e.<ul>
     *     <li>Correct number of cards placed</li>
     *     <li>Correct position covered by cards</li>
     *     <li>Correct card at the correct position</li>
     * </ul>
     */
    @Test
    @DisplayName("Dummy Playing Board: Up-Sided Tower")
    public void testUpSidedTower() {

        // Get a card for the tower.
        KingdomCardFace towerCard = ResourceCard.getCardWithId(0).getBackFace();

        // Get a card for the base.
        KingdomCardFace baseCard = ResourceCard.getCardWithId(10).getBackFace();

        // The location of the base.
        CornerLocation baseLocation = CornerLocation.TOP_RIGHT;

        // Set the reference location of the tower on the board.
        final BoardSlot refLocation = new BoardSlot(0, 0);

        // Get a board with only one tower on it.
        DummyPlayingBoard board = DummyPlayingBoard.getNewBoardWithTowerPattern(
                refLocation,
                towerCard,
                baseCard,
                baseLocation
        );

        // Place a new tower pattern on the right side of the first.
        final BoardSlot topLocation = refLocation.getSlotAt(TOP).getSlotAt(TOP);
        DummyPlayingBoard.placeTowerPattern(
                board,
                topLocation,
                towerCard,
                baseCard,
                baseLocation
        );



        // Check there are only six cards on the board.
        assertEquals(6, board.getCoveredSlots().size());

        // Check location of cards is correct (both old cards and new cards).
        assert(board.getCoveredSlots().contains(refLocation));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(refLocation.getSlotAt(CornerLocation.TOP_RIGHT)));
        assert(board.getCoveredSlots().contains(topLocation));
        assert(board.getCoveredSlots().contains(topLocation.getSlotAt(BOTTOM)));
        assert(board.getCoveredSlots().contains(topLocation.getSlotAt(CornerLocation.TOP_RIGHT)));

        // Check cards themselves are correct (both old cards and new cards).
        assertEquals(towerCard, board.getCardAt(refLocation));
        assertEquals(towerCard, board.getCardAt(refLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(refLocation.getSlotAt(CornerLocation.TOP_RIGHT)));
        assertEquals(towerCard, board.getCardAt(topLocation));
        assertEquals(towerCard, board.getCardAt(topLocation.getSlotAt(BOTTOM)));
        assertEquals(baseCard, board.getCardAt(topLocation.getSlotAt(CornerLocation.TOP_RIGHT)));

    }
}
