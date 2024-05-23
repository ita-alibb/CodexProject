package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.strategy.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * This class is used to read the input stream.
 */
public class InputReader implements Runnable {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BufferedReader br;

    /**
     * The Strategy adopted
     */
    private Strategy strategy;

    private InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
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

            input = br.readLine();

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
    public static void readLine() {
        executorService.execute(new InputReader());
    }

    private void executeCommand(String input) {

        if (!TuiPrinter.checkValidCommand(input.charAt(0))){
            // Command not valid, run new scanner thread and end current thread
            readLine();
            return;
        }

        // TODO: COMMANDS can be registered with multiple reads (one read for J, one for nickname asking for it, one for id asking for it) here can be useful a strategy pattern in this case
        switch (input.charAt(0)) {
            case 'J': this.setStrategy(new JoinLobbyStrategy()); break;
            case 'C': this.setStrategy(new CreateLobbyStrategy()); break;
            case 'R': this.setStrategy(new ReloadLobbyStrategy()); break;
            case 'L': this.setStrategy(new LeaveLobbyStrategy()); break;
        }
        this.execute();

        readLine();
    }

    /**
     * The method to set the new strategy to execute the right command
     * @param strategy  The new strategy
     */
    private void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Execute the right method, using the Strategy Pattern
     */
    private void execute() {
        this.strategy.execute();
    }
}
