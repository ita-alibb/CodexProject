package it.polimi.ingsw.am52.view.tui.strategy;

/**
 * The interface to implement a Strategy Pattern on the InputReader class
 */
public interface Strategy {
    /**
     * The method to execute the method which is associated to different actions, depending on which strategy we are in.
     */
    void execute();
}
