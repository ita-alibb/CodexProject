package it.polimi.ingsw.am52.Model.Game;

import it.polimi.ingsw.am52.Exceptions.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameLobby {
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
     * @param maxPlayers The max number of players in the lobby
     */
    public GameLobby(int maxPlayers) {
        if (maxPlayers > 4 || maxPlayers < 2){
            throw new GameException("Lobby must have 2 to 4 Players");
        }

        this.maxPlayers = maxPlayers;
        this.Players = new ArrayList<>();
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
}

