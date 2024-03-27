package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.model.cards.ItemsCounter;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Objective class, item objective with two equal items.
 */
public class SingleItemPatternTest {

    private static final int ORIGIN = 0;
    private static final int BOUND = 21;

    @Test
    @DisplayName("Single-Item Pattern Test: Single Item")
    public void singleResourcePatternTest() {

        // All single-item objectives (Id from 13 to 15).
        Objective[] targets = new Objective[] {
                Objective.getObjectiveWithId(13), // Two-Feathers
                Objective.getObjectiveWithId(14), // Two-Inks
                Objective.getObjectiveWithId(15), // Two-Vellum
        };

        // Get a dummy playing board (with zero items).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Check all objectives give zero points.
        for (Objective target : targets) {
            assertEquals(0, target.calculatePoints(board));
        }

        // Increment the feathers counter.
        int targetId = 0;
        for (int i = 1; i != 10; i++) {
            // The new item counter
            final ItemsCounter items =
                    new ItemsCounter(i, 0, 0);
            // Set items of the board.
            board.setItems(items);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 2) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }

        // Increment the inks counter.
        targetId = 1;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ItemsCounter items =
                    new ItemsCounter(0, i, 0);
            // Set items of the board.
            board.setItems(items);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 2) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }

        // Increment the vellum counter.
        targetId = 2;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ItemsCounter items =
                    new ItemsCounter(0, 0, i);
            // Set items of the board.
            board.setItems(items);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 2) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }
    }

    @Test
    @DisplayName("Single-Item Pattern Test: Multiple Items")
    public void multiResourcesPatternTest() {

        // All single-item objectives (Id from 13 to 15).
        Objective[] targets = new Objective[] {
                Objective.getObjectiveWithId(13), // Two-Feathers
                Objective.getObjectiveWithId(14), // Two-Inks
                Objective.getObjectiveWithId(15), // Two-Vellum
        };

        // Get a dummy playing board (with zero items).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Get a random generator.
        Random random = new Random();

        // Increment the feathers counter and assign random values to other items.
        int targetId = 0;
        for (int itemTarget = 1; itemTarget != 10; itemTarget++) {

            final int items1 = random.nextInt(ORIGIN, BOUND);
            final int items2 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ItemsCounter items =
                    new ItemsCounter(itemTarget, items1, items2);

            // Set resources of the board.
            board.setItems(items);

            // Expected points.
            final int expectedPoints = (itemTarget / 2) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }

        // Increment the inks counter and assign random values to other items.
        targetId = 1;
        for (int itemTarget = 1; itemTarget != 10; itemTarget++) {

            final int items1 = random.nextInt(ORIGIN, BOUND);
            final int items2 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ItemsCounter items =
                    new ItemsCounter(items1, itemTarget, items2);

            // Set resources of the board.
            board.setItems(items);

            // Expected points.
            final int expectedPoints = (itemTarget / 2) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }

        // Increment the inks counter and assign random values to other items.
        targetId = 2;
        for (int itemTarget = 1; itemTarget != 10; itemTarget++) {

            final int items1 = random.nextInt(ORIGIN, BOUND);
            final int items2 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ItemsCounter items =
                    new ItemsCounter(items1, items2, itemTarget);

            // Set resources of the board.
            board.setItems(items);

            // Expected points.
            final int expectedPoints = (itemTarget / 2) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }
    }
}
