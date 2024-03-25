package it.polimi.ingsw.am52.Model.Player;

import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.Model.cards.KingdomColor;
import it.polimi.ingsw.am52.Model.objectives.Objective;
import it.polimi.ingsw.am52.Util.ImmutableList;
import it.polimi.ingsw.am52.Model.cards.KingdomCard;
import it.polimi.ingsw.am52.Model.cards.StarterCard;
import it.polimi.ingsw.am52.Model.cards.StarterCardFace;
import it.polimi.ingsw.am52.Model.playingBoards.BoardInfo;

/**
 * Provides information about a Player
 */
public interface PlayerInfo {

    /**
     *
     * @return The nickname of the Player
     */
    String getNickname();

    /**
     *
     * @return The color of the pawn of the Player
     */
    KingdomColor getPawnColor();

    /**
     *
     * @return The list of card in the hand of the player as an ImmutableList
     */
    ImmutableList<KingdomCard> getHand();

    /**
     *
     * @return The starter card chosen by the Player
     */
    StarterCard getStarterCard();

    /**
     *
     * @return The starter card Face in the PlayingBoard
     */
    StarterCardFace getPlacedStarterCardFace();

    /**
     *
     * @return the secret Objective of the player
     */
    Objective getObjective();

    /**
     *
     * @return The BoardInfo interface of the player's PlayingBoard
     */
    BoardInfo getPlayingBoard() throws PlayingBoardException;

    /**
     *
     * @return The total score of the Player
     */
    int getScore();
}
