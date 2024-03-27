package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.model.cards.ItemsCounter;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Objective class, item objective with multiple items.
 */
public class MultiItemsPatternTest {

    private static final int ORIGIN = 0;
    private static final int BOUND = 21;

    @Test
    @DisplayName("Multi-Item Pattern Test: Single Item")
    public void singleResourcePatternTest() {

        // The objective with one item of each type (id. 12).
        Objective target  =  Objective.getObjectiveWithId(12);

        // Get a dummy playing board (with zero items).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Check zero points
        assertEquals(0, target.calculatePoints(board));

        // Increment the feathers counter.
        for (int i = 1; i != 10; i++) {
            // The new item counter
            final ItemsCounter items =
                    new ItemsCounter(i, 0, 0);
            // Set items of the board.
            board.setItems(items);

            // Always zero points.
            assertEquals(0, target.calculatePoints(board));
        }

        // Increment the inks counter.
        for (int i = 1; i != 10; i++) {
            // The new item counter
            final ItemsCounter items =
                    new ItemsCounter(0, i, 0);
            // Set items of the board.
            board.setItems(items);

            // Always zero points.
            assertEquals(0, target.calculatePoints(board));
        }

        // Increment the vellum counter.
        for (int i = 1; i != 10; i++) {
            // The new item counter
            final ItemsCounter items =
                    new ItemsCounter(0, 0, i);
            // Set items of the board.
            board.setItems(items);

            // Always zero points.
            assertEquals(0, target.calculatePoints(board));
        }
    }

    @Test
    @DisplayName("Single-Item Pattern Test: Multiple Items")
    public void multiResourcesPatternTest() {

        // The objective with one item of each type (id. 12).
        Objective target  =  Objective.getObjectiveWithId(12);

        // Get a dummy playing board (with zero items).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Get a random generator.
        Random random = new Random();

        // Iterates a few times.
        for (int i = 0; i != 20; i++) {

            // Get random items.
            final int feathers = random.nextInt(ORIGIN, BOUND);
            final int inks = random.nextInt(ORIGIN, BOUND);
            final int vellum = random.nextInt(ORIGIN, BOUND);

            // Create an items counter.
            ItemsCounter items = new ItemsCounter(feathers, inks, vellum);

            // Set items of the board.
            board.setItems(items);

            // Expected points.
            final int expectedPoints = 3 * Math.min(Math.min(feathers, inks), vellum);

            // Check points.
            assertEquals(expectedPoints, target.calculatePoints(board));
        }
    }
}
