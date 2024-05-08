package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.network.ClientHandler;
import it.polimi.ingsw.am52.network.Sender;

/**
 * The User class, it represents the connected clients to the server
 */
public class User {
    /**
     * The username
     */
    private String username;

    /**
     * The connected boolean
     */
    private boolean isConnected;

    /**
     * The client message Handler
     */
    private final Sender handler;

    /**
     * The client d, taken from its handler
     */
    private final int clientId;

    /**
     * Constructor of User
     * @param username The username
     */
    public User(String username, ClientHandler handler){
        this.username = username;
        this.isConnected = true;
        this.handler = handler;
        this.clientId = handler.getClientId();
    }

    /**
     * Return the clientId
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return The username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get User's Handler
     */
    public Sender getHandler(){
        return this.handler;
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
}
