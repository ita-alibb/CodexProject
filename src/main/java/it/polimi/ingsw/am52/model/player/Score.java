package it.polimi.ingsw.am52.model.player;

/**
 * The Score object, needed to keep separate the two types of bonuses
 */
public class Score {
    /**
     * The score obtained from Objectives
     */
    private int objectiveScore;

    /**
     * The score obtained from Card bonuses
     */
    private int cardScore;

    /**
     * Initialize the Score object with 0 points
     */
    public Score() {
        this.objectiveScore = 0;
        this.cardScore = 0;
    }

    /**
     *
     * @return The total score of the player
     */
    public int getTotalScore() {
        return this.objectiveScore + this.cardScore;
    }

    /**
     *
     * @return The score obtained from the objectives by the player
     */
    public int getObjectiveScore() {
        return this.objectiveScore;
    }

    /**
     *
     * @param pointsToAdd The points to be added to the card Score
     */
    public void updateCardScore(int pointsToAdd) {
        this.cardScore += pointsToAdd;
    }

    /**
     *
     * @param pointsToAdd The points to be added to the objectives Score
     */
    public void updateObjectiveScore(int pointsToAdd) {
        this.objectiveScore += pointsToAdd;
    }
}
