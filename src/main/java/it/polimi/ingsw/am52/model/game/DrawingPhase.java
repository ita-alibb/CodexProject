package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.model.cards.GoldCard;
import it.polimi.ingsw.am52.model.cards.ResourceCard;
import it.polimi.ingsw.am52.model.player.PlayerDrawing;

import java.util.List;

/**
 * The concrete class for the drawing state
 */

public class DrawingPhase extends Phase {

    //region Constructor

    public DrawingPhase(Phase oldPhase) {
        super(oldPhase);
        this.phase = GamePhase.DRAWING;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     * In this case, the normal next phase is PLACING, so the next phase is PlacingPhase
     */
    @Override
    public synchronized void next(GameManager manager) {
        //Update the current player
        var tmpPlayer = manager.getNextPlayer(this.currPlayer);

        if (tmpPlayer != null) {
            this.currPlayer = tmpPlayer;

            //Update the phase, choosing the correct one thanks to the number of currentPlayer
            if (manager.getPlayerInfos().get(0).getNickname().equals(this.currPlayer)) {
                if (isLastTurn) {
                    manager.setPhase(new EndingPhase(this));
                    return;
                } else {
                    this.isLastTurn = (manager.getScoreBoard().containsValue(20) || (manager.getResourceDeckCount() == 0 && manager.getGoldDeckCount() == 0));
                    turn++;
                }
            }

            manager.setPhase(new PlacingPhase(this));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void drawResourceCard(GameManager manager, PlayerDrawing player, ResourceCard card) {
        player.drawCard(card);
        //Update the phase
        this.next(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void drawGoldCard(GameManager manager, PlayerDrawing player, GoldCard card) {
        player.drawCard(card);
        //Update the phase
        this.next(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void takeResourceCard(GameManager manager, PlayerDrawing player, ResourceCard drawnCard, List<ResourceCard> visibleCards) {
        //IMPORTANT: At this point, we are sure that the given card is in the list of the visible cards
        //Remove the card from the List of the visible cards
        visibleCards.remove(drawnCard);
        player.drawCard(drawnCard);
        //Update the phase
        this.next(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void takeGoldCard(GameManager manager, PlayerDrawing player, GoldCard drawnCard, List<GoldCard> visibleCards) {
        //IMPORTANT: At this point, we are sure that the given card is in the list of the visible cards
        //Remove the card from the List of the visible cards
        visibleCards.remove(drawnCard);
        player.drawCard(drawnCard);
        //Update the phase
        this.next(manager);
    }

    //endregion
}
