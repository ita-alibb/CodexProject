package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.view.tui.state.ViewType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This class is used to read the input stream.
 */
public class InputReader implements Runnable {
    private final BufferedReader reader;

    private final List<Character> validCommands;

    public InputReader(List<Character> validCommands) {
        reader = new BufferedReader(new InputStreamReader(System.in));
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
            do {
                reader.reset();
                input = reader.readLine();
            } while (this.checkValidCommand(input));
            reader.close();

            // TODO: MAYBE IMPLEMENT A COMMAND PATTERN
            this.executeCommand(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
