package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The crete lobby response object. The method associated to this response
 * is "createLobby" and the data is a CreateLobbyResponseData object.
 */
public class CreateLobbyResponse extends JsonMessage<BaseResponseData> {

    //region Constructors

    /**
     * Create the request with the specified lobby data.
     * @param lobbyData The data for the lobby to create.
     */
    public CreateLobbyResponse(JoinLobbyResponseData lobbyData) {
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

    /**
     *
     * @return The data associated to this message.
     */
    @Override
    public JoinLobbyResponseData getData() {
        return (JoinLobbyResponseData) super.data;
    }
    //endregion
}
