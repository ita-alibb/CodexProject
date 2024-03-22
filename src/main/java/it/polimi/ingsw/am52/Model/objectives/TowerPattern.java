package it.polimi.ingsw.am52.Model.objectives;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.polimi.ingsw.am52.Model.cards.CornerLocation;
import it.polimi.ingsw.am52.Model.cards.Kingdom;
import it.polimi.ingsw.am52.Model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.Model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.Model.playingBoards.RelativeLocation;

/**
 * Find the occurrences of the tower pattern on the
 * playing board. The pattern is composed by two cards of
 * the same kingdom, aligned in vertical direction (the tower), and
 * a card of different kingdom (the base) at one corner of the card
 * at the extremity of the tower.
 */
public class TowerPattern extends PatternFinder {

    //region Private Static Fields

    private static final int TOWER_HEIGHT = 2;

    //endregion

    //region Public Static Final Fields

    /**
     * The instance that is able to find the fungi tower pattern (Objective #4, pag. 91).
     */
    public static final TowerPattern FUNGI_TOWER = new TowerPattern(Kingdom.FUNGI_KINGDOM, Kingdom.PLANT_KINGDOM, CornerLocation.BOTTOM_RIGHT);

    /**
     * The instance that is able to find the plant tower pattern (Objective #5, pag. 92).
     */
    public static final TowerPattern PLANT_TOWER = new TowerPattern(Kingdom.PLANT_KINGDOM, Kingdom.INSECT_KINGDOM, CornerLocation.BOTTOM_LEFT);

    /**
     * The instance that is able to find the animal tower pattern (Objective #6, pag. 93).
     */
    public static final TowerPattern ANIMAL_TOWER = new TowerPattern(Kingdom.ANIMAL_KINGDOM, Kingdom.FUNGI_KINGDOM, CornerLocation.TOP_RIGHT);

    /**
     * The instance that is able to find the insect tower pattern (Objective #7, pag. 94).
     */
    public static final TowerPattern INSECT_TOWER = new TowerPattern(Kingdom.INSECT_KINGDOM, Kingdom.ANIMAL_KINGDOM, CornerLocation.TOP_LEFT);

    //endregion

    //region Private Fields

    /**
     * The kingdom of the two cards composing the tower of the pattern.
     */
    private final Kingdom towerKingdom;

    /**
     * The kingdom of the card composing the base of the pattern.
     */
    private final Kingdom baseKingdom;

    /**
     * The location of the base card of the pattern.
     */
    private final CornerLocation baseLocation;

    //endregion

    //region Private Static Methods

    /**
     * This method check if there is any corresponding base at the correct location,
     * in order to create some tower patterns.
     * @param board The playing board info.
     * @param tower The list of slot positions aligned along the tower direction.
     * It may contain more than two positions, and the method check all pair of
     * positions that can create a tower.
     * @param baseLocation The location of the base card.
     * @param baseKingdom The Kingdom of the card needed to create the tower.
     * @return The number of towers found.
     */
    private static int findMatchingBase(BoardInfo board, List<BoardSlot> tower, CornerLocation baseLocation,
                                        Kingdom baseKingdom) {
        
        // initialize the pattern counter.
        int patterns = 0;
        
        // Check if there are enough slots to form at least one tower.
        if (tower.size() < TOWER_HEIGHT) {
            return patterns;
        }

        // Check if there is a matching base to form a tower pattern.
        // Start at the first possible roof of the tower.
        int roofIndex = TOWER_HEIGHT - 1;
        while (roofIndex < tower.size()) {

            // The slot that is the candidate to be the roof of
            // the tower pattern.
            BoardSlot roof = tower.get(roofIndex);

            // Get the (optional) card placed on the base slot location.
            Optional<KingdomCardFace> baseCard = board.getNeighborCard(roof, baseLocation);

            //Check if there is the card at the base location, and if it
            // is of the required kingdom.
            if (baseCard.isPresent() && baseCard.get().getKingdom().equals(baseKingdom)) {

                // There is a tower pattern, thus increment the counter.
                patterns++;

                // Increment the index of the roof slot, depending on the tower
                // height.
                roofIndex += TOWER_HEIGHT - 1;
            }

            // Increment the index of the roof slot.
            roofIndex++;
        }

        // Return the counter of found patterns.
        return patterns;
    }

    //endregion

    //region Constructors

    /**
     * Creates an object that is able to find the tower patterns,
     * in accordance with the specified settings.
     * @param towerKingdom The kingdom of the two cards composing the tower of the pattern.
     * @param baseKingdom The kingdom of the card composing the base of the pattern.
     * @param baseLocation The location of the base card of the pattern.
     */
    private TowerPattern(Kingdom towerKingdom, Kingdom baseKingdom, CornerLocation baseLocation) {
        // Initialize the private final fields.
        this.baseKingdom = baseKingdom;
        this.towerKingdom = towerKingdom;
        this.baseLocation = baseLocation;
    }

    //endregion

    //region Overrides

    @Override
    public int findPatterns(BoardInfo board) {

        // Initialize the counter of the patters found on the playing board.
        int patterns = 0;

        // Get the list of all slots of the playing board, that have a card
        // placed on them. Get only slots that have a card with the specific 
        // kingdom of the tower of this pattern.
        List<BoardSlot> slots = board.getCoveredSlots().stream().filter(slot -> board.getCardAt(slot).getKingdom().equals(this.towerKingdom)).toList();

        // The direction of aligned elements (direction of the tower).
        RelativeLocation direction = switch(this.baseLocation) {
            case BOTTOM_RIGHT, BOTTOM_LEFT -> RelativeLocation.TOP;
            case TOP_RIGHT, TOP_LEFT -> RelativeLocation.BOTTOM;
        };

        // Iterate until there are no more elements in the list.
        // At each iteration, check if there are aligned slots, along the 
        // direction of the tower, and then remove the aligned slots from
        // the list.
        while (!slots.isEmpty()) {
            
            // Find slots aligned along the diagonal.
            List<BoardSlot> aligned = PatternFinder.findAlignedSlots(slots, direction);
            
            // Increment the counter: every three slots aligned.
            patterns += findMatchingBase(board, aligned, baseLocation, baseKingdom);
            
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
