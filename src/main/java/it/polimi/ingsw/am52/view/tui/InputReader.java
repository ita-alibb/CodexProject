package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.strategy.*;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * This class is used to read the input stream.
 */
public class InputReader implements Runnable {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BufferedReader br;

    private InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            String input;
            System.out.print("> ");
            // mark the current position in the stream
            br.mark(1);
            // wait until there is data to complete a readLine()
            while (!(br.ready() || Thread.currentThread().isInterrupted())) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            input = br.readLine();

            // reset the stream to the marked position
            br.reset();

            this.executeCommand(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method used to handle only one thread in Stream.in.
     */
    public static void readLine() {
        executorService.execute(new InputReader());
    }

    public static void updateInputReaderOnBroadcast() {
        executorService.shutdownNow();
        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new InputReader());
    }

    /**
     * The method to set the Strategy and execute it.
     * @param input     The command given by the player
     */
    private void executeCommand(String input) {
        Strategy strategy = null;

        if (!TuiPrinter.checkValidCommand(input.toUpperCase().charAt(0))){
            // Command not valid, run new scanner thread and end current thread
            readLine();
            return;
        }

        switch (ViewModelState.getInstance().getViewTypeShown()) {
            case MENU : {
                strategy = switch (input.toUpperCase().charAt(0)) {
                    case 'J' -> new JoinLobbyStrategy();
                    case 'C' -> new CreateLobbyStrategy();
                    case 'R' -> new ReloadLobbyStrategy();
                    default -> strategy;
                };
                break;
            }
            case LOBBY: {
                if (input.toUpperCase().charAt(0) == 'L') {
                    strategy = new LeaveLobbyStrategy();
                }
                break;
            }
            case SETUP: {
                strategy = switch (input.toUpperCase().charAt(0)) {
                    case 'S' -> new PlaceStarterCardStrategy();
                    case 'O' -> new SelectObjectiveStrategy();
                    default -> strategy;
                };
                break;
            }
            case BOARD: {
                strategy = switch (input.toUpperCase().charAt(0)) {
                    case 'P' -> new PlaceCardStrategy();
                    case 'O' -> new ShowBoardStrategy(true);
                    case 'C' -> new ShowCommonBoardStrategy();
                    case 'M' -> new ShowChatStrategy();
                    case 'B' -> new ShowBoardStrategy(false);
                    default -> strategy;
                };
                break;
            }
            case COMMON_BOARD: {
                strategy = switch (input.toUpperCase().charAt(0)) {
                    case 'D' -> new DrawCardStrategy();
                    case 'T' -> new TakeCardStrategy();
                    case 'O' -> new ShowBoardStrategy(true);
                    case 'B' -> new ShowBoardStrategy(false);
                    case 'L' -> new LeaveLobbyStrategy();
                    case 'M' -> new ShowChatStrategy();
                    default -> strategy;
                };
                break;
            }
            case CHAT: {
                strategy = switch (input.toUpperCase().charAt(0)) {
                    case 'O' -> new ShowBoardStrategy(true);
                    case 'C' -> new ShowCommonBoardStrategy();
                    case 'B' -> new ShowBoardStrategy(false);
                    case 'M' -> new ChatStrategy(false);
                    case 'W' -> new ChatStrategy(true);
                    default -> strategy;
                };
            }
            case END: {
                if (input.toUpperCase().charAt(0) == 'L') {
                    strategy = new LeaveLobbyStrategy();
                }
                break;
            }
        }

        if (strategy != null) {
            strategy.execute();
        } else {
            System.out.println("Unknown command: " + input + " retry:");
        }

        readLine();
    }
}
