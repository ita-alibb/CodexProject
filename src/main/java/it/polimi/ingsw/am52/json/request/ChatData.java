package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the chat method.
 * The method requires the message and the sender, and if present the recipient.
 */
public class ChatData implements Serializable {

    //region Private Fields

    /**
     * The nickname of the sender.
     */
    private final String sender;

    /**
     * The message.
     */
    private final String message;

    /**
     * The nickname of the recipient.
     */
    private final String recipient;

    //endregion

    //region Constructors

    /**
     * Default constructor, for deserialization purpose only.
     */
    protected ChatData() {
        this("","","");
    }

    /**
     * Create data for chat
     * @param sender the sender
     * @param message the message
     */
    public ChatData(String sender, String message) {
        // Assign private fields.
        this.sender = sender;
        this.message = message;
        this.recipient = null;
    }

    /**
     * Create data for chat
     * @param sender the sender
     * @param message the message
     * @param recipient the recipient if whisper
     */
    public ChatData(String sender, String message, String recipient) {
        // Assign private fields.
        this.sender = sender;
        this.message = message;
        this.recipient = recipient;
    }

    //endregion

    //region Getters

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    //endregion
}