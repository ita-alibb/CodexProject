package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.GameException;
import it.polimi.ingsw.am52.exceptions.PhaseException;
import it.polimi.ingsw.am52.exceptions.PlayerException;
import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.decks.Deck;
import it.polimi.ingsw.am52.model.decks.RandomDealer;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.player.*;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.util.*;

/**
 * The model of the entire game. It creates every object needed to play a game and provides all the information needed
 * to the View via Controller.
 */

public class GameManager {

    //region Static Fields

    /**
     * The maximum number of players in a lobby
     */
    static final int MAX_PLAYERS = 4;

    //endregion

    //region Private Fields

    /**
     * The Players of the game
     */
    private final List<Player> players;
    /**
     * The players who disconnected
     */
    private final List<Player> disconnectedPlayers;
    /**
     * The phase of the current turn
     */
    private Phase phase;
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
     * The Scoreboard of the game; it contains the players' score
     */
    private final Map<Integer, Integer> scoreBoard;

    //endregion

    //region Constructor

    /**
     * Create a new instance of GameManager
     * @param players The nicknames of the players in the lobby
     */
    public GameManager(List<String> players) {
        //First check on the players list, if greater than MAX_PLAYER throws an exception
        if (players.size() > MAX_PLAYERS) {
            throw new GameException("Too many players");
        }
        //Second check on the players list, if empty throws an exception
        if (players.isEmpty()) {
            throw new GameException("Player list is empty");
        }
        //Define the turn phase
        this.phase = new InitPhase();
        //Create and initialize the dealer, which represents a fictitious deck of objective cards
        RandomDealer<Objective> objectiveDealer = Objective.getObjectiveDealer();
        objectiveDealer.init();

        //Use the dealer of the objectives to get 2 objectives, in common for all the players of the lobby
        this.commonObjectives = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            this.commonObjectives.add(objectiveDealer.getNextItem());
        }

        //Create the decks of all types of cards required and shuffle them
        this.resourceCardDeck = Deck.getResourceCardsDeck();
        this.goldCardDeck = Deck.getGoldCardsDeck();
        Deck<StarterCard> starterCardDeck = Deck.getStarterCardsDeck();
        this.resourceCardDeck.shuffle();
        this.goldCardDeck.shuffle();
        starterCardDeck.shuffle();

