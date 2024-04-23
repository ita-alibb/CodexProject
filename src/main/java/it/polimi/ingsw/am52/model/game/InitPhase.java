package it.polimi.ingsw.am52.model.game;

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
    public InitPhase() {
        this.phase = GamePhase.INIT;
        this.currPlayer = 0;
        this.turn = 0;
        this.isLastTurn = false;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void next(GameManager manager) {
        manager.setPhase(new PlacingPhase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void setPlayerChosenObject(GameManager manager, PlayerSetup player, int objectiveId) {
        //We call the method of the Interface PlayerSetup to choose the objective card of the player
        player.setSecretObjective(Objective.getObjectiveWithId(objectiveId));
        //Check if all the players chose a secret objective
        if (manager.getPlayerInfos().stream().allMatch(pInfo -> pInfo.getObjective() != null)) {
            this.next(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public void placeStarterCard(PlayerBoardSetup player, StarterCard card, CardSide side) {
        player.placeStarterCardFace(side == CardSide.FRONT ? card.getFrontFace() : card.getBackFace());
    }

    //endregion
}
