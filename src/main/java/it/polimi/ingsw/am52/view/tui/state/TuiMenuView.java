package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.tui.InputReader;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.Map;

import static it.polimi.ingsw.am52.view.tui.InputReader.readLine;

public class TuiMenuView extends TuiView {
    public TuiMenuView() {
        super(ViewType.MENU);
    }

    @Override
    protected void printView() {
        //draw view
        Map<Integer,Integer> lobbies = ViewModelState.getInstance().getLobbies();
        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                              LOBBIES                                       │");
        for (var lobby : lobbies.entrySet()) {
            System.out.println("          ├────────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("          │ LOBBY ID: %d                   Available slots: %d                           │%n", lobby.getKey(), lobby.getValue());
        }

        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands(){
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("          │ - (J {id} {nickname}) join_lobby -> join lobby with id               │");
        System.out.println("          │ - (C {maxPlayers} {nickname}) create_lobby -> to create a lobby      │");
        System.out.println("          │ - (R) reload_lobby -> to reload available lobbies                    │");
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");


        readLine(
                new ArrayList<>(){{
                    add('J');
                    add('C');
                    add('R');
                };}
        );
    }
    // IMPLEMENT A STATE PATTERN, based on responses. One thread is listening to input on terminal. one thread is the one on connection listening on broadcast (triggers re-draw of tui)
    // both threads modify the viewModel (for lobby phase is menumodel, for game phase will be GameModel) and after modify it (with the response or broadcast TODO: MAYBE IT'S BETTER TO HAVE THE STRING METHOD ON ConnectionRMI.sendResponse() to know how to use the data (otherwise class check on BaseResponseData or method elaborate(JoinResponseData) elaborate(LeaveGameData) ecc).)
    // after the modifying trigger redraw of the terminal, <-- how to draw a pretty terminal?
}
