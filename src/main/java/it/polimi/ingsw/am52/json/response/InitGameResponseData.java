package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;
import it.polimi.ingsw.am52.model.game.GameManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The object representing the data for the joinLobby response.
 */
public class InitGameResponseData  extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The Players of the game
     */
    private final List<String> playersNickname;

    /**
     * The common objectives in a game
     */
    private final List<Integer> commonObjectiveIds;

    /**
     * The visible resource cards
     */
    private final List<Integer> visibleResourceCardIds;

    /**
     * The visible gold cards
     */
    private final List<Integer> visibleGoldCardIds;

    /**
     * The player's hand card ids
     */
    private final List<Integer> playerHandCardIds;

    /**
     * The player's objective card ids
     */
    private final List<Integer> playerObjectiveCardIds;

    /**
     * The player's starter card id
     */
    private final Integer starterCardId;

    /**
     * The next card on the resource pile
     */
    private final Integer nextResourceCardId;

    /**
     * The next card on the gold pile
     */
    private final Integer nextGoldCardId;

    //endregion

    //region Constructors

    /**
     * The Empty constructor needed for Jackson library
     */
    public InitGameResponseData() {
        super();
        this.playersNickname = new ArrayList<>();
        this.commonObjectiveIds = new ArrayList<>();
        this.visibleResourceCardIds = new ArrayList<>();
        this.visibleGoldCardIds = new ArrayList<>();
        this.playerHandCardIds = new ArrayList<>();
        this.playerObjectiveCardIds = new ArrayList<>();
        this.starterCardId = -1;
        this.nextResourceCardId = -1;
        this.nextGoldCardId = -1;
    }

    /**
     * Create a joinLobby data object.
     * @param status the status of the game
     * @param playersNickname the nickname of other players in the game
     * @param commonObjectiveIds the id of the common objectives
     * @param visibleResourceCardIds the id of the 2 visible resource cards
     * @param visibleGoldCardIds the id of the 2 visible gold cards
     * @param playerHandCardIds the id of the 3 cards in player hand
     * @param playerObjectiveCardIds the id of the objective cards
     * @param starterCardId the id of the starter card
     * @param nextResourceCardId the id of the next resource card in the pile
     * @param nextGoldCardId the id of the next gold card in the pile
     */
    public InitGameResponseData( ResponseStatus status, List<String> playersNickname, List<Integer> commonObjectiveIds, List<Integer> visibleResourceCardIds, List<Integer> visibleGoldCardIds, List<Integer> playerHandCardIds, List<Integer> playerObjectiveCardIds, Integer starterCardId, Integer nextResourceCardId, Integer nextGoldCardId ) {
        super(status);

        this.playersNickname = playersNickname;
        this.commonObjectiveIds = commonObjectiveIds;
        this.visibleResourceCardIds = visibleResourceCardIds;
        this.visibleGoldCardIds = visibleGoldCardIds;
        this.playerHandCardIds = playerHandCardIds;
        this.playerObjectiveCardIds = playerObjectiveCardIds;
        this.starterCardId = starterCardId;
        this.nextResourceCardId = nextResourceCardId;
        this.nextGoldCardId = nextGoldCardId;
    }

    /**
     * Create a joinLobby data object.
     * @param status The status of the response
     */
    public InitGameResponseData(ResponseStatus status) {
        // Assign private fields.
        super(status);

        this.playersNickname = new ArrayList<>();
        this.commonObjectiveIds = new ArrayList<>();
        this.visibleResourceCardIds = new ArrayList<>();
        this.visibleGoldCardIds = new ArrayList<>();
        this.playerHandCardIds = new ArrayList<>();
        this.playerObjectiveCardIds = new ArrayList<>();
        this.starterCardId = -1;
        this.nextResourceCardId = -1;
        this.nextGoldCardId = -1;
    }

    public List<String> getPlayersNickname() {
        return playersNickname;
    }

    public List<Integer> getCommonObjectiveIds() {
        return commonObjectiveIds;
    }

    public List<Integer> getVisibleResourceCardIds() {
        return visibleResourceCardIds;
    }

    public List<Integer> getVisibleGoldCardIds() {
        return visibleGoldCardIds;
    }

    public List<Integer> getPlayerHandCardIds() {
        return new ArrayList<>(this.playerHandCardIds);
    }

    public List<Integer> getPlayerObjectiveCardIds() {
        return new ArrayList<>(this.playerObjectiveCardIds);
    }

    public Integer getStarterCardId() {
        return starterCardId;
    }

    public int getNextResourceCardId() {
        return this.nextResourceCardId;
    }

    public int getNextGoldCardId() {
        return this.nextGoldCardId;
    }

    //endregion

    //region Getters


    //endregion
}
