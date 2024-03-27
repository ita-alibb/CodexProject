package it.polimi.ingsw.am52.exceptions;

/**
 * Base class for exceptions related to the game cards.
 */
public class CardException extends GameException {
    public CardException() {
    }

    public CardException(String message) {
        super(message);
    }

    public CardException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardException(Throwable cause) {
        super(cause);
    }

    public CardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
