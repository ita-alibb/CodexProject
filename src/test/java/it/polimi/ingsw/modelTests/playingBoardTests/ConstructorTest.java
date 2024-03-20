package it.polimi.ingsw.modelTests.playingBoardTests;

import it.polimi.ingsw.am52.model.cards.StarterCard;
import it.polimi.ingsw.am52.model.cards.StarterCardFace;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.PlayingBoard;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class ConstructorTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConstructorTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConstructorTest.class );
    }

    /**
     * Test the constructor of the PlayingBoard class, passing
     * all possible starer cards (both faces):<ul>
     *     <li>check resources/items counters</li>
     *     <li>check number and positions of all available slots</li>
     *     <li>check method getStarterCard()</li>
     * </ul>
     *
     */
    public void testApp()
    {
        //region Starter Card #1 (id = 80)

        // Get the starter card.
        StarterCard card = StarterCard.getCardWithId(80);

        //region Front Face

        // Front face statistics
        // top-right corner:    PLANT
        // bottom-right corner: BLANK
        // bottom-left corner:  INSECT
        // top-left corner:     BLANK
        // permanent resource:  INSECT

        // Get front face.
        StarterCardFace cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        PlayingBoard board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(0, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(0, board.getResources().getAnimalCount());
        assertEquals(2, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        BoardSlot[] expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    PLANT
        // bottom-right corner: ANIMAL
        // bottom-left corner:  INSECT
        // top-left corner:     FUNGI
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion

        //region Starter Card #2 (id = 81)

        // Get the starter card.
        card = StarterCard.getCardWithId(81);

        //region Front Face

        // Front face statistics
        // top-right corner:    BLANK
        // bottom-right corner: FUNGI
        // bottom-left corner:  BLANK
        // top-left corner:     ANIMAL
        // permanent resource:  FUNGI

        // Get front face.
        cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(2, board.getResources().getFungiCount());
        assertEquals(0, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(0, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    ANIMAL
        // bottom-right corner: INSECT
        // bottom-left corner:  FUNGI
        // top-left corner:     PLANT
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion

        //region Starter Card #3 (id = 82)

        // Get the starter card.
        card = StarterCard.getCardWithId(82);

        //region Front Face

        // Front face statistics
        // top-right corner:    BLANK
        // bottom-right corner: BLANK
        // bottom-left corner:  BLANK
        // top-left corner:     BLANK
        // permanent resource:  FUNGI, PLANT

        // Get front face.
        cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(0, board.getResources().getAnimalCount());
        assertEquals(0, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    ANIMAL
        // bottom-right corner: PLANT
        // bottom-left corner:  FUNGI
        // top-left corner:     INSECT
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion

        //region Starter Card #4 (id = 83)

        // Get the starter card.
        card = StarterCard.getCardWithId(83);

        //region Front Face

        // Front face statistics
        // top-right corner:    BLANK
        // bottom-right corner: BLANK
        // bottom-left corner:  BLANK
        // top-left corner:     BLANK
        // permanent resource:  ANIMAL, INSECT

        // Get front face.
        cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(0, board.getResources().getFungiCount());
        assertEquals(0, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    INSECT
        // bottom-right corner: FUNGI
        // bottom-left corner:  ANIMAL
        // top-left corner:     PLANT
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion

        //region Starter Card #5 (id = 84)

        // Get the starter card.
        card = StarterCard.getCardWithId(84);

        //region Front Face

        // Front face statistics
        // top-right corner:    BLANK
        // bottom-right corner: <NONE>
        // bottom-left corner:  <NONE>
        // top-left corner:     BLANK
        // permanent resource:  PLANT, INSECT, ANIMAL

        // Get front face.
        cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(0, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(2, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    FUNGI
        // bottom-right corner: ANIMAL
        // bottom-left corner:  PLANT
        // top-left corner:     INSECT
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion

        //region Starter Card #6 (id = 85)

        // Get the starter card.
        card = StarterCard.getCardWithId(85);

        //region Front Face

        // Front face statistics
        // top-right corner:    BLANK
        // bottom-right corner: <NONE>
        // bottom-left corner:  <NONE>>
        // top-left corner:     BLANK
        // permanent resource:  FUNGI, ANIMAL, PLANT

        // Get front face.
        cardFace = card.getFrontFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(0, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(2, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //region Back Face

        // Back face statistics
        // top-right corner:    ANIMAL
        // bottom-right corner: INSECT
        // bottom-left corner:  PLANT
        // top-left corner:     FUNGI
        // permanent resource:  <NONE>

        // Get front face.
        cardFace = card.getBackFace();

        // Create a new PlayingBoard with starter card.
        board = new PlayingBoard(cardFace);

        // Check method getStarterCard().
        assertEquals(cardFace, board.getStarerCard());

        // Check resources/items counters.
        assertEquals(1, board.getResources().getFungiCount());
        assertEquals(1, board.getResources().getPlantCount());
        assertEquals(1, board.getResources().getAnimalCount());
        assertEquals(1, board.getResources().getInsectCount());
        assertEquals(0, board.getItems().getFeatherCount());
        assertEquals(0, board.getItems().getInkCount());
        assertEquals(0, board.getItems().getVellumCount());

        // Check available slots.
        assertEquals(4, board.getAvailableSlots().size());

        // Expected slot positions.
        expectedAvailable = new BoardSlot[] {
                new BoardSlot(1, 1),    // top-right
                new BoardSlot(1, -1),   // bottom-right
                new BoardSlot(-1, -1),    // bottom-left
                new BoardSlot(-1, 1),    // top-left
        };
        // Check that available slot contains all expected positions.
        assertTrue(
                board.getAvailableSlots().toList().containsAll(
                        Arrays.stream(expectedAvailable).toList()
                )
        );

        //endregion

        //endregion
    }
}
