package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

public class JoinLobbyStrategy extends Strategy {
    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields to be instantiated
     */
    public JoinLobbyStrategy() {

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

        System.out.println("          ┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("          │                             JOINLOBBY                                │");
        System.out.println("          ├──────────────────────────────────────────────────────────────────────┘");
        System.out.print(  "          │ - Enter your username: ");
        String username = scanner.nextLine();
        int id = 0;
        while (true) {
            System.out.print(  "          │ - Enter the ID of the lobby you want to join: ");
            id = scanner.nextInt();
            if (ViewModelState.getInstance().getLobbies().containsKey(id)) {
                networkResponse = ClientConnection.joinLobby(username, id);
                break;
            }
            System.out.println("         │ The given lobby doesn't exist");
        }

        return networkResponse;
    }

    //endregion
}
