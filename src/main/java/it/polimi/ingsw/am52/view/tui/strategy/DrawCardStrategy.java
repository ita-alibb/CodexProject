package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;

import java.util.Scanner;

public class DrawCardStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public DrawCardStrategy() {

    }

    //endregion

    //region Public Method

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        ResponseStatus networkResponse;
        Scanner scanner = new Scanner(System.in);
        int deck;

        System.out.println(    "          ┌──────────────────────────────────────────────────────────────────────┐");

        while (true) {
            System.out.println("          │ - Enter the deck you want to draw from (0 = Resource, 1 = Gold): ");
            deck = scanner.nextInt();

            if (DrawType.fromInteger(deck) != null) {
                break;
            }

            System.out.println("          │ The given deck is not available!");
        }

        networkResponse = ClientConnection.drawCard(DrawType.fromInteger(deck));

        //handle only good cases, bad case is automatically handled by Strategy abstract class
        if (networkResponse != null && networkResponse.getErrorCode() == 0) {
            System.out.println("          ├──────────────────────────────────────────────────────────────────────┐");
            System.out.println("          │                        Card drawn successfully!                      │");
            System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
        }

        return networkResponse;
    }

    //endregion
}
