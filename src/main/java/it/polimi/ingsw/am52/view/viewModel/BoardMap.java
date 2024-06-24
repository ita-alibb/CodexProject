package it.polimi.ingsw.am52.view.viewModel;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

/**
 * Generic class, in our case used to represent the board of a player. It is a subclass of LinkedHashMap, which has the property of storing the order
 * of insertion of its elements.
 * @param <K>       The key of the map
 * @param <V>       The value of the map
 */
public class BoardMap<K,V> extends LinkedHashMap<K,V> {
    /**
     * Map which connects the key to an insertion order, starting from 0
     */
    private final Map<K,Integer> orderedMap;
    /**
     * The order of insertion
     */
    private int order = 0;

    /**
     * Constructor of the class
     */
    public BoardMap(){
        super();
        orderedMap = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key,V val){
        orderedMap.put(key,order++);
        return super.put(key,val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key){
        orderedMap.remove(key);
        return super.remove(key);
    }

    /**
     * Method to verify if an object is inserted before another object.
     * In our case, the method is used to verified if a card has been placed before another one or after, to verify if a corner is covered or not.
     * @param key1      The first object
     * @param key2      The second object
     * @return          True if the first object is inserted before the second one, otherwise False
     */
    public boolean isFirst(K key1,K key2) {
        if(!orderedMap.containsKey(key1)) {
            return false;
        }

        if(!orderedMap.containsKey(key2)) {
            return false;
        }

        return orderedMap.get(key1) < orderedMap.get(key2);
    }
}