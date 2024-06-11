package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.cards.KingdomCard;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.player.PlayerDrawing;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

/**
 * The concrete class for the placing phase
 */

public class PlacingPhase extends Phase {

    //region Constructor

    /**
     * Constructor of the class
     */
    public PlacingPhase(Phase oldPhase) {
        super(oldPhase);
        this.phase = GamePhase.PLACING;
    }

    //endregion

    //region Public Methods

    /**
     *  {@inheritDoc}
     */
    @Override
    synchronized public void next(GameManager manager) {
        manager.setPhase(new DrawingPhase(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void placeCard(GameManager manager, PlayerDrawing player, BoardSlot slot, KingdomCard card, CardSide side) {
        player.placeCard(slot, card, side);

        //Update the value in the ScoreBoard
        manager.updateScoreBoard(this.getCurrPlayer());

        this.next(manager);
    }

    //endregion
}
