package it.polimi.ingsw.am52.model.game;

import it.polimi.ingsw.am52.exceptions.*;
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
     * The minimum number of players in a lobby
     */
    public static final int MIN_PLAYERS = 2;

    /**
     * The maximum number of players in a lobby
     */
    public static final int MAX_PLAYERS = 4;

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
     * The Scoreboard of the game; it contains the players' score the map is nickname:score
     */
    private final Map<String, Integer> scoreBoard;

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
        //Shuffle the list of the players, so that the order is random. Needed another list because the list passed is unmodifiable
        var shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);
        //Iterate for every player
        for (String nickname : shuffledPlayers) {
            /*
             * Draw two random Objective cards and one Starter card to initialize a Player
             */
            this.players.add(new Player(nickname, objectiveDealer.getNextItem(), objectiveDealer.getNextItem() , starterCardDeck.draw()));
            //Add the player to the ScoreBoard, with his initial score, i.e. zero
            this.scoreBoard.put(nickname, 0);
            //Give to each player 2 resource card and 1 gold card in his hand, drawn by the corresponding decks
            this.players.getLast().drawCard(this.resourceCardDeck.draw());
            this.players.getLast().drawCard(this.resourceCardDeck.draw());
            this.players.getLast().drawCard(this.goldCardDeck.draw());
        }
        //Initialize the list of the disconnected player, as an empty list
        this.disconnectedPlayers = new ArrayList<>();

        //Define the turn phase
        this.phase = new InitPhase(this.players.getFirst().getNickname());
    }

    //endregion

    //region Private Methods

    /**
     * After each move, updates the values in the scoreboard
     */
    private void updateScoreBoard() {
        this.scoreBoard.put(this.phase.getCurrPlayer(), this.getPlayer(this.phase.getCurrPlayer()).getScore());
    }

    /**
     * @param nickname The nickname of the player
     * @return The player, with the Setup interface
     */
    private PlayerSetup getPlayerSetup(String nickname) {
        try{
            return this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
    }

    /**
     * @param nickname The nickname of the player
     * @return The player with the BoardSetup interface
     */
    private PlayerBoardSetup getPlayerBoardSetup(String nickname) {
        try{
            return this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
    }

    /**
     * @param nickname The nickname of the player
     * @return The player with the Drawing interface
     */
    private PlayerDrawing getPlayerDrawing(String nickname) {
        try{
            return this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
    }

    /**
     * @param nickname The nickname of the player
     * @return The information about an object Player, thanks to the interface PlayerInfo
     */
    public PlayerInfo getPlayer(String nickname) {
        try{
            return this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
    }

    /**
     * Used from the Phase to get the next player
     *
     * @param nickname The nickname of the player
     * @return The nickname of the next player which has to play
     */
    public String getNextPlayer(String nickname) {
        try{
            var currPlayer = this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get();
            var index = (this.players.indexOf(currPlayer) + 1) % this.players.size();
            return this.players.get(index).getNickname();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
    }

    /**
     * @return The list of the players, using the interface PlayerInfo
     */
    public List<PlayerInfo> getPlayerInfos() {
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
     * @param nickname the player
     * @return the player's objectives options
     */
    public ArrayList<Objective> getPlayerObjectiveOptions(String nickname){
        try{
            return this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().get().getObjectiveOptions();
        } catch (Exception e){
            //TODO: maybe better handling
            return null;
        }
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
    public Map<String, Integer> getScoreBoard() {
        return this.scoreBoard;
    }

    /**
     * TODO: must be improved, not send the actual object
     */
    public Phase getStatusResponse() {
        return this.phase;
    }
    //endregion

    //region Setters

    /**
     * Method used to set the chosen secret objective
     * @param nickname the player to set
     * @param objectiveId the objective chosen
     * @throws GameException    The phase is not correct
     * @throws PlayerException          The objective cannot be chosen by the player or has been already chosen
     */
    public void setPlayerChosenObject(String nickname, int objectiveId){
        try {
            this.phase.setPlayerChosenObject(this, this.getPlayerSetup(nickname), objectiveId);
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    //endregion

    //region Public Methods

    /**
     * Place a given starter card using the given face
     * @param nickname  The player who place the card
     * @param cardId    The ID of the card
     * @param side      The face used by the card
     * @throws GameException            The phase is not correct
     * @throws PlayingBoardException    The Playing Board of the player has already been instantiated or the player doesn't have the given starter card
     */
    public void placeStarterCard(String nickname, int cardId, int side) {
        try {
            //Use the ID to identify the player who made the move and use the correct face of the card
            this.phase.placeStarterCard(
                    this,
                    this.getPlayerBoardSetup(nickname),
                    StarterCard.getCardWithId(cardId),
                    CardSide.fromInteger(side)
            );
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Place a given generic card in a certain position
     * @param cardId    The ID of the card
     * @param face      The face used by the card, (0) if front, (1) if back
     * @param slot      The position of the card
     * @throws GameException            The phase is not correct
     * @throws CardException            The card doesn't exist
     * @throws PlayerException          The card isn't in the player's hand or the face does not belong to the card
     * @throws PlayingBoardException    There aren't enough resources or items on the board
     */
    public void placeCard(int cardId, int face, BoardSlot slot) {
        try {
            //Create the object KingdomCard
            KingdomCard card = KingdomCard.getCardWithId(cardId);
            //Call the method on the current player
            phase.placeCard(
                    this,
                    this.getPlayerDrawing(this.phase.getCurrPlayer()),
                    slot,
                    card,
                    CardSide.fromInteger(face)
            );
            //Update the value in the ScoreBoard
            this.updateScoreBoard();
        } catch (PhaseException e) {
            throw new GameException(e.getMessage());
        }
    }

    /**
     * Draw a random card from the resource deck
     * @throws GameException            The phase is not correct
     * @throws DeckException            There are no more items in the dealer
     * @throws IllegalStateException    There are no more items in the list
     * @throws PlayerException          The hand of the player has already 3 cards or adding a duplicate card
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
     * @throws GameException            The phase is not correct
     * @throws DeckException            There are no more items in the dealer
     * @throws IllegalStateException    There are no more items in the list
     * @throws PlayerException          The hand of the player has already 3 cards or adding a duplicate card
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
     * @param nickname  The ID of the disconnected player
     */
    public void leaveGame(String nickname) {
        /*
         * Put the player in the disconnected list players and remove it from the playing players.
         * It could be useful for a system which is resilient to disconnection, because it doesn't block the game,
         * preventing the deletion of the data connected to the player who left.
         */
        //If the player isn't in the lobby, throws an exception
        var player = this.players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        if (player.isEmpty()) {
            throw new GameException("The selected player is not in the game");
        }
        this.disconnectedPlayers.add(player.get());
        this.players.remove(player.get());
        this.scoreBoard.remove(nickname);
    }

    //endregion
}