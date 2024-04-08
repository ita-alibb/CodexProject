package it.polimi.ingsw.am52.model.player;

import it.polimi.ingsw.am52.model.objectives.Objective;

import java.util.ArrayList;

public interface PlayerSetup {
    /**
     * Used to get the secret objective options
     */
    public ArrayList<Objective> getObjectiveOptions ();

    /**
     * Used to set the secret objective
     * @param secretObjective The objective chosen by the player
     */
    public void setSecretObjective (Objective secretObjective);
}
