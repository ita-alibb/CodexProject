package it.polimi.ingsw.modelTests.deckTests;

import it.polimi.ingsw.am52.model.decks.RandomDealer;
import it.polimi.ingsw.am52.exceptions.DeckException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Unit test for RandomDealer class, when used without a seed
 * for the random numbers' generator.
 */
public class RandomDealerTest
{
    /**
     * Test functionality of the RandomDealer class:<lu>
     * <li> Constructor without a seed (also with an empty source list)
     * <li> Method getItemsCount()
     * <li> Method hasNext()
     * <li> Method init()
     * <li> Method getNextItem()</lu>
     */
    @Test
    @DisplayName("Dealer default constructor")
    public void testConstructor()
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
        } catch (DeckException ex) {
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

    @Test
    @DisplayName("Test peekNextItem()")
    public void testPeek() {

        // Create a list of integers.
        List<Integer> ints = new ArrayList<>(Arrays.stream((new Integer[] {1,2,3,4,5,6,7,8,9,10})).toList());

        // Create a RandomDealer object.
        RandomDealer<Integer> dealer = new RandomDealer<Integer>(ints);

        // Iterate over all items in the dealer.
        while (dealer.hasNext()) {

            // Store the items count.
            int counter = dealer.getItemsCount();

            // Peek next item. This gives a reference to the next object, but does not
            // remove the object from the list.
            Integer nextPeek = dealer.peekNextItem();

            // The counter should NOT be changed.
            assertEquals(counter, dealer.getItemsCount());

            // Now, get the next item. This time, the counter is decremented.
            Integer nextGet = dealer.getNextItem();

            // 1) Check the peek and get methods give the same item.
            assertEquals(nextPeek, nextGet);

            // 2) Check the counter has been decremented.
            assertEquals(counter-1, dealer.getItemsCount());

        }

        // Now the dealer is empty. Check for exception.
        assertThrows(DeckException.class, dealer::peekNextItem);

        // Now the dealer is empty. Check for exception.
        assertThrows(DeckException.class, dealer::getNextItem);

    }
}
