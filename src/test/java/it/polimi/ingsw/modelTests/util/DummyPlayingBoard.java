package it.polimi.ingsw.modelTests.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.polimi.ingsw.am52.Model.cards.CornerLocation;
import it.polimi.ingsw.am52.Util.ImmutableList;
import it.polimi.ingsw.am52.Model.cards.ItemsCounter;
import it.polimi.ingsw.am52.Model.cards.KingdomCardFace;
import it.polimi.ingsw.am52.Model.cards.ResourcesCounter;
import it.polimi.ingsw.am52.Model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.Model.playingBoards.RelativeLocation;

/**
 * This class is for testing purpose. It represents a playing board
 * where you can put any Kingdom card on any slot position, and you can
 * set any resources or items.
 * This class does NOT implement the following methods:<ul>
 * <li>getAvailableSlots()</li>
 * <li>getInfo()</li></ul>
 * This class is useful for testing the follwing
 * methods: <ul>
 * <li>Class KingdomCardFace: canPlace() </li>
 * <li>Class KingdomCardFace: gainedPoints() </li>
 * <li>Class Objective: calculatePoints() </li>
 * </ul> 
 */
public class DummyPlayingBoard implements BoardInfo {

    //region Private Fields

    /**
     * The map of plced cards and their positions.
     */
    private final Map<BoardSlot, KingdomCardFace> placedCards = new HashMap<>();

    /**
     * The visible resources of the playng board.
     */
    private ResourcesCounter resources;

    /**
     * The visible items of the playng board.
     */
    private ItemsCounter items;
    
    //endregion

    //region Constructors

    /**
     * Creates a fake playing board with no resources, no items
     * and no cards placed on it.
     */
    public DummyPlayingBoard() {
        this(new ResourcesCounter(), new ItemsCounter());
    }

    /**
     * Creates a fake playing board with the specified resources,
     * no items, and no cards placed on it.
     * @param resources The desired resources.
     */
    public DummyPlayingBoard(ResourcesCounter resources) {
        this(resources, new ItemsCounter());
    }

    /**
     * Creates a fake playing board with the specified items,
     * no resources, and no cards placed on it.
     * @param items The desired items.
     */
    public DummyPlayingBoard(ItemsCounter items) {
        this(new ResourcesCounter(), items);
    }

    /**
     -
     * Creates a fake playing board with the specified resources ad,
     * items, and no cards placed on it.
     * @param resources The desired resources.
     * @param items The desired items.
     */
    public DummyPlayingBoard(ResourcesCounter resources, ItemsCounter items) {
         this.resources = resources;
         this.items = items;
    }

    /**
     * Creates a fake playing board with specified settings.
     * @param resources The desired visible resources.
     * @param items The desired visible items.
     * @param cards The placed cards and their positions.
     */
    public DummyPlayingBoard(ResourcesCounter resources, ItemsCounter items, Map<BoardSlot, KingdomCardFace> cards) {
         this.resources = resources;
         this.items = items;
         this.placedCards.putAll(cards);
    }
    
    //endregion

    //region Setters

    /**
     * 
     * @param resources The desired resources.
     */
    public void setResources(ResourcesCounter resources) {
        this.resources = resources;
    }

    public void setItems(ItemsCounter items) {
        this.items = items;
    }

    //endregion

    //region Public Methods

    /**
     * Add a card on the playing board.
     * @param location The location where the card is placed
     * @param card The card to place.
     */
    public void addCard(BoardSlot location, KingdomCardFace card) {
        this.placedCards.put(location, card);
    }

    /**
     * Remove a placed card from the playing board.
     * @param location The location of the card to remove.
     * @return The removed card.
     */
    public KingdomCardFace removeCard(BoardSlot location) {
        return this.placedCards.remove(location);
    }

    /**
     * Replace a placed card with a new card.
     * @param location The location where replace.
     * @param card The new card to place, replacing the actual placed card.
     */
    public void replaceCard(BoardSlot location, KingdomCardFace card) {
        this.placedCards.replace(location, card);
    }

    //endregion

    //region BoardInfo Interface

    @Override
    public ResourcesCounter getResources() {
        return this.resources;
    }

    @Override
    public ItemsCounter getItems() {
        return this.items;
    }
    
    @Override
    public ImmutableList<BoardSlot> getCoveredSlots() {
        return new ImmutableList<BoardSlot>(new ArrayList<>(this.placedCards.keySet()));
    }

    @Override
    public ImmutableList<BoardSlot> getAvailableSlots() {
        throw new UnsupportedOperationException();
    }

    @Override
    public KingdomCardFace getCardAt(BoardSlot location) {
        return this.placedCards.get(location);
    }

    @Override
    public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, RelativeLocation location) {

        // Get the candidate slot position.
        BoardSlot candidate = refSlot.getSlotAt(location);

        // Return the card placed on the slot (if any), or an empty optional.
        return /* Is there a card? */ this.placedCards.containsKey(candidate) ?
        /* Yes, return it */ Optional.of(this.placedCards.get(candidate)) :
        /* No, return empty() */ Optional.empty();
    }

    public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, CornerLocation location) {

        // Get the candidate slot position.
        BoardSlot candidate = refSlot.getSlotAt(location);

        // Return the card placed on the slot (if any), or an empty optional.
        return /* Is there a card? */ this.placedCards.containsKey(candidate) ?
        /* Yes, return it */ Optional.of(this.placedCards.get(candidate)) :
        /* No, return empty() */ Optional.empty();
    }
    
    //endregion

}
