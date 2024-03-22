package it.polimi.ingsw.am52.Model.Player;

import it.polimi.ingsw.am52.Exceptions.PlayerException;
import it.polimi.ingsw.am52.Model.cards.KingdomCard;

/**
 * Provides method to access the Player's Hand
 */
public interface PlayerDrawing {

    /**
     *
     * @param drawnCard The drawnCard to be added in the hand of the player
     */
    void assignCard(KingdomCard drawnCard) throws PlayerException;

    /**
     *
     * @param placedCard The placedCard that must be removed from the hand of the player
     */
    void removeCard(KingdomCard placedCard) throws PlayerException;
}
