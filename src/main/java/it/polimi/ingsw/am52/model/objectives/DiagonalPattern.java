package it.polimi.ingsw.am52.model.objectives;

import it.polimi.ingsw.am52.model.cards.Kingdom;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.RelativeLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Find the occurrences of the diagonal pattern on the
 * playing board. The pattern is composed by three cards of
 * the same kingdom, placed along the diagonal direction.
 */
public class DiagonalPattern extends PatternFinder {

    //region Private Static Fields

    private static final int DIAG_LENGTH = 3;

    //endregion

    //region Public Static Final Fields

    /**
     * The instance that is able to find the fungi diagonal pattern (Objective #0, pag. 87).
     */
    public static final DiagonalPattern FUNGI_DIAGONAL = new DiagonalPattern(Kingdom.FUNGI_KINGDOM, DiagonalHand.RIGHT);

    /**
     * The instance that is able to find the plant diagonal pattern (Objective #1, pag. 88).
     */
    public static final DiagonalPattern PLANT_DIAGONAL = new DiagonalPattern(Kingdom.PLANT_KINGDOM, DiagonalHand.LEFT);

    /**
     * The instance that is able to find the animal diagonal pattern (Objective #2, pag. 89).
     */
    public static final DiagonalPattern ANIMAL_DIAGONAL = new DiagonalPattern(Kingdom.ANIMAL_KINGDOM, DiagonalHand.RIGHT);

    /**
     * The instance that is able to find the insect diagonal pattern (Objective #3, pag. 90).
     */
    public static final DiagonalPattern INSECT_DIAGONAL = new DiagonalPattern(Kingdom.INSECT_KINGDOM, DiagonalHand.LEFT);

    //endregion

    //region Private Fields

    /**
     * The orientation of the diagonal.
     */
    private final DiagonalHand hand;

    /**
     * The kingdom of the cards composing the pattern.
     */
    private final Kingdom kingdom;

    //endregion

    //region Constructor

    /**
     * Creates an object that is able to find the diagonal patterns composed
     * of card having the specified kingdom and forming a diagonal with the
     * specified orientation
     * @param kingdom The kingdom of the cards.
     * @param hand The orientation of the diagonal.
     */
    private DiagonalPattern(Kingdom kingdom, DiagonalHand hand) {

        // Initialize private final fields.
        this.kingdom = kingdom;
        this.hand = hand;
    }

    //endregion

    //region Overrides

    @Override
    public int findPatterns(BoardInfo board) {

        // Initialize the counter of the patters found on the playing board.
        int patterns = 0;

        // Get the list of all slots of the playing board, that have a card
        // placed on them. Get only slots that have a card with the specific 
        // kingdom of this pattern.
        List<BoardSlot> slots = board.getCoveredSlots().stream().filter(slot -> board.getCardAt(slot).getKingdom().equals(this.kingdom)).toList();

        // The direction of aligned elements.
        RelativeLocation direction = switch(this.hand) {
            case RIGHT -> RelativeLocation.TOP_RIGHT;
            case LEFT -> RelativeLocation.TOP_LEFT;
        };

        // Iterate until there are no more elements in the list.
        // At each iteration, check if there are aligned slots, along the 
        // direction of the diagonal, and then remove the aligned slots from
        // the list.
        while (!slots.isEmpty()) {

            /*
            // // The slot can be in the middle of the pattern. If so,
            // // move back step-by-step to the beginning (in this way, we can find two
            // // distinct patterns if there are two diagonals one next to the other).
            
            // BoardSlot start = slots.get(0); // The candidate slot where the pattern starts.
            // while (true) {
            //     // Move back, depending on the hand of the diagonal.
            //     BoardSlot prevSlot = switch (this.hand) {
            //             case RIGHT -> start.getSlotAt(RelativeLocation.BOTTOM_LEFT);
            //             case LEFT -> start.getSlotAt(RelativeLocation.BOTTOM_RIGHT);
            //     };
                
            //     // There is not a previous slot.
            //     if (!slots.contains(prevSlot)) {
            //         break;
            //     }
            
            //     // Move the starting point to the previous slot.
            //     start = prevSlot;
            // }
            
            // // Create a list with all checked slot.
            // List<BoardSlot> checked = new ArrayList<>();
            // // Add the starting slot.
            // checked.add(start);
            
            // BoardSlot slot = start;
            // // From the candidate starting slot, check if there is the full pattern,
            // // i.e. there are two more cards on the diagonal.
            // for (int i = 0; i != 2; i++) {
            //     BoardSlot nextSlot = switch (this.hand) {
            //             case RIGHT -> slot.getSlotAt(RelativeLocation.TOP_RIGHT);
            //             case LEFT -> slot.getSlotAt(RelativeLocation.TOP_LEFT);
            //     };
                
            //     // There is not a next slot.
            //     if (!slots.contains(nextSlot)) {
            //         break;
            //     }
            
            //     // Add the slot to the checked list.
            //     checked.add(nextSlot);
            //     slot = nextSlot;
            
            //     // If I reached the last iteration, I have found a pattern.
            //     if (i == 1) {
            //         patterns++;
            //     }
            // } // End of for loop
            */
            
            // Find slots aligned along the diagonal.
            List<BoardSlot> aligned = PatternFinder.findAlignedSlots(slots, direction);
            
            // Increment the counter: every three slots aligned.
            patterns += (aligned.size() / DIAG_LENGTH);
            
            // Remove all checked slots from the collection of slots available.
            Set<BoardSlot> remaining = new HashSet<>(slots);
            aligned.forEach(remaining::remove);
            slots = new ArrayList<>(remaining);
        }

        // Return the counter of found patterns.
        return patterns;
    }

    //endregion

}
