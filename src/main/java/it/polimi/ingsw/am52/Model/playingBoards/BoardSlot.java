package it.polimi.ingsw.am52.Model.playingBoards;

import it.polimi.ingsw.am52.Model.cards.CornerLocation;
import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;

/**
 * Immutable class that represents the position of a slot in the Playing board.
 * Every slot has its horizontal and vertical coordinates, only slots with both
 * coordinates odd or both even are valid. The slot with coordinates (0,0) is
 * the root slot, because it has a special meaning in Playing board (only the 
 * starter card can be placed on the root slot).
 */
public class BoardSlot {

    //region Private Fields

    /**
     * The horizontal coordinate of this slot on the playing board.
     */
    private final int h;

    /**
     * The vertcal coordinate of this slot on the playing board.
     */
    private final int v;

    //endregion

    //region Public Static Methods

    /**
    * The coordinates of a valid board slot must be:<ul>
     * <li> Both 'horiz' and 'vert' with odd values, or
     * <li> Both 'horiz' and 'vert' with even values.
     * </ul>
     * The slot at (h=0, v=0) is the root of the playing board and
     * only the starter card can be placed on that slot.
     * @param horiz The horizontal coordinate of the slot.
     * @param vert The vertical coordinate of the slot.
     * @return True if the coordinates of the slot represent a valid
     * coordinate pair for a slot of the playing board.
     */
    public static boolean validateCoords(int horiz, int vert) {
        // Return true if both coordinates are even or both are odd.
        return /* Both even*/ ((horiz % 2 == 0) && (vert % 2 == 0)) ||
        /* Both odds */ ((horiz % 2 != 0) && (vert % 2 != 0));

    }
    
