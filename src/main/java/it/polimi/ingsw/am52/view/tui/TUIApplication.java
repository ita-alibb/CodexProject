package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.network.client.Connection;

import java.util.Objects;

import static it.polimi.ingsw.am52.view.tui.InputReader.readLine;

public class TUIApplication {
    public static void main(String[] args) {
        Connection connection;
        try {
            if (Objects.equals(args[0], "RMI")) {
               TuiController.setConnection(false);

            } else {
                TuiController.setConnection(true);
            }

            // First call to init model
            TuiController.getLobbyList();

            // Print View
            TuiPrinter.getInstance().update();

            // Start Listening thread
            readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
