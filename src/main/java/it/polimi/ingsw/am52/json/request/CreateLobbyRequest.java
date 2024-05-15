package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The crete lobby request object. The method associated to this request
 * is "createLobby" and the data is a CreateLobbyData object.
 */
public class CreateLobbyRequest extends JsonMessage<CreateLobbyData> {

    //region Constructors

    /**
     * Create the request with the specified player's nickname, name of the lobby
     * and number of players for the game.
     * @param nickname The nickname of the player.
     * @param lobbyName The name of the lobby.
     * @param numberOfPlayers The number of players of the game.
     */
    public CreateLobbyRequest(String nickname, String lobbyName, int numberOfPlayers) {
        this(new CreateLobbyData(nickname, numberOfPlayers));
    }

    /**
     * Create the request with the specified lobby data.
     * @param lobbyData The data for the lobby to create.
     */
    public CreateLobbyRequest(CreateLobbyData lobbyData) {
        super(lobbyData);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public CreateLobbyData getData() {
        return super.data;
    }

    /**
     *
     * @return The "createLobby" method.
     */
    @Override
    public String getMethod() {
        return "createLobby";
    }

    //endregion
}
