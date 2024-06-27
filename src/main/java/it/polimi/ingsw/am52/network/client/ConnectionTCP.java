package it.polimi.ingsw.am52.network.client;

import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.*;

public class ConnectionTCP implements Connection, Runnable{

    private static ConnectionTCP INSTANCE;
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

    private final ExecutorService broadcastThread = Executors.newSingleThreadExecutor();

    private final LinkedBlockingQueue<BaseResponseData> broadcastQueue = new LinkedBlockingQueue<>();

    /**
     * The object needed to pass the response between two threads
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/SynchronousQueue.html">SynchronousQueue API</a>
     * Although the SynchronousQueue has an interface of a queue, we should think about it as an exchange point for a single element between two threads,
     * in which one thread is handing off an element, and another thread is taking that element.
     */
    private final SynchronousQueue<JsonMessage<BaseResponseData>> responseQueue;

    public ConnectionTCP(String serverIp, int tcpPort) throws IOException {
        System.out.printf("Client started with host %s and port %d %n", serverIp,tcpPort);
        // establish connection to server
        this.socket = new Socket(serverIp,tcpPort);

        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        this.responseQueue = new SynchronousQueue<>();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        System.out.println("Listening Thread started");

        // Start thread to send broadcast messages
        this.broadcastThread.execute(this::executeBroadcastAsync);
        // Initialize Listening thread
        String jsonResponse;

        try {
            while((jsonResponse = in.readLine()) != null){
                /*System.out.println("received: " + jsonResponse);*/

                try {
                    var res = JsonDeserializer.deserializeResponse(jsonResponse);

                    if (res.getData().getIsBroadcast()) {
                        this.broadcastQueue.put(res.getData());
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

            // Wait the response to be received, 1 minute timeout
            return this.responseQueue.poll(1, TimeUnit.MINUTES).getData();
        } catch (NullPointerException e){
            System.out.println("Request Timeout");
        } catch (Exception e) {
            System.out.println("Exception on sending request to server; exception: " + e.getMessage());
        }
        return null;
    }

    private void executeBroadcastAsync() {
        while (true) {
            try {
                var broadcast = this.broadcastQueue.take();

                ViewModelState.getInstance().broadcastUpdate(broadcast);
            } catch (InterruptedException e) {
                System.out.println("Error on executing broadcast");
            }
        }
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

    /**
     * @param data the request data
     * @return the response data
     * @throws RemoteException if exemptions happens in RMI
     */
    @Override
    public ChatResponseData chat(ChatData data) throws RemoteException {
        return (ChatResponseData) this.send(new ChatRequest(data));
    }
}
