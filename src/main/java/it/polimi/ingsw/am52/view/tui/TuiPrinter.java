package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.state.TuiLobbyView;
import it.polimi.ingsw.am52.view.tui.state.TuiMenuView;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

//Context of the StatePattern
public class TuiPrinter implements ModelObserver {
    private static TuiPrinter INSTANCE;

    /**
     * The type of view printed in terminal
     */
    private ViewType type;

    private TuiPrinter() {
        this.type = ViewType.MENU;
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
        this.type = type;
    }
}
