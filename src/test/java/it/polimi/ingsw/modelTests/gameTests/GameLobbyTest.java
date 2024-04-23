package it.polimi.ingsw.modelTests.gameTests;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.model.game.GameLobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    @DisplayName("Lobby players list")
    public void playersListTest() {
        // Create an (empty) lobby, for 4-players game.
        GameLobby lobby = new GameLobby(1036, 4);
        // Check is empty.
        assertEquals(0, lobby.getPlayersCount());

        // Add four players.
        List<String> playersToAdd = getPlayersList("Livio", "Andrea", "Lorenzo", "William");
        for (String nickName :playersToAdd) {
            assertTrue(lobby.addPlayer(nickName));
        }

        // Check that lobby contains 4 and only the 4 players added:
        // 1 - the count is 4
        // 2 - contains all added names
        assertEquals(4, lobby.getPlayersCount());
        // Get the list of nicknames in the lobby.
        List<String> players = lobby.getPlayers();
        assertTrue(players.containsAll(playersToAdd));

        // I add a player to the returned list of string,
        // this MUST not add a player to the lobby.
        players.add("Spiderman");

        // The lobby MUST NOT be changed.
        assertEquals(4, lobby.getPlayersCount());
    }

    private List<String> getPlayersList(String...nickNames) {
        final List<String> playersToAdd = new ArrayList<>();
        playersToAdd.addAll(Arrays.asList(nickNames));
        return playersToAdd;
    }
}
