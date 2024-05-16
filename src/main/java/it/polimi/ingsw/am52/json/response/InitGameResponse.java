package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The joinLobby response object. The method of this request is "initGame" and the
 * data is a InitGameResponseData object.
 */
public class InitGameResponse  extends JsonMessage<BaseResponseData> {

    //region Constructors

    /**
     * Creates an init game response.
     * @param initGameResponseData The init game data.
     */
    public InitGameResponse(InitGameResponseData initGameResponseData) {
        super(initGameResponseData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "joinLobby" method.
     */
    @Override
    public String getMethod() {
        return "initGame";
    }

    /**
     *
     * @return The data associated to this message.
     */
    @Override
    public InitGameResponseData getData() {
        return (InitGameResponseData) super.data;
    }
    //endregion
}