        //Use the decks to get 4 cards, 2 resource cards and 2 gold cards; these will be the first visible cards.
        this.visibleResourceCards = new ArrayList<>();
        this.visibleGoldCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            this.visibleResourceCards.add(this.resourceCardDeck.draw());
            this.visibleGoldCards.add(this.goldCardDeck.draw());
        }

        //Instantiate the scoreBoard
        this.scoreBoard = new Hashtable<>();
        //Create the objects Player for every string passed to this constructor
        this.players = new ArrayList<>();
        //Shuffle the list of the players, so that the order is random
        Collections.shuffle(players);
        //Iterate for every player
        for (String player : players) {
            /*
             * Draw two random Objective cards and one Starter card to initialize a Player
             */
            this.players.add(new Player(player, objectiveDealer.getNextItem(), objectiveDealer.getNextItem() , starterCardDeck.draw()));
            //Add the player to the ScoreBoard, with his initial score, i.e. zero
            this.scoreBoard.put(players.indexOf(player), this.players.get(players.indexOf(player)).getScore());
            //Give to each player 2 resource card and 1 gold card in his hand, drawn by the corresponding decks
            this.players.getLast().drawCard(this.resourceCardDeck.draw());
            this.players.getLast().drawCard(this.resourceCardDeck.draw());
            this.players.getLast().drawCard(this.goldCardDeck.draw());
        }
        //Initialize the list of the disconnected player, as an empty list
        this.disconnectedPlayers = new ArrayList<>();
    }

    //endregion

    //region Private Methods

    /**
     * After each move, updates the values in the scoreboard
     */
    private void updateScoreBoard() {
        this.scoreBoard.put(this.phase.getCurrPlayer(), this.players.get(this.phase.getCurrPlayer()).getScore());
    }

    /**
     * @param playerId The ID of the player
     * @return The player, with the Setup interface
     */
    private PlayerSetup getPlayerSetup(int playerId) {
        return this.players.get(playerId);
    }

    /**
     * @param playerId  The ID of the player
     * @return The player with the BoardSetup interface
     */
    private PlayerBoardSetup getPlayerBoardSetup(int playerId) {
        return this.players.get(playerId);
    }

    /**
     * @param playerId  The ID of the player
     * @return The player with the Drawing interface
     */
    private PlayerDrawing getPlayerDrawing(int playerId) {
        return this.players.get(playerId);
    }

    /**
     * @return The list of the players, using the interface PlayerInfo
     */
    protected List<PlayerInfo> getPlayerInfos() {
        return new ArrayList<>(this.players);
    }

    /**
     * Update the phase, thanks to the State Design Pattern
     * @param newPhase  The new Phase
     */
    protected void setPhase(Phase newPhase) {
        this.phase = newPhase;
    }

    //endregion

    //region Getters

    /**
     * @return The number of players in the lobby
     */
    public int getPlayersCount() {
        return this.players.size();
    }

    /**
     * @param playerId The ID of the player
     * @return The information about an object Player, thanks to the interface PlayerInfo
     */
    public PlayerInfo getPlayer(int playerId) {
        return this.players.get(playerId);
    }

    /**
     * @return The information of the current player
     */
    public PlayerInfo getCurrentPlayer() {
        return this.getPlayer(this.phase.getCurrPlayer());
    }

    /**
     * @return The number of the turns
     */
    public int getTurn() {
        return this.phase.getTurn();
    }

    /**
     * @return true if it's the last turn, false otherwise
     */
    public boolean isLastTurn() {
        return this.phase.isLastTurn();
    }

    /**
     * Method used to show to player the two objective to choose
     * @param playerId the player
     * @return the player's objectives options
     */
    public ArrayList<Objective> getPlayerObjectiveOptions(int playerId){
        return this.players.get(playerId).getObjectiveOptions();
    }

    /**
     * @return The lasting cards in the resource deck
     */
    public int getResourceDeckCount() {
        return this.resourceCardDeck.cardsCount();
    }

    /**
     * @return The lasting cards in the gold deck
     */
    public int getGoldDeckCount() {
        return this.goldCardDeck.cardsCount();
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
            visibleCards.add(this.visibleResourceCards.get(i).getCardId());
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
            visibleCards.add(this.visibleGoldCards.get(i).getCardId());
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
            visibleCards.add(this.commonObjectives.get(i).getObjectiveId());
        }
        return visibleCards;
    }

    /**
     * @return The status of the turn
     */
    public GamePhase getStatus() {
        return this.phase.getPhase();
    }

    /**
     * @return The winner or the list of the winners. Could be one or more, in case of a tie.
     */
    public List<PlayerInfo> getWinners() {
        try {
            List<PlayerInfo> playersInfo = new ArrayList<>(this.players);
            return this.phase.getWinners(this, playersInfo);
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * @return The scoreboard of the game
     */
    public Map<Integer, Integer> getScoreBoard() {
        return this.scoreBoard;
    }

    //endregion

    //region Setters

    /**
     * Method used to set the chosen secret objective
     * @param playerId the player to set
     * @param objectiveId the objective chosen
     */
    public void setPlayerChosenObject(int playerId, int objectiveId){
        try {
            this.phase.setPlayerChosenObject(this, this.getPlayerSetup(playerId), objectiveId);
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
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
        try {
            //Use the ID to identify the player who made the move and use the correct face of the card
            this.phase.placeStarterCard(
                    this.getPlayerBoardSetup(playerId),
                    StarterCard.getCardWithId(cardId),
                    side
            );
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Place a given generic card in a certain position
     * @param h         The horizontal coordinate
     * @param v         The vertical coordinate
     * @param cardId    The ID of the card
     * @param face      The face used by the card, true if front, false if back
     */
    public void placeCard(int h, int v, int cardId, int face) {
        try {
            //Create the object KingdomCard
            KingdomCard card = KingdomCard.getCardWithId(cardId);
            //Call the method on the current player
            phase.placeCard(
                    this,
                    this.getPlayerDrawing(this.phase.getCurrPlayer()),
                    new BoardSlot(h, v),
                    card,
                    (CardSide.fromInteger(face) == CardSide.FRONT) ? card.getFrontFace() : card.getBackFace()
            );
            //Update the value in the ScoreBoard
            this.updateScoreBoard();
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Draw a random card from the resource deck
     */
    public void drawResourceCard() {
        try {
            this.phase.drawResourceCard(
                    this,
                    this.getPlayerDrawing(this.phase.getCurrPlayer()),
                    this.resourceCardDeck.draw()
            );
        } catch (PlayerException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Draw a random card from the gold deck
     */
    public void drawGoldCard() {
        try {
            this.phase.drawGoldCard(
                    this,
                    this.getPlayerDrawing(this.phase.getCurrPlayer()),
                    this.goldCardDeck.draw()
            );
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Take the visible resource card on the board and draw another card to fill the gap
     * @param cardId    The ID of the visible card
     */
    public void takeResourceCard(int cardId) {
        //If the List of the visible resource cards doesn't contain the card, throw an exception
        if (!this.visibleResourceCards.contains(ResourceCard.getCardWithId(cardId))) {
            throw new GameException("The card selected is not a visible card");
        }
        else {
            try {
                this.phase.takeResourceCard(
                        this,
                        this.getPlayerDrawing(this.phase.getCurrPlayer()),
                        ResourceCard.getCardWithId(cardId),
                        this.visibleResourceCards
                );
            } catch (PhaseException e) {
                throw new GameException(e.getMessage());
            }
        }
    }

    /**
     * Take the visible gold card on the board and draw another card to fill the gap
     * @param cardId    The ID of the visible card
     */
    public void takeGoldCard(int cardId) {
        //If the List of the visible resource cards doesn't contain the card, throw an exception
        if (!this.visibleGoldCards.contains(GoldCard.getCardWithId(cardId))) {
            throw new GameException("The card selected is not a visible card");
        }
        else {
            try {
                this.phase.takeGoldCard(
                        this,
                        this.getPlayerDrawing(this.phase.getCurrPlayer()),
                        GoldCard.getCardWithId(cardId),
                        this.visibleGoldCards
                );
            } catch (PhaseException e) {
                throw new GameException(e.getMessage());
            }
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
        //If the player isn't in the lobby, throws an exception
        if (playerId >= this.players.size()) {
            throw new GameException("The selected player is not in the game");
        }
        this.disconnectedPlayers.add(this.players.get(playerId));
        this.players.remove(playerId);
        this.scoreBoard.remove(playerId);
    }

    //endregion
}