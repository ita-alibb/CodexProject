package it.polimi.ingsw.modelTests.cardsTest;

import it.polimi.ingsw.am52.Model.cards.Item;
import it.polimi.ingsw.am52.Model.cards.ItemsCounter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Unit test for the ItemsCounter class.
 */
public class ItemsCounterTest
{
    /**
     * Test for class ItemsCounter:<ul>
     *     <li>Default constructor creates a zero-counter</li>
     *     <li>Constructors with int values for each item</li>
     *     <li>Constructor with enum Item</li>
     * </ul>
     */
    @Test
    @DisplayName("ItemsCounter constructors")
    public void constructorsTest()
    {
        // Random numbers generator.
        Random r = new Random();
        final int origin = -1000;
        final int bound = 1001;

        //region Default constructor

        // Create a new ItemsCounter via default constructor.
        ItemsCounter counter = new ItemsCounter();
        // Check all items are set to zero.
        assertEquals(0, counter.getFeatherCount());
        assertEquals(0, counter.getInkCount());
        assertEquals(0, counter.getVellumCount());

        //endregion

        //region Constructor Taking Integers

        // The desired items.
        int feathers = r.nextInt(origin, bound);
        int inks = r.nextInt(origin, bound);
        int vellum = r.nextInt(origin, bound);

        // Create a ResourcesCounter that counts the desired resources.
        counter = new ItemsCounter(feathers, inks, vellum);

        // Check all items are in the counter.
        assertEquals(feathers, counter.getFeatherCount());
        assertEquals(inks, counter.getInkCount());
        assertEquals(vellum, counter.getVellumCount());

        //endregion

        //region Constructor Taking a Resource

        // Create a counter that counts one feather item.
        counter = new ItemsCounter(Item.FEATHER);
        // Check feathers is 1, and all others are zero.
        assertEquals(1, counter.getFeatherCount());
        assertEquals(0, counter.getInkCount());
        assertEquals(0, counter.getVellumCount());

        // Create a counter that counts one ink item.
        counter = new ItemsCounter(Item.INK);
        // Check inks is 1, and all others are zero.
        assertEquals(0, counter.getFeatherCount());
        assertEquals(1, counter.getInkCount());
        assertEquals(0, counter.getVellumCount());

        // Create a counter that counts one vellum item.
        counter = new ItemsCounter(Item.VELLUM);
        // Check vellum is 1, and all others are zero.
        assertEquals(0, counter.getFeatherCount());
        assertEquals(0, counter.getInkCount());
        assertEquals(1, counter.getVellumCount());

        //endregion
    }

    /**
     * Test for class ItemsCounter:<ul>
     *     <li>Static method add(), addition between counters</li>
     * </ul>
     */
    @Test
    @DisplayName("ItemsCounter add()")
    public void addTest() {
        // Random numbers generator.
        Random r = new Random();
        final int origin = -1000;
        final int bound = 1001;

        //region Add() Static Method

        // Adding two empty counters gives an empty counter.
        ItemsCounter counter = ItemsCounter.add(new ItemsCounter(), new ItemsCounter());
        // Check all items are set to zero.
        assertEquals(0, counter.getFeatherCount());
        assertEquals(0, counter.getInkCount());
        assertEquals(0, counter.getVellumCount());

        // Items of the first counter.
        int feathers1 = r.nextInt(origin, bound);
        int inks1 = r.nextInt(origin, bound);
        int vellum1 = r.nextInt(origin, bound);
        // Instantiate the first counter.
        ItemsCounter c1 = new ItemsCounter(feathers1, inks1, vellum1);

        // Items of the second counter.
        int feathers2 = r.nextInt(origin, bound);
        int inks2 = r.nextInt(origin, bound);
        int vellum2 = r.nextInt(origin, bound);
        // Instantiate the second counter.
        ItemsCounter c2 = new ItemsCounter(feathers2, inks2, vellum2);

        // Add the two counters.
        ItemsCounter sum = ItemsCounter.add(c1, c2);

        // Check the sum of all items.
        assertEquals(feathers1+feathers2, sum.getFeatherCount());
        assertEquals(inks1+inks2, sum.getInkCount());
        assertEquals(vellum1+vellum2, sum.getVellumCount());

        //endregion
    }

    /**
     * Test for class ItemsCounter:<ul>
     *     <li>Static method subtract(), subtraction between counters</li>
     * </ul>
     */
    @Test
    @DisplayName("ItemsCounter subtract()")
    public void subtractTest() {
        // Random numbers generator.
        Random r = new Random();
        final int origin = -1000;
        final int bound = 1001;

        //region Static Method Subtract

        // Subtracting two empty counters gives an empty counter.
        ItemsCounter counter = ItemsCounter.add(new ItemsCounter(), new ItemsCounter());
        // Check all items are set to zero.
        assertEquals(0, counter.getFeatherCount());
        assertEquals(0, counter.getInkCount());
        assertEquals(0, counter.getVellumCount());

        // Items of the first counter.
        int feathers1 = r.nextInt(origin, bound);
        int inks1 = r.nextInt(origin, bound);
        int vellum1 = r.nextInt(origin, bound);
        // Instantiate the first counter.
        ItemsCounter c1 = new ItemsCounter(feathers1, inks1, vellum1);

        // Items of the second counter.
        int feathers2 = r.nextInt(origin, bound);
        int inks2 = r.nextInt(origin, bound);
        int vellum2 = r.nextInt(origin, bound);
        // Instantiate the second counter.
        ItemsCounter c2 = new ItemsCounter(feathers2, inks2, vellum2);

        // Subtract the two counters.
        ItemsCounter sub = ItemsCounter.subtract(c1, c2);

        // Check the subtraction of all items.
        assertEquals(feathers1-feathers2, sub.getFeatherCount());
        assertEquals(inks1-inks2, sub.getInkCount());
        assertEquals(vellum1-vellum2, sub.getVellumCount());

        //endregion
    }
}
