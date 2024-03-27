package it.polimi.ingsw.modelTests.util;

import java.util.*;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.util.ImmutableList;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.RelativeLocation;

import static it.polimi.ingsw.am52.model.playingBoards.RelativeLocation.*;

/**
 * This class is for testing purpose. It represents a playing board
 * where you can put any Kingdom card on any slot position, and you can
 * set any resources or items.
 * This class does NOT implement the following methods:<ul>
 * <li>getAvailableSlots()</li>
 * <li>getInfo()</li></ul>
 * This class is useful for testing the following
 * methods: <ul>
 * <li>Class KingdomCardFace: canPlace() </li>
 * <li>Class KingdomCardFace: gainedPoints() </li>
 * <li>Class Objective: calculatePoints() </li>
 * </ul> 
 */
public class DummyPlayingBoard implements BoardInfo {

    //region Public Static Methods

    /**
     * Create a rectangular playing board with all cards of the same kingdom.
     * @param origin The bottom-left corner of the board.
     * @param rows Number of rows.
     * @param columns Number of columns.
     * @param kingdom The kingdom of the cards.
     * @return The rectangular board with placed cards.
     */
    public static DummyPlayingBoard getUniformKingdomBoard(BoardSlot origin, int rows, int columns, Kingdom kingdom) {

        // Create an empty playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // If roes or columns are less or equal than zero, return an empty playing board.
        if (rows <= 0 || columns <= 0) {
            return board;
        }

        // Get a Kingdom card of the specified kingdom.
        KingdomCardFace card = switch (kingdom.getResource()) {
            case Resource.FUNGI-> KingdomCard.getCardWithId(0).getFrontFace();
            case Resource.PLANT -> KingdomCard.getCardWithId(10).getFrontFace();
            case Resource.ANIMAL -> KingdomCard.getCardWithId(20).getFrontFace();
            case Resource.INSECT -> KingdomCard.getCardWithId(30).getFrontFace();
        };

        // Populate the playing board.
        for (int row = 0; row != rows; row++) {
            for (int column = 0; column != columns; column++) {
                // The theo. coords of the slot.
                int h = origin.getHoriz() + column;
                int v = origin.getVert() + row;

                // Check if the slot is valid.
                if (BoardSlot.validateCoords(h, v)) {
                    BoardSlot slot = new BoardSlot(h, v);
                    board.addCard(slot, card);
                }
            }
        }

        // Return the playing board.
        return board;
    }

    /**
     * Create a rectangular playing board with alternate kingdom of the
     * cards along the rows.
     * @param origin The bottom-left corner of the board.
     * @param rows Number of rows.
     * @param columns Number of columns.
     * @param kingdom1 The kingdom of odds columns.
     * @param kingdom2 The kingdom of even columns.
     * @return The rectangular board with placed cards.
     */
    public static DummyPlayingBoard getStripedKingdomBoard(BoardSlot origin, int rows, int columns, Kingdom kingdom1, Kingdom kingdom2) {

        // Create an empty playing board.
        DummyPlayingBoard board = new DummyPlayingBoard();

        // If roes or columns are less or equal than zero, return an empty playing board.
        if (rows <= 0 || columns <= 0) {
            return board;
        }

        // Get a Kingdom card of the specified kingdom.
        KingdomCardFace card1 = switch (kingdom1.getResource()) {
            case Resource.FUNGI-> KingdomCard.getCardWithId(0).getFrontFace();
            case Resource.PLANT -> KingdomCard.getCardWithId(10).getFrontFace();
            case Resource.ANIMAL -> KingdomCard.getCardWithId(20).getFrontFace();
            case Resource.INSECT -> KingdomCard.getCardWithId(30).getFrontFace();
        };

        // Get a Kingdom card of the specified kingdom.
        KingdomCardFace card2 = switch (kingdom2.getResource()) {
            case Resource.FUNGI-> KingdomCard.getCardWithId(0).getFrontFace();
            case Resource.PLANT -> KingdomCard.getCardWithId(10).getFrontFace();
            case Resource.ANIMAL -> KingdomCard.getCardWithId(20).getFrontFace();
            case Resource.INSECT -> KingdomCard.getCardWithId(30).getFrontFace();
        };

        // Populate the playing board.
        for (int row = 0; row != rows; row++) {
            for (int column = 0; column != columns; column++) {

                // The theo. coords of the slot.
                int h = origin.getHoriz() + column;
                int v = origin.getVert() + row;

                // Check if the slot is valid.
                if (BoardSlot.validateCoords(h, v)) {
                    BoardSlot slot = new BoardSlot(h, v);
                    board.addCard(slot, (column % 2 == 0) ? card1 :card2);
                }
            }
        }

        // Return the playing board.
        return board;
    }

