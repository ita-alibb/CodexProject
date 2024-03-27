package it.polimi.ingsw.modelTests.objectivesTests;

import it.polimi.ingsw.am52.model.cards.ResourcesCounter;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.modelTests.util.DummyPlayingBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Objective class, resource objective.
 */
public class ResourcePatternTest {

    private static final int ORIGIN = 0;
    private static final int BOUND = 21;

    @Test
    @DisplayName("Resource Pattern Test: Single Resource")
    public void singleResourcePatternTest() {

        // All resources objectives (Id from 8 to 11).
        Objective[] targets = new Objective[] {
                Objective.getObjectiveWithId(8), // Fungi
                Objective.getObjectiveWithId(9), // Plant
                Objective.getObjectiveWithId(10),// Animal
                Objective.getObjectiveWithId(11),// Insect
        };

        // Get a dummy playing board (with zero resources).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // Check all objectives give zero points.
        for (Objective target : targets) {
            assertEquals(0, target.calculatePoints(board));
        }

        // Increment the fungi counter.
        int targetId = 0;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(i, 0, 0, 0);
            // Set resources of the board.
            board.setResources(resources);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 3) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }

        // Increment the plant counter.
        targetId = 1;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(0, i, 0, 0);
            // Set resources of the board.
            board.setResources(resources);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 3) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }

        // Increment the animal counter.
        targetId = 2;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(0, 0, i, 0);
            // Set resources of the board.
            board.setResources(resources);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 3) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }

        // Increment the insect counter.
        targetId = 3;
        for (int i = 1; i != 10; i++) {
            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(0, 0, 0, i);
            // Set resources of the board.
            board.setResources(resources);

            for (int t = 0; t != targets.length; t++) {

                // Expected points.
                final int expectedPoints = (t == targetId) ? (i / 3) * 2 : 0;

                assertEquals(expectedPoints, targets[t].calculatePoints(board));
            }
        }
    }

    @Test
    @DisplayName("Resource Pattern Test: Multiple Resources")
    public void multiResourcesPatternTest() {

        // Get a dummy playing board (with zero resources).
        DummyPlayingBoard board = new DummyPlayingBoard();

        // All resources objectives (Id from 8 to 11).
        Objective[] targets = new Objective[] {
                Objective.getObjectiveWithId(8), // Fungi
                Objective.getObjectiveWithId(9), // Plant
                Objective.getObjectiveWithId(10),// Animal
                Objective.getObjectiveWithId(11),// Insect
        };

        // Get a random generator.
        Random random = new Random();

        // Increment the fungi counter and assign random values to other resources.
        int targetId = 0;
        for (int resourceTarget = 1; resourceTarget != 10; resourceTarget++) {

            final int res1 = random.nextInt(ORIGIN, BOUND);
            final int res2 = random.nextInt(ORIGIN, BOUND);
            final int res3 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(resourceTarget, res1, res2, res3);

            // Set resources of the board.
            board.setResources(resources);

            // Expected points.
            final int expectedPoints = (resourceTarget / 3) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }

        // Increment the plant counter and assign random values to other resources.
        targetId = 1;
        for (int resourceTarget = 1; resourceTarget != 10; resourceTarget++) {

            final int res1 = random.nextInt(ORIGIN, BOUND);
            final int res2 = random.nextInt(ORIGIN, BOUND);
            final int res3 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(res1, resourceTarget, res2, res3);
            // Set resources of the board.
            board.setResources(resources);

            // Expected points.
            final int expectedPoints = (resourceTarget / 3) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }

        // Increment the animal counter and assign random values to other resources.
        targetId = 2;
        for (int resourceTarget = 1; resourceTarget != 10; resourceTarget++) {

            final int res1 = random.nextInt(ORIGIN, BOUND);
            final int res2 = random.nextInt(ORIGIN, BOUND);
            final int res3 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(res1, res2, resourceTarget, res3);
            // Set resources of the board.
            board.setResources(resources);

            // Expected points.
            final int expectedPoints = (resourceTarget / 3) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }

        // Increment the insect counter and assign random values to other resources.
        targetId = 3;
        for (int resourceTarget = 1; resourceTarget != 10; resourceTarget++) {

            final int res1 = random.nextInt(ORIGIN, BOUND);
            final int res2 = random.nextInt(ORIGIN, BOUND);
            final int res3 = random.nextInt(ORIGIN, BOUND);

            // The new resource counter
            final ResourcesCounter resources =
                    new ResourcesCounter(res1, res2, res3, resourceTarget);
            // Set resources of the board.
            board.setResources(resources);

            // Expected points.
            final int expectedPoints = (resourceTarget / 3) * 2;
            assertEquals(expectedPoints, targets[targetId].calculatePoints(board));
        }
    }
}
