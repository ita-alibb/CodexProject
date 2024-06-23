package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

/**
 * The class from Strategy to implement the behaviour to place the starter card
 */
public class PlaceStarterCardStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public PlaceStarterCardStrategy() {

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

        System.out.print("- Enter the face of the starter card (0 = front, 1 = back): ");
        int face = scanner.nextInt();

        networkResponse = ClientConnection.placeStarterCard(ViewModelState.getInstance().getStarterCard(), CardSide.fromInteger(face));

        return networkResponse;
    }

    //endregion
}
