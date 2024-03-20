package it.polimi.ingsw.modelTests.cardsTest;

import it.polimi.ingsw.am52.model.cards.Resource;
import it.polimi.ingsw.am52.model.cards.ResourcesCounter;
import it.polimi.ingsw.am52.model.decks.RandomDealer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class ResourcesCounterTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ResourcesCounterTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ResourcesCounterTest.class );
    }

    /**
     * Test for class ResourcesCounter:<ul>
     *     <li>Default constructor creates a zero-counter</li>
     *     <li>Constructors with int values for each resource</li>
     *     <li>Constructor with enum Resource</li>
     *     <li>Static method add(), addition between counters</li>
     *     <li>Static method subtract(), subtraction between counters</li>
     * </ul>
     */
    public void testApp()
    {
        // Random numbers generator.
        Random r = new Random();
        final int origin = -1000;
        final int bound = 1001;

        //region Default constructor

        // Create a new ResourcesCounter via default constructor.
        ResourcesCounter counter = new ResourcesCounter();
        // Check all resources are set to zero.
        assertEquals(0, counter.getFungiCount());
        assertEquals(0, counter.getPlantCount());
        assertEquals(0, counter.getAnimalCount());
        assertEquals(0, counter.getInsectCount());

        //endregion

        //region Constructor Taking Integers

        // The desired resources.
        int fungi = r.nextInt(origin, bound);
        int plants = r.nextInt(origin, bound);
        int animals = r.nextInt(origin, bound);
        int insects = r.nextInt(origin, bound);

        // Create a ResourcesCounter that counts the desired resources.
        counter = new ResourcesCounter(fungi, plants, animals, insects);

        // Check all resources are in the counter.
        assertEquals(fungi, counter.getFungiCount());
        assertEquals(plants, counter.getPlantCount());
        assertEquals(animals, counter.getAnimalCount());
        assertEquals(insects, counter.getInsectCount());

        //endregion

        //region Constructor Taking a Resource

        // Create a counter that counts one fungi resource.
        counter = new ResourcesCounter(Resource.FUNGI);
        // Check fungi is 1, and all others are zero.
        assertEquals(1, counter.getFungiCount());
        assertEquals(0, counter.getPlantCount());
        assertEquals(0, counter.getAnimalCount());
        assertEquals(0, counter.getInsectCount());

        // Create a counter that counts one plant resource.
        counter = new ResourcesCounter(Resource.PLANT);
        // Check plant is 1, and all others are zero.
        assertEquals(0, counter.getFungiCount());
        assertEquals(1, counter.getPlantCount());
        assertEquals(0, counter.getAnimalCount());
        assertEquals(0, counter.getInsectCount());

        // Create a counter that counts one animal resource.
        counter = new ResourcesCounter(Resource.ANIMAL);
        // Check animal is 1, and all others are zero.
        assertEquals(0, counter.getFungiCount());
        assertEquals(0, counter.getPlantCount());
        assertEquals(1, counter.getAnimalCount());
        assertEquals(0, counter.getInsectCount());

        // Create a counter that counts one insect resource.
        counter = new ResourcesCounter(Resource.INSECT);
        // Check insect is 1, and all others are zero.
        assertEquals(0, counter.getFungiCount());
        assertEquals(0, counter.getPlantCount());
        assertEquals(0, counter.getAnimalCount());
        assertEquals(1, counter.getInsectCount());

        //endregion

        //region Add() Static Method

        // Adding two empty counters gives an empty counter.
        counter = ResourcesCounter.add(new ResourcesCounter(), new ResourcesCounter());
        // Check all resources are set to zero.
        assertEquals(0, counter.getFungiCount());
        assertEquals(0, counter.getPlantCount());
        assertEquals(0, counter.getAnimalCount());
        assertEquals(0, counter.getInsectCount());

        // Resources of the first counter.
        int fungi1 = r.nextInt(origin, bound);
        int plant1 = r.nextInt(origin, bound);
        int animal1 = r.nextInt(origin, bound);
        int insect1 = r.nextInt(origin, bound);
        // Instantiate the first counter.
        ResourcesCounter c1 = new ResourcesCounter(fungi1, plant1, animal1, insect1);

        // Resources of the second counter.
        int fungi2 = r.nextInt(origin, bound);
        int plant2 = r.nextInt(origin, bound);
        int animal2 = r.nextInt(origin, bound);
        int insect2 = r.nextInt(origin, bound);
        // Instantiate the second counter.
        ResourcesCounter c2 = new ResourcesCounter(fungi2, plant2, animal2, insect2);

        // Add the two counters.
        ResourcesCounter sum = ResourcesCounter.add(c1, c2);

        // Check the sum of all resources.
        assertEquals(fungi1+fungi2, sum.getFungiCount());
        assertEquals(plant1+plant2, sum.getPlantCount());
        assertEquals(animal1+animal2, sum.getAnimalCount());
        assertEquals(insect1+insect2, sum.getInsectCount());

        //endregion

        //region Static Method Subtract

        // Subtract the two counters.
        ResourcesCounter sub = ResourcesCounter.subtract(c1, c2);

        // Check the subtraction of all resources.
        assertEquals(fungi1-fungi2, sub.getFungiCount());
        assertEquals(plant1-plant2, sub.getPlantCount());
        assertEquals(animal1-animal2, sub.getAnimalCount());
        assertEquals(insect1-insect2, sub.getInsectCount());

        //endregion
    }
}
