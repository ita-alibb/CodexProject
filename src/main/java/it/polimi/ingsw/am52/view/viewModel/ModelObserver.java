package it.polimi.ingsw.am52.view.viewModel;

/**
 * Class to create an Observer-Observable Pattern. This is triggered the update in the view depending on the method called in
 * the model.
 */
public abstract class ModelObserver {
    /**
     * The public method to update, it triggers the right update based on eventType
     * @param type The type of update
     */
    public synchronized final void update(EventType type) {
        switch (type) {
            case CREATE_LOBBY : this.updateCreateLobby(); break;
            case JOIN_LOBBY : this.updateJoinLobby(); break;
            case LIST_LOBBY : this.updateListLobby(); break;
            case LEAVE_GAME : this.updateLeaveGame(); break;
            case SELECT_OBJECTIVE : this.updateSelectObjective(); break;
            case PLACE_STARTER_CARD : this.updatePlaceStarterCard(); break;
            case PLACE_CARD : this.updatePlaceCard(); break;
            case DRAW_CARD : this.updateDrawCard(); break;
            case TAKE_CARD : this.updateTakeCard(); break;
            case INIT_GAME : this.updateInitGame(); break;
            case END_GAME : this.updateEndGame(); break;
            case CHAT: this.updateChat(); break;
        }
    }

    /**
     * Update that is triggered for event END_GAME
     */
    protected abstract void updateEndGame();

    /**
     * Update that is triggered for event INIT_GAME
     */
    protected abstract void updateInitGame();

    /**
     * Update that is triggered for event TAKE_CARD
     */
    protected abstract void updateTakeCard();

    /**
     * Update that is triggered for event DRAW_CARD
     */
    protected abstract void updateDrawCard();

    /**
     * Update that is triggered for event PLACE_CARD
     */
    protected abstract void updatePlaceCard();

    /**
     * Update that is triggered for event PLACE_STARTER_CARD
     */
    protected abstract void updatePlaceStarterCard();

    /**
     * Update that is triggered for event SELECT_OBJECTIVE
     */
    protected abstract void updateSelectObjective();

    /**
     * Update that is triggered for event LEAVE_GAME
     */
    protected abstract void updateLeaveGame();

    /**
     * Update that is triggered for event LIST_LOBBY
     */
    protected abstract void updateListLobby();

    /**
     * Update that is triggered for event CREATE_LOBBY
     */
    protected abstract void updateCreateLobby();

    /**
     * Update that is triggered for event JOIN_LOBBY
     */
    protected abstract void updateJoinLobby();

    /**
     * Update that is triggered for event CHAT
     */
    protected void updateChat(){
    }
}
