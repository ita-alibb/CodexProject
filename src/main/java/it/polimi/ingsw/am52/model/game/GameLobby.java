package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.controller.User;
import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.network.Sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class GameLobby {
    /**
     * The max number of character in nickname
     */
    private static final int MAX_NAME_LENGTH = 16;

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
    private final List<User> players;

    /**
     * Create a new instance of a GameLobby
     * @param id The unique ID the uniqueness is handled by the ServerController
     * @param maxPlayers The max number of players in the lobby
     */
    public GameLobby(int id, int maxPlayers) throws GameException {
        this.id = id;
        if (maxPlayers > GameManager.MAX_PLAYERS || maxPlayers < GameManager.MIN_PLAYERS){
            throw new GameException("Lobby must have 2 to 4 Players");
        }

        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
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
    private boolean validateNickName(String nickName) {
        return nickName != null && !nickName.isBlank() && nickName.length() <= MAX_NAME_LENGTH && !this.getPlayersNickname().contains(nickName);
    }

    /**
     * Method to add a new player to the lobby
     * @param user The user of the new player
     * @return A bool indicating whether the Player has been added
     */
    public synchronized boolean addPlayer(User user) throws GameException {
        if (this.isFull()){
            throw new GameException("Lobby is full");
        }

        if (validateNickName(user.getUsername())){
            return this.players.add(user);
        }
        return false;
    }

    /**
     * Method to remove a new player from the lobby
     * @param nickName The nickname of the player to remove
     */
    public synchronized void removePlayer(String nickName) throws GameException {
        if (!this.players.removeIf(user -> user.getUsername().equals(nickName))) {
            throw new GameException("Player already not in lobby");
        }
    }

    /**
     * Method to remove a new player from the lobby
     * @param clientId The clientId of the player to remove
     */
    public synchronized void removePlayer(int clientId) throws GameException {
        if (!this.players.removeIf(user -> user.getClientId() == clientId)) {
            throw new GameException("Player already not in lobby");
        }
    }

    /**
     * Method to retrieve all players in lobby
     * @return The list of nicknames of players in lobby
     */
    public List<String> getPlayersNickname() {
        return this.players.stream().map(User::getUsername).toList();
    }

    /**
     * Method to retrieve the count of players
     * @return The number of players
     */
    public long getPlayersCount() {
        return this.players.size();
    }

    /**
     * Method to retrieve if the lobby is full
     * @return A bool indicating whether the lobby is full
     */
    public boolean isFull() {
        return this.getPlayersCount() == this.maxPlayers;
    }

    /**
     * Method to retrieve if the lobby is empty
     * @return A bool indicating whether the lobby is empty
     */
    public boolean isEmpty() {
        return this.getPlayersCount() == 0;
    }

    /**
     * Method to check if the player is in lobby and is connected
     * @return A boolean indicating whether the user is connected (to know that the turn must be skipped)
     *
     * @throws GameException if the requested user is not in the game
     */
    public boolean isPlayerLoggedInLobby() throws GameException {
        //TODO: To do this some refactoring is needed: make players a
        // list of User (so refactor all other methods, eg add player takes a User) .
        // Then remove the list of connected user from the ServerController (if needed one can query every GameLobby and get the players),
        // then this class can be used to check the connection of a player to the game
        // forse non serve perche' il client manda un "heartbeat" mentre e' il suo turno ogni tot secondi controlli che sia ancora connesso, se lo trovi non connesso allora si e' disconnesso e skippi il turno
        return true;
    }

    /**
     * Method to get the user with nickname
     * @param nickName the user nickname to search
     * @return the optional of the user
     */
    public Optional<User> getPlayer(String nickName) {
        return this.players.stream().filter(user -> user.getUsername().equals(nickName)).findFirst();
    }

    /**
     * Method to get the user with clientId
     * @param clientId the user clientId to search
     * @return the optional of the user
     */
    public Optional<User> getPlayer(int clientId) {
        return this.players.stream().filter(user -> user.getClientId() == clientId).findFirst();
    }

    /**
     * Return all ClientHandler except the one to exclude
     * @param clientIdToExclude the clientToNotBroadcast
     */
    public List<Sender> handlerToBroadcast(int clientIdToExclude) {
        return this.players.stream().filter(u -> u.getClientId() != clientIdToExclude).map(User::getHandler).toList();
    }

    /**
     * Get free spaces
     * @return the free spaces
     */
    public int getFreeSpace(){
        return this.maxPlayers - this.players.size();
    }
}

