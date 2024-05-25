package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiSetupView extends TuiView {
    public TuiSetupView() {
        super(ViewType.SETUP);
    }

    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>();

        if (ViewModelState.getInstance().getBoard().size() == 0) { // if the card is not already in the board
            available.add('S');
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) { //if the objective is not chosen yet
            available.add('O');
        }
        return available;
    }

    @Override
    protected void printView() {
        var starterCardId = ViewModelState.getInstance().getStarterCard();
        var objectiveIds = ViewModelState.getInstance().getPlayerObjectives();

        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "          │ %-74s │%n", "Starter Card: " + starterCardId);
        System.out.printf( "          │ %-74s │%n", "Your secret objectives: " + objectiveIds.getFirst() + " " + objectiveIds.getLast());
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands() {
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getBoard().size() == 0) { // if the card is not already in the board
            System.out.println("          │ - (S) placeStarterCardFace -> place the selected face in the board   │");
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) { //if the objective is not chosen yet
            System.out.println("          │ - (O) selectObjective -> select one of the two secretObjective       │");
        }
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }
}
