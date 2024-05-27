package it.polimi.ingsw.am52.view.viewModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoardMap<K,V> extends LinkedHashMap<K,V> {
    private final Map<K,Integer> orderedMap;
    private int order = 0;

    public BoardMap(){
        super();
        orderedMap = new HashMap<>();
    }

    @Override
    public V put(K key,V val){
        orderedMap.put(key,order++);
        return super.put(key,val);
    }

    @Override
    public V remove(Object key){
        orderedMap.remove(key);
        return super.remove(key);
    }

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