package it.polimi.ingsw.am52.Model.cards;

/**
 * Gives the player a constant number of points when the card is placed
 * on the playing board. This class cannot be instantiated, you can get
 * the available objects are available by public static (final) fields.
 */
public class ConstantPoints extends CardPoints {
    /**
     * The number of (constant) points that the player gains when the
     * card is placed on the playing board.
     */
    private final int points;

    public static final ConstantPoints ONE_POINTS = new ConstantPoints(1);
    public static final ConstantPoints THREE_POINTS = new ConstantPoints(3);
    public static final ConstantPoints FIVE_POINTS = new ConstantPoints(5);

    /**
     * Private constructor. Creates an object that gives the player the specified
     * (constant) number of points when the card is placed on the playing board.
     * @param points The number of (constant) points.
     */
    private ConstantPoints(int points) {
        this.points = points;
    }

    /**
     * The gained points are a fixed number for this card.
     */
    @Override
    public int calculatePoints(ItemsProvider items, int linkedCorners) {
        return this.points;
    }
}
