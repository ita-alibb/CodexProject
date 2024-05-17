package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The listLobby response object. The method of this request is "listLobby" and the
 * data is a ListLobbyResponseData object.
 */
public class ListLobbyResponse extends JsonMessage<BaseResponseData> {

    //region Constructors

    /**
     * Creates a join response.
     * @param joinLobbyData The join data.
     */
    public ListLobbyResponse(ListLobbyResponseData joinLobbyData) {
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
        return "listLobby";
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
