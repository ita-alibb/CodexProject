package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.view.viewModel.BoardMap;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class to visualize the TUI view representing the personal board of a player of the current game.
 * The board contains all the cards placed by a player and also all the available slots where a card can be placed,
 * with the coordinates of that slot in it. If the visualized board is the one of this client, also the player's hand and
 * the secret objective are visualized.
 */
public class TuiBoardView extends TuiView {

    //region Constructor

    /**
     * The constructor of the class
     */
    public TuiBoardView() {
        super(ViewType.BOARD);
    }

    //endregion

    //region Public Method

    /**
     * Method to create a list of all possible commands the player can perform
     * @return      The list of possible commands
     */
    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>(){{
            add('O');
            add('C');
            add('M');
        }};
        if (ViewModelState.getInstance().getPhase() == GamePhase.PLACING && ViewModelState.getInstance().isClientView() && ViewModelState.getInstance().isClientTurn()) {
            available.add('P');
        }

        if (!ViewModelState.getInstance().isClientView()) {
            available.add('B');
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
        var secretObjectiveId = ViewModelState.getInstance().getSecretObjective();

        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        if (ViewModelState.getInstance().getViewTypeNickname().equals(ViewModelState.getInstance().getClientNickname())) {
            System.out.println("│                                 Your Board                                 │");
        } else {
            System.out.printf("│                     %16s's board                             │%n", ViewModelState.getInstance().getViewTypeNickname());
        }
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");

        printBoard();
        if (ViewModelState.getInstance().isClientView()) {
            printHand();
            System.out.printf("│ %-74s │%n", "Your secret objective: " + secretObjectiveId);
            CardIds.printSingleObjective(new CardIds(ViewModelState.getInstance().getSecretObjective()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        if (ViewModelState.getInstance().getPhase() == GamePhase.PLACING && ViewModelState.getInstance().isClientView() && ViewModelState.getInstance().isClientTurn()){
            System.out.println("│ - (P) place-card -> place a card from your hand in an available space│");
        }
        if (!ViewModelState.getInstance().isClientView()) {
            System.out.println("│ - (B) board -> show your game board                                  │");
        }
        System.out.println("│ - (O) see opponent board -> see the board of a specific opponent     │");
        System.out.println("│ - (C) common board -> switch to the common board view                │");
        System.out.println("│ - (M) message view -> switch to the chat view                        │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion

    //region Private Method

    /**
     * Method to create and visualize the cards and the available slots of the board of the player we are currently inspecting.
     * <p>
     *      The board in the view is organized like a matrix n*m, where:
     *      <ul>
     *          <li>n is the length of the matrix</li>
     *          <li>m is the height of the matrix</li>
     *      </ul>
     * <p>
     *     The cards are formatted and printed thanks to a template, which can be found in the class {@link it.polimi.ingsw.am52.view.viewModel.CardIds}.
     */
    private void printBoard(){
        var board = ViewModelState.getInstance().getBoard();
        List<BoardSlot> availableSlots = new ArrayList<>();

        if (ViewModelState.getInstance().getViewTypeNickname().equals(ViewModelState.getInstance().getClientNickname())) {
            availableSlots = ViewModelState.getInstance().getAvailableSlots();
        }

        //get corners
        int maxH = Math.max(
                board.keySet().stream().map(BoardSlot::getHoriz).max(Integer::compareTo).orElse(0),
                availableSlots.stream().map(BoardSlot::getHoriz).max(Integer::compareTo).orElse(0)
        );
        int maxV = Math.max(
                board.keySet().stream().map(BoardSlot::getVert).max(Integer::compareTo).orElse(0),
                availableSlots.stream().map(BoardSlot::getVert).max(Integer::compareTo).orElse(0)
        );
        int minH = Math.min(
                board.keySet().stream().map(BoardSlot::getHoriz).min(Integer::compareTo).orElse(0),
                availableSlots.stream().map(BoardSlot::getHoriz).min(Integer::compareTo).orElse(0)
        );
        int minV = Math.min(
                board.keySet().stream().map(BoardSlot::getVert).min(Integer::compareTo).orElse(0),
                availableSlots.stream().map(BoardSlot::getVert).min(Integer::compareTo).orElse(0)
        );

        // for most high to most low card //rows
        for (int y = maxV; y >= minV ; y--) {
            String[] row = new String[CardIds.TEMPLATE.length];
            Arrays.fill(row, "");
            // for most left to most right card //columns
            for (int h = minH; h <= maxH; h++) {
                String[] column = new String[CardIds.TEMPLATE.length];
                Arrays.fill(column, "");
                if ((Math.floorMod(h, 2) == Math.floorMod(y, 2))) {
                    //square loop
                    var currentSlot = new BoardSlot(h, y);
                    var cardIds = board.get(currentSlot);

                    if (cardIds != null) {
                        if (h == 0 && y == 0){
                            cardIds.loadStarterFace();
                        } else {
                            cardIds.loadFace();
                        }

                        column = cardIds.getCardAsArrayString(isCornerCovered(-1,1, currentSlot, board), isCornerCovered(1,1, currentSlot, board), isCornerCovered(1,-1, currentSlot, board), isCornerCovered(-1,-1, currentSlot, board));
                    } else if (availableSlots.contains(currentSlot)) {
                        column = CardIds.getEmptyTemplate(currentSlot.getHoriz(), currentSlot.getVert());
                    } else {
                        column = CardIds.getEmptyTemplate();
                    }
                } else {
                    column = CardIds.getEmptyTemplate();
                }

                for (int i = 0; i < row.length; i++) {
                    row[i] += column[i];
                }
            }

            for (String r : row) {
                System.out.println(r);
            }
        }
    }

    /**
     * The method to print the hand of the player
     */
    private void printHand() {
        var hand = ViewModelState.getInstance().getPlayerHand();

        System.out.print("│Your hand: ");
        for (int index : hand ){
            System.out.printf("%-20s ", index);
        }
        System.out.printf("│%n");

        var frontHand = hand.stream()
                .map(c -> new CardIds(c, 0))
                .toList();
        frontHand.forEach(CardIds::loadFace);
        var frontHandList = frontHand.stream()
                .map(c -> c.getCardAsArrayString(false, false, false, false))
                .toList();

        var backHand = hand.stream()
                .map(c -> new CardIds(c, 1))
                .toList();
        backHand.forEach(CardIds::loadFace);
        var backHandList = backHand.stream()
                .map(c -> c.getCardAsArrayString(false, false, false, false))
                .toList();

        System.out.printf("│ %-74s │%n", "Front:");
        for (int i = 0; i < CardIds.TEMPLATE.length; i++) {
            StringBuilder row = new StringBuilder();

            for (String[] card : frontHandList) {
                row.append(" ");
                row.append(card[i]);
                row.append(" ");
            }
            System.out.printf("│%s %n", row);
        }

        System.out.printf("│ %-74s │%n", "Back:");
        for (int i = 0; i < CardIds.TEMPLATE.length; i++) {
            StringBuilder row = new StringBuilder();

            for (String[] card : backHandList) {
                row.append(" ");
                row.append(card[i]);
                row.append(" ");
            }
            System.out.printf("│%s %n", row);
        }
    }

    /**
     * Method to check if a corner is covered or not
     * @param plusH             The number to add to the horizontal coordinate to check the correct corner of the card
     * @param plusV             The number to add to the vertical coordinate to check the correct corner of the card
     * @param currentSlot       The slot we are checking
     * @param board             The board of the player
     * @return                  True if the corner is covered, otherwise False
     */
    private boolean isCornerCovered(int plusH, int plusV, BoardSlot currentSlot, BoardMap<BoardSlot, CardIds> board){
        return board.isFirst(currentSlot, new BoardSlot(currentSlot.getHoriz() + plusH, currentSlot.getVert() + plusV));
    }

    //endregion
}
