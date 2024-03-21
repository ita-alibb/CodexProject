package it.polimi.ingsw.am52.model.playingBoards;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.exceptions.PlayingBoardException;

/**
 * The playing board of a player. The playing board is created from
 * the starter card. Then, Kingdom cards (Gold or Resource) can be placed
 * in available slots. The playing board keep track of the available (visible)
 * resources and items, and the position of each placed card (in order to
 * valuate patterns for objective points).
 */
public class PlayingBoard implements BoardInfo {

    //region Private Fields

    /**
     * The root card of the playing board.
     */
    private final StarterCardFace starterCard;

    /**
     * The hash map that keep track of all placed cards. Each time a card is placed
     * on the playing bord, the card must e added to this map together with it
     * position (CardSlot).
     * @implNote This collection does not contain the starter card.
     */
    private final Map<BoardSlot, KingdomCardFace> placedCards = new HashMap<>();
    
    /**
     * The set of available position, where a new card can be placed. Every time
     * a new card is placed on the playing board, this collection must be updated
     * to remove the occupied slot and add any additional slots.
     */
    private final Set<BoardSlot> availableSlots = new HashSet<>();

    /**
     * The available (visible) resource on the playing board. Every time a new card is
     * placed on the playing board, this counter must be updated subtracting the 
     * resource that the new card hides and adding the new resource visible on the
     * new card.
     */
    private ResourcesCounter resources;

    /**
     * The available (visible) items on the playing board. Every time a new card is
     * placed on the playing board, this counter must be updated subtracting the 
     * item that the new card hides and adding the new items visible on the
     * new card.
     */
    private ItemsCounter items;

    //endregion

    //region Private Static Methods

    /**
     * This method takes a Map of placed cards and their positions, and return the (optional)
     * card placed at the specified neighbor position relative to the specified reference
     * board slot. If there isn't any card at the neighbor slot position, the method returns
     * an Optional.empty() instance.
     * @param cards A Map of placed cards and their slot position.
     * @param refSlot The reference slot position.
     * @param location The location of the card to get, relative to the reference slot.
     * @return The card placed at the specified location. If there is non cord at the specified
     * location, the method returns an Optional.Empty().
     * @implNote This method is static because it is used also by the nexted class PlayingBoardInfo.
     */
    private static Optional<KingdomCardFace> getNeighborCard(Map<BoardSlot, KingdomCardFace> cards, BoardSlot refSlot, RelativeLocation location) {
        
        // Get the candidate slot position.
        BoardSlot candidate = refSlot.getSlotAt(location);

        // Return the card placed on the slot (if any), or an empty optional.
        return /* Is there a card? */   cards.containsKey(candidate) ?
               /* Yes, return it */     Optional.of(cards.get(candidate)) :
               /* No, return empty() */ Optional.empty();
    }

    /**
     * This method takes a Map of placed cards and their positions, and return the (optional)
     * card placed at the specified neighbor position at the corner relative to the specified reference
     * board slot. If there isn't any card at the neighbor slot position, the method returns
     * an Optional.empty() instance.
     * @param cards A Map of placed cards and their slot position.
     * @param refSlot The reference slot position.
     * @param location The corner location of the card to get, relative to the reference slot.
     * @return The card placed at the specified location. If there is non cord at the specified
     * location, the method returns an Optional.Empty().
     * @implNote This method is static because it is used also by the nexted class PlayingBoardInfo.
     */
    private static Optional<KingdomCardFace> getNeighborCard(Map<BoardSlot, KingdomCardFace> cards, BoardSlot refSlot, CornerLocation location) {
        
        // Get the candidate slot position.
        BoardSlot candidate = refSlot.getSlotAt(location);

        // Return the card placed on the slot (if any), or an empty optional.
        return /* Is there a card? */   cards.containsKey(candidate) ?
               /* Yes, return it */     Optional.of(cards.get(candidate)) :
               /* No, return empty() */ Optional.empty();
    }

