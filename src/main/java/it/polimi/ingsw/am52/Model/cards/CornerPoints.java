package it.polimi.ingsw.am52.Model.cards;

/**
 * An object that calculates the gained points based on the number
 * linked corners when the card is placed on the playing board.
 * This class cannot be instantiated, you can get the available object
 *  are available by public static (final) fields.
 */
public class CornerPoints extends CardPoints {
    /**
     * The bonus points for each linked corner.
     */
    public final int CORNER_BONUS = 2;

    /**
     * An object that calculates the gained points based on the number
     * of linked corners when the card is placed on the playing board.
     */
    public static final CornerPoints CORNER_POINTS = new CornerPoints();

    /**
     * Private default constructor.
     */
    private CornerPoints() { }

    /**
     * The gained points depend on the number of linked corners when
     * the card is placed on the playing board. Each linked corner
     * gives a bonus.
     */
    @Override
    public int calculatePoints(ItemsProvider items, int linkedCorners) {
        // Calculate the gained points by multiplying the number of the
        // linked corners to the bonus points.
        return CORNER_BONUS * linkedCorners;
    }
}
