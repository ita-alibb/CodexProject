package it.polimi.ingsw.modelTests.cardsTest;

import it.polimi.ingsw.am52.Model.cards.*;
import it.polimi.ingsw.am52.Exceptions.CardException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Card classes.
 */
public class CardIdsTest {
    /**
     * Test if the static methods getCardWithId() works properly
     * for the starter cards.
     */
    @Test
    @DisplayName("Cards Ids of Starter Cards")
    public void starterCardsIds() {

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
            StarterCard.getCardWithId(startId - 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Check out of bounds (upper).
        try {
            StarterCard.getCardWithId(lastId + 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
    }

    /**
     * Test if the static methods getCardWithId() works properly
     * for the resource cards.
     */
    @Test
    @DisplayName("Cards Ids of Resource Cards")
    public void resourceCardsIds() {

        // Test the static method ResourceCard.getCardWithId().
        // Check the method returns the correct card.
        int startId = 0;
        int lastId = 39;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            ResourceCard card =
                    ResourceCard.getCards().toList().
                            stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, ResourceCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            ResourceCard.getCardWithId(startId - 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Check out of bounds (upper).
        try {
            ResourceCard.getCardWithId(lastId + 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
    }

    /**
     * Test if the static methods getCardWithId() works properly
     * for the gold cards.
     */
    @Test
    @DisplayName("Cards Ids of Gold Cards")
    public void goldCardsIds() {

        // Test the static method GoldCard.getCardWithId().
        // Check the method returns the correct card.
        int startId = 40;
        int lastId = 79;
        for (int id = startId; id <= lastId; id++) {
            int cardId = id;
            GoldCard card =
                    GoldCard.getCards().toList().
                            stream().filter(c -> c.getCardId() == cardId).limit(1).findFirst().orElse(null);
            assertSame(card, GoldCard.getCardWithId(id));
        }

        // Check out of bounds (lower).
        try {
            GoldCard.getCardWithId(startId - 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Check out of bounds (upper).
        try {
            GoldCard.getCardWithId(lastId + 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
    }


    /**
     * Test if the static methods getCardWithId() works properly
     * for the kingdom cards.
     */
    @Test
    @DisplayName("Cards Ids of Kingdom Cards")
    public void kingdomCardsIds() {

        // Test the static method KingdomCard.getCardWithId().
        // Check the method returns the correct card.
        int startId = 0;
        int lastId = 79;
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
            KingdomCard.getCardWithId(startId - 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Check out of bounds (upper).
        try {
            KingdomCard.getCardWithId(lastId + 1);
            assert (false);
        } catch (CardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
    }
}
