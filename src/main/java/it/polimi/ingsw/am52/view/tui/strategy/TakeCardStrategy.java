package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

/**
 * The class from Strategy to implement the behaviour to take one of the visible cards
 */
public class TakeCardStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public TakeCardStrategy() {

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

        System.out.print("- Enter the deck you want to take from (0 = Resource, 1 = Gold): ");
        DrawType deck = DrawType.fromInteger(scanner.nextInt());

        if (deck == null) {
            return new ResponseStatus(ViewModelState.getInstance().getPhase(), 1, "Invalid deck");
        }

        System.out.print("- Enter the ID of the card you want to take: ");
        int cardId = scanner.nextInt();

        networkResponse = ClientConnection.takeCard(cardId, deck);

        return networkResponse;
    }

    //endregion
}
