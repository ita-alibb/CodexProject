package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

public class BoardSlotOrdered extends BoardSlot {
    /**
     * Order in which the card is placed on the slot
     */
    public int order;

    public BoardSlotOrdered(BoardSlot slot, int order) {
        super(slot.getHoriz(), slot.getVert());
        this.order = order;
    }

    public BoardSlotOrdered(int h, int v, int order) {
        super(h, v);
        this.order = order;
    }
}
