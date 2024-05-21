package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.model.game.GameLobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
         *//*
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,5));

        *//*
         * Game lobby too small
         *//*
        assertThrows(GameException.class, () -> lobby = new GameLobby(1,1));

        lobby = new GameLobby(1,3);

        assertEquals(0,lobby.getPlayersCount());

        assertTrue(lobby.addPlayer(new User("Andrea", null)));
        assertFalse(lobby.addPlayer(new User("Andrea", null)));
        assertFalse(lobby.addPlayer(new User("NameActuallyTooLongReallyTooMuch", null)));
        assertFalse(lobby.addPlayer(new User("", null)));
        assertTrue(lobby.addPlayer(new User("Livio", null)));
        assertTrue(lobby.addPlayer(new User("Lorenzo", null)));

        assertTrue(lobby.isFull());

        assertThrows(GameException.class, () -> {
            lobby.addPlayer(new User("William", null));
        });

        assertTrue(lobby.getPlayersNickname().stream().anyMatch(nickname -> Objects.equals(nickname, "Andrea")));
        assertTrue(lobby.getPlayersNickname().stream().anyMatch(nickname -> Objects.equals(nickname, "Lorenzo")));
        assertTrue(lobby.getPlayersNickname().stream().anyMatch(nickname -> Objects.equals(nickname, "Livio")));
        assertFalse(lobby.getPlayersNickname().stream().anyMatch(nickname -> Objects.equals(nickname, "William")));*/
    }
}
