package it.polimi.ingsw.am52.model.cards;

import java.util.Optional;

/**
 * The base class for all kingdom cards (Gold and Resource cards).
 * These cards have an associated kingdom, and they can participate
 * in the creation of the target patterns when placed on the
 * playing board.
 * This class defines the method to check if the card can be placed
 * on the playing board and to calculate the point eventually gained
 * by the player when the card is placed.
 */
public abstract class KingdomCardFace extends CardFace implements ItemsProvider {

    //region Private Fields

    /**
     * The (optional) instance used to calculate the points gained by
     * the player when this card is placed on the playing board.
     */
    private final Optional<CardPoints> points;

    //endregion

    //region Constructors
    
    /**
     * Creates a card face that does not give any point to the
     * player.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     */
    protected KingdomCardFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
        this.points = Optional.empty();
    }

    /**
     * Creates a card face that does not give points to the
     * player based on the specified CardPoints instance.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     * @param points The instance that calculates the points gained by the
     * player when the card is placed on the playing board.
     */
    protected KingdomCardFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft,
            CardPoints points) {
        super(topRight, bottomRight, bottomLeft, topLeft);
        this.points = Optional.of(points);
    }

    //endregion

    //region Getters

    /**
     * All kingdom card (Gold and Resource cards) has an
     * associated kingdom.
     * @return The kingdom of the card.
     */
    public abstract Kingdom getKingdom();

    //endregion

    //region Public Methods

    /**
     * Calculates the points gained by the player, when the card face
     * is placed on the playing board. The number of points can be a fixed number,
     * or depend on the items visible on the playing board, or linked corners of
     * the placed card.
     * @param items The visible items on the playing board (after the card is placed).
     * @param linkedCorners The corners linked to the placed card.
     * @return The number of points gained by the player when the cad is placed on the
     * playing board.
     */
    public int gainedPoints(ItemsProvider items, int linkedCorners) {
        // If present, delegate the computation to the CardPoints instance,
        // or return zero if not present.
        return this.points.map(cardPoints -> cardPoints.calculatePoints(items, linkedCorners)).orElse(0);
    }

    /**
     * Check if available resources (from the playing board) are enough
     * to fulfill the resources required by this card face.
     * <P>
     * The default implementation always return true. If a subclass has
     * different behavior, it must override this method.
     * @param availableResources The available resources (from the playing board).
     * @return True if the card can be placed, false otherwise.
     */
    public boolean canPlace(ResourcesProvider availableResources) {
        return true;
    }

    /**
     * Method used by the User Interface to print the useful information about the points gained
     * by the placement of the card.
     * @return The string with the necessary information about the points of the face of the card.
     */
    public String getPoints() {
        return this.points.map(CardPoints::toString).orElse("");
    }

    //endregion
    
    //region ItemsProvider Interface

    /**
     * The items visible on all card face corners.
     */
    @Override
    public ItemsCounter getItems() {

        // Initialize zero-items counter.
        ItemsCounter items = new ItemsCounter();

        // Add items to the counter, from all corners.        
        // 1) Top-right corner.
        if (getTopRightCorner().isPresent()) {
            items = ItemsCounter.add(items, getTopRightCorner().get().getItems());
        }
        // 2) Bottom-right corner.
        if (getBottomRightCorner().isPresent()) {
            items = ItemsCounter.add(items, getBottomRightCorner().get().getItems());
        }
        // 3) Bottom-left corner.
        if (getBottomLeftCorner().isPresent()) {
            items = ItemsCounter.add(items, getBottomLeftCorner().get().getItems());
        }
        // 4) Top-left corner.
        if (getTopLeftCorner().isPresent()) {
            items = ItemsCounter.add(items, getTopLeftCorner().get().getItems());
        }

        // Return the summation.
        return items;
    }

    //endregion
}
