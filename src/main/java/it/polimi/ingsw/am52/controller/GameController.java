package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.network.Sender;
import it.polimi.ingsw.am52.json.response.Response;
import it.polimi.ingsw.am52.json.response.Status;

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
    public GameController(GameLobby lobby){
        this.lobby = lobby;
    }

    // region Endpoints

    /**
     * The method to join the lobby
     * @param clientId the client who requested
     * @param user the user that joined
     */
    public synchronized Response<String> joinLobby(int clientId, User user) {
        if (!this.lobby.addPlayer(user)) {
            return new Response<String>(new Status(), 1,"Qui ci sara' la risposta Errore");
        }

        Response<String> res;

        if (this.lobby.isFull()) {
            res = this.startGame();
        } else {
            res = new Response<String>(new Status(), 0,"Qui ci sara' la risposta");
        }

        // Notify the clients and Response
        return res;
    }

    /**
     * Method to join a lobby.
     *
     */
    public synchronized Response<String> leaveLobby(int clientId) {
        var user = this.lobby.getPlayer(clientId);

        if (user.isEmpty())
        {
            //TODO: ERROR
            return new Response<String>(new Status(), 1,"Qui ci sara' la risposta Errore");
        }

        if (!this.lobby.removePlayer(user.get().getUsername()))
        {
            // TODO: ERROR
            return new Response<String>(new Status(), 1,"Qui ci sara' la risposta Errore");
        }

        if (this.lobby.isEmpty()){
            ServerController.getInstance().deleteGame(this);
        }

        // Notify the clients and Response
        var res = new Response<String>(new Status(), 0,"Qui ci sara' la risposta");
        return res;
    }

    /**
     * Method to draw a Resource card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     */
    public Response<String> drawResourceCard(int clientId, String Nickname){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return new Response<String>(new Status(this.game.getStatusResponse()), 1,"Qui ci sara' la risposta Errore");
        }

        game.drawResourceCard(); // TODO: handle exception

        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
    }

    /**
     * Method to draw a Gold card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     */
    public Response<String> drawGoldCard(int clientId, String Nickname){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return new Response<String>(new Status(this.game.getStatusResponse()), 1,"Qui ci sara' la risposta Errore");
        }

        game.drawGoldCard(); // TODO: handle exception

        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
    }

    /**
     * Method to take a Resource card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     * @param cardId the card to take
     */
    public Response<String> takeResourceCard(int clientId, String Nickname, int cardId){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return new Response<String>(new Status(this.game.getStatusResponse()), 1,"Qui ci sara' la risposta Errore");
        }

        game.takeResourceCard(cardId);

        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
    }

    /**
     * Method to take a Gold card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     * @param cardId the card to take
     */
    public Response<String> takeGoldCard(int clientId, String Nickname, int cardId){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return new Response<String>(new Status(this.game.getStatusResponse()), 1,"Qui ci sara' la risposta Errore");
        }

        game.takeGoldCard(cardId); // TODO: handle exception

        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
    }

    /**
     * Method to place a card
     * @param Nickname the nickname of the player
     * @param hPos the h position
     * @param vPos the v position
     * @param cardId the cardId placed
     * @param face the card face chosen
     * @return the response
     */
    public Response<String> placeCard(int clientId, String Nickname,int hPos, int vPos, int cardId, int face){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return new Response<String>(new Status(this.game.getStatusResponse()), 1,"Qui ci sara' la risposta Errore");
        }

        game.placeCard(hPos,vPos,cardId,face); //TODO: handle exception

        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
    }

    //endregion

    // region Utilities

    /**
     * This method starts a game:
     * Delete the GameLobby from the available list
     * Init the GameController
     * Notify all user by in the lobby
     */
    private Response<String> startGame(){
        if (this.game != null){
            return new Response<String>(new Status(), 1,"Qui ci sara' la risposta Errore");
        }

        this.game = new GameManager(this.lobby.getPlayersNickname());


        // Notify the clients and Response
        var res = new Response<String>(new Status(this.game.getStatusResponse()), 0,"Qui ci sara' la risposta");
        return res;
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
     * TODO: Broadcast DELLA RISPOSTA SOLO IN CASO NON CI SIANO ERRORI!! INOLTRE SI PUO' RIMETTERE L'HANDLER DA ESCLUDERE E MANDARE IL MESSAGGIO ALLA FINE DEL THREAD CHE NEL CLIENTHANDLER CHIAMA LA VIRTUALVIEW
     * Private method to broadcast the responses
     * @param clientToExclude the client id who did the request, if you broadcast to him, he will receive double message
     */
    public List<Sender> handlerToBroadcast(int clientToExclude){
        return this.lobby.handlerToBroadcast(clientToExclude);
    }

    // endregion
}