    //endregion

    //region Constructor

    /**
     * Creates a new playing board, with the specified starter card as the initial
     * root.
     * @param starterCard The starer card representing the root of the playing board.
     */
    public PlayingBoard(StarterCardFace starterCard) {

        // Store the reference to the starter card.
        this.starterCard = starterCard;

        // Initialize the resource counter with the resources visible
        // on the starter card.
        this.resources = starterCard.getResources();

        // The starter card does not have items, so initialize the items counter
        // to zero (use default constructor).
        this.items = new ItemsCounter();

        // Add the new available positions.
        // Top-Right corner.
        if (starterCard.getTopRightCorner().isPresent()) {
            this.availableSlots.add(new BoardSlot(1, 1));
        }
        // Bottom-Right corner.
        if (starterCard.getBottomRightCorner().isPresent()) {
            this.availableSlots.add(new BoardSlot(1, -1));
        }
        // Bottom-Left corner.
        if (starterCard.getBottomLeftCorner().isPresent()) {
            this.availableSlots.add(new BoardSlot(-1, -1));
        }
        // Top-Left corner.
        if (starterCard.getTopLeftCorner().isPresent()) {
            this.availableSlots.add(new BoardSlot(-1, 1));
        }
    }

    //endregion

    //region Getters

    /**
     *
     * @return The starter card face that is the root of this playing board.
     */
    public StarterCardFace getStarerCard() {
        return this.starterCard;
    }

    /**
     * 
     * @return An immutable object containing the info of this playing board.
     */
    public BoardInfo getInfo() {
        return new PlayingBoardInfo(this.placedCards, this.availableSlots, this.resources, this.items);
    }

    //endregion

    //region Public Methods

    /**
     * Place a card on an available slot on the playing board and returns the points gained
     * by the player. The status of the playing board is updated as follows:<ul>
     * <li>The specified slot is removed from the collection of the available slots 
     * <li>Eventually, new slots are added to the collection of available slots
     * <li>The resources counter is updated, taking into consideration resources hidden by the
     * placed card and new resources visible on the placed card.
     * <li>The items counter is updated, taking into consideration items hidden by the
     * placed card and new items visible on the placed card.
     * </ul>
     * @param slot The slot on the playing board where the card is placed.
     * @param card The card to place.
     * @return The points gained by the player.
     * @throws IllegalArgumentException If the specified slot is not an available slot on the playing board
     * or the specified card cannot be placed (i.e. there are no required resources).
     */
    public int placeCard(BoardSlot slot, KingdomCardFace card) throws IllegalArgumentException {

        /*
         * Preliminary checks.
         */
        // Check if the slot is available, otherwise throw an exception.
        if (!this.availableSlots.contains(slot)) {
            throw new PlayingBoardException("The specified slot is not available on the playing board.");
        }

        // Check if the card can be placed.
        if (!card.canPlace(this)) {
            throw new PlayingBoardException("The specified card cannot be placed on the playing board.");
        }

        /*
         * 1) Update the collection of placed cards.
         */
        // Add the card in the collection of the placed cards.
        this.placedCards.put(slot, card);
        
        /*
         * 2) Update the collection of available cards.
         */
        // Remove the slot from the collection of the available slots.
        this.availableSlots.remove(slot);
        
        // Get the list of new available slots.
        List<BoardSlot> newAvailableSlot = findNewAvailableSlots(slot);
        this.availableSlots.addAll(newAvailableSlot);
        
        /*
         * 3) Update resources and items
         */
        // Get the list of corners hidden by the placed card.
        List<CardCorner> hiddenCorners = findHiddenCorners(slot);
        
        // Update resources and items counter, subtracting hidden resources/items.
        for (CardCorner hidden : hiddenCorners) {
            this.resources = ResourcesCounter.subtract(resources, hidden.getResources());
            this.items = ItemsCounter.subtract(items, hidden.getItems());
        }
        
        // Update resources and items counter, adding the visible resources/items
        // of the placed card.
        this.resources = ResourcesCounter.add(resources, card.getResources());
        this.items = ItemsCounter.add(items, card.getItems());
        
        // Return the points gained by the player by placing the card on
        // the playing board.
        System.out.println(hiddenCorners.size());
        return card.gainedPoints(this, hiddenCorners.size());
        }
        
