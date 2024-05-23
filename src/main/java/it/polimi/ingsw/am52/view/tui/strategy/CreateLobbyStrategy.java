package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.view.tui.TuiController;

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

        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             CREATELOBBY                              │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┘");
        System.out.print("          │ - Enter your username: ");
        String username = scanner.nextLine();
        while (true) {
            System.out.printf("          │ - Enter the number of players in the lobby (MAX %d): ", GameManager.MAX_PLAYERS);
            int maxPlayers = scanner.nextInt();
            if (maxPlayers <= GameManager.MAX_PLAYERS) {
                networkResponse = TuiController.createLobby(username, maxPlayers);
                break;
            }
            System.out.println("          │ There are too many players in the lobby!");
        }

        //handle only good case, bad case is automatically handled by Strategy abstract class
        if (networkResponse != null && networkResponse.errorCode == 0) {
            System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
            System.out.println("          │                     Lobby created successfully!                      │");
            System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
        }

        return networkResponse;
    }

    //endregion
}
