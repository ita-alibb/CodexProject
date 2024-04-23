package it.polimi.ingsw.am52.exceptions;

/**
 * Base class for exceptions related to Phase passages
 */

public class PhaseException extends GameException {
    public PhaseException() {
    }

    public PhaseException(String message) {
        super(message);
    }

    public PhaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhaseException(Throwable cause) {
        super(cause);
    }

    public PhaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
