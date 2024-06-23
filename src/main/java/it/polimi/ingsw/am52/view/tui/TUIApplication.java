package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.EventType;

import java.util.Objects;

import static it.polimi.ingsw.am52.view.tui.InputReader.readLine;

/**
 * The main application for the TUI, which can start the TUI in rmi or tcp connection mode.
 */
public class TUIApplication {
    public static void main(String[] args) {
        try {
            ClientConnection.setConnection(!Objects.equals(args[0], "RMI"));

            // First call to init model
            ClientConnection.getLobbyList();

            // Print View
            TuiPrinter.getInstance().update(EventType.LIST_LOBBY);

            // Start Listening thread
            readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
