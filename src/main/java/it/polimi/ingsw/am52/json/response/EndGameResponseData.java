package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EndGameResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The winner/s of the game
     */
    private final List<String> winners;

    /**
     * The nickname of the first player to disconnect that caused the crash of the game
     */
    private final String disconnectedPlayerNickname;
    //endregion

    //region Constructor

    /**
     * The empty constructor needed for jackson library
     */
    public EndGameResponseData() {
        super();
        this.winners = new ArrayList<>();
        this.disconnectedPlayerNickname = "";
    }

    /**
     * Create an EndGame data object, used for disconnection
     * @param status    The status of the game
     * @param winners   The winner/s of the game
     * @param disconnectedPlayerNickname The Nickname of the player that disconnected
     */
    public EndGameResponseData(ResponseStatus status, List<String> winners, String disconnectedPlayerNickname) {
        super(status);
        this.winners = winners;
        this.disconnectedPlayerNickname = disconnectedPlayerNickname;
    }

    /**
     * Create an EndGame data object
     * @param status    The status of the game
     * @param winners   The winner/s of the game
     */
    public EndGameResponseData(ResponseStatus status, List<String> winners) {
        super(status);
        this.winners = winners;
        this.disconnectedPlayerNickname = "";
    }

    /**
     * Create an EndGame data object
     * @param status    The status of the game
     */
    public EndGameResponseData(ResponseStatus status) {
        super(status);
        this.winners = new ArrayList<>();
        this.disconnectedPlayerNickname = "";
    }

    //endregion

    //region Getters

    public List<String> getWinners() {
        return this.winners;
    }

    public String getDisconnectedPlayerNickname() {
        return this.disconnectedPlayerNickname;
    }

    //endregion
}
