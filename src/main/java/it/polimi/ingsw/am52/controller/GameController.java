package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.game.GameManager;

/**
 * Class to control the Game
 * TODO: Maybe all methods sends and accepts specific class of response, that will be in network shared. like in REST API
 */
public class GameController {

    /**
     * The server controller, it is used to handle the connections
     */
    private final ServerController serverController;

    /**
     * The game Lobby, it is used to handle the current logged user
     */
    private final GameLobby lobby;

    /**
     * The GameManager, it controls all the Model
     */
    private final GameManager game;

    /**
     * Constructor of the GameController
     * @param serverController the server Controller
     * @param lobby The Lobby linked to this Game
     */
    public GameController(ServerController serverController, GameLobby lobby){
        this.serverController = serverController;
        this.lobby = lobby;
        this.game = new GameManager(this.lobby.getPlayers());
    }

    /**
     * Method to draw a Resource card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     */
    public void drawResourceCard(String Nickname){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return; // TODO: Error response (Maybe class Response that encapsulate various type of object
        }

        game.drawResourceCard(); // TODO: handle exception
        return;
    }

    /**
     * Method to draw a Gold card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     */
    public void drawGoldCard(String Nickname){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return; // TODO: Error response (Maybe class Response that encapsulate various type of object
        }

        game.drawGoldCard(); // TODO: handle exception
        return;
    }

    /**
     * Method to take a Resource card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     * @param cardId the card to take
     */
    public void takeResourceCard(String Nickname, int cardId){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return; // TODO: Error response (Maybe class Response that encapsulate various type of object
        }

        game.takeResourceCard(cardId); // TODO: handle exception
        return;
    }

    /**
     * Method to take a Gold card
     * @param Nickname the nickname of the client who requested to draw a card (It is always sent with the network connection)
     * @param cardId the card to take
     */
    public void takeGoldCard(String Nickname, int cardId){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return; // TODO: Error response (Maybe class Response that encapsulate various type of object
        }

        game.takeGoldCard(cardId); // TODO: handle exception
        return;
    }

    public void placeCard(String Nickname,int hPos, int vPos, int cardId, int face){
        // Error if the player is not the current player
        if (!game.getCurrentPlayer().getNickname().equals(Nickname)){
            return; // TODO: Error response (Maybe class Response that encapsulate various type of object
        }

        game.placeCard(hPos,vPos,cardId,face); //TODO: handle exception
    }
}
