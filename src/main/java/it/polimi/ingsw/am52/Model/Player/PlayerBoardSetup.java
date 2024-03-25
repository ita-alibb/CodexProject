package it.polimi.ingsw.am52.Model.Player;

import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.Model.cards.StarterCardFace;

public interface  PlayerBoardSetup {
    /**
     * Used to instantiate the Player's PlayingBoard
     * @param card The starter card face chosen by the player
     */
    void placeStarterCardFace(StarterCardFace card) throws PlayingBoardException;
}
