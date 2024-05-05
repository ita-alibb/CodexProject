package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.json.CreateLobbyData;
import it.polimi.ingsw.am52.json.JoinLobbyData;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.json.response.Response;
import it.polimi.ingsw.am52.json.response.Status;

import java.util.*;

/**
 * Class to handle the Server actions, it is instantiated only once
 */
public class ServerController {
    /**
     * The private instance of the ServerController
     */
    private static ServerController INSTANCE;

    /**
     * The max id, to generate id for lobby
     */
    private static int maxId = 0;

    /**
     * List of all User that have ever been registered to the Server.
     */
    private final List<ClientHandler> clientHandlers = new ArrayList<>();

    /**
     * List of all open games.
     */
    private final List<GameController> gameControllerList = new ArrayList<>();

    /**
     * Constructor can't be used outside the class
     */
    private ServerController(){
    }

    /**
     * The method used to retrieve the ServerController
     * @return ServerController instance
     */
    public static synchronized ServerController getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ServerController();
        }
        return INSTANCE;
    }

    // region Endpoints

    /**
     * Method to open a lobby.
     *
     */
    public Response<Integer> createLobby(int clientId, CreateLobbyData createLobbyData) {
        var handler = this.getHandler(clientId);

        if (handler.isEmpty()) {
            return new Response<Integer>(new Status(), 1,0);
        }

        var id = this.getUniqueId();

        var gameController = new GameController(new GameLobby(id, createLobbyData.getNumPlayers()));

        this.gameControllerList.add(gameController);

        var user = new User(createLobbyData.getNickname(), handler.get());

        gameController.joinLobby(clientId, user);
        //tempo return
        return new Response<Integer>(new Status(), 0,id);
        // TODO: IN GENERAL ADD ALL "throws" in interface and methods that can throw exception and then handle them in the controller
    }

    /**
     * Method to join a lobby.
     *
     * @param joinLobbyData the joinLobbyRequest's Data
     *
     */
    public Response<String> joinLobby(int clientId, JoinLobbyData joinLobbyData) {
        var gameController = this.getGameController(joinLobbyData.getLobbyId());
        var handler = this.getHandler(clientId);

        if (handler.isEmpty() || gameController.isEmpty())
        {
            // Fail Response, the username is not correct. TODO: How to send Responses?
            return new Response<String>(new Status(), 1,"Qui ci sara' la risposta Errore");
        }

        var user = new User(joinLobbyData.getNickname(), handler.get());

        return gameController.get().joinLobby(clientId, user);
    }

    // endregion

    // region Utilities

    /**
     * Method to register the client handler in the server
     * @param handler The handler
     */
    public synchronized void addHandler(ClientHandler handler){
        clientHandlers.add(handler);
    }

    /**
     * Method to un-register the client handler in the server
     * @param handler The handler
     */
    public void disconnect(ClientHandler handler) {
        clientHandlers.remove(handler);
    }

    /**
     * Method to get a Handler that is connected to the server
     * @param clientId The clientId given by the server
     * @return The Optional of a user
     */
    public Optional<ClientHandler> getHandler(int clientId) {
        return this.clientHandlers.stream().filter(h -> Objects.equals(h.getClientId(), clientId)).findFirst();
    }

    /**
     * Method to get a Handler that is connected to the server
     * @param lobbyId The lobbyId which is the same as the GameControllerId given by the server
     * @return The Optional of a user
     */
    public Optional<GameController> getGameController(int lobbyId) {
        return this.gameControllerList.stream().filter(c -> c.getId() == lobbyId).findFirst();
    }

    /**
     * Method used to remove the reference to a GameController, it deletes the Game
     * @param gameController the game controller to delete
     */
    public void deleteGame(GameController gameController) {
        this.gameControllerList.remove(gameController);
    }

    /**
     * Method to get an unique id
     */
    private synchronized int getUniqueId() {
        return ++maxId;
    }
    // endregion
}
