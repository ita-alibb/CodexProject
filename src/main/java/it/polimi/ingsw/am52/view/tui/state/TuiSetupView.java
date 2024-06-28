package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to visualize the TUI view representing the setup phase of the game.
 * The setup phase is the phase before the start of the game, when we have to set up the table and our starting cards,
 * the starter card and the secret objective. Once all the players have chosen these elements, the game can start and the
 * view is automatically updated.
 */
public class TuiSetupView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiSetupView() {
        super(ViewType.SETUP);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
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

    //endregion

    //region Inherited Methods

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printView() {
        var starterCardId = ViewModelState.getInstance().getStarterCard();
        var objectiveIds = ViewModelState.getInstance().getPlayerObjectives();

        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "│ %-68s │%n", "Starter Card: " + starterCardId);
        if (ViewModelState.getInstance().getBoard().isEmpty()) {
            System.out.printf( "│ %-23s   %-43s│%n", "Front face:", "Back face:");
            CardIds.printTwoStarterCards(
                    new CardIds(starterCardId, 0),
                    new CardIds(starterCardId, 1)
            );
        } else {
            CardIds.printSingleStarterCard(
                    ViewModelState.getInstance().getBoard().get(new BoardSlot(0,0))
            );
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) {
            System.out.printf( "│ %-68s │%n", "Your secret objectives: ");
            System.out.printf( "│ %-23d   %-43d│%n", objectiveIds.getFirst(), objectiveIds.getLast());

            CardIds.printTwoObjectives(
                    new CardIds(objectiveIds.getFirst()),
                    new CardIds(objectiveIds.getLast()),
                    true
            );
        } else {
            System.out.printf( "│ %-68s │%n", "Your secret objective: " + ViewModelState.getInstance().getSecretObjective());

            CardIds.printSingleObjective(new CardIds(ViewModelState.getInstance().getSecretObjective()), true);
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getBoard().isEmpty()) { // if the card is not already in the board
            System.out.println("│ - (S) placeStarterCardFace -> place the selected face in the board   │");
        }
        if (ViewModelState.getInstance().getSecretObjective() == -1) { //if the objective is not chosen yet
            System.out.println("│ - (O) selectObjective -> select one of the two secretObjective       │");
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
