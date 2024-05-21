package it.polimi.ingsw.am52.network.server;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.network.server.rmi.ClientHandlerRMI;
import it.polimi.ingsw.am52.network.server.tcp.ClientHandlerTCP;

/**
 * Interface to implement to forward the message to the client
 */
public interface Sender {
    /**
     * Method to send the response, implementation in {@link ClientHandlerRMI} and {@link ClientHandlerTCP}
     * @param response the message to send
     */
    void sendMessage(JsonMessage<BaseResponseData> response);
}
