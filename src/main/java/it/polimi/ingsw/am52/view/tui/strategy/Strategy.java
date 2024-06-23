package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;

/**
 * The abstract class to implement a Strategy Pattern on the InputReader class
 */
public abstract class Strategy {

    //region Public Method

    /**
     * Method to execute a different action depending on which strategy we are in and check if there are any errors from the server.
     */
    public final void execute(){
        var networkResult = executeWithNetworkCall();

        if (networkResult == null){
            System.out.println("Connection Error");
        } else if (networkResult.getErrorCode() != 0) {
            System.out.println("Error: " + networkResult.getErrorMessage());
        }
        //if the call goes right the ViewModelState handles it
    }

    //endregion

    //region Protected Method

    /**
     * Method to catch the user inputs and perform the call to the server.
     * @return  The response from the server
     */
    protected abstract ResponseStatus executeWithNetworkCall();

    //endregion
}