    /**
     * Create a slot with random horizontal and vertical coordinates.
     * @param origin The least value of the coordinates
     * @param bound The upper bound (exclusive) of the coordinates
     * @return The random slot with its coordinates pseudorandomly chosen
     * between the origin (inclusive) and the bound (exclusive).
     */
    public static BoardSlot getRandomSlot(int origin, int bound) {

        // Create a random number generator.
        Random random = new Random();

        // Iterate while I find a valid pair of coordinates (both even o both odd).
        while (true) {
            // The horizontal coordinate.
            final int refHoriz = random.nextInt(origin, bound);
            // The horizontal coordinate.
            final int refVert = random.nextInt(origin, bound);
            // Check coordinates of the board slot.
            if (BoardSlot.validateCoords(refHoriz, refVert)) {
                // Coordinates are valid: return the corresponding slot.
                return new BoardSlot(refHoriz, refVert);
            }
        }
    }

    /**
     * Create a slot with random horizontal and vertical coordinates.
     * @param origin The least value of the coordinates
     * @param bound The upper bound (exclusive) of the coordinates
     * @return The random slot with its coordinates pseudorandomly chosen
     * between the origin (inclusive) and the bound (exclusive).
     */
    public static BoardSlot getRandomSlot(int origin, int bound, int seed) {

        // Create a random number generator.
        Random random = new Random(seed);

        // Iterate while I find a valid pair of coordinates (both even o both odd).
        while (true) {
            // The horizontal coordinate.
            final int refHoriz = random.nextInt(origin, bound);
            // The horizontal coordinate.
            final int refVert = random.nextInt(origin, bound);
            // Check coordinates of the board slot.
            if (BoardSlot.validateCoords(refHoriz, refVert)) {
                // Coordinates are valid: return the corresponding slot.
                return new BoardSlot(refHoriz, refVert);
            }
        }
    }

    /**
     * Crete a new DummyPlayingBoard with a tower pattern placed on it, at the
     * specified location. The location is defined by the parameter origin, and
     * it represents the slot of the tower linked to the tower's base.
     * @param origin The origin of the tower (the position of the card linked to the base)
     * @param towerCard The two cards to place in order to create the tower
     * @param baseCard The card to place in order to create the base of the tower
     * @param baseLocation The relative location of the base, with respect to the origin.
     * @return A new DummyPlayingBoard that has a tower pattern placed on it.
     */
    public static DummyPlayingBoard getNewBoardWithTowerPattern(BoardSlot origin, KingdomCardFace towerCard, KingdomCardFace baseCard, CornerLocation baseLocation) {

        // Create a dummy (empty) playing board.
        final DummyPlayingBoard board = new DummyPlayingBoard();

        // Place the tower pattern on the empty board.
        placeTowerPattern(board, origin, towerCard, baseCard, baseLocation);

        // Return the board.
        return board;
    }

    /**
     * Place a tower pattern on the board, at the specified location.
     * @param board The board where to place the pattern
     * @param origin The origin of the tower (the position of the card linked to the base)
     * @param towerCard The two cards to place in order to create the tower
     * @param baseCard The card to place in order to create the base of the tower
     * @param baseLocation The relative location of the base, with respect to the origin
     */
    public static void placeTowerPattern(DummyPlayingBoard board, BoardSlot origin, KingdomCardFace towerCard, KingdomCardFace baseCard, CornerLocation baseLocation) {

        // Select the direction of the tower, depending on the relative
        // location of the base.
        RelativeLocation towerDirection = getTowerDirection(baseLocation);

        // Create the tower placing two card aligned in vertical direction.
        board.addCard(origin, towerCard);
        board.addCard(origin.getSlotAt(towerDirection), towerCard);
        // Add the base card.
        board.addCard(origin.getSlotAt(baseLocation), baseCard);
    }

    /**
     *
     * @param baseLocation The location of the tower's base, relative to the tower origin
     * @return The direction of the tower
     */
    private static RelativeLocation getTowerDirection(CornerLocation baseLocation) {
        return switch (baseLocation) {
            case TOP_RIGHT, TOP_LEFT -> BOTTOM;
            case BOTTOM_RIGHT, BOTTOM_LEFT -> TOP;
        };
    }

    //endregion

    //region Private Fields

    /**
     * The map of placed cards and their positions.
     */
    private final Map<BoardSlot, KingdomCardFace> placedCards = new HashMap<>();

    /**
     * The visible resources of the playing board.
     */
    private ResourcesCounter resources;

    /**
     * The visible items of the playing board.
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
        return new ImmutableList<>(new ArrayList<>(this.placedCards.keySet()));
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
