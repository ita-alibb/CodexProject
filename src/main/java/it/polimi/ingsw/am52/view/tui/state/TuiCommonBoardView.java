package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to visualize the TUI view representing the common board of the current game.
 * In the common board, there are the following elements:
 * <ul>
 *     <li>Resource and Gold card decks</li>
 *     <li>Visible Resource and Gold cards</li>
 *     <li>Common objectives</li>
 *     <li>Scoreboard</li>
 * </ul>
 */
public class TuiCommonBoardView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiCommonBoardView() {
        super(ViewType.COMMON_BOARD);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands() {
        ArrayList<Character> available = new ArrayList<>(){{
            add('O');
            add('B');
            add('M');
        }};
        if (ViewModelState.getInstance().getPhase() == GamePhase.DRAWING) {
            available.add('D');
            available.add('T');
        }

        if (ViewModelState.getInstance().getPhase() == GamePhase.END) {
            available = new ArrayList<>(){{
                add('L');
            }};
        }

        return available;
    }

    //endregion

    //region Inherited Methods

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printView() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "│ Current turn:%3d                          %32s │%n", ViewModelState.getInstance().getTurn(), "Current player: " + ViewModelState.getInstance().getCurrentPlayer());
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
        System.out.printf( "│ %-75s%n", "Resource card deck: ");
        CardIds.printBack(ViewModelState.getInstance().getResourceDeckNextId());
        System.out.printf( "│ %-75s%n", "Gold card deck: ");
        CardIds.printBack(ViewModelState.getInstance().getGoldDeckNextId());
        System.out.printf( "│ %-75s%n", "Visible resource cards: ");
        CardIds.printVisibleResourceCards();
        System.out.printf( "│ %-75s%n", "Visible gold cards: ");
        CardIds.printVisibleGoldCards();
        System.out.printf( "│ %-75s%n", "Common objectives: ");
        CardIds.printTwoObjectives(
                new CardIds(ViewModelState.getInstance().getCommonObjectives().getFirst()),
                new CardIds(ViewModelState.getInstance().getCommonObjectives().getLast()),
                false
        );
        System.out.printf( "│ %-76s%n", "Scores: " + ViewModelState.getInstance().getScoreboard().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getPhase() == GamePhase.DRAWING && ViewModelState.getInstance().isClientTurn()) {
            System.out.println("│ - (D) draw_card -> draw a card from a chosen deck                    │");
            System.out.println("│ - (T) take_card -> take a card from the one chosen                   │");
        }

        if (ViewModelState.getInstance().getPhase() != GamePhase.END) {
            System.out.println("│ - (O) other_board -> show the game board of another player           │");
            System.out.println("│ - (B) board -> show your game board                                  │");
            System.out.println("│ - (M) message view -> switch to the chat view                        │");
        } else {
            System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}
