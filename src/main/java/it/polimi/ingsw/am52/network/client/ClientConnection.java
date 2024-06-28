package it.polimi.ingsw.am52.network.client;

import it.polimi.ingsw.am52.json.dto.DrawType;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.settings.NetworkMode;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.io.IOException;

/**
 * To the client the connection is now a Singleton.
 * One can access the connection from everywhere in Client and be sure that the connection is always the same
 * The INSTANCE is initialized only on startup of Client and can be called only once per Application
 */
public class ClientConnection {
    private static Connection INSTANCE;

    /**
     * The constructor of the class
     */
    public ClientConnection() {
    }

    /**
     * Set the connection to the server
     * @param serverIp          The IP address of the server
     * @param port              The port to communicate
     * @param type              The type of Network protocol
     * @throws IOException      The connection has already been instantiated
     */
    public static void setConnection(String serverIp, int port, NetworkMode type) throws IOException {
        if (INSTANCE != null) {
            throw new IllegalArgumentException ("The connection has already been initialized");
        }

        INSTANCE = switch (type) {
            case RMI -> new ConnectionRMI(serverIp, port);
            case SOCKET -> new ConnectionTCP(serverIp, port);
        };

        // If the connection is of type TCP, start listening on a new thread.
        if (type == NetworkMode.SOCKET) {
            new Thread((ConnectionTCP)INSTANCE).start();
        }
    }

    // region STATIC (singleton pattern) Methods called by the view to update the ViewModel

    /**
     * @return  The existing lobbies in the server
     */
    public static ResponseStatus getLobbyList() {
        try {
            var result = INSTANCE.listLobby();
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateLobbyList(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * The method to create a lobby
     * @param nickname      The nickname of the player who creates the lobby
     * @param maxPlayers    The max number of player
     * @return              The response from the server
     */
    public static ResponseStatus createLobby(String nickname, int maxPlayers) {
        try {
            var result = INSTANCE.createLobby(new CreateLobbyData(nickname, maxPlayers));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().setClientNickname(nickname);
                ViewModelState.getInstance().updateJoinLobby(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * The method to join an existing lobby
     * @param nickname      The nickname of the player who joins the lobby
     * @param lobbyId       The ID of the lobby
     * @return              The response from the server
     */
    public static ResponseStatus joinLobby(String nickname, int lobbyId) {
        try {
            var result = INSTANCE.joinLobby(new JoinLobbyData(nickname, lobbyId));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().setClientNickname(nickname);
                ViewModelState.getInstance().updateJoinLobby(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to leave a lobby
     * @return      The response from the server
     */
    public static ResponseStatus leaveLobby() {
        try  {
            var result = INSTANCE.leaveGame();
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateLeaveGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to initialize a game
     * @return      The response from the server
     */
    public static ResponseStatus initGame() {
        try  {
            var result = INSTANCE.initGame();
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateInitGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to end a started game
     * @return      The response from the server
     */
    public static ResponseStatus endGame() {
        try  {
            var result = INSTANCE.endGame();
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateEndGame(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to select an objective card
     * @param objectiveId       The ID of the objective card
     * @return                  The response from the server
     */
    public static ResponseStatus selectObjective(int objectiveId) {
        try  {
            var result = INSTANCE.selectObjective(new SelectObjectiveData(objectiveId));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateSelectObjective(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to select a starter card and place it on the board
     * @param cardId        The ID of the starter card
     * @param visibleFace   The visible face of the card
     * @return              The response from the server
     */
    public static ResponseStatus placeStarterCard(int cardId, CardSide visibleFace) {
        try  {
            var result = INSTANCE.placeStarterCard(new PlaceStarterCardData(cardId, visibleFace.toInteger()));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updatePlaceStarterCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to place a card in a certain position
     * @param cardId            The ID of the card to be placed
     * @param visibleFace       The visible face of the card
     * @param position          The position where this card is going to be placed
     * @return                  The response from the server
     */
    public static ResponseStatus placeCard(int cardId, CardSide visibleFace, BoardSlot position) {
        try  {
            var result = INSTANCE.placeCard(new PlaceCardData(cardId, visibleFace.toInteger(), position));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updatePlaceCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to draw a card
     * @param drawType      The deck from which the player draws the card
     * @return              The response from the server
     */
    public static ResponseStatus drawCard(DrawType drawType) {
        try  {
            var result = INSTANCE.drawCard(new DrawCardData(drawType.toInteger()));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateDrawCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * Method to take a visible card
     * @param cardId        The ID of the taken card
     * @param drawType      The type of the card
     * @return              The response from the server
     */
    public static ResponseStatus takeCard(int cardId, DrawType drawType) {
        try {
            var result = INSTANCE.takeCard(new TakeCardData(cardId, drawType.toInteger()));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateTakeCard(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }

    /**
     * The method to communicate with the Server
     * @param message   The message sent
     * @param recipient The recipient
     * @return          The response from the server
     */
    public static ResponseStatus chat(String message, String recipient) {
        try {
            var result = INSTANCE.chat(new ChatData(ViewModelState.getInstance().getClientNickname(), message, recipient));
            if (result.getStatus().getErrorCode() == 0) {
                ViewModelState.getInstance().updateChat(result);
            }

            //if the call is not correct the viewModelState is not edited and the caller will handle the bad response
            return result.getStatus();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(0);
            return null;
        }
    }
    // endregion
}
