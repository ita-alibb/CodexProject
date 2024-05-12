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
    private boolean isBroadcast;

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
     *
     * @return the status of the request
     */
    public ResponseStatus getStatus() {
        return this.status;
    }

    /**
     * Method used to mark the response as broadcast
     *
     */
    public void setIsBroadcast() {
        this.isBroadcast = true;
    }

    /**
     * Method used to mark the response as broadcast
     *
     * @return the isBroadcast property
     */
    public boolean isBroadcast() {
        return this.isBroadcast;
    }
}
