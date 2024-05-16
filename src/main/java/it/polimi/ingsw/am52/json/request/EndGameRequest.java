package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The EndGame request object. The method associated to this request is "endGame" and the data is a EndGameData object.
 */
public class EndGameRequest extends JsonMessage<EndGameData> {

    //region Constructor

    /**
     * Create the request with the specified lobby data
     * @param data  The data for the lobby to signal the end to
     */
    public EndGameRequest(EndGameData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message
     */
    @Override
    public EndGameData getData() {
        return super.data;
    }

    /**
     * @return The "EndGame" method
     */
    @Override
    public String getMethod() {
        return "endGame";
    }

    //endregion
}
