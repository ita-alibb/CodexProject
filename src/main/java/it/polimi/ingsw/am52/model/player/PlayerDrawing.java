package it.polimi.ingsw.am52.model.player;

import it.polimi.ingsw.am52.exceptions.PlayerException;
import it.polimi.ingsw.am52.model.cards.KingdomCard;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

/**
 * Provides method to access the Player's Hand
 */
public interface PlayerDrawing {

    /**
     * Add the provided card to the player's Hand
     * @param drawnCard The drawnCard to be added in the hand of the player
     */
    void drawCard(KingdomCard drawnCard) throws PlayerException;

    /**
     * Method to place the card
     * 1. Place card on board
     * 2. Remove card from hand
     * 3. Add points to score
     * @param location The location in which the card is placed
     * @param card The placedCard that must be removed from the hand of the player
     * @param face The face chosen
     */
    void placeCard(BoardSlot location, KingdomCard card, KingdomCardFace face) throws PlayerException;
}
