package it.polimi.ingsw.am52.Model.Player;

import it.polimi.ingsw.am52.Exceptions.PlayerException;
import it.polimi.ingsw.am52.Exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.Model.cards.KingdomColor;
import it.polimi.ingsw.am52.Model.cards.StarterCard;
import it.polimi.ingsw.am52.Model.objectives.Objective;
import it.polimi.ingsw.am52.Util.ImmutableList;
import it.polimi.ingsw.am52.Model.cards.KingdomCard;
import it.polimi.ingsw.am52.Model.cards.StarterCardFace;
import it.polimi.ingsw.am52.Model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.Model.playingBoards.PlayingBoard;

import java.util.HashSet;
import java.util.Set;

/**
 * The Player Object. The player implements PlayerInfo to expose its properties and PlayerDrawable
 */
public class Player implements PlayerBoardSetup, PlayerInfo, PlayerDrawing{
    /**
     * The Nickname chosen by the Player
     */
    private final String nickname;

    /**
     * The color chosen by the Player for his pawn
     */
    private final KingdomColor pawnColor;

    /**
     * The Player's Hand of cards
     */
    private final Set<KingdomCard> cardHand;

    /**
     * The Player's Score
     */
    private final Score score;

    /**
     * The secret Objective
     */
    private final Objective secretObjective;

    /**
     * The starter Card
     */
    private final StarterCard starterCard;

    /**
     * The player's PlayingBoard
     */
    private PlayingBoard playingBoard;

    /**
     *
     * @param nickname The nickname chosen by the player
     * @param pawnColor The color of the pawn chosen by the player
     * @param secretObjective The secretObjective drawn by the player
     * @param starterCard The starterCard chosen by the player
     */
    public Player(String nickname, KingdomColor pawnColor, Objective secretObjective, StarterCard starterCard) {
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.cardHand = new HashSet<>();
        this.score = new Score();
        this.secretObjective = secretObjective;
        this.starterCard = starterCard;
    }

    /**
     * Used to instantiate the Player's PlayingBoard
     *
     * @param card The starter card face chosen by the player
     */
    @Override
    public void placeStarterCardFace(StarterCardFace card) throws PlayingBoardException {

        if (this.playingBoard != null){
            throw new PlayingBoardException("The PlayingBoard for the player is already instantiated");
        }

        if (!this.starterCard.getSide(card).isPresent()){
            throw new PlayingBoardException("The provided StarterCardFace does not belong to the StarterCard chosen by the player");
        }

        this.playingBoard = new PlayingBoard(card);
    }

    /**
     * @return The nickname of the Player
     */
    @Override
    public String getNickname() { return this.nickname; }

    /**
     * @return The color of the pawn of the Player
     */
    @Override
    public KingdomColor getPawnColor() { return this.pawnColor; }

    /**
     * @return The list of card in the hand of the player as an ImmutableList
     */
    @Override
    public ImmutableList<KingdomCard> getHand() {
        return new ImmutableList<>(this.cardHand.stream().toList());
    }

    /**
     * @return The starter card chosen by the Player
     */
    @Override
    public StarterCardFace getStarterCard() {
        return this.playingBoard.getStarerCard();
    }

    /**
     * @return the secret Objective of the player
     */
    @Override
    public Objective getObjective() {
        return this.secretObjective;
    }

    /**
     * @return The BoardInfo interface of the player's PlayingBoard
     */
    @Override
    public BoardInfo getPlayingBoard() {
        return this.playingBoard.getInfo();
    }

    /**
     * @return The total score of the Player
     */
    @Override
    public int getScore() {
        return this.score.getTotalScore();
    }

    /**
     * Add card to Hand
     * @param drawnCard The drawnCard to be added in the hand of the player
     */
    @Override
    public void assignCard(KingdomCard drawnCard) throws PlayerException  {
        if (this.cardHand.size() >= 3){
            throw new PlayerException("Trying to add a 4th card");
        }

        if (this.cardHand.contains(drawnCard)){
            throw new PlayerException("Trying to add duplicate card");
        }

        this.cardHand.add(drawnCard);
    }

    /**
     * Remove card from Hand
     * @param placedCard The placedCard that must be removed from the hand of the player
     */
    @Override
    public void removeCard(KingdomCard placedCard) throws PlayerException {
        if (!this.cardHand.contains(placedCard)){
            throw new PlayerException("Trying to place a card that is not on the player's hand");
        }

        this.cardHand.remove(placedCard);
    }
}
