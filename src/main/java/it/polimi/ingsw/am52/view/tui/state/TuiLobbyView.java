package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to visualize the TUI view representing the lobby.
 * In this view, there is a list of nicknames, which represents other players waiting for the game to reach the maximum amount of player
 * in the lobby and start. Each time a new player enters the game, the view updates automatically.
 */
public class TuiLobbyView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiLobbyView() {
        super(ViewType.LOBBY);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands() {
        return new ArrayList<>(){{
            add('L');
        }};
    }

    //endregion

    //region Inherited Methods

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printView() {
        //draw view
        var players = ViewModelState.getInstance().getNicknames();
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│%30s%21s%25s│%n","LOBBY", "ID:" + ViewModelState.getInstance().getCurrentLobbyId(), "");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        players.forEach(p -> {
            int padding = (16 - p.length()) / 2;
            System.out.printf("│                              %16s                              │%n", String.format("%" + padding + "s%s%" + padding + "s", "", p, ""));
        });
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands(){
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
