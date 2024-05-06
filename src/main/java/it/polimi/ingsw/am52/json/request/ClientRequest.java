package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The base class of all client request's messages.
 * @author Livio B.
 */
public abstract class ClientRequest<TData> extends JsonMessage<TData> {

    //region Constructor

    /**
     * Create the request object with specified data.
     * @param data The request data.
     */
    protected ClientRequest(TData data) {
        super(data);
    }

    //endregion
}
