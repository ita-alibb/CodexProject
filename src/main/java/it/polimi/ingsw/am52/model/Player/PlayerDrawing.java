package it.polimi.ingsw.am52.Model.Player;

import it.polimi.ingsw.am52.Exceptions.PlayerException;
import it.polimi.ingsw.am52.Model.cards.KingdomCard;
import it.polimi.ingsw.am52.Model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;

/**
 * Provides method to access the Player's Hand
 */
public interface PlayerDrawing {

    /**
     *
     * @param drawnCard The drawnCard to be added in the hand of the player
     */
    void drawCard(KingdomCard drawnCard) throws PlayerException;

    /**
     * Method to place the card
     * @param location The location in which the card is placed
     * @param card The placedCard that must be removed from the hand of the player
     * @param face The face chosen
     * @return The points obtained
     */
    int placeCard(BoardSlot location, KingdomCard card, KingdomCardFace face) throws PlayerException;
}
