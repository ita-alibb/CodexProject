package it.polimi.ingsw.am52.controller;

import java.util.Objects;
import java.util.Optional;

/**
 * The User class, it represents the connected clients to the server
 */
public class User {
    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * The connected boolean
     */
    private boolean isConnected;

    /**
     * The last GameLobbyId to which it has been connected
     */
    private Optional<Integer> lastGameLobbyId;

    /**
     * Constructor of User
     * @param username The username
     * @param password The password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.isConnected = true;
    }

    /**
     * @return The username of the user
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Method to "login", return true if the provided password is correct
     * @param password The password to test
     * @return A bool indicating whether the login was successful
     */
    public boolean login(String password){
        return Objects.equals(password, this.password);
    }

    /**
     * Return the connection state
     */
    public boolean isConnected(){
        return this.isConnected;
    }

    /**
     * Set the connection state
     */
    public void setConnected(boolean isConnected){
        this.isConnected = isConnected;
    }

    /**
     * @return the last gameLobbyId connected
     */
    public Optional<Integer> getLastGameLobbyId() {
        return lastGameLobbyId;
    }

    /**
     * @param lastGameLobbyId the last game lobby id
     */
    public void setLastGameLobbyId(int lastGameLobbyId) {
        this.lastGameLobbyId = Optional.of(lastGameLobbyId);
    }

    /**
     * Reset the LastGameLobbyId
     */
    public void resetLastGameLobbyId() {
        this.lastGameLobbyId = Optional.empty();
    }
}
