package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.PhaseException;
import it.polimi.ingsw.am52.model.game.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the behaviour of the methods of the class Phase.
 * For implementation reasons, the behaviour of this class cannot be tested in its entirety here; the complete tests
 * will be present in the test class GameManagerTest, where there will be the simulation of a turn.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhaseTest {
    private static Phase phase;
    private static GameManager manager;

    @BeforeAll
    public static void setUp() {
        //Create a new instance of Phase, to test the getters
        phase = new InitPhase("Andrea");
        //Create an instance of GameManager, to test the passages of phases
        List<String> players = new ArrayList<>();
        players.add("Andrea");
        players.add("Livio");
        players.add("William");
        players.add("Lorenzo");
        manager = new GameManager(players);
    }

    @Test
    @Order(1)
    @DisplayName("Getters test")
    public void gettersTest() {
        assertEquals(GamePhase.INIT, phase.getPhase());
        assertEquals("Andrea", phase.getCurrPlayer());
        assertEquals(0, phase.getTurn());
        assertFalse(phase.isLastTurn());
    }

    @Test
    @Order(2)
    @DisplayName("INIT phase test")
    public void initPhaseTest() {
        //In the first place, we will know that the manager has the phase INIT phase
        assertEquals(GamePhase.INIT, manager.getStatus());
        //We call the method next on manager, to change the phase to the next, using the flow of operations
        phase.next(manager);
        assertEquals(GamePhase.PLACING, manager.getStatus());
    }

    @Test
    @Order(3)
    @DisplayName("PLACING phase test")
    public void placePhaseTest() {
        assertEquals(GamePhase.PLACING, manager.getStatus());
        //Update the used phase.
        phase = new PlacingPhase(phase);
        //We call the method to change the phase, which now should be DRAWING
        phase.next(manager);
        assertEquals(GamePhase.DRAWING, manager.getStatus());
    }

    @Test
    @Order(4)
    @DisplayName("DRAWING phase test")
    public void drawPhaseTest() {
        assertEquals(GamePhase.DRAWING, manager.getStatus());
        //Update the used phase
        phase = new DrawingPhase(phase);
        //We call the method to change the phase, which now should be again PLACING
        phase.next(manager);
        assertEquals(GamePhase.PLACING, manager.getStatus());
    }

    @Test
    @Order(5)
    @DisplayName("ENDING phase test")
    public void endingPhaseTest() {
        assertEquals(GamePhase.PLACING, manager.getStatus());
        //Update the used phase
        phase = new PlacingPhase(phase);
        phase.next(manager);
        assertEquals(GamePhase.DRAWING, manager.getStatus());
        //Only from the drawing phase we can reach the EndingPhase
        phase = new DrawingPhase(phase);
        phase.next(manager);
        //Now we will obtain the placing phase, because the game in GameManager is not over yet
        assertEquals(GamePhase.PLACING, manager.getStatus());
        //If we try to update to an Ending Phase, we will receive an exception
        phase = new EndingPhase(phase);
        PhaseException exception = assertThrows(PhaseException.class, () -> phase.next(manager));
        //EXCEPTION: The Ending phase isn't reachable yet
        String expectedMessage = "Incorrect phase";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
