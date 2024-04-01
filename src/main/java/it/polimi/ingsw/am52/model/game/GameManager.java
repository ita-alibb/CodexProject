package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.decks.Deck;
import it.polimi.ingsw.am52.model.decks.RandomDealer;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.player.Player;
import it.polimi.ingsw.am52.model.player.PlayerInfo;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.util.*;

/**
 * The model of the entire game. It creates every object needed to play a game and provides all the information needed
 * to the View via Controller.
 */

public class GameManager {

    //region Private Fields

    /**
     * The Players of the game
     */
    private final List<Player> players;
    /**
     * The players who disconnected
     */
    private List<Player> disconnectedPlayers;
    /**
     * The player who is playing the turn
     */
    private int currPlayer;
    /**
     * The turn which is playing
     */
    private int turn;
    /**
     * The phase of the current turn
     */
    private GameState phase;
    /**
     * The common objectives in a game
     */
    private final List<Objective> commonObjectives;
    /**
     * The visible resource cards
     */
    private final List<ResourceCard> visibleResourceCards;
    /**
     * The visible gold cards
     */
    private final List<GoldCard> visibleGoldCards;
    /**
     * The deck for the resource cards
     */
    private final Deck<ResourceCard> resourceCardDeck;
    /**
     * The deck for the gold cards
     */
    private final Deck<GoldCard> goldCardDeck;
    /**
     * The deck for the starter cards, useful during the creation of the lobby
     */
    private final Deck<StarterCard> starterCardDeck;
    /**
     * An object RandomDealer, which represent a false "deck" for objective cards
     */
    private final RandomDealer<Objective> objectiveDealer;
    /**
     * The Scoreboard of the game; it contains the players' score
     */
    private Dictionary<Integer, Integer> scoreBoard;

    //endregion

    //region Constructor

    /**
     * Create a new instance of GameManager
     * @param players The nicknames of the players in the lobby
     */
    public GameManager(List<String> players) {
        //Create and initialize the dealer, which represents a fictitious deck of objective cards
        this.objectiveDealer = Objective.getObjectiveDealer();
        objectiveDealer.init();

        //Use the dealer of the objectives to get 2 objectives, in common for all the players of the lobby
        this.commonObjectives = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            this.commonObjectives.add(objectiveDealer.getNextItem());
        }

        //Create the decks of all types of cards required and shuffle them
        this.resourceCardDeck = Deck.getResourceCardsDeck();
        this.goldCardDeck = Deck.getGoldCardsDeck();
        this.starterCardDeck = Deck.getStarterCardsDeck();
        resourceCardDeck.shuffle();
        goldCardDeck.shuffle();
        starterCardDeck.shuffle();

