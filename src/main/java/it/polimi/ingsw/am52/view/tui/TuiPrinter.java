package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.tui.state.*;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

/**
 * Class to print the correct View. This represents the context of the Strategy Pattern.
 * It is a singleton, so only one instance of this class can exists; if it is invoked after the first instance, it is called
 * a method on the same object.
 */
public class TuiPrinter extends ModelObserver {

    //region Static Fields

    /**
     * The Instance of the singleton
     */
    private static TuiPrinter INSTANCE;

    //endregion

    //region Private Methods

    /**
     * The private constructor, which subscribe to the list of observers
     */
    private TuiPrinter() {
        // register the printer to the existent observers
        ViewModelState.getInstance().registerObserver(this);
    }

    //endregion

    //region Public Methods

    /**
     * Method to invoke another method of the same object. If this object doesn't exist, it instantiates a new one.
     * @return      The singleton class
     */
    public static TuiPrinter getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new TuiPrinter();
        }

        return INSTANCE;
    }

    /**
     * Method to check if the current input is valid in the current view
     * @param c the command char
     * @return true if valid, false otherwise
     */
    public static boolean checkValidCommand(Character c) {
        return switch (ViewModelState.getInstance().getViewTypeShown()) {
            case MENU -> TuiMenuView.getAvailableCommands().contains(c);
            case LOBBY -> TuiLobbyView.getAvailableCommands().contains(c);
            case SETUP -> TuiSetupView.getAvailableCommands().contains(c);
            case COMMON_BOARD -> TuiCommonBoardView.getAvailableCommands().contains(c);
            case BOARD -> TuiBoardView.getAvailableCommands().contains(c);
            case CHAT -> TuiChatView.getAvailableCommands().contains(c);
            case END -> TuiEndView.getAvailableCommands().contains(c);
        };
    }

    /**
     * Method called to update the viewModel, every event for TUI has to reprint the whole view
     *
     * @param type the view Type to reprint
     */
    public synchronized void reprint(ViewType type){
        switch (type) {
            case MENU: new TuiMenuView().print(); break;
            case LOBBY: new TuiLobbyView().print(); break;
            case SETUP: new TuiSetupView().print(); break;
            case COMMON_BOARD: new TuiCommonBoardView().print(); break;
            case BOARD: new TuiBoardView().print(); break;
            case CHAT: new TuiChatView().print(); break;
            case END: new TuiEndView().print(); break;
        }
    }

    //endregion

    //region Protected Methods

    /**
     * Update that is triggered for event END_GAME
     */
    @Override
    protected void updateEndGame() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event INIT_GAME
     */
    @Override
    protected void updateInitGame() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event TAKE_CARD
     */
    @Override
    protected void updateTakeCard() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.COMMON_BOARD) {
            this.reprint(type);
        }
    }

    /**
     * Update that is triggered for event DRAW_CARD
     */
    @Override
    protected void updateDrawCard() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.COMMON_BOARD) {
            this.reprint(type);
        }
    }

    /**
     * Update that is triggered for event PLACE_CARD
     */
    @Override
    protected void updatePlaceCard() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.BOARD) {
            this.reprint(type);
        }
    }

    /**
     * Update that is triggered for event PLACE_STARTER_CARD
     */
    @Override
    protected void updatePlaceStarterCard() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.SETUP || ViewModelState.getInstance().getPhase() == GamePhase.PLACING) {
            this.reprint(type);
        }
    }

    /**
     * Update that is triggered for event SELECT_OBJECTIVE
     */
    @Override
    protected void updateSelectObjective() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.SETUP || ViewModelState.getInstance().getPhase() == GamePhase.PLACING) {
            this.reprint(type);
        }
    }

    /**
     * Update that is triggered for event LEAVE_GAME
     */
    @Override
    protected void updateLeaveGame() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event LIST_LOBBY
     */
    @Override
    protected void updateListLobby() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event CREATE_LOBBY
     */
    @Override
    protected void updateCreateLobby() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event JOIN_LOBBY
     */
    @Override
    protected void updateJoinLobby() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    @Override
    protected void updateChat() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.CHAT){
            this.reprint(type);
        }
    }

    //endregion
}
