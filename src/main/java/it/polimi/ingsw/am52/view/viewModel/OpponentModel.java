package it.polimi.ingsw.am52.view.viewModel;

import java.util.HashMap;
import java.util.Map;

public class OpponentModel {
    /**
     * Opponent nickname in the lobby
     */
    private final String nickname;

    /**
     * The order in the game
     */
    private final int turnOrder;

    /**
     * This player's board
     */
    private final Map<BoardSlotOrdered, CardIds> board;

    public OpponentModel(String nickname, int turnOrder) {
        this.nickname = nickname;
        this.turnOrder = turnOrder;
        this.board = new HashMap<>();
    }

    public void addCard(BoardSlotOrdered slot, CardIds cardIds) {
        this.board.put(slot, cardIds);
    }

    public String getNickname() {
        return nickname;
    }

    public Map<BoardSlotOrdered, CardIds> getBoard() {
        return board;
    }

    public int getTurnOrder() {
        return turnOrder;
    }
}
