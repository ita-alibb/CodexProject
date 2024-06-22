package it.polimi.ingsw.am52.json.request;

import it.polimi.ingsw.am52.json.JsonMessage;

/**
 * The object representing the data for the chat method.
 * The method requires the message and the sender, and if present the recipient.
 */
public class ChatRequest extends JsonMessage<ChatData> {

    //region constructor

    /**
     * Creates the request object for the chat method, with the specified
     * data.
     * @param data The data for the chat method.
     */
    public ChatRequest(ChatData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public ChatData getData() {
        return super.data;
    }

    /**
     *
     * @return The "chat" method.
     */
    @Override
    public String getMethod() {
        return "chat";
    }

    //endregion
}
