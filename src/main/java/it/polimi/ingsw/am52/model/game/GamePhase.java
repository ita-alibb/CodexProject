package it.polimi.ingsw.am52.model.game;

/**
 * The phases which compose every turn
 */

public enum GamePhase {
    /**
     * The game is not started yet, only Lobby is created
     */
    LOBBY,
    /**
     * The very beginning of the game, the setup phase
     */
    INIT,
    /**
     * The placing phase, when the player place a certain card
     */
    PLACING,
    /**
     * The drawing phase, when the player draw a card from a certain deck
     */
    DRAWING,
    /**
     * The end of a game
     */
    END
}
