package it.polimi.ingsw.am52.model.cards;

/**
 * The base class for an object ables to calculate the points
 * that a card face gives, when the card is placed on the playing board.
 * The obtained points can be a fixed number, or depend on the items visible on the playing board,
 * or linked corners of the placed card.
 */
public abstract class CardPoints {

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
    public abstract int calculatePoints(ItemsProvider items, int linkedCorners);

}
