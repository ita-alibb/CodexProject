package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.state.*;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

//Context of the StatePattern
public class TuiPrinter extends ModelObserver {
    private static TuiPrinter INSTANCE;

    private TuiPrinter() {
        // register the printer to
        ViewModelState.getInstance().registerObserver(this);
    }

    //SINGLETON,
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
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event DRAW_CARD
     */
    @Override
    protected void updateDrawCard() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event PLACE_CARD
     */
    @Override
    protected void updatePlaceCard() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event PLACE_STARTER_CARD
     */
    @Override
    protected void updatePlaceStarterCard() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event SELECT_OBJECTIVE
     */
    @Override
    protected void updateSelectObjective() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event LEAVE_GAME
     */
    @Override
    protected void updateLeaveGame() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event LIST_LOBBY
     */
    @Override
    protected void updateListLobby() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event CREATE_LOBBY
     */
    @Override
    protected void updateCreateLobby() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    /**
     * Update that is triggered for event JOIN_LOBBY
     */
    @Override
    protected void updateJoinLobby() {
        this.reprint(ViewModelState.getInstance().getViewTypeShown());
    }

    @Override
    protected void updateChat() {
        var type = ViewModelState.getInstance().getViewTypeShown();
        if (type == ViewType.CHAT){
            this.reprint(type);
        }
    }
}
