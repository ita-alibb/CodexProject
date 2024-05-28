package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

public class PlaceCardStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public PlaceCardStrategy() {

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
        int cardId;
        int face;
        int h, v;

        System.out.println(    "          ┌──────────────────────────────────────────────────────────────────────┐");

        while (true) {
            System.out.println("          │ - Enter the ID of the card from your hand you want to place: ");
            if (ViewModelState.getInstance().getPlayerHand().contains(cardId = scanner.nextInt())) {
                break;
            }
            System.out.println("          │ The given id is not available!");
        }
        while (true) {
            System.out.println("          │ - Enter the face of the card you want to place (0 = front, 1 = back): ");
            face = scanner.nextInt();
            if (face == 0 || face == 1) {
                break;
            }
            System.out.println("          │ The given face is not available!");
        }
        System.out.println("          │ - Enter the horizontal position where you want to place the card: ");
        h = scanner.nextInt();
        System.out.println("          │ - Enter the vertical position where you want to place the card: ");
        v = scanner.nextInt();

        networkResponse = ClientConnection.placeCard(cardId, CardSide.fromInteger(face), new BoardSlot(h, v));

        //handle only good cases, bad case is automatically handled by Strategy abstract class
        if (networkResponse != null && networkResponse.errorCode == 0) {
            System.out.println("          ├──────────────────────────────────────────────────────────────────────┐");
            System.out.println("          │                              Card placed!                            │");
            System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
        }

        return networkResponse;
    }

    //endregion
}