    //endregion
    
    //region Private Methods
    
    /**
    * When a card is placed on a slot in the playing board, it hides at least
    * one corner of another card previously placed on the playing board. But,
    * in particular circumstances, the placed card can hide more corners.
    * This method check all slots at positioned at the corners of the placed
    * card, and return a list with all hidden corners.
    * @param slot The slot where the new card is placed.
    * @return A list of corners hidden by placing the new card on the playing board,
    * on the specified slot.
    */
    private List<CardCorner> findHiddenCorners(BoardSlot slot) {

        /*
            * Important note:
            * If a card covers a corner, that corner is visible, so it means
            * that the method getXXXCorner() returns a Optional that is NOT empty!
            */

        // Initialize an empty list of corners, that will be populated with
        // all corners covered by placing a card on the specified slot.
        List<CardCorner> hiddenCorners = new ArrayList<>();

        // The list of corners to check.
        CornerLocation[] locations = new CornerLocation[] {
                CornerLocation.TOP_RIGHT,
                CornerLocation.BOTTOM_RIGHT,
                CornerLocation.BOTTOM_LEFT,
                CornerLocation.TOP_LEFT,
        };

        // Check all corners.
        for (CornerLocation cornerLocation : locations) {
            Optional<CardCorner> hidden = getHiddenCorner(slot, cornerLocation);
            // If there is a hidden corner, add it to the collection.
            hidden.ifPresent(hiddenCorners::add);
        }

        // Return the collection with hidden corners.
        return hiddenCorners;
    }

    private List<BoardSlot> findNewAvailableSlots(BoardSlot slot) throws IllegalArgumentException {

        // The list hat will contain new available slots.
        final List<BoardSlot> availableSlots = new ArrayList<>();

        // Check if the specified slot has a card placed on it. Otherwise,
        // return the empty list.
        if (!this.placedCards.containsKey(slot)) {
            return availableSlots;
        }

        // The list of candidates available slot, linkable to the specified slot.
        List<BoardSlot> candidates = new ArrayList<>();

        // The corner locations to check.
        CornerLocation[] corners = new CornerLocation[] {
                CornerLocation.TOP_RIGHT,
                CornerLocation.BOTTOM_RIGHT,
                CornerLocation.BOTTOM_LEFT,
                CornerLocation.TOP_LEFT,
        };

        // Get the card on the reference slot.
        KingdomCardFace refCard = this.placedCards.get(slot);

        // Iterate over each corner and check if the corresponding
        // neighbor slot is a candidate.
        for (CornerLocation cornerLocation : corners) {
            
            // get the corner of the reference card.
            Optional<CardCorner> corner = refCard.getCornerAt(cornerLocation);

            // If the corner is empty, there isn't a candidate available
            // slot linkable to that corner, so continue to the next corner.
            if (corner.isEmpty()) {
                continue;
            }

            // Get the neighbor slot at this corner.
            BoardSlot neighbor = slot.getSlotAt(cornerLocation);

            // If the slot already has a card placed on it, it isn't a candidate
            // so continue to the next corner.
            if (this.placedCards.containsKey(neighbor)) {
                continue;
            }

            // Add the neighbor slot to the list of candidate available slots.
            candidates.add(neighbor);

            // Check each candidate.
            for (BoardSlot card : candidates) {
                if (isLinkable(card)) {
                    availableSlots.add(card);
                }
            }
        }

        // Return the list of linkable slots.
        return availableSlots;
    }
    
