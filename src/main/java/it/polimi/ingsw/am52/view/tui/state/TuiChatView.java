package it.polimi.ingsw.am52.view.tui.state;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.view.viewModel.BoardMap;
import it.polimi.ingsw.am52.view.viewModel.CardIds;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuiChatView extends TuiView {

    public TuiChatView() {
        super(ViewType.BOARD);
    }

    public static List<Character> getAvailableCommands() {
        var available = new ArrayList<Character>(){{
            add('O');
            add('C');
            add('B');
            add('M');
            add('W');
        }};

        return available;
    }

    @Override
    protected void printView() {
        var chatRecords = ViewModelState.getInstance().getChatRecords();
        var opponents = String.join(" ", ViewModelState.getInstance().getNicknames().stream().filter(n -> !n.equals(ViewModelState.getInstance().getClientNickname())).toList());

        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                 CHAT                                       │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│Nicks: %-69s│%n", opponents);
        System.out.println("├────────────────────────────────────────────────────────────────────────────┘");


        for (var chatRecord : chatRecords) {
            System.out.println("│ " + chatRecord);
        }
    }

    @Override
    protected void printCommands() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             COMMANDS                                 │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ - (O) see opponent board -> see the board of a specific opponent     │");
        System.out.println("│ - (C) common board -> switch to the common board view                │");
        System.out.println("│ - (B) board -> show your game board                                  │");
        System.out.println("│ - (M) message -> send message to EVERYONE                            │");
        System.out.println("│ - (W) whisper -> send message to specific player                     │");
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}
