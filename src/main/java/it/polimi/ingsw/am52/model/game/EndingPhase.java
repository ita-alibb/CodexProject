package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.player.PlayerInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The concrete class for the end phase
 */

public class EndingPhase extends Phase {

    //region Constructor

    public EndingPhase(Phase oldPhase) {
        super(oldPhase);
        this.phase = GamePhase.END;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public List<String> getWinners(GameManager manager, List<PlayerInfo> players, List<Objective> commonObjectives) {
        // calculate new score for every player based on objectives
        for (PlayerInfo player : players) {
            int objectivePoints = 0;

            // -- get common objectives points
            for (Objective objective : commonObjectives) {
                objectivePoints += objective.calculatePoints(player.getPlayingBoard());
            }

            // -- add personal objective points
            objectivePoints += player.getObjective().calculatePoints(player.getPlayingBoard());

            // -- update player score
            player.setObjScore(objectivePoints);

            // -- update scoreboard
            manager.updateScoreBoard(player.getNickname());
        }

        //return the max in points
        final int maxScore = players.stream()
                .map(PlayerInfo::getScore)
                .max(Integer::compareTo)
                .orElse(-1);

        var winners = players.stream()
                .filter(player -> player.getScore() == maxScore)
                .collect(Collectors.toList());

        if (winners.size() > 1) {
            // check for objective points
            final int maxObj = players.stream()
                    .map(PlayerInfo::getObjScore)
                    .max(Integer::compareTo)
                    .orElse(-1);

            winners = winners.stream()
                    .filter(player -> player.getObjScore() == maxObj)
                    .collect(Collectors.toList());
        }

        return winners.stream().map(PlayerInfo::getNickname).toList();
    }

    //endregion
}
