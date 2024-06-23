package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.view.tui.TuiPrinter;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

/**
 * The class from Strategy to implement the behaviour to visualize the common board, with the common information of the game (decks, common objectives, scores, visible cards)
 */
public class ShowCommonBoardStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public ShowCommonBoardStrategy() {

    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        ViewModelState.getInstance().setViewTypeShown(ViewType.COMMON_BOARD);
        TuiPrinter.getInstance().reprint(ViewModelState.getInstance().getViewTypeShown());
        return new ResponseStatus();
    }
    //endregion
}
