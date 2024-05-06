package it.polimi.ingsw.am52.json;

import it.polimi.ingsw.am52.json.response.ResponseStatus;

import java.io.Serializable;

public abstract class BaseResponseData implements Serializable {
    /**
     * The Status of the response
     */
    public ResponseStatus status;

    /**
     * The Empty constructor
     */
    public BaseResponseData() {
        this.status = null;
    }

    /**
     * The Base constructor
     * @param status the Response Status
     */
    public BaseResponseData(ResponseStatus status) {
        this.status = status;
    }

    /**
     *
     * @return the status of the request
     */
    public ResponseStatus getStatus() {
        return this.status;
    }
}
