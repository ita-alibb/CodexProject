package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiLobbyView extends TuiView {
    public TuiLobbyView() {
        super(ViewType.LOBBY);
    }

    public static List<Character> getAvailableCommands() {
        return new ArrayList<>(){{
            add('L');
        };};
    }

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

    @Override
    protected void printCommands(){
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}
