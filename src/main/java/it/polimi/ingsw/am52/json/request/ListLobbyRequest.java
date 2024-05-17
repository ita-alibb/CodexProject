package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The list lobby request object. The method associated to this request
 * is "listLobby" and the data is a ListLobbyData object.
 */
public class ListLobbyRequest extends JsonMessage<ListLobbyData> {

    //region Constructors

    /**
     * Create the request with the specified lobby data.
     * @param listLobbyData The data for the lobby to create.
     */
    public ListLobbyRequest(ListLobbyData listLobbyData) {
        super(listLobbyData);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public ListLobbyData getData() {
        return super.data;
    }

    /**
     *
     * @return The "createLobby" method.
     */
    @Override
    public String getMethod() {
        return "listLobby";
    }

    //endregion
}
