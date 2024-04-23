package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.CardSide;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.model.game.GamePhase;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the behaviour of GameManager
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameManagerTest {
    private static List<String> players;
    private static GameManager manager;

    /**
     * Instantiate the List of Player, which comes from the Controller
     */
    @BeforeEach
    public void setUp() {
        players = new ArrayList<>();
        //Creating a list of nicknames
        players.add("Andrea");      //PlayerId: 0
        players.add("Livio");       //PlayerId: 1
        players.add("Lorenzo");     //PlayerId: 2
        players.add("William");     //PlayerId: 3
        //Create the object GameManager
        manager = new GameManager(players);
    }
    /**
     * Test on some exceptions thrown by the constructor, to add in future
     */
    @Test
    @Order(1)
    @DisplayName("Test GameManager constructor exceptions")
    public void constructorExceptionTest() {
        //Add a player to the list
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
    @DisplayName("Test GameManager Getters and Setters")
    public void gettersTest() {
        //Players must be 4
        assertEquals(players.size(), manager.getPlayersCount());
        //The given players are in the game; they are shuffled, so the order in players could be different
        for (int i = 0; i < players.size(); i++) {
            assertTrue(players.contains(manager.getPlayer(i).getNickname()));
        }
        //There aren't other players in the lobby but the ones given
        for (int i = 0; i < players.size(); i++) {
            assertNotEquals("Unknown", manager.getPlayer(i).getNickname());
        }
        //Starting the game, the game phase should be "INIT"
        assertEquals(GamePhase.INIT, manager.getStatus());
        //The initial score of all players is zero
        assertEquals(0, manager.getScoreBoard().get(0));
        assertEquals(0, manager.getScoreBoard().get(1));
        assertEquals(0, manager.getScoreBoard().get(2));
        assertEquals(0, manager.getScoreBoard().get(3));
        //The secret objective at this point should be null
        assertNull(manager.getPlayer(0).getObjective());
        //If we choose the Objective, this field should be occupied, and not null
        manager.setPlayerChosenObject(0, manager.getPlayerObjectiveOptions(0).getFirst().getObjectiveId());
        assertNotNull(manager.getPlayer(0).getObjective());
        //To make sure we modify only the objective of the given player, the others' objectives should be null at the moment
        assertNull(manager.getPlayer(1).getObjective());
        //If we associate an objective to a Player, this is certainly different from the one of another player
        manager.setPlayerChosenObject(1, manager.getPlayerObjectiveOptions(1).getFirst().getObjectiveId());
        assertNotEquals(manager.getPlayer(0).getObjective(), manager.getPlayer(1).getObjective());
        //Let's associate an objective to the other two players, and the game phase should change to DRAWING, because the game is starting
        manager.setPlayerChosenObject(2, manager.getPlayerObjectiveOptions(2).getFirst().getObjectiveId());
        manager.setPlayerChosenObject(3, manager.getPlayerObjectiveOptions(3).getFirst().getObjectiveId());
        assertEquals(GamePhase.PLACING, manager.getStatus());
    }
    /**
     * Test disconnection
     */
    @Test
    @Order(3)
    @DisplayName("Test GameManager disconnections system")
    public void disconnectionTest() {
        //PlayerId 2, image of the Player "Lorenzo", disconnect
        String disconnectedPlayer = manager.getPlayer(2).getNickname();
        manager.leaveGame(2);
        //The player is no more in the list of players, and playersCount is 3 and no more 4
        assertEquals(3, manager.getPlayersCount());
        for (int i = 0; i < manager.getPlayersCount(); i++) {
            assertNotEquals(disconnectedPlayer, manager.getPlayer(i).getNickname());
        }
        //Let's check if the ScoreBoard is correctly updated
        assertNull(manager.getScoreBoard().get(2));
    }
    /**
     * Test on drawing cards
     */
    @Test
    @Order(4)
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
    @Order(5)
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

        //INIT phase

        //After the initialization of the object GameManager, each player will choose the face of his starter card and the secret objective
        for (int i = 0; i < manager.getPlayersCount(); i++) {
            //While we are in the for, the game phase is INIT
            assertEquals(GamePhase.INIT, manager.getStatus());
            //After this passage, the PlayingBoard is null, so is generated an exception
            int finalI = i;
            PlayingBoardException exception = assertThrows(PlayingBoardException.class, () -> manager.getPlayer(finalI).getPlayingBoard());
            String expectedMessage = "The PlayingBoard for the player is not instantiated";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
            //Let's place the starter card
            manager.placeStarterCard(
                    i,
                    manager.getPlayer(i).getStarterCard().getCardId(),
                    CardSide.FRONT
            );
            //Now, if we invoke the same methods of before, this value will not be null
            assertNotNull(manager.getPlayer(i).getPlayingBoard());
            //Let's set the objective of each player
            manager.setPlayerChosenObject(i, manager.getPlayerObjectiveOptions(i).getFirst().getObjectiveId());
            //Check if the objective has been inserted
            assertNotNull(manager.getPlayer(i).getObjective());
        }

        //GAME phase (PLACING-DRAWING)

        //The nickname of the first player to play, for a test on the order of the turn
        String firstPlayer = manager.getCurrentPlayer().getNickname();

        for (int i = 0; i < manager.getPlayersCount(); i++) {
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
    @Order(6)
    @DisplayName("Test on another simulated game with errors")
    public void turnWithErrorsTest() {
        /*
         * We will do the same thing as above, but this time I will insert some kinds of errors, to see if the game handles them
         * in the correct way. The errors will be indicated when done
         */

        //INIT phase

        //After the initialization of the object GameManager, each player will choose the face of his starter card and the secret objective
        for (int i = 0; i < manager.getPlayersCount(); i++) {
            //While we are in the for, the game phase is INIT
            assertEquals(GamePhase.INIT, manager.getStatus());
            //After this passage, the PlayingBoard is null, so is generated an exception
            int finalI = i;
            PlayingBoardException exception = assertThrows(PlayingBoardException.class, () -> manager.getPlayer(finalI).getPlayingBoard());
            String expectedMessage = "The PlayingBoard for the player is not instantiated";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
            //Let's place the starter card
            manager.placeStarterCard(
                    i,
                    manager.getPlayer(i).getStarterCard().getCardId(),
                    CardSide.FRONT
            );
            //Now, if we invoke the same methods of before, this value will not be null
            assertNotNull(manager.getPlayer(i).getPlayingBoard());
            //Let's set the objective of each player
            manager.setPlayerChosenObject(i, manager.getPlayerObjectiveOptions(i).getFirst().getObjectiveId());
            //Check if the objective has been inserted
            assertNotNull(manager.getPlayer(i).getObjective());
        }

        //GAME phase (PLACING-DRAWING)

        //The nickname of the first player to play, for a test on the order of the turn
        String firstPlayer = manager.getCurrentPlayer().getNickname();

        for (int i = 0; i < manager.getPlayersCount(); i++) {
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