        //Use the decks to get 4 cards, 2 resource cards and 2 gold cards; these will be the first visible cards.
        this.visibleResourceCards = new ArrayList<>();
        this.visibleGoldCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            this.visibleResourceCards.add(resourceCardDeck.draw());
            this.visibleGoldCards.add(goldCardDeck.draw());
        }

        //Instantiate the scoreBoard
        this.scoreBoard = new Hashtable<>();
        //Create the objects Player for every string passed to this constructor
        this.players = new ArrayList<>();
        for (String player : players) {
            /*
             * The color of the pawn is chosen depending on the entering order of the players. The order is:
             *      First player    : Red
             *      Second player   : Blue
             *      Third Player    : Green
             *      Fourth Player   : Yellow (Al momento è viola, perché c'è un'incongruenza tra i colori dei regni e i colori delle pedine)
             */
            KingdomColor pawnColor = switch (players.indexOf(player)) {
                case 0 -> KingdomColor.RED;
                case 1 -> KingdomColor.BLUE;
                case 2 -> KingdomColor.GREEN;
                case 3 -> KingdomColor.VIOLET;
                default -> null;
            };
            this.players.add(new Player(player, pawnColor, starterCardDeck.draw()));
            //Add the player to the ScoreBoard, with his initial score, i.e. zero
            this.scoreBoard.put(players.indexOf(player), this.players.get(players.indexOf(player)).getScore());
        }
        //Shuffle the list of the players, so that the order is random
        Collections.shuffle(players);
        //Initialize the list of the disconnected player, as an empty list
        this.disconnectedPlayers = new ArrayList<>();
        //Initialize by default the current player, the turn and the turn phase
        this.currPlayer = 0;
        this.turn = 0;
        this.phase = GameState.INIT;
    }

    //endregion

    //region Private Methods

    /**
     * Private method to check and set the state of a game. It is represented by a finite state automaton, with the possible transitions:
     * (INIT-->PLACING, PLACING-->DRAWING, DRAWING-->PLACING, DRAWING-->END)
     * @param phase The game phase
     */
    private void setGameState(GameState phase) throws GameException {
        //Switch on the private field phase
        switch (this.phase) {
            //If the current phase is INIT
            case INIT -> {
                //If the next phase isn't PLACING, throws an exception
                if (phase != GameState.PLACING) {
                    throw new GameException("The phase of the turn does not match with the action of the player");
                }
            }
            //If the current phase is PLACING
            case PLACING -> {
                //If the next phase isn't DRAWING, throws an exception
                if (phase != GameState.DRAWING) {
                    throw new GameException("The phase of the turn does not match with the action of the player");
                }
            }
            //If the current phase is DRAWING
            case DRAWING -> {
                //If the next phase isn't PLACING either END, throws an exception
                if (phase != GameState.PLACING && phase != GameState.END) {
                    throw new GameException("The phase of the turn does not match with the action of the player");
                }
            }
        }
        //After all the checks, set the new phase in the private field
        this.phase = phase;
    }
    /**
     * After each move, updates the values in the scoreboard
     */
    private void updateScoreBoard() {
        scoreBoard.put(currPlayer, players.get(currPlayer).getScore());
    }

    //endregion

    //region Getters

    /**
     * @return The number of players in the lobby
     */
    public int getPlayersCount() {
        return players.size();
    }

    /**
     * @return The information about an object Player, thanks to the interface PlayerInfo
     */
    public PlayerInfo getPlayer(int playerId) {
        return players.get(playerId);
    }
    /**
     * @return The information of the current player
     */
    public PlayerInfo getCurrentPlayer() {
        return getPlayer(currPlayer);
    }
    /**
     * @return The lasting cards in the resource deck
     */
    public int getResourceDeckCount() {
        return resourceCardDeck.cardsCount();
    }
    /**
     * @return The lasting cards in the gold deck
     */
    public int getGoldDeckCount() {
        return goldCardDeck.cardsCount();
    }
    /**
     * @return The list of IDs the visible resource cards
     */
    public List<Integer> getVisibleResourceCards() {
        //Create a new List of integers
        List<Integer> visibleCards = new ArrayList<>();
        //For the 2 cards on the common board
        for (int i = 0; i < 2; i++) {
            //Add the ID of the visible cards to the list
            visibleCards.add(visibleResourceCards.get(i).getCardId());
        }
        return visibleCards;
    }
    /**
     * @return The list of IDs the visible gold cards
     */
    public List<Integer> getVisibleGoldCards() {
        //Create a new List of integers
        List<Integer> visibleCards = new ArrayList<>();
        //For the 2 cards on the common board
        for (int i = 0; i < 2; i++) {
            //Add the ID of the visible cards to the list
            visibleCards.add(visibleGoldCards.get(i).getCardId());
        }
        return visibleCards;
    }
    /**
     * @return The list of IDs the common objective cards
     */
    public List<Integer> getCommonObjectives() {
        //Create a new List of integers
        List<Integer> visibleCards = new ArrayList<>();
        //For the 2 cards on the common board
        for (int i = 0; i < 2; i++) {
            //Add the ID of the visible cards to the list
            visibleCards.add(commonObjectives.get(i).getObjectiveId());
        }
        return visibleCards;
    }
    /**
     * @return A list of two objectives, so that the player can choose one out of them
     */
    public List<Objective> getRandomObjectives() {
        //Create a new list of Objective
        List<Objective> objectiveToChoose = new ArrayList<>();
        //For 2 times, so for 2 cards
        for (int i = 0; i < 2; i++) {
            //Add an objective to the list, and return it
            objectiveToChoose.add(objectiveDealer.getNextItem());
        }
        return objectiveToChoose;
    }
    /**
     * @return The status of the turn
     */
    public GameState getStatus() {
        return phase;
    }
    /**
     * @return The winner or the list of the winners. Could be one or more, in case of a tie.
     */
    public List<PlayerInfo> getWinners() throws GameException {
        //Check if the game is ending
        if (getStatus() == GameState.END) {
            throw new GameException("The game is not ended yet!");
        }
        //Create the list of PlayerInfo
        List<PlayerInfo> possibleWinners = new ArrayList<>();
        //Add by default the first player and pass to the next
        possibleWinners.add(players.getFirst());
        //For each person in the lobby
        for (int i = 1; i < players.size(); i++) {
            //If the score of a player is greater than the one of player who is already in the list
            if (scoreBoard.get(i) > possibleWinners.getFirst().getScore()) {
                possibleWinners.clear();
                possibleWinners.add(players.get(i));
            }
            //If the score of a player is equal to the one of the player who is already in the list
            else if (scoreBoard.get(i) == possibleWinners.getFirst().getScore()) {
                possibleWinners.add(players.get(i));
            }
        }
        //Once I have the list of the winner, if this contains more than one player, return it, otherwise choose the winner with the objectives score
        if (possibleWinners.size() == 1) {
            return possibleWinners;
        }
        //Create a new list
        List<PlayerInfo> winners = new ArrayList<>();
        winners.add(possibleWinners.getFirst());
        //If the list is bigger than one element
        //For each item in the temp list possibleWinners
        for (int i = 1; i < possibleWinners.size(); i++) {
            //If a Player has a bigger objective score than the one in the definitive list
            if (winners.getFirst().getObjScore() < possibleWinners.get(i).getObjScore()) {
                //Clear the list and add the Player in it
                winners.clear();
                winners.add(possibleWinners.get(i));
            }
            //If a Player has the same objective score as the one in the definitive list
            else if (winners.getFirst().getObjScore() == possibleWinners.get(i).getObjScore()) {
                //Add the Player in the list
                winners.add(possibleWinners.get(i));
            }
        }
        //Return the value of the winner
        return winners;
    }
    /**
     * @return Two objective cards, so that each player can choose one out of them
     */
    public List<Objective> getObjectiveToChoose() {
        List<Objective> cards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            cards.add(objectiveDealer.getNextItem());
        }
        return cards;
    }
    /**
     * @return The scoreboard of the game
     */
    public Dictionary<Integer, Integer> getScoreBoard() {
        return scoreBoard;
    }

    //endregion

    //region Setters

    /**
     * Take the chosen objective and set the secret objective of a player
     * @param playerId          The ID of the player who made the choice
     * @param secretObjective   The objective chosen
     */
    public void setSecretObjective(int playerId, Objective secretObjective) {
        players.get(playerId).setSecretObjective(secretObjective);
    }

    //endregion

    //region Public Methods

    /**
     * Place a given starter card using the given face
     * @param playerId  The player who place the card
     * @param cardId    The ID of the card
     * @param side      The face used by the card
     */
    public void placeStarterCard(int playerId, int cardId, CardSide side) {
        //Create the card to place
        StarterCard firstCard = StarterCard.getCardWithId(cardId);
        //Use the ID to identify the player who made the move and use the correct face of the card
        players.get(playerId).placeStarterCardFace(side == CardSide.FRONT ? firstCard.getFrontFace() : firstCard.getBackFace());
    }
    /**
     * Place a given generic card in a certain position
     * @param h         The horizontal coordinate
     * @param v         The vertical coordinate
     * @param cardId    The ID of the card
     * @param face      The face used by the card, true if front, false if back
     */
    public void placeCard(int h, int v, int cardId, int face) throws GameException {
        //Check and update the phase of the turn
        setGameState(GameState.DRAWING);
        //Create the object BoardSlot from the integer coordinates
        BoardSlot slot = new BoardSlot(h, v);
        //Create the object CardSide from the boolean variable
        CardSide side = CardSide.fromInteger(face);
        //Create the card and the face chosen
        KingdomCard card = KingdomCard.getCardWithId(cardId);
        KingdomCardFace cardFace = (side == CardSide.FRONT) ? card.getFrontFace() : card.getBackFace();
        //Call the method on the current player
        players.get(currPlayer).placeCard(slot, card, cardFace);
        //Update the value in the ScoreBoard
        updateScoreBoard();
    }
    /**
     * Draw a random card from the resource deck
     */
    public void drawResourceCard() throws GameException {
        //Check the turn phase and set the new one
        setGameState(GameState.PLACING);
        players.get(currPlayer).drawCard(resourceCardDeck.draw());
        //Update the current player
        currPlayer = (currPlayer + 1) % players.size();
        //Update the number of the turn, if necessary
        if (currPlayer == 0) {
            turn++;
        }
    }
    /**
     * Draw a random card from the gold deck
     */
    public void drawGoldCard() {
        //Check and update the turn phase
        setGameState(GameState.PLACING);
        players.get(currPlayer).drawCard(goldCardDeck.draw());
        //Update the current player
        currPlayer = (currPlayer + 1) % players.size();
        //Update the number of the turn, if necessary
        if (currPlayer == 0) {
            turn++;
        }
    }
    /**
     * Take the visible resource card on the board and draw another card to fill the gap
     * @param cardId    The ID of the visible card
     */
    public void takeResourceCard(int cardId) {
        //Check and update the turn phase
        setGameState(GameState.PLACING);
        //Create the object KingdomCard
        KingdomCard card;
        //If the drawn card is the first card
        if (visibleResourceCards.getFirst().getCardId() == cardId) {
            //Take the first card, remove it from the list and draw another card to fill the gap
            card = visibleResourceCards.getFirst();
            visibleResourceCards.removeFirst();
            visibleResourceCards.add(resourceCardDeck.draw());
        }
        //If the drawn card is the second and last card
        else if (visibleResourceCards.getLast().getCardId() == cardId) {
            //Take the second card, remove it from the list and draw another card to fill the gap
            card = visibleResourceCards.getLast();
            visibleResourceCards.removeLast();
            visibleResourceCards.add(resourceCardDeck.draw());
        }
        //If the ID is not corresponding, throw an exception
        else {
            throw new GameException("The card selected is not a visible card");
        }
        //Add the card in the hand of the current player
        players.get(currPlayer).drawCard(card);
        //Update the current player
        currPlayer = (currPlayer + 1) % players.size();
        //Update the number of the turn, if necessary
        if (currPlayer == 0) {
            turn++;
        }
    }
    /**
     * Take the visible gold card on the board and draw another card to fill the gap
     * @param cardId    The ID of the visible card
     */
    public void takeGoldCard(int cardId) {
        //Check and update the turn phase
        setGameState(GameState.PLACING);
        //Create the object KingdomCard
        KingdomCard card;
        //If the drawn card is the first card
        if (visibleGoldCards.getFirst().getCardId() == cardId) {
            //Take the first card, remove it from the list and draw another card to fill the gap
            card = visibleGoldCards.getFirst();
            visibleGoldCards.removeFirst();
            visibleGoldCards.add(goldCardDeck.draw());
        }
        //If the drawn card is the second and last card
        else if (visibleGoldCards.getLast().getCardId() == cardId) {
            //Take the second card, remove it from the list and draw another card to fill the gap
            card = visibleGoldCards.getLast();
            visibleGoldCards.removeLast();
            visibleGoldCards.add(goldCardDeck.draw());
        }
        //If the ID is not corresponding, throw an exception
        else {
            throw new GameException("The selected card is not a visible card");
        }
        players.get(currPlayer).drawCard(card);
        //Update the current player
        currPlayer = (currPlayer + 1) % players.size();
        //Update the number of the turn, if necessary
        if (currPlayer == 0) {
            turn++;
        }
    }
    /**
     * Report a player disconnected
     * @param playerId  The ID of the disconnected player
     */
    public void leaveGame(int playerId) {
        /*
         * Put the player in the disconnected list players and remove it from the playing players.
         * It could be useful for a system which is resilient to disconnection, because it doesn't block the game,
         * preventing the deletion of the data connected to the player who left.
         */
        disconnectedPlayers.add(players.get(playerId));
        players.remove(playerId);
    }

    //endregion
}