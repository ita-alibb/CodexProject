package it.polimi.ingsw.am52.json.response;

import java.io.Serializable;

public class Response<TData> implements Serializable {
    public Status gameStatus;
    public int errorCode;
    public TData body;

    public Response(Status gameStatus, int errorCode,TData body) {
        this.gameStatus = gameStatus;
        this.errorCode = errorCode;
        this.body = body;
    }

    public String getString(){
        return body.toString();
    }
}
