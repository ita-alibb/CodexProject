package it.polimi.ingsw.am52.view.tui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class is used to read the input stream.
 */
public class InputReader implements Runnable {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BufferedReader br;

    private final List<Character> validCommands;

    private InputReader(List<Character> validCommands) {
        br = new BufferedReader(new InputStreamReader(System.in));
        this.validCommands = validCommands;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            //TODO: verify ON INPUT
            String input;
            // mark the current position in the stream
            br.mark(1);
            // wait until there is data to complete a readLine()
            while (!br.ready()) {
                Thread.sleep(200);
            }
            do {
                input = br.readLine();
            } while (!this.checkValidCommand(input));

            // reset the stream to the marked position
            br.reset();

            this.executeCommand(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method used to handle only one thread in Stream.in
     */
    public static void readLine(List<Character> validCommands) {
        executorService.submit(new InputReader(validCommands));
    }

    private void executeCommand(String input) {
        // TODO: COMMANDS can be registered with multiple reads (one read for J, one for nickname asking for it, one for id asking for it) here can be useful a strategy pattern in this case
        switch (input.charAt(0)) {
            case 'J': TuiController.joinLobby(input.substring(4), (int) (input.charAt(2) - '0')); break;
            case 'C': TuiController.createLobby(input.substring(4), (int) (input.charAt(2) - '0')); break;
            case 'R': TuiController.getLobbyList(); break;
        }
    }

    private boolean checkValidCommand(String input) {
        return validCommands.contains(input.charAt(0));
    }
}
