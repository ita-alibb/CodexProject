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

                        if (res.getData().getIsBroadcast()) {
                            //TODO: here we receive the broadcast response, as in ConnectionRMI we need to process it to update the game
                            System.out.println("response received BROADCAST TCP");
                        } else {
                            // If the response is not a broadcast then one of the ActionRMI method is waiting for the response to be added in the queue
                            // the offer methods inserts the element in the queue only if a thread is waiting for it, timeout handled
                            if (!responseQueue.offer(res)){
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

            // Wait the response to be received, 20 seconds timeout
            return this.responseQueue.poll(20, TimeUnit.SECONDS).getData();
        } catch (NullPointerException e){
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
     * Method to perform the listLobby Request
     */
    @Override
    public ListLobbyResponseData listLobby() throws RemoteException {
        return (ListLobbyResponseData) this.send(new ListLobbyRequest(null));
    }

    /**
     * Method to perform the leaveGame Request
     */
    @Override
    public synchronized LeaveGameResponseData leaveGame() throws RemoteException {
        return (LeaveGameResponseData)this.send(new LeaveGameRequest(null));
    }

    /**
     * Method to fetch all information needed on game initialization
     */
    @Override
    public synchronized InitGameResponseData initGame() throws RemoteException {
        return (InitGameResponseData)this.send(new InitGameRequest(null));
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

    /**
     * Method to perform the placeStarterCard request
     * @param data  The request
     */
    @Override
    public synchronized PlaceStarterCardResponseData placeStarterCard(PlaceStarterCardData data) throws RemoteException {
        return (PlaceStarterCardResponseData) this.send(new PlaceStarterCardRequest(data));
    }

    /**
     * Method to perform the placeCard request
     * @param data  The request
     */
    @Override
    public synchronized PlaceCardResponseData placeCard(PlaceCardData data) throws RemoteException {
        return (PlaceCardResponseData) this.send(new PlaceCardRequest(data));
    }

    /**
     * Method to perform the drawCard request
     * @param data  The request
     */
    @Override
    public synchronized DrawCardResponseData drawCard(DrawCardData data) throws RemoteException {
        return (DrawCardResponseData) this.send(new DrawCardRequest(data));
    }

    /**
     * Method to perform the takeCard request
     *
     * @param data The request
     */
    @Override
    public TakeCardResponseData takeCard(TakeCardData data) throws RemoteException {
        return (TakeCardResponseData) this.send(new TakeCardRequest(data));
    }

    /**
     * Method to perform the endGame request
     */
    @Override
    public synchronized EndGameResponseData endGame() throws RemoteException {
        return (EndGameResponseData) this.send(new EndGameRequest(null));
    }
}
