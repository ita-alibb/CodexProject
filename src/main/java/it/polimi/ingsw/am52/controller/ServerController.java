package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.json.request.CreateLobbyData;
import it.polimi.ingsw.am52.json.request.JoinLobbyData;
import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.ListLobbyResponseData;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.network.server.ClientHandler;
import it.polimi.ingsw.am52.json.response.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

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
     * Method to get the list of lobbies
     * @return
     */
    public ListLobbyResponseData getLobbyList() {
        try{
            return new ListLobbyResponseData(new ResponseStatus(), this.getLobbies());
        } catch (Exception e) {
            return new ListLobbyResponseData(new ResponseStatus(503, "Error on createLobby: " + e.getMessage()));
        }
    }

    /**
     * Method to request to open a lobby
     * @param clientId the client who sent the request
     * @param createLobbyData the data sent by the client
     * @return the JoinLobbyResponseData
     */
    public JoinLobbyResponseData createLobby(int clientId, CreateLobbyData createLobbyData) {
        try {
            var handler = this.getHandler(clientId);

            if (handler.isEmpty()) {
                return new JoinLobbyResponseData(new ResponseStatus(404, "Client not found"));
            }

            var id = this.getUniqueId();

            var gameController = new GameController(new GameLobby(id, createLobbyData.getNumPlayers()));

            this.gameControllerList.add(gameController);

            var user = new User(createLobbyData.getNickname(), handler.get());

            return gameController.joinLobby(user);
        } catch (Exception e) {
            return new JoinLobbyResponseData(new ResponseStatus(503, "Error on createLobby: " + e.getMessage()));
        }
    }

    /**
     * Method to join a lobby.
     *
     * @param joinLobbyData the joinLobbyRequest's Data
     *
     */
    public JoinLobbyResponseData joinLobby(int clientId, JoinLobbyData joinLobbyData) {
        try {
            var gameController = this.getGameController(joinLobbyData.getLobbyId());

            if (gameController.isEmpty())
            {
                return new JoinLobbyResponseData(new ResponseStatus(404, "Game not found"));
            }

            var handler = this.getHandler(clientId);

            if (handler.isEmpty())
            {
                return new JoinLobbyResponseData(new ResponseStatus(404, "Client not found"));
            }


            var user = new User(joinLobbyData.getNickname(), handler.get());

            return gameController.get().joinLobby(user);
        } catch (Exception e) {
            return new JoinLobbyResponseData(new ResponseStatus(503, "Error on createLobby: " + e.getMessage()));
        }
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

    /**
     *
     * @return The hashmap for ListLobbies response
     */
    private synchronized Map<Integer,Integer> getLobbies() {
        return this.gameControllerList.stream()
                .collect(Collectors.toMap(GameController::getId, GameController::getFreeSpace));
    }
    // endregion
}
