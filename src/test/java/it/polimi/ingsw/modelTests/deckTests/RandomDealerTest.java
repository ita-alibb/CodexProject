package it.polimi.ingsw.modelTests.deckTests;

import it.polimi.ingsw.am52.model.decks.RandomDealer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

/**
 * Unit test for RandomDealer class, when used without a seed
 * for the random numbers' generator.
 */
public class RandomDealerTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RandomDealerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RandomDealerTest.class );
    }

    /**
     * Test functionality of the RandomDealer class:<lu>
     * <li> Constructor without a seed (also with an empty source list)
     * <li> Method getItemsCount()
     * <li> Method hasNext()
     * <li> Method init()
     * <li> Method getNextItem()</lu>
     */
    public void testApp()
    {
        // Check constructor with an empty list.
        // Create an empty list.
        List<Object> emptyList = new ArrayList<>();
        // Instantiate a RandomDealer with that empty list.
        RandomDealer<Object> emptyDealer = new RandomDealer<>(emptyList);
        // The items count must be zero.
        assertEquals(0, emptyDealer.getItemsCount());
        // There a NO items to give.
        assertFalse(emptyDealer.hasNext());

        // Re-init the dealer.
        emptyDealer.init();
        // Re-test it.
        // The items count must be zero.
        assertEquals(0, emptyDealer.getItemsCount());
        // There a NO items to give.
        assertFalse(emptyDealer.hasNext());

        // Use a list of integers (from 100 to 105) for tests.
        List<Integer> numbers = new ArrayList<>(Arrays.stream((new Integer[]{100, 101, 102, 103, 104, 105})).toList());

        // Create a RandomDealer (of Integers), with default constructor.
        RandomDealer<Integer> dealer = new RandomDealer<>(numbers);

        // Check number of items, method getItemCount():
        // the returned value must be equal to the size of the source list.
        assertEquals(numbers.size(), dealer.getItemsCount());

        // Check the order of elements and hasNext() method:
        // The constructor must create a dealer with elements sorted as the
        // source list

        // Initialize the counter of picked elements.
        int counter = 0;
        while (true) {

            // Check number of remaining items in the dealer.
            if (counter == numbers.size()) {
                // If I have already picked all items, the dealer does not have any
                // further item di give.
                assertFalse(dealer.hasNext());

                // Exit while loop.
                break;
            } else {
                // There are further items in the dealer.
                assertTrue(dealer.hasNext());
            }

            // Pick the i-th element of the source list.
            int refValue = numbers.get(counter);

            // Pick the next item from the dealer.
            int value = dealer.getNextItem();
            // Increment the counter of picked elements.
            counter++;

            // Check that the picked item is equal to the reference item.
            assertEquals(refValue, value);

            // Check the number of remaining items in the dealer.
            assertEquals(numbers.size()-counter, dealer.getItemsCount());
        }

        // The dealer now is empty (no more items to give).
        assertFalse(dealer.hasNext());

        // Asking for a new item triggers an exception of type IllegalStateException.
        try {
            // Illegal method call.
            dealer.getNextItem();
            assert(false);
        } catch (IllegalStateException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Re-init the dealer.
        dealer.init();

        // Check the number of items: must be equal to the size of
        // source list.
        assertEquals(numbers.size(), dealer.getItemsCount());

        // The dealer must contain all elements.
        // Get all elements from the dealer, and store them in a Set.
        Set<Integer> numbersFromDealer = new HashSet<>();
        while (dealer.hasNext()) {
            numbersFromDealer.add(dealer.getNextItem());
        }
        // Check there are all source items.
        assertTrue(numbersFromDealer.containsAll(numbers));
    }
}
