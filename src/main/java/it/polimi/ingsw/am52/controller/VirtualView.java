package it.polimi.ingsw.am52.controller;

import it.polimi.ingsw.am52.json.*;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.network.server.ClientHandler;
import it.polimi.ingsw.am52.network.server.Sender;
import it.polimi.ingsw.am52.network.server.ServerConnection;
import it.polimi.ingsw.am52.network.server.rmi.ActionsRMI;
import it.polimi.ingsw.am52.network.server.tcp.ClientHandlerTCP;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The VirtualView, it is instantiated in {@link ServerConnection} and assigned to a {@link ClientHandler}
 * All request from both RMI and TCP pass from the linked instance of this class.
 * RMI: client calls directly method implementation of {@link ActionsRMI}
 * TCP: the request arrived to the {@link ClientHandlerTCP} is executed by the linked VirtualView
 */
public class VirtualView extends UnicastRemoteObject implements ActionsRMI {
    /**
     * The clientId to which the View is referred
     */
    private final int clientId;

    /**
     * The GameController related to the View
     */
    private GameController gameController;

    /**
     * Constructor for the VirtualView, takes the same clientId as the related ClientHandler
     * @param clientId the id of the ClientHandler
     */
    public VirtualView(int clientId) throws RemoteException {
        this.clientId = clientId;
    }

    /**
     * Method used by the {@link ClientHandlerTCP} to dispatch the execution of the method, it is thrown on new thread.
     * @param jsonMsg the json message read from the socket
     * @throws NoSuchMethodException when the request method do not exist
     */
    public JsonMessage<BaseResponseData> execute(String jsonMsg) throws NoSuchMethodException {
        JsonMessage request;
        try {
            request = JsonDeserializer.deserializeRequest(jsonMsg);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "from id: " + clientId);
            // if the message cannot be deserialized we ignore it
            return null;
        }

