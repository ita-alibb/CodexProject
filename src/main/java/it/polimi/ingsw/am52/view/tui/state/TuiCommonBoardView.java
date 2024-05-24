package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiCommonBoardView extends TuiView {
    public TuiCommonBoardView() {
        super(ViewType.COMMON_BOARD);
    }
    public static List<Character> getAvailableCommands() {
        return new ArrayList<>(){{
            add('B');
            add('P');
            add('D');
            add('T');
        }};
    }

    @Override
    protected void printView() {
        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "          │ %-74s │%n", "Resource card deck: " + ViewModelState.getInstance().getResourceDeck());
        System.out.printf( "          │ %-74s │%n", "Gold card deck: " + ViewModelState.getInstance().getGoldDeck());
        System.out.printf( "          │ %-74s │%n", "Resource card deck: " + ViewModelState.getInstance().getResourceDeck());
        System.out.printf( "          │ %-74s │%n", "Visible gold cards: " + ViewModelState.getInstance().getVisibleGoldCards());
        System.out.printf( "          │ %-74s │%n", "Visible resource cards: " + ViewModelState.getInstance().getVisibleResourceCards());
        System.out.printf( "          │ %-74s │%n", "Scores: " + ViewModelState.getInstance().getScoreboard().toString());
        System.out.printf( "          │ %-74s │%n", "Phase: " + ViewModelState.getInstance().getPhase());
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands() {
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("          │ - (B) board -> show your game board                                  │");
        System.out.println("          │ - (O) other_board -> show the game board of another player           │");
        System.out.println("          │ - (D) draw_card -> draw a card from a chosen deck                    │");
        System.out.println("          │ - (T) take_card -> take a card from the one chosen                   │");
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }
}
