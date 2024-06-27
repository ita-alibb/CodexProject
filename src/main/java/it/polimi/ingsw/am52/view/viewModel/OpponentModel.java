package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.cards.KingdomColor;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

/**
 * OpponentModel represents an image of the information about the other players in the lobby.
 * Obviously, a player doesn't know all information about each player, for example each player's objective is secret.
 */
public class OpponentModel {
    /**
     * Opponent nickname in the lobby
     */
    private final String nickname;

    /**
     * The order in the game
     */
    private KingdomColor color;

    /**
     * This player's board
     */
    private final BoardMap<BoardSlot, CardIds> board;

    /**
     * The constructor of OpponentModel class.
     * @param nickname      The nickname of the opponent
     */
    public OpponentModel(String nickname, KingdomColor color) {
        this.nickname = nickname;
        this.color = color;
        this.board = new BoardMap<>();
    }

    /**
     * Method to add a card to the board of the opponent
     * @param slot      The slot where the card has been placed
     * @param cardIds   The representation of the placed card
     */
    public void addCard(BoardSlot slot, CardIds cardIds) {
        this.board.put(slot, cardIds);
    }

    /**
     * @return  The nickname of the opponent
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return  The board of the opponent
     */
    public BoardMap<BoardSlot, CardIds> getBoard() {
        return board;
    }

    /**
     * @return  The color of this opponent pawn
     */
    public KingdomColor getColor() {
        return this.color;
    }
}
