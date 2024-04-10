package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The base class of all json messages. All json requests
 * have a "method" and "data" properties. All requests have a method able to
 * convert the object in a json text string.
 * @author Livio B.
 */
public abstract class JsonMessage<TData> {

    // region Private Fields

    /**
     * The data of this request.
     */
    private final TData data;

    // endregion

    //region Constructor

    /**
     * Create the message object with specified data.
     * @param data The request data.
     */
    protected JsonMessage(TData data) {
        // Assign the data of the request.
        this.data = data;
    }

    //endregion

    //region Getters

    public TData getData() {
        return this.data;
    }

    //endregion

    //region Public Methods

    /**
     *
     * @return The method of this client request.
     */
    public abstract String getMethod();

    /**
     * Convert this request in a json text string.
     * @return The json text representing this request.
     * @throws JsonProcessingException If a json processing error occurs.
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    //endregion
}
