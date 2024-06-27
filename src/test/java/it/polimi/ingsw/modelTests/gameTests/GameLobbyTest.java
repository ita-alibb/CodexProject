package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.model.game.GameLobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        /* TODO: temp disable, game lobby has now list of User and not of strings, cannot instantiate with null
         * Game lobby too large
         */
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,5));

        /*
         * Game lobby too small
         */
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,1));

        lobby = new GameLobby(1,3);

        assertEquals(0,lobby.getPlayersCount());
    }
}
