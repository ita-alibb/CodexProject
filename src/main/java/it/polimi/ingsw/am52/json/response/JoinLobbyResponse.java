package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The joinLobby response object. The method of this request is "joinLobby" and the
 * data is a JoinLobbyResponseData object.
 */
public class JoinLobbyResponse extends JsonMessage<BaseResponseData> {

    //region Constructors

    /**
     * Creates a join response.
     * @param joinLobbyData The join data.
     */
    public JoinLobbyResponse(JoinLobbyResponseData joinLobbyData) {
        super(joinLobbyData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "joinLobby" method.
     */
    @Override
    public String getMethod() {
        return "joinLobby";
    }

    //endregion
}
