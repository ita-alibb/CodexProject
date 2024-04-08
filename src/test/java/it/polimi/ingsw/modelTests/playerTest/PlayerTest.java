package it.polimi.ingsw.modelTests.playerTest;

import it.polimi.ingsw.am52.exceptions.PlayerException;
import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.player.Player;
import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
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
        currentPlayer = new Player("Andrea", Objective.getObjectiveWithId(2), Objective.getObjectiveWithId(3), StarterCard.getCardWithId(80));
    }

    /**
     * Test player Getters
     */
    @Test
    @Order(1)
    @DisplayName("Player getters test")
    public void gettersTest() {
        assertEquals("Andrea", currentPlayer.getNickname());
        assertEquals(2, currentPlayer.getObjectiveOptions().get(0).getObjectiveId());
        assertEquals(3, currentPlayer.getObjectiveOptions().get(1).getObjectiveId());
        assertNull(currentPlayer.getObjective());
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
        assertThrows(PlayingBoardException.class, () -> {
            currentPlayer.getPlayingBoard();
        });

        /*
         * Card face not in player's starter card
         */
        assertThrows(PlayingBoardException.class, () -> {
            currentPlayer.placeStarterCardFace(StarterCard.getCardWithId(81).getFrontFace());
        });

        currentPlayer.placeStarterCardFace(currentPlayer.getStarterCard().getFrontFace());

        assertNotNull(StarterCard.getCardWithId(80).getSide(currentPlayer.getPlacedStarterCardFace()));
        assertEquals(CardSide.FRONT, currentPlayer.getPlacedStarterCardFace().getCardSide());

        /*
         * Playing board already initialized
         */
        assertThrows(PlayingBoardException.class, () -> {
            currentPlayer.placeStarterCardFace(StarterCard.getCardWithId(80).getFrontFace());
        });
    }

    /**
     * Test player Objective setup
     */
    @Test
    @Order(3)
    @DisplayName("Secret Objective setup")
    public void setObjectiveBoard(){
        /*
         * Objective not in Player choices
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.setSecretObjective(Objective.getObjectiveWithId(1));
        });

        currentPlayer.setSecretObjective(Objective.getObjectiveWithId(3));

        assertEquals(3, currentPlayer.getObjective().getObjectiveId());

        /*
         * Secret objective already set
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.setSecretObjective(Objective.getObjectiveWithId(2));
        });
    }

    /**
     * Test player Hand management
     */
    @Test
    @Order(4)
    @DisplayName("Player Hand management")
    public void manageHand(){
        /*
         * Remove card from empty hand
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40),  GoldCard.getCardWithId(40).getFrontFace());
        });

        assertThrows(PlayerException.class, () -> {
            currentPlayer.placeCard(new BoardSlot(1,1), ResourceCard.getCardWithId(0), ResourceCard.getCardWithId(0).getBackFace());
        });

        // Add cards
        currentPlayer.drawCard(GoldCard.getCardWithId(40));
        currentPlayer.drawCard(ResourceCard.getCardWithId(18));

        /*
         * Duplicated cards
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.drawCard(GoldCard.getCardWithId(40));
        });
        assertThrows(PlayerException.class, () -> {
            currentPlayer.drawCard(ResourceCard.getCardWithId(18));
        });

        currentPlayer.drawCard(GoldCard.getCardWithId(41));

        /*
         * More than 3 cards
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.drawCard(GoldCard.getCardWithId(42));
        });

        /*
         * Remove non-existing card
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.placeCard(new BoardSlot(1, 1), GoldCard.getCardWithId(42), GoldCard.getCardWithId(42).getFrontFace());
        });

        /*
         * Card Face does not belong to the Card
         */
        assertThrows(PlayerException.class, () -> {
            currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40),  GoldCard.getCardWithId(41).getFrontFace());
        });
        assertThrows(PlayerException.class, () -> {
            currentPlayer.placeCard(new BoardSlot(1,1), ResourceCard.getCardWithId(18), ResourceCard.getCardWithId(0).getBackFace());
        });

        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 18));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));

        currentPlayer.placeCard(new BoardSlot(1,1), GoldCard.getCardWithId(40), GoldCard.getCardWithId(40).getBackFace());
        currentPlayer.placeCard(new BoardSlot(2,0), ResourceCard.getCardWithId(18), ResourceCard.getCardWithId(18).getFrontFace());

        /*
         * Check that cards are removed
         */
        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 40));
        assertFalse(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 0));
        assertTrue(currentPlayer.getHand().stream().map(Card::getCardId).anyMatch(id -> id == 41));

        /*
         * Check that one point is set
         */
        assertEquals(1, currentPlayer.getScore());
    }
}
