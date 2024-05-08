package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.model.player.PlayerInfo;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the behaviour of GameManager
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameManagerTest {
    private static List<String> initList;
    private static GameManager manager;

    /**
     * Instantiate the List of Player, which comes from the Controller
     */
    @BeforeAll
    public static void setUp() {
        initList = new ArrayList<String>();
        //Creating a list of nicknames
        initList.add("Andrea");      //PlayerId: 0
        initList.add("Livio");       //PlayerId: 1
        initList.add("Lorenzo");     //PlayerId: 2
        initList.add("William");     //PlayerId: 3
        //Create the object GameManager
        manager = new GameManager(initList);
    }
    /**
     * Test on some exceptions thrown by the constructor, to add in future
     */
    @Test
    @Order(1)
    @DisplayName("Test GameManager constructor exceptions")
    public void constructorExceptionTest() {
        //Add a player to the list
        var players = new ArrayList<String>();
        //Creating a list of nicknames
        players.add("Andrea");      //PlayerId: 0
        players.add("Livio");       //PlayerId: 1
        players.add("Lorenzo");     //PlayerId: 2
        players.add("William");
        players.add("Not expected");
        //Catch the exception
        GameException exception = assertThrows(GameException.class, () -> new GameManager(players));
        //EXCEPTION: 5 players are trying to connect to the game!
        String expectedMessage = "Too many players";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        //If the list is empty, another exception is thrown
        players.clear();
        //Catch the exception
        GameException exception2 = assertThrows(GameException.class, () -> new GameManager(players));
        //EXCEPTION: the list is empty
        expectedMessage = "Player list is empty";
        actualMessage = exception2.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
    /**
     * Test GameManager Getters
     */
    @Test
    @Order(2)
    @DisplayName("Test GameManager Init")
    public void InitTest() {
        //Players must be 4
        assertEquals(4, manager.getPlayersCount());
        //The given players are in the game; they are shuffled, so the order in players could be different
        for (String nickname : initList) {
            assertNotNull(manager.getPlayer(nickname));
        }

        //The initial score of all players is zero
        assertTrue(manager.getScoreBoard().values().stream().allMatch(s -> s == 0));

        for (String nickname : manager.getPlayerInfos().stream().map(PlayerInfo::getNickname).toList()) {
            // The game is still in INIT
            assertEquals(GamePhase.INIT, manager.getStatus());
            //Objective INIT
            //The secret objective at this point should be null
            assertNull(manager.getPlayer(nickname).getObjective());

            //If we choose the Objective, this field should be occupied, and not null
            manager.setPlayerChosenObject(nickname, manager.getPlayerObjectiveOptions(nickname).getFirst().getObjectiveId());
            assertNotNull(manager.getPlayer(nickname).getObjective());

            //PlayingBoard INIT
            PlayingBoardException exception = assertThrows(PlayingBoardException.class, () -> manager.getPlayer(nickname).getPlayingBoard());
            String expectedMessage = "The PlayingBoard for the player is not instantiated";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
            //Let's place the starter card
            manager.placeStarterCard(
                    nickname,
                    manager.getPlayer(nickname).getStarterCard().getCardId(),
                    CardSide.FRONT
            );
            //Now, if we invoke the same methods of before, this value will not be null
            assertNotNull(manager.getPlayer(nickname).getPlayingBoard());
        }

        //The game phase should change to PLACING, because the game is starting
        assertEquals(GamePhase.PLACING, manager.getStatus());
    }

    /**
     * Test on drawing cards
     */
    @Test
    @Order(3)
    @DisplayName("Test GameManager cards handler")
    public void cardsHandlerTest() {
        /*
         * Let's check the number of cards after the creation of the object GameManager.
         * In the entire game there are:
         *      -40 resource cards
         *      -40 gold cards
         *      -6 starter cards
         *      -16 objective cards
         * After the creation of the GameManager, we assign 2 resource cards and 1 gold card to each player. So
         *      -2 * 4 = 8 resource cards used
         *      -1 * 4 = 4 gold cards used
         * In addition to this, there are 2 resource cards and 2 gold cards which are uncovered.
         * Finally, we will have 2 decks, for resource and gold cards, with the expected cards:
         *      -Resource cards:    40 - (8 + 2) = 30
         *      -Gold cards:        40 - (4 + 2) = 34
         * Starter cards and Objective cards are only useful in the INIT phase, so are local variables in the constructor. No need to test them.
         */
        assertEquals(30, manager.getResourceDeckCount());
        assertEquals(34, manager.getGoldDeckCount());
        //Obviously, the List of visible cards should be 2, even after we draw one of them
        assertEquals(2, manager.getVisibleResourceCards().size());
        assertEquals(2, manager.getVisibleGoldCards().size());
        //The common objective cards are two, and are the same during all the game
        assertEquals(2, manager.getCommonObjectives().size());
    }

    @Test
    @Order(4)
    @DisplayName("Test on a simulated game turn")
    public void turnTest() {
        /*
         * With this test, we want to simulate a real game turn, with the following phases:
         *      -INIT, the first phase of initialization of all the players and the connected Objects, i.e. the PlayingBoard,
         *          the objective card, and the start of the game
         *      -PLACING, the phase when a player place a card in one of his available slots
         *      -DRAWING, the phase when a player draw his card from the deck he chooses
         * This test will be done with 4 players, so that we are sure that all the phases are coherently connected.
         */

        //GAME phase (PLACING-DRAWING)

        //The nickname of the first player to play, for a test on the order of the turn
        String firstPlayer = manager.getCurrentPlayer().getNickname();

        for (String nickname : manager.getPlayerInfos().stream().map(PlayerInfo::getNickname).toList()) {
            // Check correct turn
            assertEquals(nickname, manager.getCurrentPlayer().getNickname());

            //Once we are out of this loop, the game phase has changed into PLACING
            assertEquals(GamePhase.PLACING, manager.getStatus());
            //Now, we will use the value of currPlayer in GameManager to know which player is playing a turn.
            //The first player will place the first card in his hand, and will draw a resource card
            manager.placeCard(
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getHoriz(),
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getVert(),
                    manager.getCurrentPlayer().getHand().get(0).getCardId(),
                    1
            );
            //Now, the new game phase is DRAWING
            assertEquals(GamePhase.DRAWING, manager.getStatus());
            //The player will draw a card from the resource cards deck
            manager.drawResourceCard();
        }
        //Out of the for, the phase is PLACING, and the current player will be again the first player who played, because turn 0 ended
        assertEquals(GamePhase.PLACING, manager.getStatus());
        assertEquals(firstPlayer, manager.getCurrentPlayer().getNickname());
    }

    @Test
    @Order(5)
    @DisplayName("Test on another simulated game with errors")
    public void turnWithErrorsTest() {
        /*
         * We will do the same thing as above, but this time I will insert some kinds of errors, to see if the game handles them
         * in the correct way. The errors will be indicated when done
         */

        //GAME phase (PLACING-DRAWING)

        //The nickname of the first player to play, for a test on the order of the turn
        String firstPlayer = manager.getCurrentPlayer().getNickname();

        for (String nickname : manager.getPlayerInfos().stream().map(PlayerInfo::getNickname).toList()) {
            // Assert the turn change correctly
            assertEquals(nickname, manager.getCurrentPlayer().getNickname());
            //Once we are out of this loop, the game phase has changed into PLACING
            assertEquals(GamePhase.PLACING, manager.getStatus());
            //ERROR: The Player wants to place a card which is not in his hand
            GameException exception = assertThrows(GameException.class, () -> manager.placeCard(manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getHoriz(),
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getVert(),
                    manager.getVisibleResourceCards().getFirst(),
                    1));
            String expectedMessage = "Trying to place a card that is not on the player's hand";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
            //Now, we will use the value of currPlayer in GameManager to know which player is playing a turn.
            //The player will place the first card in his hand, and will draw a resource card.
            manager.placeCard(
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getHoriz(),
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getVert(),
                    manager.getCurrentPlayer().getHand().get(0).getCardId(),
                    1
            );
            //ERROR: Placing another card during the same turn, in the wrong phase of the game
            GameException exception1 = assertThrows(GameException.class, () -> manager.placeCard(
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getHoriz(),
                    manager.getCurrentPlayer().getPlayingBoard().getAvailableSlots().get(0).getVert(),
                    manager.getCurrentPlayer().getHand().get(0).getCardId(),
                    1
            ));
            String expectedMessage1 = "Incorrect phase";
            actualMessage = exception1.getMessage();
            assertEquals(expectedMessage1, actualMessage);
            //Now, the new game phase is DRAWING
            assertEquals(GamePhase.DRAWING, manager.getStatus());
            //The player will draw a card from the resource cards deck
            manager.drawResourceCard();
            //ERROR: The player draws another card!
            GameException exception2 = assertThrows(GameException.class, () -> manager.drawResourceCard());
            actualMessage = exception2.getMessage();
            assertEquals(expectedMessage1, actualMessage);
        }
        //Out of the for, the phase is PLACING, and the current player will be again the first player who played, because turn 0 ended
        assertEquals(GamePhase.PLACING, manager.getStatus());
        assertEquals(firstPlayer, manager.getCurrentPlayer().getNickname());
    }
}