    /**
     * Checks if an empty slot of the playing board can be linked to its
     * neighbor slot at the specified corner.
     * It only checks if:<ul>
     * <li>The slot is not the root slot of the playing board
     * <li>The neighbor slot at the corner do not have a card
     * <li>The neighbor slot at corner have a card, but with a visible
     * linkable corner.
     * </ul>
     * @param slot The slot to check.
     * @param cornerLocation The location of the corner to check.
     * @return True if the slot is linkable.
     */
    private boolean isLinkable(BoardSlot slot, CornerLocation cornerLocation) {

        if (slot.isRootSlot()) {
            return false;
        }

        // Get the neighbor slot at the specified corner.
        BoardSlot neighbor = slot.getSlotAt(cornerLocation);

        // Check if the neighbor slot has a card   it.
        if (this.placedCards.containsKey(neighbor)) {
            // If there is a card, check if the corner is visible.
            // Get the card.
            KingdomCardFace card = this.placedCards.get(neighbor);
            // Get the appropriate corner.
            Optional<CardCorner> corner = switch (cornerLocation) {
                case TOP_RIGHT -> card.getCornerAt(CornerLocation.BOTTOM_LEFT);
                case BOTTOM_RIGHT -> card.getCornerAt(CornerLocation.TOP_LEFT);
                case BOTTOM_LEFT -> card.getCornerAt(CornerLocation.TOP_RIGHT);
                case TOP_LEFT -> card.getCornerAt(CornerLocation.BOTTOM_RIGHT);
            };

            // Return true if the corner is visible.
            return corner.isPresent();
        } else {
            return true;
        }

    }

    /**
     * Checks if the specified slot of this playing board can be linked
     * to all its neighbor slot, i.e. if it is possible to place a card
     * on that slot.
     * @param slot The board slot to check.
     * @return True if it is possible to place a card on the specified slot,
     * false otherwise.
     * @implNote This method does not check the absolute meaning of 'linkable',
     * because it does NOT if there is at least one card placed at the corner of the slot,
     * in order to link the placed card to that card. For this method, an empty
     * slot that does not have any card placed around it is linkable.
     */
    private boolean isLinkable(BoardSlot slot) {

        // The root slot is not linkable by definition (it can only
        // contain the starter card).
        if (slot.isRootSlot()) {
            return false;
        }

        // The four corner locations to check (all corners).
        CornerLocation[] corners = new CornerLocation[] {
                CornerLocation.TOP_RIGHT,
                CornerLocation.BOTTOM_RIGHT,
                CornerLocation.BOTTOM_LEFT,
                CornerLocation.TOP_LEFT,
        };

        // Iterate over all corners. The specified slot is linkable
        // only if ALL its corners are linkable.
        for (CornerLocation cornerLocation : corners) {
            // If at least one corner is not linkable, return false.
            if (!isLinkable(slot, cornerLocation)) {
                return false;
            }
        }

        // All corners have been checked, thus this slot is linkable.
        return true;
    }

    /**
     * Check if the card positioned on the specified slot hides the corner
     * of another card at the specified corner. If there isn't a card on
     * the slot linked to the specified corner, the method returns an
     * Optional.empty() value.
     * @implNote If there is a card on the linked slot, its corner is visible
     * due to the rules of card placement. Thus, the getXxxCorner() method
     * on the card instance return a non-empty value.
     * @param refSlot The slot where the card is placed.
     * @param location The corner location to check.
     * @return The (optional) corner hidden by the card.
     */
    private Optional<CardCorner> getHiddenCorner(BoardSlot refSlot, CornerLocation location) {

        // Get the slot that is a candidate to have its corner hidden.
        BoardSlot candidate = refSlot.getSlotAt(location);

        CardFace card;

        // Check if the candidate is the root slot.
        if (candidate.isRootSlot()) {

            // Get the card on the candidate slot.
            card = this.starterCard;
        } else {

            // Check if the candidate slot has a card placed on it.
            if (!this.placedCards.containsKey(candidate)) {

                // The candidate does not have a card placed on it, so there is
                // no covered corners.
                return Optional.empty();
            }

            // Get the card on the candidate slot.
            card = this.placedCards.get(candidate);
        }

        // Return the covered corner, depending on the relative location
        // of the candidate slot.
        return switch (location) {
            case TOP_RIGHT -> card.getBottomLeftCorner();
            case BOTTOM_RIGHT -> card.getTopLeftCorner();
            case BOTTOM_LEFT -> card.getTopRightCorner();
            case TOP_LEFT -> card.getBottomRightCorner();
        };

    }

