package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.state.*;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

//Context of the StatePattern
public class TuiPrinter implements ModelObserver {
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
        };
    }

    /**
     * Method called to update the viewModel
     */
    public void update(){
        switch (ViewModelState.getInstance().getViewTypeShown()) {
            case MENU: new TuiMenuView().print(); break;
            case LOBBY: new TuiLobbyView().print(); break;
            case SETUP: new TuiSetupView().print(); break;
            case COMMON_BOARD: new TuiCommonBoardView().print(); break;
            case BOARD: new TuiBoardView().print(); break;
        }
    }
}
