package it.polimi.ingsw.modelTests.deckTests;

import it.polimi.ingsw.am52.Model.decks.RandomDealer;
import it.polimi.ingsw.am52.Exceptions.DeckException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

/**
 * Unit test for RandomDealer class, when used with a seed
 * for the random numbers' generator.
 */
public class RandomDealerWIthSeedTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RandomDealerWIthSeedTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RandomDealerWIthSeedTest.class );
    }

    /**
     * Test functionality of the RandomDealer class:
     * </ul>
     * <li> Constructor with a seed
     * <li> After calling init(), all subsequent calls gives same order of items
     * <li> Two instances creates with the same seed give same items
     * </ul>
     */
    public void testApp()
    {
        // The seed used.
        final int seed = 1;

        // Use a list of integers (from 100 to 105) for tests.
        List<Integer> numbers = new ArrayList<>(Arrays.stream((new Integer[]{100, 101, 102, 103, 104, 105})).toList());

        // Create a RandomDealer (of Integers), passing the seed to the constructor.
        RandomDealer<Integer> dealer = new RandomDealer<>(numbers, seed);

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
        } catch (DeckException ex) {
            assert(true);
        } catch (Exception allEx) {
            assert(false);
        }

        // Re-init the dealer (first time).
        dealer.init();

        // Check the number of items: must be equal to the size of
        // source list.
        assertEquals(numbers.size(), dealer.getItemsCount());

        // The dealer must contain all elements.
        // Get all elements from the dealer, and store them in a List.
        List<Integer> numbersFromDealer = new ArrayList<>();
        while (dealer.hasNext()) {
            numbersFromDealer.add(dealer.getNextItem());
        }
        // Check there are all source items.
        assertTrue(numbersFromDealer.containsAll(numbers));

        // Re-init the dealer (first time).
        dealer.init();

        // Re-check numbers of items.
        assertEquals(numbers.size(), dealer.getItemsCount());

        // The order of all items must be identical to their order
        // after first init() invocation.

        // Get all elements from the dealer, and store them in a Set.
        List<Integer> numbersFromDealerSecond = new ArrayList<>();
        while (dealer.hasNext()) {
            numbersFromDealerSecond.add(dealer.getNextItem());
        }

        // At each list position, the item must be equal in both list
        // (first and second list).
        for (int i = 0; i != numbersFromDealer.size(); i++) {
            assertEquals(
                    numbersFromDealer.get(i),
                    numbersFromDealerSecond.get(i)
            );
        }

        // Create a different instance of the dealer, with same source
        // list and same seed.
        RandomDealer<Integer> dealer2 = new RandomDealer<>(numbers, seed);

        // Initialize (shuffle) both instances.
        dealer.init();
        dealer2.init();

        // Check number of items.
        assertEquals(dealer.getItemsCount(), dealer2.getItemsCount());

        // Pick all items and check are the same in both dealer.
        while (dealer.hasNext()) {
            // Pick from dealer #1
            int n1 = dealer.getNextItem();
            // Pick from dealer #2
            int n2 = dealer2.getNextItem();

            // Check for equality.
            assertEquals(n1, n2);

        }
    }
}
