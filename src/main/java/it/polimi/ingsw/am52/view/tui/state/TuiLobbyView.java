package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.tui.InputReader;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.Map;

public class TuiLobbyView extends TuiView {
    public TuiLobbyView() {
        super(ViewType.LOBBY);
    }

    @Override
    protected void printView() {
        //draw view
        var players = ViewModelState.getInstance().getNicknames();
        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("          │                              LOBBY                   ID:%d                  │", ViewModelState.getInstance().getCurrentLobbyId());
        System.out.println("          ├────────────────────────────────────────────────────────────────────────────┤");
        for (var player : players) {
            System.out.printf("          │                           " + player + "                         │%n");
        }
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands(){
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("          │ - (L) leave_lobby -> leave lobby                                     │");
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");


        new Thread(new InputReader(
                new ArrayList<>(){{
                    add('J');
                    add('C');
                    add('R');
                };}
        )).start();
    }
}
