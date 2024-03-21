package it.polimi.ingsw.am52.model.cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * A collection of items that can be only accessed by index or can be
 * iterated.
 */
public class ImmutableList<T> implements Iterable<T> {

    //region Private Field

    /**
     * The item's container.
     */
    private final List<T> items = new ArrayList<>();

    //endregion

    //region Constructors

    /**
     * Create an immutable list that contains all elements of the
     * passed list. Each element is a shallow copy of the original
     * element.
     * @param source The original list of items.
     */
    public ImmutableList(List<T> source) {
        this.items.addAll(source);
    }

    //region

    //region Iterable Implementation

    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    //endregion

    //region Public Methods

    /**
     * Add all items in this collection to the destination collection.
     * @param destination The container where add the elements of this list.
     */
    public void addTo(List<T> destination) {
        destination.addAll(items);
    }

    /**
     * Get the item at the specified position.
     * @param index The position of the item in the list.
     * @return The item ast the specified position.
     */
    public T get(int index) {
        return this.items.get(index);
    }

    /**
     *
     * @return The number of elements of this collection.
     */
    public int size() {
        return this.items.size();
    }

    public List<T> toList() {
        return new ArrayList<>(this.items);
    }

    public boolean contains(T item) {
        return this.items.contains(item);
    }

    public Stream<T> stream() {
        return this.items.stream();
    }

    //endregion

}
