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

        System.out.println(    "          ┌──────────────────────────────────────────────────────────────────────┐");

        //TODO : Delete all the while loops
        while (true) {
            System.out.println("          │ - Enter the ID of the objective you want to use: ");
            int cardId = scanner.nextInt();
            if (ViewModelState.getInstance().getPlayerObjectives().contains(cardId)) {
                networkResponse = ClientConnection.selectObjective(cardId);
                break;
            }
            System.out.println("          │ The given id is not available!");
        }

        return networkResponse;
    }

    //endregion
}
