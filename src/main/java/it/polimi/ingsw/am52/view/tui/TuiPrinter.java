package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.view.tui.state.TuiLobbyView;
import it.polimi.ingsw.am52.view.tui.state.TuiMenuView;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

//Context of the StatePattern
public class TuiPrinter implements ModelObserver {
    private static TuiPrinter INSTANCE;

    /**
     * The type of view printed in terminal
     */
    private static ViewType type;

    private TuiPrinter() {
        type = ViewType.MENU;
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
        return switch (type) {
            case MENU -> TuiMenuView.getAvailableCommands().contains(c);
            case LOBBY -> TuiLobbyView.getAvailableCommands().contains(c);
            default -> false;
        };
    }

    /**
     * Method called to update the viewModel
     */
    public void update(){
        switch (type) {
            case MENU: new TuiMenuView().print(); break;
            case LOBBY: new TuiLobbyView().print(); break;
        }
    }

    /**
     * Method to change the displayed view
     * @param type the view type
     */
    public void setType(ViewType type) {
        TuiPrinter.type = type;
    }
}
