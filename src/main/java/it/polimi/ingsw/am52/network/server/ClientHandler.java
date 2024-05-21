package it.polimi.ingsw.am52.network.server;

/**
 * Interface that both RMI and TCP connection shares
 * It permits to get the client ID, run its thread and forward message to it.
 */
public interface ClientHandler extends Runnable, Sender {
    /**
     * Get the clientId
     * @return the client id
     */
    int getClientId();
}
