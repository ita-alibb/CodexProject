package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to visualize the TUI representing the chat of the game.
 * <P>
 *      In the chat, we can have the following paths:
 *      <ul>
 *          <li>Sending a message to everyone in the lobby</li>
 *          <li>Sending a private message (whisper) to a single player</li>
 *          <li>Going back to other views</li>
 *      </ul>
 */
public class TuiChatView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiChatView() {
        super(ViewType.BOARD);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands() {
        return new ArrayList<>(){{
            add('O');
            add('C');
            add('B');
            add('M');
            add('W');
        }};
    }

    //endregion

    //region Inherited Methods

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printView() {
        var chatRecords = ViewModelState.getInstance().getChatRecords();
        //var players = String.join(", ", ViewModelState.getInstance().getNicknames().stream().filter(n -> !n.equals(ViewModelState.getInstance().getClientNickname())).toList());
        var players = String.join(", ", ViewModelState.getInstance().getNicknames().stream().map(n -> {
            if (n.equals(ViewModelState.getInstance().getClientNickname())) {
                n = n + " (YOU)";
            }
            return n;
        }).toList());

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                    CHAT                                          │");
        System.out.println("├──────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf( "│Nicks: %-75s│%n", players);
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");


        for (var chatRecord : chatRecords) {
            System.out.println("│ " + chatRecord);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                COMMANDS                                    │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (O) see opponent board -> see the board of a specific opponent           │");
        System.out.println("│ - (C) common board -> switch to the common board view                      │");
        System.out.println("│ - (B) board -> show your game board                                        │");
        System.out.println("│ - (M) message -> send message to EVERYONE                                  │");
        System.out.println("│ - (W) whisper -> send message to specific player                           │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
