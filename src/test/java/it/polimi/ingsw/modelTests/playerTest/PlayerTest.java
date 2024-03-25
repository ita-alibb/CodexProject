package it.polimi.ingsw.modelTests.playerTest;

import it.polimi.ingsw.am52.Exceptions.PlayerException;
import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.Model.Player.Player;
import it.polimi.ingsw.am52.Model.cards.*;
import it.polimi.ingsw.am52.Model.objectives.Objective;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private static Player currentPlayer;

    /**
     * Set up a Player
     */
    @BeforeAll
    public static void setUp(){
        currentPlayer = new Player("Andrea", KingdomColor.RED, Objective.getObjectiveWithId(1), StarterCard.getCardWithId(80));
    }

    /**
     * Test player Getters
     */
    @Test
    @DisplayName("Player getters test")
    public void gettersTest() {
        assertEquals("Andrea", currentPlayer.getNickname());
        assertEquals(KingdomColor.RED, currentPlayer.getPawnColor());
        assertEquals(1, currentPlayer.getObjective().getObjectiveId());
        assertEquals(0, currentPlayer.getScore());
    }

    /**
     * Test player Board setup
     */
    @Test
    @DisplayName("Player Board setup")
    public void setPlayingBoard(){
        /*
         * Playing board not instantiated
         */
        try {
            currentPlayer.getPlayingBoard();
            assert (false);
        } catch (PlayingBoardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        /*
         * Card face not in player's starter card
         */
        try {
            currentPlayer.placeStarterCardFace(StarterCard.getCardWithId(81).getFrontFace());
            assert (false);
        } catch (PlayingBoardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        currentPlayer.placeStarterCardFace(currentPlayer.getStarterCard().getFrontFace());

        assertNotNull(StarterCard.getCardWithId(80).getSide(currentPlayer.getPlacedStarterCardFace()));
        assertEquals(CardSide.FRONT, currentPlayer.getPlacedStarterCardFace().getCardSide());

        /*
         * Playing board already initialized
         */
        try {
            currentPlayer.placeStarterCardFace(StarterCard.getCardWithId(80).getFrontFace());
            assert (false);
        } catch (PlayingBoardException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
    }

    /**
     * Test player Hand management
     */
    @Test
    @DisplayName("Player Board setup")
    public void manageHand(){
        /*
         * Remove card from empty hand
         */
        try {
            currentPlayer.removeCard(GoldCard.getCardWithId(40));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
        try {
            currentPlayer.removeCard(ResourceCard.getCardWithId(0));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Add cards
        currentPlayer.assignCard(GoldCard.getCardWithId(40));
        currentPlayer.assignCard(ResourceCard.getCardWithId(0));

        /*
         * Duplicated cards
         */
        try {
            currentPlayer.assignCard(GoldCard.getCardWithId(40));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
        try {
            currentPlayer.assignCard(ResourceCard.getCardWithId(0));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        currentPlayer.assignCard(GoldCard.getCardWithId(41));

        /*
         * More than 3 cards
         */
        try {
            currentPlayer.assignCard(GoldCard.getCardWithId(42));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        /*
         * Remove non-existing card
         */
        try {
            currentPlayer.removeCard(GoldCard.getCardWithId(42));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 0));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));

        currentPlayer.removeCard(GoldCard.getCardWithId(40));
        currentPlayer.removeCard(ResourceCard.getCardWithId(0));

        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 0));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));
    }
}
