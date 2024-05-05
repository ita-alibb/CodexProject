package it.polimi.ingsw.am52.network;

import it.polimi.ingsw.am52.json.response.Response;

/**
 * Interface to implement to forward the message to the client
 */
public interface Sender {
    /**
     * Method to send the response, implementation in {@link it.polimi.ingsw.am52.network.rmi.ClientHandlerRMI} and {@link it.polimi.ingsw.am52.network.tcp.ClientHandlerTCP}
     * @param response the message to send
     */
    void sendMessage(Response<?> response);
}
