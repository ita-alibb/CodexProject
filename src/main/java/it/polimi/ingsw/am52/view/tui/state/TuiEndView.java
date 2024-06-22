package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiEndView extends TuiView {

    public TuiEndView() {
        super(ViewType.BOARD);
    }

    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>(){{
            add('L');
        }};

        return available;
    }

    @Override
    protected void printView() {
        var players = String.join(" ", ViewModelState.getInstance().getNicknames());
        var winners = String.join(" ", ViewModelState.getInstance().getWinners());
        var disconnectedPlayer = ViewModelState.getInstance().getDisconnectedPlayer();

        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                 END GAME                                   │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        if (disconnectedPlayer != null && !disconnectedPlayer.trim().isEmpty()) {
            System.out.printf("│%-76s│%n", disconnectedPlayer + " DISCONNECTED! Game ends without winners :(");
            System.out.printf("│%-76s│%n","all blame on him and not on the developers! :)))))))))");
        } else {
            System.out.printf("│Winner(s): %-69s│%n", winners);
        }
        System.out.printf("│Players: %-69s│%n", players);
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│                           THANK YOU FOR PLAYING                            │");
        System.out.printf("│                           Turns played: %-3d                                │%n", ViewModelState.getInstance().getTurn());
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");

    }

    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}
