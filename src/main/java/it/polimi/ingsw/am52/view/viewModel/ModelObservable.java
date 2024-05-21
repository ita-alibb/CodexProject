package it.polimi.ingsw.am52.view.viewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelObservable {
    protected final List<ModelObserver> observers;

    public ModelObservable() {
        observers = new ArrayList<>();
    }

    /**
     * Adds an observer to the list
     */
    public synchronized void registerObserver(ModelObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list
     */
    public synchronized void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies the observers
     */
    protected void notifyObservers() {
        ArrayList<ModelObserver> obs = new ArrayList<>(observers);
        obs.forEach(ModelObserver::update);
    }
}
