package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

/**
 * The class from Strategy to implement the behaviour to place a card
 */
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
        CardSide face;
        int h, v;

        System.out.print("- Enter the ID of the card from your hand you want to place: ");
        cardId = scanner.nextInt();

        System.out.print("- Enter the face of the card you want to place (0 = front, 1 = back): ");
        face = CardSide.fromInteger(scanner.nextInt());

        if (face == null) {
            return new ResponseStatus(ViewModelState.getInstance().getPhase(), 1, "Invalid face");
        }

        System.out.print("- Enter the horizontal position where you want to place the card: ");
        h = scanner.nextInt();
        System.out.print("- Enter the vertical position where you want to place the card: ");
        v = scanner.nextInt();

        networkResponse = ClientConnection.placeCard(cardId, face, new BoardSlot(h, v));

        return networkResponse;
    }

    //endregion
}
