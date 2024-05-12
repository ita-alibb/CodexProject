package it.polimi.ingsw.am52.network.tcp.client;

import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionTCP implements ActionsRMI {

    /**
     * The client socket
     */
    private final Socket socket;

    /**
     * The socket out stream
     */
    private final PrintWriter out;

    /**
     * The socket in stream
     */
    private final BufferedReader in;

    /**
     * The object needed to pass the response between two threads
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/SynchronousQueue.html">SynchronousQueue API</a>
     * Although the SynchronousQueue has an interface of a queue, we should think about it as an exchange point for a single element between two threads,
     * in which one thread is handing off an element, and another thread is taking that element.
     */
    private final SynchronousQueue<JsonMessage<BaseResponseData>> responseQueue;

    public ConnectionTCP() throws IOException {
        // establish connection to server
        this.socket = new Socket("127.0.0.1",5555);

        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        this.responseQueue = new SynchronousQueue<>();

        // Initialize Listening thread
        new Thread(() ->{

            String jsonResponse;

            try {
                while((jsonResponse = in.readLine()) != null){
                    System.out.println("received: " + jsonResponse);

                    try {
                        var res = JsonDeserializer.deserializeResponse(jsonResponse);

                        if (res.getData().isBroadcast()) {
                            //TODO: here we receive the broadcast response, as in ConnectionRMI we need to process it to update the game
                            System.out.println("The data was a broadcast");
                        } else {
                            // If the response is not a broadcast then one of the ActionRMI method is waiting for the response to be added in the queue
                            // the offer methods inserts the element in the queue only if a thread is waiting for it, timeout handled
                            if (!responseQueue.offer(res)){
                                // TODO: aggiungiamo la risposta (che prima o poi arrivera') ad una lista di risposte in timeout? O ignoriamo e diamo per socntato che la connessione e' caduta e la partita persa?
                                System.out.println("Request thread timed out");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Deserialize throw exception:" + e.getMessage());
                    }
                }
            } catch (IOException e) {
                // break the loop and finally call the disconnection
            } finally {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    System.out.println("Exception closing socket: exception: " + e.getMessage());
                }
            }
        }).start();

        System.out.println("initialized");
    }

    /**
     * Synchronized method to be sure that the response is correct
     * Method used to send the message to the server
     *
     * @param message the message to send
     * @return the BaseResponseData
     */
    private synchronized BaseResponseData send(JsonMessage message) {
        try {
            this.out.println(message.toJson());

            // Wait the response to be received, 2 seconds timeout
            return this.responseQueue.poll(2, TimeUnit.SECONDS).getData();
        } catch (NullPointerException e){
            // TODO: se fa timeout che dobbiamo fare? In teoria la richiesta dovrebbe essere arrivata e la risposta prima o poi arrivera' -> No timeout?
            System.out.println("Request Timeout");
        } catch (Exception e) {
            System.out.println("Exception on sending request to server; exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method to perform the createLobby Request
     *
     * @param data the request
     */
    @Override
    public synchronized JoinLobbyResponseData createLobby(CreateLobbyData data) throws RemoteException {
        return (JoinLobbyResponseData)this.send(new CreateLobbyRequest(data));
    }

    /**
     * Method to perform the joinLobby Request
     *
     * @param data the request
     */
    @Override
    public synchronized JoinLobbyResponseData joinLobby(JoinLobbyData data) throws RemoteException {
        return (JoinLobbyResponseData)this.send(new JoinLobbyRequest(data));
    }

    /**
     * Method to perform the leaveGame Request
     *
     * @param data the request
     */
    @Override
    public synchronized LeaveGameResponseData leaveGame(LeaveGameData data) throws RemoteException {
        return (LeaveGameResponseData)this.send(new LeaveGameRequest(data));
    }

    /**
     * Method to fetch all information needed on game initialization
     */
    @Override
    public synchronized InitGameResponseData initGame() throws RemoteException {
        return (InitGameResponseData)this.send(new InitGameRequest(new InitGameData()));
    }

    /**
     * Method to perform the selectObjective Request
     *
     * @param data The request
     */
    @Override
    public synchronized SelectObjectiveResponseData selectObjective(SelectObjectiveData data) throws RemoteException {
        return (SelectObjectiveResponseData)this.send(new SelectObjectiveRequest(data));
    }
}
