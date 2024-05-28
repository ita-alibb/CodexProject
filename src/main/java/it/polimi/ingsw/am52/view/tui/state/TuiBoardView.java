package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.view.viewModel.BoardMap;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
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
        printBoard();
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

    private void printBoard(){
        var board = ViewModelState.getInstance().getBoard();
        var availableSlots = ViewModelState.getInstance().getAvailableSlots();

        // get corners
        int maxH = board.keySet().stream().map(BoardSlot::getHoriz).max(Integer::compareTo).orElse(0);
        int maxY = board.keySet().stream().map(BoardSlot::getVert).max(Integer::compareTo).orElse(0);
        int minH = board.keySet().stream().map(BoardSlot::getHoriz).min(Integer::compareTo).orElse(0);
        int minY = board.keySet().stream().map(BoardSlot::getVert).min(Integer::compareTo).orElse(0);

        // for most high to most low card //rows
        for (int y = maxY; y > minY ; y--) {
            String[] row = new String[3];

            // for most left to most right card //columns
            for (int h = minH; h < maxH; h++) {
                //square loop
                var currentSlot = new BoardSlot(h, y);
                var cardIds = board.get(currentSlot);

                String[] column = new String[3];
                if (cardIds != null) {
                    column = cardIds.getCardAsArrayString(isCornerCovered(-1,1, currentSlot, board), isCornerCovered(1,1, currentSlot, board), isCornerCovered(1,-1, currentSlot, board), isCornerCovered(-1,-1, currentSlot, board));
                } else if (availableSlots.contains(currentSlot)) {
                    column = CardIds.getEmptyTemplate(currentSlot.getHoriz(), currentSlot.getVert());
                } else {
                    column = CardIds.getEmptyTemplate();
                }

                row[0] += column[0];
                row[1] += column[1];
                row[2] += column[2];
            }

            System.out.println(row[0]);
            System.out.println(row[1]);
            System.out.println(row[2]);
        }
    }

    private boolean isCornerCovered(int plusH, int plusV, BoardSlot currentSlot, BoardMap<BoardSlot, CardIds> board){
        return board.isFirst(currentSlot, new BoardSlot(currentSlot.getHoriz() + plusH, currentSlot.getVert() + plusV));
    }
}