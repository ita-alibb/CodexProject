package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO: maybe this class chan be moved to Controller section. This is used to collect hte players and then initialize the GameManager
 */
public class GameLobby {
    /**
     * The id of the Lobby
     */
    private final int id;

    /**
     * The max number of player in this lobby
     */
    private final int maxPlayers;

    /**
     * The list of nickname of players
     */
    private final List<String> Players;

    /**
     * Create a new instance of a GameLobby
     * @param id The unique ID the uniqueness is handled by the ServerController
     * @param maxPlayers The max number of players in the lobby
     */
    public GameLobby(int id, int maxPlayers) throws GameException {
        this.id = id;
        if (maxPlayers > 4 || maxPlayers < 2){
            throw new GameException("Lobby must have 2 to 4 Players");
        }

        this.maxPlayers = maxPlayers;
        this.Players = new ArrayList<>();
    }


    /**
     * @return the GameLobby id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to validate a nickname in the current lobby
     * @param nickName The nickname of the new player
     * @return A bool indicating whether the nickname is valid
     */
    private boolean validateNickName(String nickName){
        return nickName != null && !nickName.isBlank() && nickName.length() <= 16 && !this.Players.contains(nickName) && !this.isFull();
    }

    /**
     * Method to add a new player to the lobby
     * @param nickName The nickname of the new player
     * @return A bool indicating whether the Player has been added
     */
    public boolean addPlayer(String nickName){
        if (validateNickName(nickName)){
            return this.Players.add(nickName);
        }
        return false;
    }

    /**
     * Method to remove a new player from the lobby
     * @param nickName The nickname of the player to remove
     * @return A bool indicating whether the Player has been removed
     */
    public boolean removePlayer(String nickName){
        return this.Players.remove(nickName);
    }

    /**
     * Method to retrieve all players in lobby
     * @return The list of nicknames of players in lobby
     */
    public List<String> getPlayers(){
        return this.Players;
    }

    /**
     * Method to retrieve the count of players
     * @return The number of players
     */
    public long getPlayersCount(){
        return this.Players.stream().filter(Objects::nonNull).count();
    }

    /**
     * Method to retrieve if the lobby is full
     * @return A bool indicating whether the lobby is full
     */
    public boolean isFull(){
        return this.getPlayersCount() == this.maxPlayers;
    }

    /**
     * Method to check if the player is in lobby and is connected
     * @return A boolean indicating whether the user is connected (to know that the turn must be skipped)
     *
     * @throws GameException if the requested user is not in the game
     */
    public boolean isPlayerLoggedInLobby() throws GameException{
        //TODO: To do this some refactoring is needed: make players a
        // list of User (so refactor all other methods, eg add player takes a User) .
        // Then remove the list of connected user from the ServerController (if needed one can query every GameLobby and get the players),
        // then this class can be used to check the connection of a player to the game
        // forse non serve perche' il client manda un "heartbeat" mentre e' il suo turno ogni tot secondi controlli che sia ancora connesso, se lo trovi non connesso allora si e' disconnesso e skippi il turno
        return true;
    }
}

