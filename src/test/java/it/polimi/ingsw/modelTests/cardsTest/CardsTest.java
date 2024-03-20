package it.polimi.ingsw.modelTests.cardsTest;

import it.polimi.ingsw.am52.model.cards.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CardsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CardsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CardsTest.class );
    }

    /**
     * For all Starter, Resource, and Gold cards, test if:<ul>
     * <li>the number of available cards is correct
     * <li>all card's identifier is correct
     * <li>the kingdom of Resource/Gold cards is correct
     */
    public void testApp()
    {
        // The static method ResourceCard.getCards() return a list of
        // all resource cards, sorted as reported in the game manual.
        // Check the number of resource cards is equal to 40.
        assertEquals(40, ResourceCard.getCards().size());

        // Check the number of gold cards is equal to 40.
        assertEquals(40, GoldCard.getCards().size());

        // Check the number of starter cards is equal to 6.
        assertEquals(6, StarterCard.getCards().size());

        // Check the numerical identifier of each Resource card:
        // id are from 0 to 39 (40 cards).
        ImmutableList<ResourceCard> resourceCards = ResourceCard.getCards();
        for (int id = 0; id != resourceCards.size(); id++) {
            assertEquals(id, resourceCards.get(id).getCardId());
        }

        // Check the numerical identifier of each Gold card:
        // id are from 40 to 79 (40 cards).
        ImmutableList<GoldCard> goldCards = GoldCard.getCards();
        for (int id = 0; id != goldCards.size(); id++) {
            assertEquals(id + 40, goldCards.get(id).getCardId());
        }

        // Check the numerical identifier of each Starter card:
        // id are from 80 to 85 (6 cards).
        ImmutableList<StarterCard> starterCards = StarterCard.getCards();
        for (int id = 0; id != starterCards.size(); id++) {
            assertEquals(id + 80, starterCards.get(id).getCardId());
        }

        // The first 10 cards are of kingdom FUNGI.
        for (int i = 0; i != 10; i++) {
            assertEquals(Kingdom.FUNGI_KINGDOM, resourceCards.get(i).getKingdom());
            assertEquals(Kingdom.FUNGI_KINGDOM, goldCards.get(i).getKingdom());
        }

        // The cards from 10 to 19 are of kingdom PLANT.
        for (int i = 10; i != 20; i++) {
            assertEquals(Kingdom.PLANT_KINGDOM, resourceCards.get(i).getKingdom());
            assertEquals(Kingdom.PLANT_KINGDOM, goldCards.get(i).getKingdom());
        }

        // The cards from 20 to 29 are of kingdom ANIMAL.
        for (int i = 20; i != 30; i++) {
            assertEquals(Kingdom.ANIMAL_KINGDOM, resourceCards.get(i).getKingdom());
            assertEquals(Kingdom.ANIMAL_KINGDOM, goldCards.get(i).getKingdom());
        }

        // The cards from 30 to 39 are of kingdom INSECT.
        for (int i = 30; i != 40; i++) {
            assertEquals(Kingdom.INSECT_KINGDOM, resourceCards.get(i).getKingdom());
            assertEquals(Kingdom.INSECT_KINGDOM, goldCards.get(i).getKingdom());
        }
    }
}
