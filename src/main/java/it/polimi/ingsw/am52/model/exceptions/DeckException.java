package it.polimi.ingsw.am52.model.exceptions;

/**
 * Base class for game exception related to the usage of the deck.
 */
public class DeckException extends GameException {
    public DeckException() {
    }

    public DeckException(String message) {
        super(message);
    }

    public DeckException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeckException(Throwable cause) {
        super(cause);
    }

    public DeckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