    /**
     * 
     * @param refSlot The reference slot.
     * @return The slot on the playing board at the top of the specified slot.
     */
    public static BoardSlot computeTopSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz(), refSlot.getVert() + 2);
    }
    
    /**
     * 
     * @param refSlot The reference slot.
     * @return The slot on the playing board at the bottom of the specified slot.
     */
    public static BoardSlot computeBottomSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz(), refSlot.getVert() - 2);
    }
    
    /**
     * 
     * @param refSlot The reference slot.
     * @return The slot on the playing board at the right of the specified slot.
     */
    public static BoardSlot computeRightSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz()+2, refSlot.getVert());
    }
    
    /**
     * 
     * @param refSlot The reference slot.
     * @return The slot on the playingboard at the left of the specified slot.
     */
    public static BoardSlot computeLeftSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz()-2, refSlot.getVert());
    }

    /**
     * Computes the coordinates of the slot positioned in the top-right corner
     * of the specified slot.
     * @param refSlot The reference slot.
     * @return The slot on the playing board at the top-right corner of the specified slot.
     */
    private BoardSlot computeTopRightSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz() + 1, refSlot.getVert() + 1);
    }

    /**
     * Computes the coordinates of the slot positioned at the bottom-right corner
     * of the specified slot.
     * @param refSlot The reference slot.
     * @return The slot on the playingboard at the bottom-right corner of the specified slot.
     */
    private BoardSlot computeBottomRightSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz() + 1, refSlot.getVert() - 1);
    }

    /**
     * Computes the coordinates of the slot positioned at the bottom-left corner
     * of the specified slot.
     * @param refSlot The reference slot.
     * @return The slot on the playingboard at the bottom-left corner of the specified slot.
     */
    private BoardSlot computeBottomLeftSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz() - 1, refSlot.getVert() - 1);
    }

    /**
     * Computes the coordinates of the slot positioned at the top-left corner
     * of the specified slot.
     * @param refSlot The reference slot.
     * @return The slot on the playingboard at the top-left corner of the specified slot.
     */
    private BoardSlot computeTopLeftSlot(BoardSlot refSlot) {
        return new BoardSlot(refSlot.getHoriz() - 1, refSlot.getVert() + 1);
    }

    //endregion

    //region Constructor

    public BoardSlot(int horiz, int vert) throws IllegalArgumentException {

        // Check if the coordinate pair is valid.
        if (!validateCoords(horiz, vert)) {
            throw new PlayingBoardException("The coordinates of a board slot must be both even or both odd.");
        }

        // Initialize the private fields that store the coordinates.
        this.h = horiz;
        this.v = vert;
    }

    //endregion

    //region Getters

    /**
     * 
     * @return The horizontal coordinate of this board slot.
     */
    public int getHoriz() {
        return h;
    }

    /**
     * 
     * @return The vertical coordinate of this board slot.
     */
    public int getVert() {
        return v;
    }

    //endregion

    //region Public Methods

    /**
     * The root slot is the slot with both horizontal and vertical coordinates equal to zero.
     * This is a special slot position, because only the starter card can be placed on the
     * root slot.
     *
     * @return True if the specified slot is the root slot, i.e. with coordiates (0,0).
     */
    public boolean isRootSlot() {
        return getHoriz() == 0 && getVert() == 0;
    }

    /**
     * 
     * @param location The location of the slot to find, relative to this slot.
     * @return The coordinates of the slot positioned at the specified location,
     * relative to this slot.
     */
    public BoardSlot getSlotAt(RelativeLocation location) {
        return switch (location) {
            case TOP -> getTopSlot();
            case BOTTOM -> getBottomSlot();
            case RIGHT -> getRightSlot();
            case LEFT -> getLeftSlot();
            case TOP_RIGHT -> getTopRightSlot();
            case BOTTOM_RIGHT -> getBottomRightSlot();
            case BOTTOM_LEFT -> getBottomLeftSlot();
            case TOP_LEFT -> getTopLeftSlot();
        };
    }

    /**
     *
     * @param corner The corner location of this slot.
     * @return The coordinates of the slot located at the specified corner,
     * relative to this slot.
     */
    public BoardSlot getSlotAt(CornerLocation corner) {
        return switch (corner) {
            case TOP_RIGHT -> getSlotAt(RelativeLocation.TOP_RIGHT);
            case BOTTOM_RIGHT -> getSlotAt(RelativeLocation.BOTTOM_RIGHT);
            case BOTTOM_LEFT -> getSlotAt(RelativeLocation.BOTTOM_LEFT);
            case TOP_LEFT -> getSlotAt(RelativeLocation.TOP_LEFT);
        };
    }

    /**
     * 
     * @return The position of the slot at the top of this slot in the playing board.
     */
    public BoardSlot getTopSlot() {
        return computeTopSlot(this);
    }

    /**
     * 
     * @return The position of the slot at the bottom of this slot in the playing board.
     */
    public BoardSlot getBottomSlot() {
        return computeBottomSlot(this);
    }

    /**
     * 
     * @return The position of the slot at the right of this slot in the playing board.
     */
    public BoardSlot getRightSlot() {
        return computeRightSlot(this);
    }

    /**
     * 
     * @return The position of the slot at the left of this slot in the playing board.
     */
    public BoardSlot getLeftSlot() {
        return computeLeftSlot(this);
    }

    /**
     * 
     * @return The position of the slot at the top-right corner
     * of this slot in the playing board.
     */
    public BoardSlot getTopRightSlot() {
        return computeTopRightSlot(this);
    }

    /**
     * 
     * @return The position of the slot at the bottom-right corner
     * of this slot in the playing board.
     */
    public BoardSlot getBottomRightSlot() {
        return computeBottomRightSlot(this);
    }

    /**
     * 
     * @return The position of the slot in the bottom-left corner
     * of this slot in the playing board.
     */
    public BoardSlot getBottomLeftSlot() {
        return computeBottomLeftSlot(this);
    }

    /**
     * 
     * @return The position of the slot in the top-left corner.
     * of this slot in the playing board.
     */
    public BoardSlot getTopLeftSlot() {
        return computeTopLeftSlot(this);
    }

    //endregion

    //region Overrides

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + h;
        result = prime * result + v;
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof BoardSlot other)) {
            return false;
        }

        return (this.h == other.h && this.v == other.v);
    }

    /**
     * Return the string that represents this board slot.
     */
    @Override
    public String toString() {
        return String.format("[Slot: %d,%d]", getHoriz(), getVert());
    }

    //endregion
}
