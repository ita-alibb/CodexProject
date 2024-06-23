package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;

import java.util.Scanner;

/**
 * The class from Strategy to implement the behaviour to draw a card
 */
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

        System.out.print("- Enter the deck you want to draw from (0 = Resource, 1 = Gold): ");
        DrawType deck = DrawType.fromInteger(scanner.nextInt());

        networkResponse = ClientConnection.drawCard(deck);

        return networkResponse;
    }

    //endregion
}
