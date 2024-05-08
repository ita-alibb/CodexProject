package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.model.player.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

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
    synchronized public List<PlayerInfo> getWinners(GameManager manager, List<PlayerInfo> players) {
        //Create the list of PlayerInfo
        List<PlayerInfo> possibleWinners = new ArrayList<>();
        //Add by default the first player and pass to the next
        possibleWinners.add(players.getFirst());
        //For each person in the lobby
        for (int i = 1; i < manager.getPlayersCount(); i++) {
            //If the score of a player is greater than the one of player who is already in the list
            if (manager.getScoreBoard().get(i) > possibleWinners.getFirst().getScore()) {
                possibleWinners.clear();
                possibleWinners.add(players.get(i));
            }
            //If the score of a player is equal to the one of the player who is already in the list
            else if (manager.getScoreBoard().get(i) == possibleWinners.getFirst().getScore()) {
                possibleWinners.add(players.get(i));
            }
        }
        //Once I have the list of the winner, if this contains more than one player, return it, otherwise choose the winner with the objectives score
        if (possibleWinners.size() == 1) {
            return possibleWinners;
        }
        //Create a new list
        List<PlayerInfo> winners = new ArrayList<>();
        winners.add(possibleWinners.getFirst());
        //If the list is bigger than one element
        //For each item in the temp list possibleWinners
        for (int i = 1; i < possibleWinners.size(); i++) {
            //If a Player has a bigger objective score than the one in the definitive list
            if (winners.getFirst().getObjScore() < possibleWinners.get(i).getObjScore()) {
                //Clear the list and add the Player in it
                winners.clear();
                winners.add(possibleWinners.get(i));
            }
            //If a Player has the same objective score as the one in the definitive list
            else if (winners.getFirst().getObjScore() == possibleWinners.get(i).getObjScore()) {
                //Add the Player in the list
                winners.add(possibleWinners.get(i));
            }
        }
        //Return the value of the winner
        return winners;
    }

    //endregion
}