        JsonMessage<BaseResponseData> res;
        try {
            res = switch (request.getMethod()) {
                case JsonDeserializer.CREATE_LOBBY_METHOD -> new CreateLobbyResponse(this.createLobby((CreateLobbyData) request.getData()));
                case JsonDeserializer.JOIN_LOBBY_METHOD -> new JoinLobbyResponse(this.joinLobby((JoinLobbyData) request.getData()));
                case JsonDeserializer.LIST_LOBBY_METHOD -> new ListLobbyResponse(this.listLobby());
                case JsonDeserializer.LEAVE_GAME_METHOD -> new LeaveGameResponse(this.leaveGame());
                case JsonDeserializer.INIT_GAME_METHOD -> new InitGameResponse(this.initGame());
                case JsonDeserializer.SELECT_OBJECTIVE_METHOD -> new SelectObjectiveResponse(this.selectObjective((SelectObjectiveData) request.getData()));
                case JsonDeserializer.PLACE_STARTER_CARD_METHOD -> new PlaceStarterCardResponse(this.placeStarterCard((PlaceStarterCardData) request.getData()));
                case JsonDeserializer.PLACE_CARD_METHOD -> new PlaceCardResponse(this.placeCard((PlaceCardData) request.getData()));
                case JsonDeserializer.TAKE_CARD_METHOD -> new TakeCardResponse(this.takeCard((TakeCardData) request.getData()));
                case JsonDeserializer.DRAW_CARD_METHOD -> new DrawCardResponse(this.drawCard((DrawCardData) request.getData()));
                case JsonDeserializer.END_GAME_METHOD -> new EndGameResponse(this.endGame());
                case JsonDeserializer.CHAT_METHOD -> new ChatResponse(this.chat((ChatData) request.getData()));
                default -> throw new NoSuchMethodException("no method " + request.getMethod());
            };
        } catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage() + "from id: " + clientId);
            // if the message is malformed we ignore it
            return null;
        }

        return res;
    }

    /**
     * Method to call the disconnection, if the handler is not in a Game then it is called only in the ServerController
     * @param handler the handler to delete
     */
    public void disconnect(ClientHandler handler){
        if (this.gameController != null){

            var response = this.gameController.disconnect(handler, this.clientId);
            // Game crashed due to single disconnection
            // Broadcast end of game
            this.broadcast(new EndGameResponse(response));
        } else {
            ServerController.getInstance().disconnect(handler);
        }
    }

    // region Actions

    /**
     * Method to perform the createLobby Request
     */
    @Override
    public JoinLobbyResponseData createLobby(CreateLobbyData data) throws RemoteException  {
        JoinLobbyResponseData response;

        if (this.gameController == null) {
            response = ServerController.getInstance().createLobby(this.clientId, data);
        } else {
            return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 404, "Already in lobby"));
        }

        // the client has joined a lobby, set the GameController, in this way we remove the bottleneck on ServerController by using directly the related GameController
        //The LobbyId is in the response of the controller
        if (response.getStatus().getErrorCode() == 0) {
            try{
                this.gameController = ServerController.getInstance().getGameController(response.getLobbyId()).get();
            } catch (Exception e){
                return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 404, "Game not found"));
            }
        }

        return response;
    }

    /**
     * Method to perform the joinLobby Request
     * @param data the request
     */
    @Override
    public JoinLobbyResponseData joinLobby(JoinLobbyData data) throws RemoteException {
        JoinLobbyResponseData response;

        if (this.gameController == null) {
            response = ServerController.getInstance().joinLobby(this.clientId, data);
        } else {
            return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 404, "Already in lobby"));
        }

        // the client has joined a lobby, set the GameController, in this way we remove the bottleneck on ServerController by using directly the related GameController
        if (response.getStatus().getErrorCode() == 0) {
            try{
                this.gameController = ServerController.getInstance().getGameController(response.getLobbyId()).get();
            } catch (Exception e){
                return new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 404, "Game not found"));
            }
        }

        this.broadcast(new JoinLobbyResponse(new JoinLobbyResponseData(response.getStatus(), response.getLobbyId(), response.getNicknames())));

        return response;
    }

    /**
     * Method to perform the listLobby Request
     */
    @Override
    public ListLobbyResponseData listLobby() throws RemoteException {
        if (this.gameController != null){
            return new ListLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, 403, "Already in lobby"));
        }

        return ServerController.getInstance().getLobbyList();
    }

    /**
     * Method to perform the leaveGame Request
     */
    @Override
    public LeaveGameResponseData leaveGame() throws RemoteException {
        // The request here is useless, the only thing needed is the clientId, the body is empty
        var response = this.gameController.leaveLobby(this.clientId);

        this.broadcast(new LeaveGameResponse(new LeaveGameResponseData(response.getStatus(), response.getUsername(), response.getLobbies())));

        // Particular case, when you leave then you have to set the reference to null
        if (response.getStatus().getErrorCode() == 0) {
            this.gameController = null;
        }

        return response;
    }

    /**
     * Method to perform the fetch of data for game initialization
     *
     */
    @Override
    public InitGameResponseData initGame() throws RemoteException {
        return this.gameController.initGame(this.clientId);
    }

    /**
     * Method to perform the end phase of a game
     */
    @Override
    public EndGameResponseData endGame() throws RemoteException {
        var response = this.gameController.endGame();

        this.broadcast(new EndGameResponse(new EndGameResponseData(response.getStatus(), response.getWinners())));

        return response;
    }

    /**
     * Method to perform selectObjective Request
     * @param data  The request
     */
    @Override
    public SelectObjectiveResponseData selectObjective(SelectObjectiveData data) throws RemoteException {
        var response = this.gameController.selectObjective(this.clientId, data.getObjectiveId());

        this.broadcast(new SelectObjectiveResponse(new SelectObjectiveResponseData(response.getStatus())));

        return response;
    }

    /**
     * Method to perform placeStarterCard request
     * @param data  The request
     */
    @Override
    public PlaceStarterCardResponseData placeStarterCard(PlaceStarterCardData data) throws RemoteException {
        var response = this.gameController.placeStarterCard(this.clientId, data.getCardId(), data.getFace());

        this.broadcast(new PlaceStarterCardResponse(new PlaceStarterCardResponseData(response.getStatus(), response.getCardId(), response.getFace(), response.getNickname())));

        return response;
    }

    /**
     * Method to perform placeCard request
     * @param data  The request
     */
    @Override
    public PlaceCardResponseData placeCard(PlaceCardData data) throws RemoteException {
        var response = this.gameController.placeCard(this.clientId, data.getCardId(), data.getFace(), data.getPlacedSlot());

        this.broadcast(new PlaceCardResponse(new PlaceCardResponseData(
                response.getStatus(),
                response.getCardId(),
                response.getFace(),
                response.getPlacedSlot(),
                response.getPlayer(),
                response.getScore()
        )));

        return response;
    }

    /**
     * Method to perform drawCard request
     * @param data  The request
     */
    @Override
    public DrawCardResponseData drawCard(DrawCardData data) throws RemoteException {
        var response = this.gameController.drawCard(this.clientId, data.getDeck());

        this.broadcast(new DrawCardResponse(new DrawCardResponseData(
                response.getStatus(),
                response.getDeck(),
                response.getNextCardId()
        )));

        return response;
    }

    /**
     * Method to perform the takeCard request
     * @param data The request
     */
    @Override
    public TakeCardResponseData takeCard(TakeCardData data) throws RemoteException {
        var response = this.gameController.takeCard(this.clientId, data.getCardId(), data.getType());

        this.broadcast(new TakeCardResponse(new TakeCardResponseData(
                response.getStatus(),
                response.getTakenCardId(),
                response.getShownCardId(),
                response.getType(),
                response.getNextCardId()
        )));

        return response;
    }

    /**
     * @param data the request data
     * @return the response data
     * @throws RemoteException if exemptions happens in RMI
     */
    @Override
    public ChatResponseData chat(ChatData data) throws RemoteException {
        if (this.gameController != null){
            String responseMessage;
            if (data.getRecipient() != null && !data.getRecipient().trim().isEmpty()) {
                var clientIdToBroadcast = this.gameController.getClientId(data.getRecipient());

                if (clientIdToBroadcast == -1) {
                    return new ChatResponseData(new ResponseStatus(GamePhase.NULL, 404, String.format("Player %s to whisper not in lobby", data.getRecipient())));
                }
                responseMessage = String.format("%s [Whisper to: %s]: %s", data.getSender(), data.getRecipient(), data.getMessage());
                this.specificBroadcast(new ChatResponse(new ChatResponseData(new ResponseStatus(GamePhase.NULL, 0, ""), responseMessage)), clientIdToBroadcast);
            } else {
                responseMessage = String.format("%s : %s", data.getSender(), data.getMessage());
                this.broadcast(new ChatResponse(new ChatResponseData(new ResponseStatus(GamePhase.NULL, 0, ""), responseMessage)));
            }

            return new ChatResponseData(new ResponseStatus(GamePhase.NULL, 0, ""), responseMessage);
        } else {
            return new ChatResponseData(new ResponseStatus(GamePhase.NULL, 403, "Player not in a lobby"));
        }
    }

    //endregion

    /**
     * Method called at last step of every execution.
     * If the response is correct and the Client is in a Game the response is broadcast to every other user in the game.
     * The list of client is retrieved from the GameController
     * @param response the Response to send to the client's.
     */
    private void broadcast(JsonMessage<BaseResponseData> response) {
        if (this.gameController != null && response.getData().getStatus().getErrorCode() == 0) {
            // Get the handlers of the Game
            List<Sender> handlers = this.gameController.handlerToBroadcast(this.clientId);

            // Mark the response as a broadcast
            response.getData().setIsBroadcast(true);

            for (var handler : handlers) {
                try {
                    handler.sendMessage(response);
                } catch (Exception e) {
                    // TODO: Better logging
                    System.out.println("Exception:" + e.getMessage());
                }
            }
        }
    }

    /**
     * Method to broadcast to specific client
     * @param response the Response to send to the client.
     */
    private void specificBroadcast(JsonMessage<BaseResponseData> response, int clientIdToBroadcast) {
        if (this.gameController != null && (response.getData().getStatus().getErrorCode() == 0 || response.getData().getStatus().getGamePhase() == GamePhase.END)) {
            // Get the handlers of the Game
            Sender handler = this.gameController.specificHandlerToBroadcast(clientIdToBroadcast);

            // Mark the response as a broadcast
            response.getData().setIsBroadcast(true);

            try {
                handler.sendMessage(response);
            } catch (Exception e) {
                // TODO: Better logging
                System.out.println("Exception:" + e.getMessage());
            }
        }
    }
}
