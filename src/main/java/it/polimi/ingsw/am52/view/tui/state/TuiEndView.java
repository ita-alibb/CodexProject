package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to visualize the TUI representing the end of the game.
 * After a player has reached 20 points and the last turn is played, the game will automatically end and the winner, or the winners,
 * are displayed, with some basic information about the number of turns played and the disconnected player, only if the game
 * ended because a player left.
 */
public class TuiEndView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiEndView() {
        super(ViewType.BOARD);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands() {
        return new ArrayList<>() {{
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
