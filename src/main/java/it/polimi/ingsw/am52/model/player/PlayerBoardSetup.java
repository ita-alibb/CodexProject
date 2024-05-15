package it.polimi.ingsw.am52.model.player;

import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.cards.StarterCard;
import it.polimi.ingsw.am52.model.cards.StarterCardFace;

public interface  PlayerBoardSetup {
    /**
     * Used to instantiate the Player's PlayingBoard
     * @param card The starter card of the player
     * @param side The side chosen
     */
    void placeStarterCardFace(StarterCard card, CardSide side) throws PlayingBoardException;
}
