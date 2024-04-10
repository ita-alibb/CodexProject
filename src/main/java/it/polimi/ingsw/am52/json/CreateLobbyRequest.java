package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The crete lobby request object. The method is "createLobby" and the
 * data is a CreateLobbyData object.
 */
public class CreateLobbyRequest extends ClientRequest<CreateLobbyData> {

    //region Constructor

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
     *
     * @return The "createLobby" method.
     */
    @Override
    public String getMethod() {
        return "createLobby";
    }

    //endregion
}
