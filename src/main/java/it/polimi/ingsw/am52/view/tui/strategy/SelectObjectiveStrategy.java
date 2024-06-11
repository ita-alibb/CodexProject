package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

public class SelectObjectiveStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public SelectObjectiveStrategy() {

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

        System.out.print("> Enter the ID of the objective you want to use: ");
        int cardId = scanner.nextInt();

        networkResponse = ClientConnection.selectObjective(cardId);

        return networkResponse;
    }

    //endregion
}
