package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

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
        boolean cardExists = false;
        DrawType deck;
        int cardId = -1;

        System.out.println(    "          ┌──────────────────────────────────────────────────────────────────────┐");

        while (true) {
            System.out.println("          │ - Enter the deck you want to take from (0 = Resource, 1 = Gold): ");
            deck = DrawType.fromInteger(scanner.nextInt());
            if (deck != null) {
                break;
            }
            System.out.println("          │ The given deck is not available!");
        }

        while (!cardExists) {
            System.out.println("          │ - Enter the ID of the card you want to take: ");
            cardId = scanner.nextInt();
            switch (deck) {
                case RESOURCE -> {
                    if (ViewModelState.getInstance().getVisibleResourceCards().contains(cardId)) {
                        cardExists = true;
                    }
                    else {
                        System.out.println("          │ The given id is not available!");
                    }
                }
                case GOLD -> {
                    if (ViewModelState.getInstance().getVisibleGoldCards().contains(cardId)) {
                        cardExists = true;
                    }
                    else {
                        System.out.println("          │ The given id is not available!");
                    }
                }
            }
        }

        networkResponse = ClientConnection.takeCard(cardId, deck);

        //handle only good cases, bad case is automatically handled by Strategy abstract class
        if (networkResponse != null && networkResponse.errorCode == 0) {
            System.out.println("          ├──────────────────────────────────────────────────────────────────────┐");
            System.out.println("          │                              Card taken!                             │");
            System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
        }

        return networkResponse;
    }

    //endregion
}
