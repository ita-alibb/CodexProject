package it.polimi.ingsw.am52.model.player;

import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.StarterCardFace;

public interface  PlayerBoardSetup {
    /**
     * Used to instantiate the Player's PlayingBoard
     * @param card The starter card face chosen by the player
     */
    void placeStarterCardFace(StarterCardFace card) throws PlayingBoardException;
}
