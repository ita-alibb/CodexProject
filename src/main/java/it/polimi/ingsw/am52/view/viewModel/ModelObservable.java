package it.polimi.ingsw.am52.view.viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModelObservable {
    protected final Map<EventType,List<ModelObserver>> observers;

    public ModelObservable() {
        this.observers = new HashMap<>();
        for (EventType eventType : EventType.values()) {
            this.observers.put(eventType, new ArrayList<>());
        }
    }

    /**
     * Adds an observer to the list
     *
     * @param observer the observer to register
     * @param types list of events to register to, if nothing is passed then it will be registered for every event
     */
    public synchronized void registerObserver(ModelObserver observer, EventType... types) {
        if (types.length == 0) {
            for (EventType type : EventType.values()) {
                observers.get(type).add(observer);
            }
            return;
        }

        for (EventType type : types) {
            observers.get(type).add(observer);
        }
    }

    /**
     * Removes an observer from the list
     *
     * @param observer the observer to un-register
     * @param types list of events to un-register to, if nothing is passed then it will be un-registered for every event
     */
    public synchronized void removeObserver(ModelObserver observer, EventType... types) {
        if (types == null) {
            for (EventType type : EventType.values()) {
                observers.get(type).remove(observer);
            }
            return;
        }

        for (EventType type : types) {
            observers.get(type).remove(observer);
        }
    }

    /**
     * Notifies the observers
     */
    protected void notifyObservers(EventType type) {
        observers.get(type).forEach(ModelObserver::update);
    }
}
