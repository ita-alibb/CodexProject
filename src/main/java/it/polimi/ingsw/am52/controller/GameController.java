package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.json.response.JoinLobbyResponseData;
import it.polimi.ingsw.am52.json.response.LeaveGameResponseData;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.network.Sender;
import it.polimi.ingsw.am52.json.response.ResponseStatus;

import java.util.List;

/**
 * Class to control the Game
 * TODO: Maybe all methods sends and accepts specific class of response, that will be in network shared. like in REST API
 */
public class GameController {
    /**
     * The game Lobby, it is used to handle the current logged user
     */
    private final GameLobby lobby;

    /**
     * The GameManager, it controls all the Model
     */
    private GameManager game;

    /**
     * Constructor of the GameController
     * @param lobby The Lobby linked to this Game
     */
    public GameController(GameLobby lobby) {
        this.lobby = lobby;
    }

    // region Endpoints

    /**
     * The method to join the lobby
     * @param clientId the client who requested
     * @param user the user that joined
     */
    public JoinLobbyResponseData joinLobby(int clientId, User user) {
        if (!this.lobby.addPlayer(user)) {
            return new JoinLobbyResponseData(new ResponseStatus(503, "Cannot add Player"));
        }

        JoinLobbyResponseData res;

        if (this.lobby.isFull()) {
            if(this.startGame()){
                res = new JoinLobbyResponseData(new ResponseStatus(this.game.getStatusResponse()), this.getId());
            } else {
                res = new JoinLobbyResponseData(new ResponseStatus(503, "Cannot start Game"), this.getId());
            }
        } else {
            res = new JoinLobbyResponseData(new ResponseStatus(), this.getId());
        }

        // Notify the clients and Response
        return res;
    }

    /**
     * Method to join a lobby.
     *
     */
    public LeaveGameResponseData leaveLobby(int clientId) {
        var user = this.lobby.getPlayer(clientId);

        if (user.isEmpty()) {
            //TODO: ERROR
            return new LeaveGameResponseData(new ResponseStatus(404, "User not found"));
        }

        if (!this.lobby.removePlayer(user.get().getUsername())) {
            // TODO: ERROR
            return new LeaveGameResponseData(new ResponseStatus(405, "Player cannot be removed"));
        }

        if (this.lobby.isEmpty()) {
            ServerController.getInstance().deleteGame(this);
        }

        // Notify the clients and Response
        return new LeaveGameResponseData(new ResponseStatus(), "Lobby leaved!");
    }

    //endregion

    // region Utilities

    /**
     * This method starts a game:
     * Delete the GameLobby from the available list
     * Init the GameController
     * Notify all user by in the lobby
     */
    private boolean startGame(){
        if (this.game != null){
            return false;
        }

        try {
            this.game = new GameManager(this.lobby.getPlayersNickname());
            return true;
        } catch (Exception e) {
            //TODO: better logging
            System.out.println("Exception on startGame: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the id of the Game which is the same as the Lobby
     * @return the id
     */
    public int getId(){
        return this.lobby.getId();
    }

    /**
     * Method called to disconnect a player in a game
     * @param handler the clientHandler that disconnects
     */
    public void disconnect(ClientHandler handler) {
        ServerController.getInstance().disconnect(handler);
        // TODO: How to handle disconnection? Set on User isConnected to false and enable reconnection or trigger the end phase of the game and shut down the game?
    }

    /**
     * Private method to broadcast the responses
     * @param clientToExclude the client id who did the request, if you broadcast to him, he will receive double message
     */
    public List<Sender> handlerToBroadcast(int clientToExclude){
        return this.lobby.handlerToBroadcast(clientToExclude);
    }

    // endregion
}
