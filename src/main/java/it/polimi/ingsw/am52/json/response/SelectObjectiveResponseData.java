package it.polimi.ingsw.am52.json.response;

import it.polimi.ingsw.am52.json.BaseResponseData;

import java.io.Serializable;

/**
 * The object representing the data for the selectObjective response
 */
public class SelectObjectiveResponseData extends BaseResponseData implements Serializable {

    //region Private Fields

    /**
     * The chosen objective
     */
    private final int objective;

    //endregion

    //region Constructor

    /**
     * The Empty constructor
     */
    public SelectObjectiveResponseData() {
        super();
        this.objective = -1;
    }

    /**
     * Create a selectObjective data object
     * @param status    The status of the response
     * @param objective The ID of the chosen objective
     */
    public SelectObjectiveResponseData(ResponseStatus status, int objective) {
        super(status);
        this.objective = objective;
    }

    /**
     * Create a selectObjective data object
     * @param status    The status of the response
     */
    public SelectObjectiveResponseData(ResponseStatus status) {
        super(status);
        this.objective = -1;
    }

    //endregion

    //region Getters

    /**
     * @return The ID of the chosen objective
     */
    public int getObjective() {
        return this.objective;
    }

    //endregion
}
