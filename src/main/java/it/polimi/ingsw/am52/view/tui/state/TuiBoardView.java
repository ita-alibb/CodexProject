package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiBoardView extends TuiView {
    public TuiBoardView() {
        super(ViewType.BOARD);
    }

    public static List<Character> getAvailableCommands() {
        return new ArrayList<>(){{
            add('R');
            add('P');
            add('O');
        }};
    }

    @Override
    protected void printView() {
        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "          │ %-74s │%n", "Starter Card: " + ViewModelState.getInstance().getStarterCard());
        System.out.printf( "          │ %-74s │%n", "Your hand: " + ViewModelState.getInstance().getPlayerHand());
        System.out.printf( "          │ %-74s │%n", "Your secret objective: " + ViewModelState.getInstance().getPlayerObjectives());
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands() {
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("          │ - (R) return -> return to the common board                           │");
        System.out.println("          │ - (P) place-card -> place a card from your hand in an available space│");
        //TODO : add the implementation of the commands and add new commands available
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }
}
