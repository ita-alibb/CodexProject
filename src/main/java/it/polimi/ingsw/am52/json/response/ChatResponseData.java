package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the chat method.
 * The method requires the message and the sender, and if present the recipient.
 */
public class ChatResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The message.
     */
    private final String message;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected ChatResponseData() {
        super();
        this.message = "";
    }

    /**
     * Constructor for error response
     */
    public ChatResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);
        this.message = "";
    }

    /**
     * Constructor for success response
     * @param message the message
     */
    public ChatResponseData(ResponseStatus status, String message) {
        // Assign private fields.
        super(status);
        this.message = message;
    }

    //endregion

    //region Getters

    public String getMessage() {
        return message;
    }

    //endregion
}