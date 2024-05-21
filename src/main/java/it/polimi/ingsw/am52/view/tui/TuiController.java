package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.network.client.Connection;
import it.polimi.ingsw.am52.network.client.ConnectionRMI;
import it.polimi.ingsw.am52.network.client.ConnectionTCP;
import it.polimi.ingsw.am52.network.server.rmi.ActionsRMI;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * To the client the connection is now a Singleton.
 * One can access the connection from everywhere in Client ( with {@link #getConnection() Get the client Connection}) and be sure that the connection is always the same
 * The INSTANCE is initialized (with  {@link #setConnection(boolean isTcp) Set the client Connection to be TCP or RMI}) only on startup of Client and can be called only once per Application
 */
public class TuiController {
    private static Connection INSTANCE;

    public TuiController() {
    }

    public static void setConnection(boolean isTcp) throws IOException {
        if (INSTANCE != null) {
            throw new IllegalArgumentException ("The connection has already been initialized");
        }

        if (isTcp) {
            INSTANCE = new ConnectionTCP();

            // Start connection thread
            new Thread((ConnectionTCP)INSTANCE).start();
        } else {
            INSTANCE = new ConnectionRMI();
        }
    }

    // region STATIC (singleton pattern) Methods called by the view to update the ViewModel
    public static void getLobbyList() {
        try {
            ViewModelState.getInstance().update(INSTANCE.listLobby());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createLobby(String nickname, int maxPlayers) {
        try {
            ViewModelState.getInstance().update(INSTANCE.createLobby(new CreateLobbyData(nickname, maxPlayers)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void joinLobby(String nickname, int lobbyId) {
        try {
            ViewModelState.getInstance().update(INSTANCE.joinLobby(new JoinLobbyData(nickname, lobbyId)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    // endregion

    //INVECE DI GETCONNECTION QUESTO SINGLETON IMPLEMENTA TUTTI I METODI DELLA CONNECTION (cosi' da qualsiasi parte possiamo chiamare ogni metodo) lui internamente
    //fa la chiamata con la sua INSTANCE di connection, prende il risultato della chiamata e la elabora (sappiamo gia' di che metodo si tratta) aggiornando la ViewModelState (singleton)
    // dopo averla aggiornata triggera il print del TuiPrinter (singleton) ed abbiamo aggiornato il terminale con i nuovi dati.

    // per i messaggi broadcast dentro questo singleton metodo update (che prende in input una baseResponse) ed a seconda del tipo aggiorna il view model. (questo stesso metodo si puo' usare anche con la risposta delle chiamate
}
