package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.cards.*;

import java.util.Optional;

public class CardIds {
    //TODO : Pattern for printing card. Could be a dedicated class?
    /**
     * The template used to create a card:
     * <lu>
     *     <li>? : The items/resources of each card</lu>
     *     <li>* : The points assigned to the player, the first and second symbols are for
     *     the pattern, the last one is for the points assigned</lu>
     *     <li>+ : The requirement to place the card</lu>
     * </lu>
     */
    public static final String[] TEMPLATE = {
            "┌─────┬─────────┬─────┐",
            "│  %c  │  ****   │  %c  │",
            "├─────┘         └─────┤",
            "│          %c          │",
            "├─────┐         ┌─────┤",
            "│  %c  │   +++   │  %c  │",
            "└─────┴─────────┴─────┘"
    };

    /**
     * The template for the empty slots, with the coordinates for available slots
     */
    public static final String[] EMPTY_TEMPLATE = {
            "┌┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┐",
            "┆                     ┆",
            "┆                     ┆",
            "┆      h=%-2d,v=%-2d      ┆",
            "┆                     ┆",
            "┆                     ┆",
            "└┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┘",
    };

    public int cardId;
    public int cardFace;

    private CardFace card;

    public CardIds(int cardId, int cardFace) {
        this.cardFace = cardFace;
        this.cardId = cardId;
    }

    public static String[] getEmptyTemplate(int h, int v) {
        var card =  EMPTY_TEMPLATE.clone();

        card[3] = card[3].formatted(h, v);

        return card;
    }

    public static String[] getEmptyTemplate() {
        var card =  EMPTY_TEMPLATE.clone();

        card[3] = card[2];

        return card;
    }

    public CardFace getCard() {
        if (card == null) {
            var card = KingdomCard.getCardWithId(cardId);
            if (cardFace == 0) {
                this.card = card.getFrontFace();
            } else if (cardFace == 1) {
                this.card = card.getBackFace();
            }
        }

        return card;
    }

    public CardFace getStarterCard() {
        if (card == null) {
            var card = StarterCard.getCardWithId(cardId);
            if (cardFace == 0) {
                this.card = card.getFrontFace();
            } else if (cardFace == 1) {
                this.card = card.getBackFace();
            }
        }

        return card;
    }

    public String[] getCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL){
        String[] result = TEMPLATE.clone();

        result[1] = result[1].formatted(getSymbol(this.card.getTopLeftCorner(), coveredTL), getSymbol(this.card.getTopRightCorner(), coveredTR));

        result[3] = result[3].formatted(this.getSymbol(this.card.getPermanentResources()));

        result[5] = result[5].formatted(getSymbol(this.card.getBottomLeftCorner(), coveredBL), getSymbol(this.card.getBottomRightCorner(), coveredBR));

        return result;

    }

    private char getSymbol(Optional<CardCorner> corner, boolean isCovered) {
        var res = ' '; // default blank placeable corner

        if (corner.isEmpty() || isCovered){ //Corner is non-placeable?
            res = 'X';
        } else {
            res = this.getSymbol(corner.get().getResources()); //corner has Resource?
            if (res == ' ') {
                res = this.getSymbol(corner.get().getItems()); //corner has Item?
            }
        }

        return res;
    }

    private char getSymbol(ResourcesCounter counter) {
        if (counter.getAnimalCount() != 0) {
            return 'A';
        } else if (counter.getFungiCount() != 0) {
            return 'F';
        } else if (counter.getInsectCount() != 0) {
            return 'I';
        } else if (counter.getPlantCount() != 0) {
            return 'P';
        } else {
            return ' ';
        }
    }

    private char getSymbol(ItemsCounter counter) {
        if (counter.getFeatherCount() != 0) {
            return 'F';
        } else if (counter.getInkCount() != 0) {
            return 'K';
        } else if (counter.getVellumCount() != 0) {
            return 'V';
        } else {
            return ' ';
        }
    }
}