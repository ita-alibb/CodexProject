package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.cards.StarterCard;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.player.PlayerBoardSetup;
import it.polimi.ingsw.am52.model.player.PlayerSetup;

/**
 * The concrete class for the initializing phase
 */

public class InitPhase extends Phase {
    //region Constructor

    /**
     * Constructor of the class
     */
    public InitPhase(String firstPlayer) {
        this.phase = GamePhase.INIT;
        this.currPlayer = firstPlayer;
        this.turn = 0;
        this.isLastTurn = false;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void next(GameManager manager) {
        manager.setPhase(new PlacingPhase(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setPlayerChosenObject(GameManager manager, PlayerSetup player, int objectiveId) {
        //We call the method of the Interface PlayerSetup to choose the objective card of the player
        player.setSecretObjective(Objective.getObjectiveWithId(objectiveId));

        //Check if all the players chose a secret objective and placed the starter card
        if (this.isSetupFinished(manager)) {
            this.next(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void placeStarterCard(GameManager manager, PlayerBoardSetup player, StarterCard card, CardSide side) {
        player.placeStarterCardFace(side == CardSide.FRONT ? card.getFrontFace() : card.getBackFace());

        //Check if all the players chose a secret objective and placed the starter card
        if (this.isSetupFinished(manager)) {
            this.next(manager);
        }
    }

    private synchronized boolean isSetupFinished(GameManager manager) {
        try {
            return manager.getPlayerInfos().stream().allMatch(pInfo -> pInfo.getObjective() != null) && manager.getPlayerInfos().stream().allMatch(pInfo -> pInfo.getPlayingBoard() != null);
        } catch (PlayingBoardException e) {
            return false;
        }
    }
    //endregion
}
