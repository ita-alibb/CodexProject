package it.polimi.ingsw.am52.json.request;

/**
 * The InitGameRequest request object. The method associated to this request
 * is "initGame" and the data is a CreateLobbyData object.
 */
public class InitGameRequest extends ClientRequest<InitGameData> {

    //region Constructors

    /**
     * Create the request with the specified lobby data.
     * @param lobbyData The data for the lobby to create.
     */
    public InitGameRequest(InitGameData lobbyData) {
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
        return "initGame";
    }

    //endregion
}
