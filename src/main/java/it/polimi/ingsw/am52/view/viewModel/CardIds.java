package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.cards.*;

import java.util.Optional;

public class CardIds {
    public int cardId;
    public int cardFace;

    private CardFace card;

    public CardIds(int cardFace, int cardId) {
        this.cardFace = cardFace;
        this.cardId = cardId;
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

    public String[] getCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL){
        String[] result = new String[3];

        if (coveredTL){
            result[0] = "X";
        } else {
            result[0] = getSymbol(this.card.getTopLeftCorner());
        }

        result[0] += "---";

        if (coveredTL){
            result[0] += "X";
        } else {
            result[0] += getSymbol(this.card.getTopLeftCorner());
        }

        result[1] = "| " + this.getSymbol(this.card.getPermanentResources()) + " |";

        if (coveredTL){
            result[2] = "X";
        } else {
            result[2] = getSymbol(this.card.getTopLeftCorner());
        }

        result[2] += "---";

        if (coveredTL){
            result[2] += "X";
        } else {
            result[2] += getSymbol(this.card.getTopLeftCorner());
        }

        return result;

    }

    private String getSymbol(Optional<CardCorner> corner) {
        var res = "*"; // default blank placeable corner

        if (corner.isEmpty()){ //Corner is non-placeable?
            res = "X";
        } else {
            res = this.getSymbol(corner.get().getResources()); //corner has Resource?
            if (res.equals(" ")) {
                res = this.getSymbol(corner.get().getItems()); //corner has Item?
            }
        }

        return res;
    }

    private String getSymbol(ResourcesCounter counter) {
        if (counter.getAnimalCount() != 0) {
            return "A";
        } else if (counter.getFungiCount() != 0) {
            return "F";
        } else if (counter.getInsectCount() != 0) {
            return "I";
        } else if (counter.getPlantCount() != 0) {
            return "P";
        } else {
            return " ";
        }
    }

    private String getSymbol(ItemsCounter counter) {
        if (counter.getFeatherCount() != 0) {
            return "F";
        } else if (counter.getInkCount() != 0) {
            return "K";
        } else if (counter.getVellumCount() != 0) {
            return "V";
        } else {
            return "*";
        }
    }
}