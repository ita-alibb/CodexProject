package it.polimi.ingsw.am52.model.player;

import it.polimi.ingsw.am52.exceptions.PlayerException;
import it.polimi.ingsw.am52.exceptions.PlayingBoardException;
import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.util.ImmutableList;
import it.polimi.ingsw.am52.model.cards.KingdomCard;
import it.polimi.ingsw.am52.model.cards.StarterCardFace;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.model.playingBoards.PlayingBoard;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The Player Object. The player implements PlayerInfo to expose its properties and PlayerDrawable
 */
public class Player implements PlayerSetup, PlayerBoardSetup, PlayerInfo, PlayerDrawing{
    /**
     * The Nickname chosen by the Player
     */
    private final String nickname;

    /**
     * The Player's Hand of cards
     */
    private final Set<KingdomCard> cardHand;

    /**
     * The Player's Score
     */
    private final Score score;

    /**
     * The first objective drawn
     */
    private final Objective firstObjective;

    /**
     * The second objective drawn
     */
    private final Objective secondObjective;

    /**
     * The secret Objective, chosen between the two
     */
    private Objective secretObjective;

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
     * @param firstObjective The first of the two random objectives
     * @param secondObjective The first of the two random objectives
     * @param starterCard The starterCard chosen by the player
     */
    public Player(String nickname, Objective firstObjective, Objective secondObjective, StarterCard starterCard) {
        this.nickname = nickname;
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.cardHand = new HashSet<>();
        this.score = new Score();
        this.starterCard = starterCard;
    }

    /**
     * Used to get the secret objective options
     */
    @Override
    public ArrayList<Objective> getObjectiveOptions() {
        return new ArrayList<>(Arrays.asList(this.firstObjective, this.secondObjective));
    }

    /**
     * Used to set the secret objective
     * @param secretObjective The objective chosen by the player
     */
    @Override
    public void setSecretObjective (Objective secretObjective) throws PlayerException {
        if (!(secretObjective.equals(this.firstObjective) || secretObjective.equals(this.secondObjective))){
            throw new PlayerException("The chosen card does not belong to the player chooses");
        }
        if (this.secretObjective != null){
            throw  new PlayerException("The secret objective is already set for this player");
        }

        this.secretObjective = secretObjective;
    }
    /**
     * Used to instantiate the Player's PlayingBoard
     *
     * @param card The starter card of the player
     * @param side The side chosen
     */
    @Override
    public void placeStarterCardFace(StarterCard card, CardSide side) throws PlayingBoardException {
        if (card.getCardId() != this.starterCard.getCardId()){
            throw new PlayingBoardException("The provided StarterCardFace does not belong to the StarterCard chosen by the player");
        }

        if (this.playingBoard != null){
            throw new PlayingBoardException("The PlayingBoard for the player is already instantiated");
        }

        this.playingBoard = new PlayingBoard(side == CardSide.FRONT ? card.getFrontFace() : card.getBackFace());
    }

    /**
     * @return The nickname of the Player
     */
    @Override
    public String getNickname() { return this.nickname; }

    /**
     * @return The list of card in the hand of the player as an ImmutableList
     */
    @Override
    public ImmutableList<KingdomCard> getHand() {
        return new ImmutableList<>(this.cardHand.stream().toList());
    }

    /**
     * @return The starter card Face in the PlayingBoard
     */
    @Override
    public StarterCard getStarterCard() {
        return this.starterCard;
    }

    /**
     * @return The starter card Face in the PlayingBoard
     */
    @Override
    public StarterCardFace getPlacedStarterCardFace() {
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
    public BoardInfo getPlayingBoard() throws PlayingBoardException  {
        if (this.playingBoard == null){
            throw new PlayingBoardException("The PlayingBoard for the player is not instantiated");
        }

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
     * @return The object Score of the player
     */
    public int getObjScore() {
        return this.score.getObjectiveScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawCard(KingdomCard drawnCard) throws PlayerException  {
        if (this.cardHand.size() >= 3){
            throw new PlayerException("Trying to add a 4th card");
        }

        if (this.cardHand.stream().map(Card::getCardId).anyMatch(id -> id == drawnCard.getCardId())){
            throw new PlayerException("Trying to add duplicate card");
        }

        this.cardHand.add(drawnCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeCard(BoardSlot location, KingdomCard card, CardSide side) throws PlayerException {
        if (this.cardHand.stream().map(Card::getCardId).noneMatch(id -> id == card.getCardId())){
            throw new PlayerException("Trying to place a card that is not on the player's hand");
        }

        int bonus = this.playingBoard.placeCard(location, side == CardSide.FRONT ? card.getFrontFace() : card.getBackFace());

        this.cardHand.remove(card);

        this.score.updateCardScore(bonus);
    }
}
