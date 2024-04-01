package it.polimi.ingsw.am52.model.game;

/**
 * The phases which compose every turn
 */

public enum GameState {
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
