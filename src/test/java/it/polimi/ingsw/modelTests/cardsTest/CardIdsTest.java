package it.polimi.ingsw.modelTests.cardsTest;

import it.polimi.ingsw.am52.model.cards.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CardIdsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CardIdsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CardIdsTest.class );
    }

    /**
     * Test if the static methods getCardWithId() works properly.
     */
    public void testApp()
    {
        //region Starter Cards

        // Test the static method StarterCard.getCardWithId().
        // Check the method returns the correct card.
        int startId = 80;
        int lastId = 85;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            StarterCard card =
                    StarterCard.getCards().toList().
                            stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, StarterCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            StarterCard.getCardWithId(startId-1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Check out of bounds (upper).
        try {
            StarterCard.getCardWithId(lastId+1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert(false);
        }

        //endregion

        //region Resource Cards

        // Test the static method ResourceCard.getCardWithId().
        // Check the method returns the correct card.
        startId = 0;
        lastId = 39;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            ResourceCard card =
                    ResourceCard.getCards().toList().
                            stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, ResourceCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            ResourceCard.getCardWithId(startId-1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Check out of bounds (upper).
        try {
            ResourceCard.getCardWithId(lastId+1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert(false);
        }

        //endregion

        //region Gold Cards

        // Test the static method GoldCard.getCardWithId().
        // Check the method returns the correct card.
        startId = 40;
        lastId = 79;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            GoldCard card =
                    GoldCard.getCards().toList().
                            stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, GoldCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            GoldCard.getCardWithId(startId-1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Check out of bounds (upper).
        try {
            GoldCard.getCardWithId(lastId+1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert(false);
        }

        //endregion

        //region Kingdom Cards

        // Test the static method KingdomCard.getCardWithId().
        // Check the method returns the correct card.
        startId = 0;
        lastId = 79;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            KingdomCard card =
                    id < 40 ?
                            ResourceCard.getCards().toList().
                                    stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null) :
                            GoldCard.getCards().toList().
                                    stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, KingdomCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            KingdomCard.getCardWithId(startId-1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Check out of bounds (upper).
        try {
            KingdomCard.getCardWithId(lastId+1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert(false);
        }

        //endregion

    }
}
