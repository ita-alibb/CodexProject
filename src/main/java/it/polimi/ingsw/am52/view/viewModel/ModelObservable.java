package it.polimi.ingsw.am52.view.viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to create an Observer-Observable Pattern. This triggers an update in the observers which are subscribed to
 * the object which inherits from this class.
 */
public abstract class ModelObservable {
    /**
     * The map between the type of event and the list of the observers subscribed to an observable.
     * The map is organized in this way to know which update must be triggered in the view.
     */
    protected final Map<EventType,List<ModelObserver>> observers;

    /**
     * The constructor of the class. This creates the map of observers.
     */
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
        if (types.length == 0) {
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
        observers.get(type).forEach(observer -> observer.update(type));
    }
}
