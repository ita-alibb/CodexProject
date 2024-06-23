package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class to visualize the TUI view representing the menu of the game.
 * This is the very first scenario we can see when we open the game, and you can perform the following commands:
 * <ul>
 *     <li>Creating a new lobby</li>
 *     <li>Joining an existing lobby</li>
 *     <li>Update the list of all existing lobby</li>
 * </ul>
 */
public class TuiMenuView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiMenuView() {
        super(ViewType.MENU);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands(){
        return new ArrayList<>(){{
            add('J');
            add('C');
            add('R');
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
        Map<Integer,Integer> lobbies = ViewModelState.getInstance().getLobbies();
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                              LOBBIES                                       │");
        for (var lobby : lobbies.entrySet()) {
            System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
            System.out.printf( "│ LOBBY ID: %d                   Available slots: %d                           │%n", lobby.getKey(), lobby.getValue());
        }

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
        System.out.println("│ - (J {id} {nickname}) join_lobby -> join lobby with id               │");
        System.out.println("│ - (C {maxPlayers} {nickname}) create_lobby -> to create a lobby      │");
        System.out.println("│ - (R) reload_lobby -> to reload available lobbies                    │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
