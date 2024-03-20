package it.polimi.ingsw.am52.model.decks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * A class that contains a list of items from which to randomly extract one at a time,
 * up to the size of the list. The dealer does NOT maintain any reference to source list
 * of items.
 * Usage:<ul>
 * <li>Create a new instance, passing the list of items.
 * <li>Call init() to put items in random order.
 * <li>Call getNextItem() to get the next item (if at least one is present).
 * <li>Call getItemsCount() to retrieve he number of remaining items.
 * <li>Call init() to re-init the list with all items (in random order).
 * </ul>
 */
public class RandomDealer<TItem> {

    //region Private Fields

    /**
     * The list of all items.
     */
    private List<TItem> items = new ArrayList<>();

    /**
     * The counter of remaining items.
     */
    private int itemsCount;

    /**
     * The optional seed of the random generator.
     */
    private final Optional<Integer> seed;

    /**
     * True if the method init() has been invoked at least one time
     * on this dealer. For a dealer instantiated with an initial seed,
     * subsequent calls to the method init() will not trigger the
     * generation of new random list of items.
     */
    private boolean initialized = false;

    //endregion

    //region Constructors

    /**
     * Create a new RandomDealer that manages the specified list of items
     * @param items The list of items to manage.
     */
    public RandomDealer(List<TItem> items) {
        // Shallow copy each element to the internal private list.
        this.items.addAll(items);

        // Initialize the items counter with the size of the list.
        this.itemsCount = this.items.size();

        // No seed.
        this.seed = Optional.empty();
    }

    /**
     * Create a new RandomDealer that manages the specified list of items,
     * with a seed for the random generator.
     * @param items The list of items to manage.
     * @param seed The seed of the random generator.
     */
    public RandomDealer(List<TItem> items, int seed) {
        // Shallow copy each element to the internal private list.
        this.items.addAll(items);

        // Initialize the items counter with the size of the list.
        this.itemsCount = this.items.size();

        // Store the seed.
        this.seed = Optional.of(seed);
    }

    //endregion

    //region Getters

    /**
     * 
     * @return The count of the remaining items in the list.
     */
    public int getItemsCount() {
        return this.itemsCount;
    }

    //endregion

    //region Public Methods

    /**
     * 
     * @return True if there is at least one item to retrieve from the list.
     */
    public boolean hasNext() {
        return getItemsCount() != 0;
    }

    /**
     * Initialize the dealer with all items in random order.
     */
    public void init() {

        // Create a new (empty) list, that will contain the items sorted in
        // random order.
        List<TItem> newList = new ArrayList<>();

        // Reset the items counter.
        this.itemsCount = this.items.size();

        // If the dealer has a seed, and it has been already initialized,
        // then return from the method, without making any change on the
        // internal list of items.
        if (this.seed.isPresent() && this.initialized) {
            return;
        }

        // Boolean array, initialized with all false elements.
        // For each i-th element, true means that the i-th element has
        // been added to the new list.
        boolean[] added = new boolean[this.itemsCount];

        // Create a random number generator.
        Random random = this.seed.map(Random::new).orElseGet(Random::new);

        // Initialize the counter of items to add to the new list. At the
        // beginning, it is equal to the total number of items.
        int nItems = this.itemsCount;

        // Iterate while there is one element to add to the new array.
        while (nItems > 0) {

            // Get the random index, in the range 0 <= index < i.
            int index = random.nextInt(itemsCount);

            // Check if the item in the old list at that index has been already
            // added to the new list. Otherwise, add it and, set true the i-th
            // element of the boolean array and decrement the counter of the 
            // remaining elements to add.
            if (!added[index]) {
                newList.add(this.items.get(index));
                added[index] = true;
                nItems--;
            }
        }

        // Replace the old list with the new list.
        this.items = newList;

        // Set flag initialized to true.
        this.initialized = true;
    }

    /**
     * Return the next item of the list and decrement the counter of available
     * items.
     * @return The next item in the list.
     * @throws IllegalStateException There are no more items in the list.
     */
    public TItem getNextItem() throws IllegalStateException {

        // If there are no more items, throw an exception.
        if (!this.hasNext()) {
            throw new IllegalStateException("There are no more items in the dealer.");
        }

        // Return the item and decrement the counter.
        return this.items.get(this.items.size() - this.itemsCount--);
    }

    //endregion

}
