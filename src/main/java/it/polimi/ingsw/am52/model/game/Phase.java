package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.PhaseException;
import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.player.*;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.util.List;

/**
 * The interface that simulate all the possible phases of a game. This interface will be used by different "concrete" classes,
 * which will simulate a Finite State Machine, with this possible configuration:
 * <p>
 *     (INIT-->PLACING, PLACING-->DRAWING, DRAWING-->PLACING, DRAWING-->END)
 * </p>
 * <p>
 *     The idea at the base is very simple: in each state, the game will provide a different response, throwing an exception if
 *  * a method is called in a wrong part of the game; when we will call a method, first we will call the state saved in the class
 *  * GameManager on the corresponding action the user wants to play, then, when the State says that everything is matching, GameManager
 *  * will continue its computing, and finally it will call the method "next" to switch to the next possible state.
 * </p>
 * <p>
 *     IMPORTANT: In the DRAWING phase, we have two different possible ways: returning in the PLACING phase or entering the END
 *      phase and calculating the winner/winners. So, GameManager will do the check on ScoreBoard and will call the method "endGame",
 *      and the state will be switched to END.
 * </p>
 * <p>
 *     Other things handled by the Phase are the turn and the currentPlayer.
 * </p>
 */

public abstract class Phase {
    //region Private Fields

    /**
     * The phase using the enum GamePhase
     */
    protected GamePhase phase;

    /**
     * The current turn
     */
    protected int turn;

    /**
     * The current player TODO: maybe this should be the nickname string?
     */
    protected int currPlayer;

    /**
     * The indicator of the very last turn
     */
    protected boolean isLastTurn;

    //endregion

    //region Constructor

    /**
     * The constructor of the class Phase
     */
    public Phase() {

    }

    //endregion

    //region Public Methods

    /**
     * Used to update the current phase
     */
    synchronized public void next(GameManager manager) {
        throw new PhaseException("Incorrect phase");
    }

    //endregion

    //region Getters

    /**
     * @return The current phase of the game
     */
    public GamePhase getPhase() {
        return this.phase;
    }

    /**
     * @return The current turn
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * @return The current player
     */
    public int getCurrPlayer() {
        return this.currPlayer;
    }

    /**
     * @return true if it's the last turn, false otherwise
     */
    public boolean isLastTurn() {
        return this.isLastTurn;
    }

    //endregion

    //region Public Methods

    //region InitPhase methods
    /**
     * Set the objective card of a player.
     * @param player        The player who chose
     * @param objectiveId   The ID of the card
     * @implNote This method can only be executed during the InitPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void setPlayerChosenObject(GameManager manager, PlayerSetup player, int objectiveId) {
        throw new PhaseException("Incorrect phase");
    }

    /**
     * Place the starter card of a player
     * @param player    The slot of the board where the player places his card
     * @param card      The card to place
     * @param side      The face of the card
     * @implNote This method can only be executed during the InitPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void placeStarterCard(PlayerBoardSetup player, StarterCard card, CardSide side) {
        throw new PhaseException("Incorrect phase");
    }

    //endregion

    //region PlacingPhase methods

    /**
     * Place a card of a player
     * @param slot      The slot where the player wants to place the card
     * @param card      The card to be placed
     * @param face      The face of the card
     * @implNote This method can only be executed during the PlacingPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void placeCard(GameManager manager, PlayerDrawing player, BoardSlot slot, KingdomCard card, KingdomCardFace face) {
        throw new PhaseException("Incorrect phase");
    }

    //endregion

    //region DrawingPhase methods

    /**
     * Draw a card from the resource deck.
     * @param player    The player who draws the card
     * @param card      The card drawn
     * @implNote This method can only be executed during the DrawingPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void drawResourceCard(GameManager manager, PlayerDrawing player, ResourceCard card) {
        throw new PhaseException("Incorrect phase");
    }

    /**
     * Draw a card from the gold deck
     * @param player    The player who draws the card
     * @param card      The card drawn
     * @implNote This method can only be executed during the DrawingPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void drawGoldCard(GameManager manager, PlayerDrawing player, GoldCard card) {
        throw new PhaseException("Incorrect phase");
    }

    /**
     * Take one of the uncovered resource cards.
     * @param player        The player who takes the card
     * @param drawnCard     The taken card
     * @param visibleCards  The List of the visible cards
     * @implNote This method can only be executed during the DrawingPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void takeResourceCard(GameManager manager, PlayerDrawing player, ResourceCard drawnCard, List<ResourceCard> visibleCards) {
        throw new PhaseException("Incorrect phase");
    }

    /**
     * Take one of the uncovered gold cards
     * @param player        The player who takes the card
     * @param drawnCard     The taken card
     * @param visibleCards  The List of the visible cards
     * @implNote This method can only be executed during the DrawingPhase; in the other phases, this will throw a new exception.
     */
    synchronized public void takeGoldCard(GameManager manager, PlayerDrawing player, GoldCard drawnCard, List<GoldCard> visibleCards) {
        throw new PhaseException("Incorrect phase");
    }

    //endregion

    //region EndPhase methods

    /**
     * @param players The list of the players in the lobby
     * @return  The list with one or more winners, in case of a tie.
     * @implNote This method can only be executed during the EndPhase; in the other phases, this will throw a new exception.
     */
    synchronized public List<PlayerInfo> getWinners(GameManager manager, List<PlayerInfo> players) {
        throw new PhaseException("Incorrect phase");
    }

    //endregion

    //endregion
}
