package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.view.tui.TuiController;

import java.util.Scanner;

public class CreateLobbyStrategy implements Strategy {

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
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             CREATELOBBY                              │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┘");
        System.out.print(  "          │ - Enter your username: ");
        String username = scanner.nextLine();
        while (true) {
            System.out.printf("          │ - Enter the number of players in the lobby (MAX %d): ", GameManager.MAX_PLAYERS);
            int maxPlayers = scanner.nextInt();
            if (maxPlayers <= GameManager.MAX_PLAYERS) {
                TuiController.createLobby(username, maxPlayers);
                break;
            }
            System.out.println("          │ There are too many players in the lobby!");
        }
        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                     Lobby created successfully!                      │");
        System.out.println("          └──────────────────────────────────────────────────────────────────────┘");
    }

    //endregion
}
