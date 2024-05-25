package it.polimi.ingsw.am52.view.tui;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.cards.CardFace;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.network.client.Connection;
import it.polimi.ingsw.am52.network.client.ConnectionRMI;
import it.polimi.ingsw.am52.network.client.ConnectionTCP;
import it.polimi.ingsw.am52.network.server.rmi.ActionsRMI;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * To the client the connection is now a Singleton.
 * One can access the connection from everywhere in Client and be sure that the connection is always the same
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
    public static ResponseStatus getLobbyList() {
        try {
            var result = INSTANCE.listLobby();
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateLobbyList(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus createLobby(String nickname, int maxPlayers) {
        try {
            var result = INSTANCE.createLobby(new CreateLobbyData(nickname, maxPlayers));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateJoinLobby(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus joinLobby(String nickname, int lobbyId) {
        try {
            var result = INSTANCE.joinLobby(new JoinLobbyData(nickname, lobbyId));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().setClientNickname(nickname);
                ViewModelState.getInstance().updateJoinLobby(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus leaveLobby() {
        try  {
            var result = INSTANCE.leaveGame();
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateLeaveGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus initGame() {
        try  {
            var result = INSTANCE.initGame();
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateInitGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus endGame() {
        try  {
            var result = INSTANCE.endGame();
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateEndGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus selectObjective(int objectiveId) {
        try  {
            var result = INSTANCE.selectObjective(new SelectObjectiveData(objectiveId));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateSelectObjective(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus placeStarterCard(int cardId, CardSide visibleFace) {
        try  {
            var result = INSTANCE.placeStarterCard(new PlaceStarterCardData(cardId, visibleFace.toInteger()));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updatePlaceStarterCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus placeCard(int cardId, CardSide visibleFace, BoardSlot position) {
        try  {
            var result = INSTANCE.placeCard(new PlaceCardData(cardId, visibleFace.toInteger(), position));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updatePlaceCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus drawCard(DrawType drawType) {
        try  {
            var result = INSTANCE.drawCard(new DrawCardData(drawType.toInteger()));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateDrawCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }

    public static ResponseStatus takeCard(int cardId, DrawType drawType) {
        try  {
            var result = INSTANCE.takeCard(new TakeCardData(cardId, drawType.toInteger()));
            if (result.getStatus().errorCode == 0) {
                ViewModelState.getInstance().updateTakeCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            return new ResponseStatus(999, "Fatal exception");
        }
    }
    // endregion

    //INVECE DI GETCONNECTION QUESTO SINGLETON IMPLEMENTA TUTTI I METODI DELLA CONNECTION (cosi' da qualsiasi parte possiamo chiamare ogni metodo) lui internamente
    //fa la chiamata con la sua INSTANCE di connection, prende il risultato della chiamata e la elabora (sappiamo gia' di che metodo si tratta) aggiornando la ViewModelState (singleton)
    // dopo averla aggiornata triggera il print del TuiPrinter (singleton) ed abbiamo aggiornato il terminale con i nuovi dati.

    // per i messaggi broadcast dentro questo singleton metodo update (che prende in input una baseResponse) ed a seconda del tipo aggiorna il view model. (questo stesso metodo si puo' usare anche con la risposta delle chiamate
}
