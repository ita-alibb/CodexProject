package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.view.tui.TuiController;

public class LeaveLobbyStrategy implements Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public LeaveLobbyStrategy() {

    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        TuiController.leaveLobby();
    }

    //endregion
}
