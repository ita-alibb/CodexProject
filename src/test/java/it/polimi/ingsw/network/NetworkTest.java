package it.polimi.ingsw.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonDeserializer;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.json.request.*;
import it.polimi.ingsw.am52.json.response.*;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.network.server.ServerConnection;
import it.polimi.ingsw.am52.network.server.rmi.ActionsRMI;
import it.polimi.ingsw.am52.network.client.ConnectionRMI;
import it.polimi.ingsw.am52.network.client.ConnectionTCP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;
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
            firstClient = new TestConnectionTCP();
            new Thread((TestConnectionTCP)firstClient).start();
        } catch (Exception ex){
            assert false;
            return;
        }

        // Second Client
        ActionsRMI secondClient;
        try {
            secondClient = new TestConnectionRMI();
        } catch (Exception ex){
            assert false;
            return;
        }

        // Third Client
        ActionsRMI thirdClient;
        try {
            thirdClient = new TestConnectionTCP();
            new Thread((TestConnectionTCP)thirdClient).start();
        } catch (Exception ex){
            assert false;
            return;
        }

        // region LobbyPhase
        System.out.println("-----LOBBY PHASE-----");
        List<String> players = new ArrayList<>();
        players.add("Andrea");
        //First client create lobby
        this.testCallExactMatch(
            firstClient,
            new CreateLobbyRequest(new CreateLobbyData("Andrea", 2)),
            new CreateLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, "", 0, ""), 1, players))
        );

        players.add("Lorenzo");
        // second client join lobby
        var initRes = (JoinLobbyResponse) this.call(
            secondClient,
            new JoinLobbyRequest(new JoinLobbyData("Lorenzo", 1))
        );

        // CHECK THAT THE RESPONSE AFTER SECOND JOIN (lobby full) THE STATUS IS INIT
        var firstPlayer = initRes.getData().getStatus().getCurrPlayer();
        var secondPlayer = Objects.equals(firstPlayer, "Andrea") ? "Lorenzo" : "Andrea";
        this.match(new JoinLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.INIT, firstPlayer, 0, ""), 1, players)), initRes);
        //endregion

        // Create lobby created by third client
        this.testCallExactMatch(
                thirdClient,
                new CreateLobbyRequest(new CreateLobbyData("Livio", 2)),
                new CreateLobbyResponse(new JoinLobbyResponseData(new ResponseStatus(GamePhase.LOBBY, "", 0, ""), 2, new ArrayList<>(){{add("Livio");}}))
        );

        // region InitGameResponses
        System.out.println("-----INITGAME PHASE-----");
        var init1 = (InitGameResponseData)call(
                firstClient,
                new InitGameRequest(new InitGameData())
        ).getData();

        var init2 = (InitGameResponseData)call(
                secondClient,
                new InitGameRequest(new InitGameData())
        ).getData();

        assertNotNull(init1);
        assertNotNull(init2);
        assertEquals(init2.getPlayersNickname(),init1.getPlayersNickname());
        assertEquals(init2.getVisibleResourceCardIds(),init1.getVisibleResourceCardIds());
        assertEquals(init2.getVisibleGoldCardIds(),init1.getVisibleGoldCardIds());
        assertEquals(init2.getCommonObjectiveIds(),init1.getCommonObjectiveIds());
        assertNotEquals(init2.getStarterCardId(),init1.getStarterCardId());
        assertNotEquals(init2.getPlayerHandCardIds(),init1.getPlayerHandCardIds());
        Set<Integer> commonCards = init1.getPlayerHandCardIds().stream()
                .distinct()
                .filter(init2.getPlayerHandCardIds()::contains)
                .collect(Collectors.toSet());
        assertEquals(0, commonCards.size());
        Set<Integer> commonObjectives = init1.getPlayerObjectiveCardIds().stream()
                .distinct()
                .filter(init2.getPlayerObjectiveCardIds()::contains)
                .collect(Collectors.toSet());
        assertEquals(0, commonObjectives.size());

        assertEquals(0, init1.getStatus().getErrorCode());
        assertEquals(0, init2.getStatus().getErrorCode());

        assertEquals(GamePhase.INIT, init1.getStatus().getGamePhase());
        assertEquals(GamePhase.INIT, init1.getStatus().getGamePhase());
        // endregion

        // region PlaceStarterCardResponses
        System.out.println("-----PLACESTARTERCARD PHASE-----");
        var starterPlace1 = (PlaceStarterCardResponseData)this.call(
                firstClient,
                new PlaceStarterCardRequest(new PlaceStarterCardData(init1.getStarterCardId(), 0))
        ).getData();

        var starterPlace2 = (PlaceStarterCardResponseData)this.call(
                secondClient,
                new PlaceStarterCardRequest(new PlaceStarterCardData(init2.getStarterCardId(), 1))
        ).getData();

        assertNotNull(starterPlace1);
        assertNotNull(starterPlace2);
        assertEquals(init1.getStarterCardId(),starterPlace1.getCardId());
        assertEquals(init2.getStarterCardId(),starterPlace2.getCardId());
        assertEquals(0,starterPlace1.getFace());
        assertEquals(1,starterPlace2.getFace());

        assertEquals(0, starterPlace1.getStatus().getErrorCode());
        assertEquals(0, starterPlace2.getStatus().getErrorCode());

        assertEquals(GamePhase.INIT, init1.getStatus().getGamePhase());
        assertEquals(GamePhase.INIT, init1.getStatus().getGamePhase());
        assertFalse(init1.getIsBroadcast());
        assertFalse(init2.getIsBroadcast());

        // endregion

        // region SelectObjectivesResponses
        System.out.println("-----SELECTOBJECTIVES PHASE-----");
        var selectObjective1 = (SelectObjectiveResponseData)this.call(
                firstClient,
                new SelectObjectiveRequest(new SelectObjectiveData(init1.getPlayerObjectiveCardIds().getLast()))
        ).getData();

        var selectObjective2 = (SelectObjectiveResponseData)this.call(
                secondClient,
                new SelectObjectiveRequest(new SelectObjectiveData(init2.getPlayerObjectiveCardIds().getFirst()))
        ).getData();

        assertNotNull(selectObjective1);
        assertNotNull(selectObjective2);
        assertEquals(init1.getPlayerObjectiveCardIds().getLast(),selectObjective1.getObjective());
        assertEquals(init2.getPlayerObjectiveCardIds().getFirst(),selectObjective2.getObjective());

        assertEquals(0, selectObjective1.getStatus().getErrorCode());
        assertEquals(0, selectObjective2.getStatus().getErrorCode());

        assertEquals(GamePhase.INIT, selectObjective1.getStatus().getGamePhase());
        //After second the init is complete, phase placing
        assertEquals(GamePhase.PLACING, selectObjective2.getStatus().getGamePhase());

        assertFalse(selectObjective1.getIsBroadcast());
        assertFalse(selectObjective2.getIsBroadcast());
        // endregion

        // Leave lobby created by third client
        this.testCallExactMatch(
                thirdClient,
                new LeaveGameRequest(new LeaveGameData()),
                new LeaveGameResponse(new LeaveGameResponseData(new ResponseStatus(GamePhase.LOBBY, "", 0, ""), "Livio", Map.ofEntries(entry(1, 0)))));

        // region PlaceCardResponse first
        System.out.println("-----PLACECARD PHASE-----");
        var currentClient = Objects.equals(firstPlayer, "Andrea") ? firstClient : secondClient;
        var nextClient = Objects.equals(firstPlayer, "Lorenzo") ? firstClient : secondClient;
        var currentInit = Objects.equals(firstPlayer, "Andrea") ? init1 : init2;
        var nextInit = Objects.equals(firstPlayer, "Lorenzo") ? init1 : init2;
        var currentBoardSlot = Objects.equals(firstPlayer, "Andrea") ? starterPlace1.getBoardSlots() : starterPlace2.getBoardSlots();
        var nextBoardSlot = Objects.equals(firstPlayer, "Lorenzo") ? starterPlace1.getBoardSlots() : starterPlace2.getBoardSlots();

        PlaceCardData currentPlaceData = new PlaceCardData(
                currentInit.getPlayerHandCardIds().getFirst(),
                1,
                currentBoardSlot.get((int)(Math.random() * currentBoardSlot.size()))
        );

        var place1 = (PlaceCardResponseData)this.call(
                currentClient,
                new PlaceCardRequest(currentPlaceData)
        ).getData();

        assertNotNull(place1);
        assertNotNull(place1.getAvailableSlots());
        assertNotNull(place1.getPlacedSlot());

        assertEquals(0, place1.getStatus().getErrorCode());

        assertEquals(GamePhase.DRAWING, place1.getStatus().getGamePhase());
        assertFalse(place1.getIsBroadcast());
        assertEquals(firstPlayer, place1.getStatus().getCurrPlayer());
        // endregion

        // region DrawCardResponse first
        System.out.println("-----DRAWCARD PHASE-----");
        DrawCardData currentDrawData = new DrawCardData(0);  // ResourceCard

        var draw1 = (DrawCardResponseData)this.call(
                currentClient,
                new DrawCardRequest(currentDrawData)
        ).getData();

        assertNotNull(draw1);
        assertEquals(0,draw1.getDeck());
        assertNotEquals(0,draw1.getCardId());

        assertEquals(0, draw1.getStatus().getErrorCode());

        assertEquals(GamePhase.PLACING, draw1.getStatus().getGamePhase());
        assertFalse(draw1.getIsBroadcast());
        assertEquals(secondPlayer, draw1.getStatus().getCurrPlayer());
        // endregion

        // region PlaceCardResponse second
        System.out.println("-----PLACECARD PHASE-----");

        PlaceCardData nextPlaceData = new PlaceCardData(
                nextInit.getPlayerHandCardIds().getFirst(),
                1,
                nextBoardSlot.get((int)(Math.random() * nextBoardSlot.size()))
        );

        var place2 = (PlaceCardResponseData)this.call(
                nextClient,
                new PlaceCardRequest(nextPlaceData)
        ).getData();

        assertNotNull(place2);
        assertNotNull(place2.getAvailableSlots());
        assertNotNull(place2.getPlacedSlot());

        assertEquals(0, place2.getStatus().getErrorCode());

        assertEquals(GamePhase.DRAWING, place2.getStatus().getGamePhase());
        assertFalse(place2.getIsBroadcast());
        assertEquals(secondPlayer, place2.getStatus().getCurrPlayer());
        // endregion

        // region TakeCardResponse second
        System.out.println("-----TAKECARD PHASE-----");
        TakeCardData nextTakeData = new TakeCardData(
                init2.getVisibleGoldCardIds().getFirst(),
                1
        );  // GoldCard

        var take2 = (TakeCardResponseData)this.call(
                nextClient,
                new TakeCardRequest(nextTakeData)
        ).getData();

        assertNotNull(take2);
        assertEquals(init2.getVisibleGoldCardIds().getFirst(),take2.getTakenCardId());
        assertNotEquals(-1,take2.getShownCardId());
        assertEquals(1,take2.getType());

        assertEquals(0, take2.getStatus().getErrorCode());

        assertEquals(GamePhase.PLACING, take2.getStatus().getGamePhase());
        assertFalse(take2.getIsBroadcast());
        assertEquals(firstPlayer, take2.getStatus().getCurrPlayer());
        // endregion
    }

    /**
     * Method to test a generic call, it will be serialized to Json and compared the strings
     * @param caller the client Connection that does the call
     * @param request the request
     * @param expectedResponse the expected response
     */
    private void testCallExactMatch(ActionsRMI caller, JsonMessage request, JsonMessage<BaseResponseData> expectedResponse) {
        JsonMessage<BaseResponseData> res = this.call(caller, request);

        assert res != null;
        this.match(expectedResponse, res);
    }

    /**
     * Call method
     * @param caller caller
     * @param request request
     * @return the response
     */
    private JsonMessage<BaseResponseData> call (ActionsRMI caller, JsonMessage request) {
        try {
            return switch (request.getMethod()) {
                case JsonDeserializer.CREATE_LOBBY_METHOD -> new CreateLobbyResponse(caller.createLobby((CreateLobbyData) request.getData()));
                case JsonDeserializer.JOIN_LOBBY_METHOD -> new JoinLobbyResponse(caller.joinLobby((JoinLobbyData) request.getData()));
                case JsonDeserializer.LEAVE_GAME_METHOD -> new LeaveGameResponse(caller.leaveGame());
                case JsonDeserializer.INIT_GAME_METHOD -> new InitGameResponse(caller.initGame());
                case JsonDeserializer.SELECT_OBJECTIVE_METHOD -> new SelectObjectiveResponse(caller.selectObjective((SelectObjectiveData) request.getData()));
                case JsonDeserializer.PLACE_STARTER_CARD_METHOD -> new PlaceStarterCardResponse(caller.placeStarterCard((PlaceStarterCardData) request.getData()));
                case JsonDeserializer.PLACE_CARD_METHOD -> new PlaceCardResponse(caller.placeCard((PlaceCardData) request.getData()));
                case JsonDeserializer.DRAW_CARD_METHOD -> new DrawCardResponse(caller.drawCard((DrawCardData) request.getData()));
                case JsonDeserializer.TAKE_CARD_METHOD -> new TakeCardResponse(caller.takeCard((TakeCardData) request.getData()));
                default -> throw new NoSuchMethodException("no method " + request.getMethod());
            };
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Method to check that json match
     * @param expectedResponse expected
     * @param actualResponse actual
     */
    private void match(JsonMessage<BaseResponseData> expectedResponse, JsonMessage<BaseResponseData> actualResponse){
        try {
            assertEquals(expectedResponse.toJson(), actualResponse.toJson());
        } catch (JsonProcessingException e) {
            assert false;
        }
    }
}
