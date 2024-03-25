package it.polimi.ingsw.am52.Model.Game;

import it.polimi.ingsw.am52.Exceptions.GameException;

import java.util.Arrays;
import java.util.List;

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
        this.Players = Arrays.asList(new String[maxPlayers]);
    }

    /**
     * Method to validate a nickname in the current lobby
     * @param nickName The nickname of the new player
     * @return A bool indicating whether the nickname is valid
     */
    private boolean validateNickName(String nickName){
        return nickName != null && !nickName.isBlank() && nickName.length() <= 16 && !this.Players.contains(nickName);
    }

    /**
     * Method to add a new player to the lobby
     * @param nickName The nickname of the new player
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
    public List<String> getPLayers(){
        return this.Players;
    }

    /**
     * Method to retrieve the count of players
     * @return The number of players
     */
    public int getPLayersCount(){
        return this.Players.size();
    }

    /**
     * Method to retrieve if the lobby is full
     * @return A bool indicating whether the lobby is full
     */
    public boolean isFull(){
        return this.getPLayersCount() == this.maxPlayers;
    }
}

