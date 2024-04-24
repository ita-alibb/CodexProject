package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.model.game.GameLobby;
import it.polimi.ingsw.am52.model.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GameLobbyTest {
    /**
     * The lobby to test
     */
    private static GameLobby lobby;

    /**
     * Test player Getters
     */
    @Test
    @DisplayName("Game Lobby test")
    public void generalTest() {
        /*
         * Game lobby too large
         */
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,5));

        /*
         * Game lobby too small
         */
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,1));

        lobby = new GameLobby(1,3);

        assertEquals(0,lobby.getPlayersCount());

        assertTrue(lobby.addPlayer("Andrea"));
        assertFalse(lobby.addPlayer("Andrea"));
        assertFalse(lobby.addPlayer("NameActuallyTooLongReallyTooMuch"));
        assertFalse(lobby.addPlayer(""));
        assertTrue(lobby.addPlayer("Livio"));
        assertTrue(lobby.addPlayer("Lorenzo"));

        assertTrue(lobby.isFull());

        assertThrows(GameException.class, () -> {
            lobby.addPlayer("William");
        });

        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Andrea")));
        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Lorenzo")));
        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Livio")));
        assertFalse(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "William")));
    }
}
