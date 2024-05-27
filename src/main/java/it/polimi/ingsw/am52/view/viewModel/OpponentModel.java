package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

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
    private final BoardMap<BoardSlot, CardIds> board;

    public OpponentModel(String nickname, int turnOrder) {
        this.nickname = nickname;
        this.turnOrder = turnOrder;
        this.board = new BoardMap<>();
    }

    public void addCard(BoardSlot slot, CardIds cardIds) {
        this.board.put(slot, cardIds);
    }

    public String getNickname() {
        return nickname;
    }

    public BoardMap<BoardSlot, CardIds> getBoard() {
        return board;
    }

    public int getTurnOrder() {
        return turnOrder;
    }
}
