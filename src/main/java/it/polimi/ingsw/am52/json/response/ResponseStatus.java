package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.game.Phase;

import java.io.Serializable;

public class ResponseStatus implements Serializable {
    /**
     * The phase using the enum GamePhase
     */
    public GamePhase gamePhase;

    /**
     * The current player
     */
    public String currPlayer;

    /**
     * The error code
     */
    public int errorCode;

    /**
     * The error message
     */
    public String errorMessage;

    /**
     * The constructor for Success response in the LOBBY phase
     */
    public ResponseStatus() {
        this.gamePhase = GamePhase.LOBBY;
        this.currPlayer = "";
        this.errorCode = 0;
        this.errorMessage = "";
    }

    /**
     * The constructor for Error response during game phase
     * @param phase the phase of the Game
     * @param errorCode the error code of the call
     * @param errorMessage the error message
     */
    public ResponseStatus(Phase phase, int errorCode, String errorMessage) {
        this.gamePhase = phase.getPhase();
        this.currPlayer = phase.getCurrPlayer();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * The constructor for Error response
     * @param gamePhase the Game Phase
     * @param errorCode the error code of the call
     * @param errorMessage the error message
     */
    public ResponseStatus(GamePhase gamePhase, int errorCode, String errorMessage) {
        this.gamePhase = gamePhase;
        this.currPlayer = "";
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * The constructor from Phase for Success response
     * @param phase the Phase class
     */
    public ResponseStatus(Phase phase) {
        this.gamePhase = phase.getPhase();
        this.currPlayer = phase.getCurrPlayer();
        this.errorCode = 0;
        this.errorMessage = "";
    }

    /**
     * The constructor for UnitTests
     * @param phase the phase of the Game
     * @param currPlayer the nick of the curr player
     * @param errorCode the error code of the call
     * @param errorMessage the error message
     */
    public ResponseStatus(GamePhase phase, String currPlayer, int errorCode, String errorMessage) {
        this.gamePhase = phase;
        this.currPlayer = currPlayer;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
