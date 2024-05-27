package it.polimi.ingsw.am52.model.cards;

import static it.polimi.ingsw.am52.model.cards.BlankCorner.BLANK_CORNER;

/**
 * The back face of a Kingdom card (Gold or Resource card). Every
 * Kingdom card has a permanent resource on its back face, equal to
 * the resource associated to the Kingdom.
 */
public abstract class KingdomBackFace extends KingdomCardFace {

    //region Getters

    /**
     * All back faces of Kingdom cards have a permanent resource.
     * @return The permanent resource of the card face.
     */
    @Override
    public ResourcesCounter getPermanentResources() {
        return new ResourcesCounter(getKingdom().getResource());
    }

    //endregion

    //region Constructors

    /**
     * Creates the back face of a Kingdom card. All back faces
     * have four blank corners.
     */
    protected KingdomBackFace() {
        super(BLANK_CORNER, BLANK_CORNER, BLANK_CORNER, BLANK_CORNER);
    }

    //endregion

    //region Overrides

    @Override
    public CardSide getCardSide() {
        return CardSide.BACK;
    }

    /**
     * Kingdom cards (Gold and Resource) have a permanent resource on
     * their back face. This method takes into consideration both resources
     * visible on the corners and the permanent resource.
     * @return The resources associated to this card face.
     */
    @Override
    public ResourcesCounter getResources() {

        // Invoke super-class implementation in order to calculate
        // visible resources on the card corners.
        ResourcesCounter cornerResources = super.getResources();

        // Add the permanent resource and return the result.
        return ResourcesCounter.add(cornerResources, getPermanentResources());
    }

    //endregion

    //region Public Static Final Fields

    /**
     * The instance of the Fungi Kingdom back face.
     */
    public static final KingdomBackFace FUNGI_BACK_FACE = new KingdomBackFace() {
        @Override
        public Kingdom getKingdom() {
            return Kingdom.FUNGI_KINGDOM;
        }
    };

    /**
     * The instance of the Plant Kingdom back face.
     */
    public static final KingdomBackFace PLANT_BACK_FACE = new KingdomBackFace() {
        @Override
        public Kingdom getKingdom() {
            return Kingdom.PLANT_KINGDOM;
        }
    };

    /**
     * The instance of the Animal Kingdom back face.
     */
    public static final KingdomBackFace ANIMAL_BACK_FACE = new KingdomBackFace() {
        @Override
        public Kingdom getKingdom() {
            return Kingdom.ANIMAL_KINGDOM;
        }
    };

    /**
     * The instance of the Insect Kingdom back face.
     */
    public static final KingdomBackFace INSECT_BACK_FACE = new KingdomBackFace() {
        @Override
        public Kingdom getKingdom() {
            return Kingdom.INSECT_KINGDOM;
        }
    };

    //endregion
}
