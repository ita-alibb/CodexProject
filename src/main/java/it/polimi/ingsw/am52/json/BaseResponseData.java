package it.polimi.ingsw.am52.json;

import it.polimi.ingsw.am52.json.response.ResponseStatus;

import java.io.Serializable;

public abstract class BaseResponseData implements Serializable {
    /**
     * The Status of the response
     */
    private final ResponseStatus status;

    /**
     * Indicates if the response is a broadcast of the response
     */
    public boolean isBroadcast;

    /**
     * The Empty constructor
     */
    public BaseResponseData() {
        this.status = null;
        this.isBroadcast = false;
    }

    /**
     * The Base constructor
     * @param status the Response Status
     */
    public BaseResponseData(ResponseStatus status) {
        this.status = status;
        this.isBroadcast = false;
    }

    /**
     * The Base constructor
     * @param status the Response Status
     * @param isBroadcast if the response is a broadcast
     */
    public BaseResponseData(ResponseStatus status, boolean isBroadcast) {
        this.status = status;
        this.isBroadcast = isBroadcast;
    }

    /**
     *
     * @return the status of the request
     */
    public ResponseStatus getStatus() {
        if (this.status == null) {
            return null;
        }
        return new ResponseStatus(this.status.getGamePhase(), this.status.getCurrPlayer(), this.status.getErrorCode(), this.status.getErrorMessage());
    }

    /**
     * Method used to mark the response as broadcast
     * @param isBroadcast the new value
     */
    public void setIsBroadcast(boolean isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    /**
     * Method used to mark the response as broadcast
     *
     * @return the isBroadcast property
     */
    public boolean getIsBroadcast() {
        return this.isBroadcast;
    }
}
