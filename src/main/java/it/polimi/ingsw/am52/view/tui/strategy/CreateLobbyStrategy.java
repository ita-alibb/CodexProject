package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.network.client.ClientConnection;

import java.util.Scanner;

public class CreateLobbyStrategy extends Strategy {

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields to be instantiated
     */
    public CreateLobbyStrategy() {}

    //endregion

    //region Public Method

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        ResponseStatus networkResponse;
        Scanner scanner = new Scanner(System.in);

        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                             CREATELOBBY                              │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┘");
        System.out.print("│ - Enter your username: ");
        String username = scanner.nextLine();

        System.out.printf("│ - Enter the number of players in the lobby (MAX %d): ", GameManager.MAX_PLAYERS);
        int maxPlayers = scanner.nextInt();

        networkResponse = ClientConnection.createLobby(username, maxPlayers);

        return networkResponse;
    }

    //endregion
}
