package it.polimi.ingsw.network;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.network.ServerConnection;
import it.polimi.ingsw.am52.network.rmi.ActionsRMI;
import it.polimi.ingsw.am52.network.rmi.client.ConnectionRMI;
import it.polimi.ingsw.am52.network.tcp.client.ConnectionTCP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class to test the network
 */
public class NetworkTest {
    private static ServerConnection server;

    @BeforeAll
    public static void setUp() {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");

        try {
            server = new ServerConnection(5555,5556, 10);
        } catch (RemoteException e) {
            assert false;
        }
        new Thread(server).start();
    }

    @Test
    @DisplayName("Test network with some client")
    public void simulate(){

        // First Client init
        ActionsRMI firstClient;
        try {
            firstClient = new ConnectionTCP();
        } catch (Exception ex){
            assert false;
            return;
        }

        // Second Client
        ActionsRMI secondClient;
        try {
            secondClient = new ConnectionRMI();
        } catch (Exception ex){
            assert false;
            return;
        }

        // Third Client
        ActionsRMI thirdClient;
        try {
            thirdClient = new ConnectionTCP();
        } catch (Exception ex){
            assert false;
            return;
        }

        //First client create lobby
        this.testCall(
            firstClient,
            new CreateLobbyRequest(new CreateLobbyData("Andrea", 2)),
            new CreateLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, "", 0, ""), 1))
        );

        // second client join lobby
        this.testCall(
            secondClient,
            new JoinLobbyRequest(new JoinLobbyData("Lorenzo", 1)),
            new JoinLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.INIT, "Andrea", 0, ""), 1)) //Here currPlayer is random, so?
        );

        // Join lobby created by third client
        this.testCall(
            thirdClient,
            new CreateLobbyRequest(new CreateLobbyData("Livio", 2)),
            new CreateLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, "", 0, ""), 2))
        );
    }


    /**
     * Method to test a generic call, it will be serialized to Json and compared the strings
     * @param caller the client Connection that does the call
     * @param request the request
     * @param expectedResponse the expected response
     */
    private void testCall(ActionsRMI caller, JsonMessage request, JsonMessage<BaseResponseData> expectedResponse) {
        try {
            var res = switch (request.getMethod()) {
                case JsonDeserializer.CREATE_LOBBY_METHOD -> new CreateLobbyResponse(caller.createLobby((CreateLobbyData) request.getData()));
                case JsonDeserializer.JOIN_LOBBY_METHOD -> new JoinLobbyResponse(caller.joinLobby((JoinLobbyData) request.getData()));
                case JsonDeserializer.LEAVE_GAME_METHOD -> new LeaveGameResponse(caller.leaveGame((LeaveGameData) request.getData()));
                case JsonDeserializer.INIT_GAME_METHOD -> new InitGameResponse(caller.initGame());
                case JsonDeserializer.SELECT_OBJECTIVE_METHOD -> new SelectObjectiveResponse(caller.selectObjective((SelectObjectiveData) request.getData()));
                default -> throw new NoSuchMethodException("no method " + request.getMethod());
            };

            assertEquals(expectedResponse.toJson(), res.toJson());
        } catch (Exception ex) {
            assert false;
        }
    }
}
