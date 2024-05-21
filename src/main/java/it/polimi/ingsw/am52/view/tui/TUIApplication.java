package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.network.client.Connection;

import java.util.Objects;

public class TUIApplication {
    public static void main(String[] args) {
        Connection connection;
        try {
            if (Objects.equals(args[0], "RMI")) {
               TuiController.setConnection(false);

            } else {
                TuiController.setConnection(true);
            }

            TuiPrinter.getInstance().update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
