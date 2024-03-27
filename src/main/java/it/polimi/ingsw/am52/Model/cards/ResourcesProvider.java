package it.polimi.ingsw.am52.Model.cards;

/**
 * Any object that has resources.
 */
public interface ResourcesProvider {
    /**
     * 
     * @return The resources associated to this object.
     */
    ResourcesCounter getResources();
}
