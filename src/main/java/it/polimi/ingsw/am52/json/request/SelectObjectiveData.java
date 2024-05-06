package it.polimi.ingsw.am52.json.request;

import java.io.Serializable;

/**
 * The object representing the data for the selectObjective method.
 * The player shall choose on of the two secret objective cards, at
 * the start of the game.
 * @author Livio B.
 */
public class SelectObjectiveData implements Serializable {

    //region Private Fields

    /**
     * The id of the selected objective card.
     */
    private final int objectiveId;

    //endregion

    //region Constructor

    /**
     * Default constructor, for json deserialization purpose only.
     */
    protected SelectObjectiveData() {
        this.objectiveId = -1;
    }

    /**
     * Create a login data object.
     * @param objectiveId The id of the selected objective.
     */
    public SelectObjectiveData(int objectiveId) {
        // Assign private fields.
        this.objectiveId = objectiveId;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The id of the selected objective.
     */
    public int getObjectiveId() {
        return this.objectiveId;
    }

    //endregion
}
