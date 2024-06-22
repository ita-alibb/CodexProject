package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.view.tui.TuiPrinter;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

public class ShowChatStrategy extends Strategy {
    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public ShowChatStrategy() {
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        ViewModelState.getInstance().setViewTypeShown(ViewType.CHAT);
        TuiPrinter.getInstance().reprint(ViewModelState.getInstance().getViewTypeShown());
        return new ResponseStatus();
    }

    //endregion
}
