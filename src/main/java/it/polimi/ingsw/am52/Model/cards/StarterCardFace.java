package it.polimi.ingsw.am52.Model.cards;

/**
 * The base class for all Starter card faces.
 */
public abstract class StarterCardFace extends CardFace {

    //region Constructors

    /**
     * Creates a card face with the specified corners. In order to set a
     * non-visible corner, pass null to the desired parameter.
     * @param topRight    The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft  The bottom-left corner.
     * @param topLeft     The top-left corner.
     */
    protected StarterCardFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        super(topRight, bottomRight, bottomLeft, topLeft);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StarterCardFace f)) {
            return false;
        }
        return (f.getCardSide() == this.getCardSide() &&
                f.getBottomLeftCorner() == this.getBottomLeftCorner() &&
                f.getTopLeftCorner() == this.getTopLeftCorner() &&
                f.getBottomRightCorner() == this.getBottomRightCorner() &&
                f.getTopRightCorner() == this.getTopRightCorner() &&
                f.getResources().equals(this.getResources())
                );
    }

    //endregion
}
