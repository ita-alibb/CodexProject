package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;

public class ReloadLobbyStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public ReloadLobbyStrategy() {

    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        return ClientConnection.getLobbyList();
    }

    //endregion
}
