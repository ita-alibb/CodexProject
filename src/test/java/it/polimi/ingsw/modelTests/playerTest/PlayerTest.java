package it.polimi.ingsw.modelTests.playerTest;

import it.polimi.ingsw.am52.Exceptions.PlayerException;
import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.Model.Player.Player;
import it.polimi.ingsw.am52.Model.cards.*;
import it.polimi.ingsw.am52.Model.objectives.Objective;
import it.polimi.ingsw.am52.Model.playingBoards.BoardSlot;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
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
    @Order(2)
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
    @Order(3)
    @DisplayName("Player Hand management")
    public void manageHand(){
        /*
         * Remove card from empty hand
         */
        try {
            currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40),  GoldCard.getCardWithId(40).getFrontFace());
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
        try {
            currentPlayer.placeCard(new BoardSlot(1,1), ResourceCard.getCardWithId(0), ResourceCard.getCardWithId(0).getBackFace());
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        // Add cards
        currentPlayer.drawCard(GoldCard.getCardWithId(40));
        currentPlayer.drawCard(ResourceCard.getCardWithId(0));

        /*
         * Duplicated cards
         */
        try {
            currentPlayer.drawCard(GoldCard.getCardWithId(40));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
        try {
            currentPlayer.drawCard(ResourceCard.getCardWithId(0));
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        currentPlayer.drawCard(GoldCard.getCardWithId(41));

        /*
         * More than 3 cards
         */
        try {
            currentPlayer.drawCard(GoldCard.getCardWithId(42));
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
            currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(42), GoldCard.getCardWithId(42).getFrontFace() );
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        /*
         * Card Face does not belong to the Card
         */
        try {
            currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40),  GoldCard.getCardWithId(41).getFrontFace());
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }
        try {
            currentPlayer.placeCard(new BoardSlot(1,1), ResourceCard.getCardWithId(0), ResourceCard.getCardWithId(11).getBackFace());
            assert (false);
        } catch (PlayerException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 0));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));

        currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40), GoldCard.getCardWithId(40).getBackFace());
        currentPlayer.placeCard(new BoardSlot(2,0), ResourceCard.getCardWithId(0), ResourceCard.getCardWithId(0).getFrontFace());

        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 0));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));
    }
}
