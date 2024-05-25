package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiBoardView extends TuiView {
    public TuiBoardView() {
        super(ViewType.BOARD);
    }

    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>(){{
            add('O');
            add('C');
        }};
        if (ViewModelState.getInstance().getPhase() == GamePhase.PLACING) {
            available.add('P');
        }

        return available;
    }

    @Override
    protected void printView() {
        var board = ViewModelState.getInstance().getBoard();
        var playerHand = ViewModelState.getInstance().getPlayerHand();
        var secretObjectiveId = ViewModelState.getInstance().getSecretObjective();

        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "          │ %-74s │%n", "Board: " + board);
        if (ViewModelState.getInstance().isClientView()) {
            System.out.printf( "          │ %-74s │%n", "Your hand: " + playerHand.getFirst() + " " + playerHand.get(1) + " " + playerHand.getLast());
            System.out.printf( "          │ %-74s │%n", "Your secret objective: " + secretObjectiveId);
        }
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands() {
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getPhase() == GamePhase.PLACING && ViewModelState.getInstance().isClientView() && ViewModelState.getInstance().isClientTurn()){
            System.out.println("          │ - (P) place-card -> place a card from your hand in an available space│");
        }
        System.out.println("          │ - (O) see opponent board -> see the board of a specific opponent     │");
        System.out.println("          │ - (C) common board -> switch to the common board view                │");
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }
}
