package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;

import java.util.Scanner;

public class ChatStrategy extends Strategy {
    //region Constructor
    private final boolean isWhisper;

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public ChatStrategy(boolean isWhisper) {
        this.isWhisper = isWhisper;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        ResponseStatus networkResponse;
        Scanner scanner = new Scanner(System.in);
        String message;
        String recipient = "";

        System.out.print("> Message: ");
        message = scanner.nextLine();

        if (isWhisper) {
            System.out.print("> Whisper to (enter nickname): ");
            recipient = scanner.nextLine();
        }

        networkResponse = ClientConnection.chat(message, recipient);

        return networkResponse;
    }
    //endregion
}