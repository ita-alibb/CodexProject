package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The leave game response object. The method is "leaveGame" and the
 * data is a LeaveGameResponseData object.
 */
public class LeaveGameResponse extends JsonMessage<BaseResponseData> {

    //region Constructor

    /**
     * Creates a leave game request with the specified data.
     * @param leaveData The login data.
     */
    public LeaveGameResponse(LeaveGameResponseData leaveData) {
        super(leaveData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "leaveGame" method.
     */
    @Override
    public String getMethod() {
        return "leaveGame";
    }

    //endregion
}
