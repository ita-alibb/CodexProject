package it.polimi.ingsw.am52.model.decks;

import java.util.List;

import it.polimi.ingsw.am52.model.cards.Card;
import it.polimi.ingsw.am52.model.cards.GoldCard;
import it.polimi.ingsw.am52.model.cards.StarterCard;
import it.polimi.ingsw.am52.model.cards.ResourceCard;

/**
 * A generic class that represents a deck of Card objects, with basic operation
 * of a deck (shuffle, draw, ecc...).
 */
public class Deck<Tcard extends Card> {

    //region Private Fields

    /**
     * The functionality of the deck are delegated to this private
     * instance of the random dealer.
     */
    private final RandomDealer<Tcard> dealer;

    //endregion

    //region Public Static Methods

    /**
     * 
     * @return A new deck with all Starter cards. The cards are sorted as
     * listed in the ref. manual. Call shuffle() to shuffle the cards.
     */
    public static Deck<StarterCard> getStarterCardsDeck() {
        return new Deck<>(StarterCard.getCards().toList());
    }

    /**
     * This method takes an integer value to be used for the random generator,
     * so that the cards in the deck are always in the same order ofter being
     * shuffled.
     * @param seed The seed of the random generator.
     * @return A new deck with all Starter cards, with a specified seed for the
     * random generator. The cards are sorted as listed in the ref. manual.
     * Call shuffle() to shuffle the cards.
     */
    public static Deck<StarterCard> getStarterCardsDeck(int seed) {
        return new Deck<>(StarterCard.getCards().toList(), seed);
    }

    /**
     * 
     * @return A new deck with all Gold cards. The cards are sorted as
     * listed in the ref. manual. Call shuffle() to shuffle the cards.
     */
    public static Deck<GoldCard> getGoldCardsDeck() {
        return new Deck<>(GoldCard.getCards().toList());
    }

    /**
     * This method takes an integer value to be used for the random generator,
     * so that the cards in the deck are always in the same order ofter being
     * shuffled.
     * @param seed The seed of the random generator.
     * @return A new deck with all Gold cards, with a specified seed for the
     * random generator. The cards are sorted as listed in the ref. manual.
     * Call shuffle() to shuffle the cards.
     */
    public static Deck<GoldCard> getGoldCardsDeck(int seed) {
        return new Deck<>(GoldCard.getCards().toList(), seed);
    }

    /**
     * 
     * @return A new deck with all Resource cards. The cards are sorted as
     * listed in the ref. manual. Call shuffle() to shuffle the cards.
     */
    public static Deck<ResourceCard> getResourceCardsDeck() {
        return new Deck<>(ResourceCard.getCards().toList());
    }

    /**
     * This method takes an integer value to be used for the random generator,
     * so that the cards in the deck are always in the same order ofter being
     * shuffled.
     * @param seed The seed of the random generator.
     * @return A new deck with all Resource cards, with a specified seed for the
     * random generator. The cards are sorted as listed in the ref. manual.
     * Call shuffle() to shuffle the cards.
     */
    public static Deck<ResourceCard> getResourceCardsDeck(int seed) {
        return new Deck<>(ResourceCard.getCards().toList(), seed);
    }

    //endregion

    //region Constructors

    /**
     * Create a new deck that handles the passed list of cards.
     * @param cards The cards of this deck.
     */
    public Deck(List<Tcard> cards) {

        // Simply instantiate the private dealer.
        this.dealer = new RandomDealer<>(cards);

    }

    /**
     * Create a new deck that handles the passed list of cards,
     * with a specified seed for the random generator.
     * @param cards The cards of this deck.
     * @param seed The seed of the random generator.
     */
    public Deck(List<Tcard> cards, int seed) {

        // Simply instantiate the private dealer.
        this.dealer = new RandomDealer<>(cards, seed);

    }

    //endregion

    //region Public Methods

    /**
     * Shuffle the deck. All cards of the deck become available,
     * and they are sorted in random order.
     */
    public void shuffle() {
        // Delegate the action to the private dealer.
        this.dealer.init();
    }

    /**
     * 
     * @return The card drawn from the deck.
     * @throws IllegalStateException If there are no more cards in the deck.
     */
    public Tcard draw() throws IllegalStateException {
        // Delegate the action to the private dealer.
        return this.dealer.getNextItem();
    }

    /**
     * 
     * @return The count of remaining cards in the deck.
     */
    public int cardsCount() {
        // Delegate the action to the private dealer.
        return this.dealer.getItemsCount();
    }

    /**
     * 
     * @return True if there are no more cards in the deck.
     */
    public boolean isEmpty() {
        // Delegate the action to the private dealer.
        return !this.dealer.hasNext();
    }

    //endregion

}
