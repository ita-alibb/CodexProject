package it.polimi.ingsw.am52.Exceptions;

/**
 * Base class for exceptions related to the playing board.
 */
public class PlayingBoardException extends GameException {
    public PlayingBoardException() {
    }

    public PlayingBoardException(String message) {
        super(message);
    }

    public PlayingBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayingBoardException(Throwable cause) {
        super(cause);
    }

    public PlayingBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
