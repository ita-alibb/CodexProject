package it.polimi.ingsw.am52.Model.objectives;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.am52.Model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.Model.playingBoards.RelativeLocation;

/**
 * Implements the algorithm to find a specific card pattern
 * in the cards placed on the playing board.
 */
public abstract class PatternFinder {

    //region Public Static Methods

    /**
     * Finds a list of aligned slot positions, along the specified direction. The search is
     * random and the method return any aligned sequence at least two elements long.
     * If there are no aligned elements, the method return a list with only the first
     * element of the list passed as argument.
     * @param slots The list of slot positions to investigate.
     * @param direction The relative direction of aligned slots.
     * @return A list of slots that are aligned along the specified direction.
     */
    public static List<BoardSlot> findAlignedSlots(List<BoardSlot> slots, RelativeLocation direction) {

        if (slots.isEmpty()) {
            return new ArrayList<>();
        }

        for (int index = 0; index != slots.size(); index++) {

            // The list of aligned elements.
            List<BoardSlot> aligned = new ArrayList<>();

            // The candidate slot where the aligned sequence starts.
            BoardSlot start = slots.get(index);

            // The slot can be in the middle of the aligned slots. If so,
            // move back step-by-step to the beginning (in this way, we
            // can then find all aligned elements by moving forward the
            // aligning direction, from the staring position).
            while (true) {
                // Move back, to the reverse direction of the aligned elements.
                BoardSlot prevSlot = switch (direction) {
                    case TOP -> start.getSlotAt(RelativeLocation.BOTTOM);
                    case BOTTOM -> start.getSlotAt(RelativeLocation.TOP);
                    case RIGHT -> start.getSlotAt(RelativeLocation.LEFT);
                    case LEFT -> start.getSlotAt(RelativeLocation.RIGHT);
                    case TOP_RIGHT -> start.getSlotAt(RelativeLocation.BOTTOM_LEFT);
                    case BOTTOM_RIGHT -> start.getSlotAt(RelativeLocation.TOP_LEFT);
                    case BOTTOM_LEFT -> start.getSlotAt(RelativeLocation.TOP_RIGHT);
                    case TOP_LEFT -> start.getSlotAt(RelativeLocation.BOTTOM_RIGHT);
                };

                // There is not a previous slot, break from while loop.
                if (!slots.contains(prevSlot)) {
                    break;
                }

                // Move the starting point to the previous slot.
                start = prevSlot;
            }

            // Add the candidate start element to the aligned collection.
            aligned.add(start);

            // From the candidate starting position, move forward the
            // direction of the alignment. If there are at least two elements
            // aligned, return them.
            while (true) {
                // Move forward, along the direction of alignment.
                BoardSlot nextSlot = start.getSlotAt(direction);

                // There is not a contiguous slot aligned, break from loop.
                if (!slots.contains(nextSlot)) {
                    break;
                }

                // Add the element to the aligned collection.
                aligned.add(nextSlot);

                // Move ahead for next iteration.
                start = nextSlot;
            }

            // If there are at least two aligned elements, return the aligned
            // collection.
            if (aligned.size() > 1) {
                return aligned;
            }
        }
        
        // There are not aligned slot positions. Return a list
        // with only the first element.
        List<BoardSlot> res = new ArrayList<>();
        res.add(slots.getFirst());

        return res;
    }

    //endregion
    
    //region Public Methods

    /**
     * Find the card pattern in the cards placed on the playing board.
     * @param board The playing board info.
     * @return The number of times the pattern is present on the playing board.
     */
    public abstract int findPatterns(BoardInfo board);

    //endregion

}
