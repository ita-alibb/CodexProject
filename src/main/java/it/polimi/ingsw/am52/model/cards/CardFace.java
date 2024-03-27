package it.polimi.ingsw.am52.Model.cards;

import java.util.Optional;

/**
 * The base class of a face of a playing card (Resource, Gold, or
 * Starter cards). The face of the card is the actual functional
 * component of the card in the game.  Every card that can be placed
 * on the playing board has two  faces (front and back) and the visible
 * face of the card when placed on the playing board produces some effects
 * for the players (e.g. gives points, gives  resources, gives items,
 * give target points at the end of the game, add new free positions on
 * the board, etc...).
 */
public abstract class CardFace implements Pluggable, ResourcesProvider {

    //region Private Fields

    /**
     * The (optional) visible top right corner of the card face.
     */
    private final Optional<CardCorner> topRightCorner;

    /**
     * The (optional) visible bottom right corner of the card face.
     */
    private final Optional<CardCorner> bottomRightCorner;

    /**
     * The (optional) visible bottom left corner of the card face.
     */
    private final Optional<CardCorner> bottomLeftCorner;

    /**
     * The (optional) visible top left corner of the card face.
     */
    private final Optional<CardCorner> topLeftCorner;

    //endregion

    //region Constructor

    /**
     * Creates a card face with the specified corners. In order to set a
     * non-visible corner, pass null to the desired parameter.
     * @param topRight The top-right corner.
     * @param bottomRight The bottom-right corner.
     * @param bottomLeft The bottom-left corner.
     * @param topLeft The top-left corner.
     */
    protected CardFace(CardCorner topRight, CardCorner bottomRight, CardCorner bottomLeft, CardCorner topLeft) {
        this.topRightCorner = (topRight == null) ? Optional.empty() : Optional.of(topRight);
        this.bottomRightCorner = (bottomRight == null) ? Optional.empty() : Optional.of(bottomRight);
        this.bottomLeftCorner = (bottomLeft == null) ? Optional.empty() : Optional.of(bottomLeft);
        this.topLeftCorner = (topLeft == null) ? Optional.empty() : Optional.of(topLeft);
    }

    //endregion

    //region Getters

    /**
     * 
     * @return The side of the card associated to this card face.
     */
    public abstract CardSide getCardSide();

    //endregion

    //region Public Methods

    /**
     *
     * @param location The location of the corner on the card face.
     * @return Return the (optional) corner at the specified location on
     * the card face.
     */
    public Optional<CardCorner> getCornerAt(CornerLocation location) {
        return switch (location) {
            case TOP_RIGHT -> getTopRightCorner();
            case BOTTOM_RIGHT -> getBottomRightCorner();
            case BOTTOM_LEFT -> getBottomLeftCorner();
            case TOP_LEFT -> getTopLeftCorner();
        };
    }

    //endregion

    //region Pluggable Interface

    public Optional<CardCorner> getTopRightCorner() {
        return this.topRightCorner;
    }

    public Optional<CardCorner> getBottomRightCorner() {
        return this.bottomRightCorner;
    }

    public Optional<CardCorner> getBottomLeftCorner() {
        return this.bottomLeftCorner;
    }

    public Optional<CardCorner> getTopLeftCorner() {
        return this.topLeftCorner;
    }

    //endregion

    //region ResourcesProvider Interface

    /**
     * The resources visible on all card face corners.
     */
    @Override
    public ResourcesCounter getResources() {

        // Initialize zero-resources counter.
        ResourcesCounter resources = new ResourcesCounter();

        // Add resources to the counter, from all corners.        
        // 1) Top-right corner.
        if (getTopRightCorner().isPresent()) {
            resources = ResourcesCounter.add(resources, getTopRightCorner().get().getResources());
        }
        // 2) Bottom-right corner.
        if (getBottomRightCorner().isPresent()) {
            resources = ResourcesCounter.add(resources, getBottomRightCorner().get().getResources());
        }
        // 3) Bottom-left corner.
        if (getBottomLeftCorner().isPresent()) {
            resources = ResourcesCounter.add(resources, getBottomLeftCorner().get().getResources());
        }
        // 4) Top-left corner.
        if (getTopLeftCorner().isPresent()) {
            resources = ResourcesCounter.add(resources, getTopLeftCorner().get().getResources());
        }

        // Return the summation.
        return resources;
    }

    //endregion

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CardFace f)) {
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
}
