package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.model.game.GameLobby;

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
    private final List<User> userList = new ArrayList<>();

    /**
     * List of all GameLobby that are currently open in the Server.
     */
    private final List<GameLobby> lobbyList = new ArrayList<>();

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

    /**
     * Method to log in to the server, if User is present it is reused, otherwise it is created
     * @param username The username
     * @param password The password
     * @return Boolean that indicates whether the login was successful
     */
    public boolean login(String username, String password){
        var user = userList.stream().filter(u -> Objects.equals(u.getUsername(), username)).findFirst();
        if (user.isPresent()){
            if (user.get().login(password)){
                user.get().setConnected(true);

                // TODO: connection initialize?
                return true;
            }

            return false;
        }

        userList.add(new User(username,password));
        return true;
    }

    /**
     * Method to log out of the server
     * @param username The username
     * @return Boolean that indicates whether the logout was successful
     */
    public boolean logout(String username){
        var user = userList.stream().filter(u -> Objects.equals(u.getUsername(), username)).findFirst();
        if (user.isPresent()){
            user.get().setConnected(false);
            return true;
        }
        return false;
    }

    /**
     * Method to get a User that is connected to the server
     * @param username The username
     * @return The Optional of a user
     */
    public Optional<User> getUser(String username){
        return userList.stream().filter(u -> Objects.equals(u.getUsername(), username) && u.isConnected()).findFirst();
    }

    /**
     * Method to open a lobby.
     *
     * @param username the username of the user who wants to start the game
     * @param maxPlayer the max player chosen for the new lobby
     */
    public void openLobby(String username, int maxPlayer) {
        if (this.userList.stream().noneMatch(u -> Objects.equals(u.getUsername(), username))) {
            // Fail Response, the username is not correct. TODO: How to send Responses?
            return;
        }

        // TODO: IN GENERAL ADD ALL throws in interface and methods that can throw exception and then handle them in the controller
        this.lobbyList.add(new GameLobby( ++maxId, maxPlayer)); //TODO: try catch with exception handling, need to discuss how to handle exception (log + error response?)

        this.joinLobby(maxId, username);

        // TODO: How to return the response?
        return;
    }

    /**
     * Method to join a lobby.
     *
     * @param gameLobbyId the GameLobby id to join
     * @param username the username of the user who wants to start the game
     *
     */
    public void joinLobby(int gameLobbyId, String username) {
        var lobby = this.lobbyList.stream().filter(l -> l.getId() == gameLobbyId).findFirst();
        var user = this.userList.stream().filter(u -> Objects.equals(u.getUsername(), username)).findFirst();

        if (user.isEmpty() || lobby.isEmpty())
        {
            // Fail Response, the username is not correct. TODO: How to send Responses?
            return;
        }

        user.get().setLastGameLobbyId(gameLobbyId);

        lobby.get().addPlayer(username);

        if (lobby.get().isFull()){
            this.startGame(lobby.get());
        }

        // TODO: How to return the response?
        return;
    }

    /**
     * Method to join a lobby.
     *
     * @param username the username of the user who wants to start the game
     *
     */
    public void leaveLobby(String username) {
        var user = this.userList.stream().filter(u -> Objects.equals(u.getUsername(), username)).findFirst();

        if (user.isEmpty())
        {
            // Fail Response, the username is not correct. TODO: How to send Responses?
            return;
        }

        var lobbyId = user.get().getLastGameLobbyId();

        if (lobbyId.isPresent()){
            var lobby = this.lobbyList.stream().filter(l -> l.getId() == lobbyId.get()).findFirst();

            if (lobby.isPresent() && !lobby.get().removePlayer(username))
            {
                // Exception? TODO: Response?
            }

            user.get().resetLastGameLobbyId();
        }

        // TODO: How to return the response?
        return;
    }

    /**
     * This method starts a game:
     * Delete the GameLobby from the available list
     * Init the GameController
     * Notify all user by in the lobby TODO: HOW? Change the controller bound to GameController instead of ServerController?
     */
    private void startGame(GameLobby lobby){
        this.lobbyList.remove(lobby);

        var gameController = new GameController(this, lobby);

        this.gameControllerList.add(gameController);

        // Notify the clients and Response
        return;
    }

    /**
     * This method ends a game, it is called by a GameController passing itself:
     * Delete the GameController from the available list
     * Dispose(?) the GameController
     * Notify all user by in the lobby TODO: HOW? Change the controller bound to GameController instead of ServerController?
     */
    public void endGame(GameController gameController){
        this.gameControllerList.remove(gameController);

        // Notify the clients and Response
        return;
    }
}
