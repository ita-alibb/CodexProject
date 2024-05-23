package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;

/**
 * The interface to implement a Strategy Pattern on the InputReader class
 */
public abstract class Strategy {
    /**
     * The method to execute the method which is associated to different actions, depending on which strategy we are in.
     */
    public final void execute(){
        var networkResult = executeWithNetworkCall();

        if (networkResult == null){
            System.out.println("Connection Error");
        } else if (networkResult.errorCode != 0) {
            System.out.println("Error: " + networkResult.errorMessage);
        }
        //if the call goes right the ViewModelState handles it
    }

    protected abstract ResponseStatus executeWithNetworkCall();
}
