package it.polimi.ingsw.am52.Model.cards;

/**
 * Any object that has items.
 */
public interface ItemsProvider {
    /**
     * 
     * @return The items associated to this object.
     */
    ItemsCounter getItems();
}