package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TuiMenuView extends TuiView {
    public TuiMenuView() {
        super(ViewType.MENU);
    }

    public static List<Character> getAvailableCommands(){
        return new ArrayList<>(){{
            add('J');
            add('C');
            add('R');
        }};
    }

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
}
