package it.polimi.ingsw.am52.model.playingBoards;

import java.util.Optional;

import it.polimi.ingsw.am52.model.cards.CornerLocation;
import it.polimi.ingsw.am52.model.cards.ImmutableList;
import it.polimi.ingsw.am52.model.cards.ItemsProvider;
import it.polimi.ingsw.am52.model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.model.cards.ResourcesProvider;

/**
 * Information related to the playing board of a player.
 */
public interface BoardInfo extends ResourcesProvider, ItemsProvider {
    
    /**
     * 
     * @return The list slots where a Kingdom card is placed.
     */
    ImmutableList<BoardSlot> getCoveredSlots();

    /**
     * 
     * @return The list of available slot on the playing board, where
     * a new Kingdom card can be placed.
     */
    ImmutableList<BoardSlot> getAvailableSlots();

    /**
     * Check if there is a card placed on the specified slot and return that
     * card.
     * @param location The slot where the card is placed.
     * @return The card placed on the specified slot.
     * @throws IllegalArgumentException If the specifed slot does not contains a card.
     */
    public KingdomCardFace getCardAt(BoardSlot location) throws IllegalArgumentException;

    /**
     * Check if there is a card placed on the neighbor slot and return that
     * card. If there isn't a card, return an Optional.empty().
     * @param refSlot The reference slot.
     * @param location The relative location of the neighbor slot.
     * @return The (optional) card placed on the specified relative location.
     */
    Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, RelativeLocation location);

    /**
     * Check if there is a card placed on the neighbor corner slot and return that
     * card. If there isn't a card, return a Optional.empty().
     * @param refSlot The reference slot.
     * @param location The relative corner location of the neighbor slot.
     * @return The (optional) card placed on the specified relative location.
     */
    Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, CornerLocation location);
}
