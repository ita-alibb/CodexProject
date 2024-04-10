package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.model.game.GameLobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GameLobbyTest {
    /**
     * Test player Getters
     */
    @Test
    @DisplayName("Game Lobby test")
    public void generalTest() {
        GameLobby lobby;

        /*
         * Game lobby too large
         */
        try {
            lobby = new GameLobby(1,5);
            assert (false);
        } catch (GameException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        /*
         * Game lobby too small
         */
        try {
            lobby = new GameLobby(1,1);
            assert (false);
        } catch (GameException ex) {
            assert (true);
        } catch (Exception allEx) {
            assert (false);
        }

        lobby = new GameLobby(1,3);

        assertEquals(0,lobby.getPlayersCount());

        assertTrue(lobby.addPlayer("Andrea"));
        assertFalse(lobby.addPlayer("Andrea"));
        assertFalse(lobby.addPlayer("NameActuallyTooLongReallyTooMuch"));
        assertFalse(lobby.addPlayer(""));
        assertTrue(lobby.addPlayer("Livio"));
        assertTrue(lobby.addPlayer("Lorenzo"));

        assertTrue(lobby.isFull());
        assertFalse(lobby.addPlayer("William"));

        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Andrea")));
        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Lorenzo")));
        assertTrue(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "Livio")));
        assertFalse(lobby.getPlayers().stream().anyMatch(nickname -> Objects.equals(nickname, "William")));
    }
}
