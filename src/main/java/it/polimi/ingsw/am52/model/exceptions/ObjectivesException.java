package it.polimi.ingsw.am52.model.exceptions;

/**
 * Base class for exceptions related to the game objectives.
 */
public class ObjectivesException extends GameException {
    public ObjectivesException() {
    }

    public ObjectivesException(String message) {
        super(message);
    }

    public ObjectivesException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectivesException(Throwable cause) {
        super(cause);
    }

    public ObjectivesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
