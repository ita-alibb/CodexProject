package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.game.Phase;

import java.io.Serializable;

public class Status implements Serializable {
    /**
     * The phase using the enum GamePhase
     */
    public GamePhase gamePhase;

    /**
     * The current turn
     */
    public int turn;

    /**
     * The current player
     */
    public int currPlayer;

    /**
     * The indicator of the very last turn
     */
    public boolean isLastTurn;

    /**
     * The constructor of lobby phase
     */
    public Status() {
        this.gamePhase = GamePhase.LOBBY;
        this.turn = 0;
        this.currPlayer = 0;
        this.isLastTurn = false;
    }

    /**
     * The constructor from Phase
     * @param phase the Phase class
     */
    public Status(Phase phase) {
        this.gamePhase = phase.getPhase();
        this.turn = phase.getTurn();
        this.currPlayer = phase.getCurrPlayer();
        this.isLastTurn = phase.isLastTurn();
    }
}
