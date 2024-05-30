package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

public class TuiSetupView extends TuiView {
    public TuiSetupView() {
        super(ViewType.SETUP);
    }

    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>();

        if (ViewModelState.getInstance().getBoard().isEmpty()) { // if the card is not already in the board
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
        var card = new CardIds(starterCardId, 0);
        var card1 = new CardIds(starterCardId, 1);
        card.loadStarterFace();
        card1.loadStarterFace();

        var cardList = card.getCardAsArrayString(false, false, false, false);
        var cardList1 = card1.getCardAsArrayString(false, false, false, false);


        System.out.println("          ┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "          │ %-74s │%n", "Starter Card: " + starterCardId);
        for (int i = 0; i < 7; i++) {
            System.out.println(cardList[i] + "   " + cardList1[i]);
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) {
            System.out.printf( "          │ %-74s │%n", "Your secret objectives: " + objectiveIds.getFirst() + " " + objectiveIds.getLast());
        } else {
            System.out.printf( "          │ %-74s │%n", "Your secret objective: " + ViewModelState.getInstance().getSecretObjective());
        }
        System.out.println("          └────────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    protected void printCommands() {
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             COMMANDS                                 │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getBoard().isEmpty()) { // if the card is not already in the board
            System.out.println("          │ - (S) placeStarterCardFace -> place the selected face in the board   │");
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) { //if the objective is not chosen yet
            System.out.println("          │ - (O) selectObjective -> select one of the two secretObjective       │");
        }
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }
}
