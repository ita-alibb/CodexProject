package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiCommonBoardView extends TuiView {
    public TuiCommonBoardView() {
        super(ViewType.COMMON_BOARD);
    }

    public static List<Character> getAvailableCommands() {
        ArrayList<Character> available = new ArrayList<>(){{
            add('B');
            add('P');
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

    @Override
    protected void printView() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "│ Current turn:%3d                          %32s │%n", ViewModelState.getInstance().getTurn(), "Current player: " + ViewModelState.getInstance().getCurrentPlayer());
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf( "│ %-74s │%n", "Resource card deck: " + ViewModelState.getInstance().getResourceDeck());
        System.out.printf( "│ %-74s │%n", "Gold card deck: " + ViewModelState.getInstance().getGoldDeck());
        System.out.printf( "│ %-74s │%n", "Visible resource cards: ");
        CardIds.printVisibleResourceCards();
        System.out.printf( "│ %-74s │%n", "Visible gold cards: ");
        CardIds.printVisibleGoldCards();
        System.out.printf( "│ %-74s │%n", "Common objectives: ");
        CardIds.printTwoObjectives(
                new CardIds(ViewModelState.getInstance().getCommonObjectives().getFirst()),
                new CardIds(ViewModelState.getInstance().getCommonObjectives().getLast())
        );
        System.out.printf( "│ %-74s │%n", "Scores: " + ViewModelState.getInstance().getScoreboard().toString());
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
    }

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
        } else {
            System.out.println("│ - (L) leave_lobby -> leave lobby                                     │");
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}
