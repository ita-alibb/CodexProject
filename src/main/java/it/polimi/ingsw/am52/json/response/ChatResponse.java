package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.json.JsonMessage;
import it.polimi.ingsw.am52.json.request.ChatData;

/**
 * The object representing the data for the chat method.
 * The response for chat message
 */
public class ChatResponse extends JsonMessage<BaseResponseData> {

    //region constructor

    /**
     * Creates the response object for the chat method, with the specified
     * data.
     * @param data The data for the chat method.
     */
    public ChatResponse(ChatResponseData data) {
        super(data);
    }

    //endregion

    //region Overrides

    /**
     * @return The data associated to this message.
     */
    @Override
    public ChatResponseData getData() {
        return (ChatResponseData) super.data;
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