    //endregion

    //region BoardInfo Implementation

    /**
     * 
     * @return The visible resources on the playing board.
     */
    @Override
    public ResourcesCounter getResources() {
        return this.resources;
    }

    /**
     * 
     * @return The visible items on the playing board.
     */
    @Override
    public ItemsCounter getItems() {
        return this.items;
    }

    /**
     * 
     * @return The list slots where a Kingdom card is placed.
     */
    @Override
    public ImmutableList<BoardSlot> getCoveredSlots() {
        return new ImmutableList<>(new ArrayList<>(this.placedCards.keySet()));
    }

    /**
     * 
     * @return The list of all available slot, where a kingdom card can
     * be placed.
     */
    @Override
    public ImmutableList<BoardSlot> getAvailableSlots() {
        return new ImmutableList<>(new ArrayList<>(this.availableSlots));
    }

    /**
     * Check if there is a card placed on the specified slot and return that
     * card.
     * @param location The slot where the card is placed.
     * @return The card placed on the specified slot.
     * @throws IllegalArgumentException If the specified slot does not contain a card.
     */
    public KingdomCardFace getCardAt(BoardSlot location) throws IllegalArgumentException {

        // Check if the specified slot contains a card.
        if (!this.placedCards.containsKey(location)) {
            throw new PlayingBoardException(String.format("The specified slot (%s) does not contains a card.", location));
        }

        // Return the card placed on the specified slot.
        return this.placedCards.get(location);
    }

    @Override
    public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, RelativeLocation location) {
        return getNeighborCard(this.placedCards, refSlot, location);
    }

    @Override
    public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, CornerLocation location) {
        return getNeighborCard(this.placedCards, refSlot, location);
    }

    //endregion

    //region Nested Types

    protected class PlayingBoardInfo implements BoardInfo {

        private final ResourcesCounter resources;
        private final ItemsCounter items;
        private final ImmutableList<BoardSlot> availableSlots;
        private final Map<BoardSlot, KingdomCardFace> placedCards;

        protected PlayingBoardInfo(Map<BoardSlot, KingdomCardFace> placedCards, Set<BoardSlot> availableSlots, ResourcesCounter resources,
                ItemsCounter items) {
            // Initialize resources.
            this.resources = resources;
            // Initialize items.
            this.items = items;
            // initialize available slots with an immutable copy of the parameter list.
            this.availableSlots = new ImmutableList<>(new ArrayList<>(availableSlots));
            // Create a new map for placed cards.
            this.placedCards = new HashMap<>();
            // Copy all entries form the input map.
            this.placedCards.putAll(placedCards);
        }

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
            return this.availableSlots;
        }

        @Override
        public KingdomCardFace getCardAt(BoardSlot location) throws IllegalArgumentException {

            if (!this.placedCards.containsKey(location)) {
                throw new PlayingBoardException(
                        String.format("The specified slot (%s) does not contains a card.", location));
            }
            
            return this.placedCards.get(location);

        }

        @Override
        public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, RelativeLocation location) {
            return PlayingBoard.getNeighborCard(this.placedCards, refSlot, location);
        }

        @Override
        public Optional<KingdomCardFace> getNeighborCard(BoardSlot refSlot, CornerLocation location) {
            return PlayingBoard.getNeighborCard(this.placedCards, refSlot, location);
        }

        //endregion

    }
    
}