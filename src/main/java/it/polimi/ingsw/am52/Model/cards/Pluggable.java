package it.polimi.ingsw.am52.Model.cards;

import java.util.Optional;

/**
 * The attributes of any element that can be plugged to other
 * elements inside the playing board.
 */
public interface Pluggable {
    /**
     *
     * @return The (optional) top-right corner of the pluggable element.
     */
    Optional<CardCorner> getTopRightCorner();

    /**
     *
     * @return The (optional) bottom-right corner of the pluggable element.
     */
    Optional<CardCorner> getBottomRightCorner();

    /**
     *
     * @return The (optional) bottom-left corner of the pluggable element.
     */
    Optional<CardCorner> getBottomLeftCorner();

    /**
     *
     * @return The (optional) top-left corner of the pluggable element.
     */
    Optional<CardCorner> getTopLeftCorner();
}