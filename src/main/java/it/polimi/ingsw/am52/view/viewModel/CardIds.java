package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.objectives.Objective;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardIds {
    /**
     * The template used to create a card.
     * <lu>
     *     <li>Angles : The items/resources of each card</li>
     *     <li>Top Center : The points assigned to the player, the first and second symbols are for
     *     the pattern, the last one is for the points assigned</li>
     *     <li>Bottom Center : The requirement to place the card</li>
     *     <li>Center : The permanent resource of the card</li>
     * </lu>
     */
    public static final String[] TEMPLATE = {
            "┌─────┬─────────┬─────┐",
            "│  %c  │  %-5s  │  %c  │",
            "├─────┘         └─────┤",
            "│          %c          │",
            "├─────┐         ┌─────┤",
            "│  %c  │  %-5s  │  %c  │",
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

    /**
     * The template to print an objective card. The first string is for the kind of pattern, and the second one is for the points assigned to the pattern
     */
    public static final String[] OBJECTIVE_TEMPLATE = {
            "┌─────────────────────┐",
            "│                     │",
            "│ %-19s │",
            "│                     │",
            "│ Points assigned: %-2d │",
            "│                     │",
            "└─────────────────────┘"
    };

    /**
     * The method to print two different starter cards
     * @param first     The first card
     * @param second    The second card
     */
    public static void printTwoStarterCards(CardIds first, CardIds second) {
        first.loadStarterFace();
        second.loadStarterFace();
        var cardList1 = first.getCardAsArrayString(false, false, false, false);
        var cardList2 = second.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < TEMPLATE.length; i++) {
            System.out.printf("│ " + cardList1[i] + "   " + cardList2[i] + "%20s│%n", "");
        }
    }

    /**
     * The method to print a single starter card
     * @param first     The single starter card
     */
    public static void printSingleStarterCard(CardIds first) {
        first.loadStarterFace();
        var cardList = first.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < CardIds.TEMPLATE.length; i++) {
            System.out.printf("│ " + cardList[i] + "%46s│%n", "");
        }
    }

    /**
     * The method to print to different objective cards, used to abstract two different methods with different purpose, in Setup and Common boards
     * @param first     The first objective
     * @param second    The second objective
     */
    public static void printTwoObjectives(CardIds first, CardIds second) {
        var objList1 = first.getObjectiveAsArrayString();
        var objList2 = second.getObjectiveAsArrayString();

        for (int i = 0; i < 7; i++) {
            System.out.printf("│ " + objList1[i] + "   " + objList2[i] + "%20s│%n", "");
        }
    }

    /**
     * The method to print a single objective card
     * @param first     The first and only objective
     */
    public static void printSingleObjective (CardIds first) {
        var objList = first.getObjectiveAsArrayString();

        for (int i = 0; i < 7; i++) {
            System.out.printf("│ " + objList[i] + "%46s│%n", "");
        }
    }

    /**
     * The method to print the two visible resource cards
     */
    public static void printVisibleResourceCards() {
        var firstFront = new CardIds(ViewModelState.getInstance().getVisibleResourceCards().getFirst(), 0);
        var firstBack = new CardIds(ViewModelState.getInstance().getVisibleResourceCards().getFirst(), 1);
        var secondFront = new CardIds(ViewModelState.getInstance().getVisibleResourceCards().getLast(), 0);
        var secondBack = new CardIds(ViewModelState.getInstance().getVisibleResourceCards().getLast(), 1);

        firstFront.loadFace();
        firstBack.loadFace();
        secondFront.loadFace();
        secondBack.loadFace();

        System.out.printf( "│ %-68s │%n", "Card Id: " + firstFront.cardId);
        System.out.printf( "│ %-23s   %-43s│%n", "Front face:", "Back face:");
        var resListFront1 = firstFront.getCardAsArrayString(false, false, false, false);
        var resListBack1 = firstBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < resListFront1.length; i++) {
            System.out.printf("│ " + resListFront1[i] + "   " + resListBack1[i] + "%20s│%n", "");
        }

        System.out.printf( "│ %-68s │%n", "Card Id: " + secondFront.cardId);
        System.out.printf( "│ %-23s   %-43s│%n", "Front face:", "Back face:");
        var resListFront2 = secondFront.getCardAsArrayString(false, false, false, false);
        var resListBack2 = secondBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < resListFront1.length; i++) {
            System.out.printf("│ " + resListFront2[i] + "   " + resListBack2[i] + "%20s│%n", "");
        }
    }

    /**
     * The method to print the two visible gold cards
     */
    public static void printVisibleGoldCards() {
        var firstFront = new CardIds(ViewModelState.getInstance().getVisibleGoldCards().getFirst(), 0);
        var firstBack = new CardIds(ViewModelState.getInstance().getVisibleGoldCards().getFirst(), 1);
        var secondFront = new CardIds(ViewModelState.getInstance().getVisibleGoldCards().getLast(), 0);
        var secondBack = new CardIds(ViewModelState.getInstance().getVisibleGoldCards().getLast(), 1);

        firstFront.loadFace();
        firstBack.loadFace();
        secondFront.loadFace();
        secondBack.loadFace();

        System.out.printf( "│ %-68s │%n", "Card Id: " + firstFront.cardId);
        System.out.printf( "│ %-23s   %-43s│%n", "Front face:", "Back face:");
        var goldListFront1 = firstFront.getCardAsArrayString(false, false, false, false);
        var goldListBack1 = firstBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < goldListFront1.length; i++) {
            System.out.printf("│ " + goldListFront1[i] + "   " + goldListBack1[i] + "%20s│%n", "");
        }

        System.out.printf( "│ %-68s │%n", "Card Id: " + secondFront.cardId);
        System.out.printf( "│ %-23s   %-43s│%n", "Front face:", "Back face:");
        var goldListFront2 = secondFront.getCardAsArrayString(false, false, false, false);
        var goldListBack2 = secondBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < goldListFront1.length; i++) {
            System.out.printf("│ " + goldListFront2[i] + "   " + goldListBack2[i] + "%20s│%n", "");
        }
    }

    /**
     * The method to print the hand of the player
     */
    public static void printHand() {
        var hand = ViewModelState.getInstance().getPlayerHand();

        var frontHand = hand.stream()
                .map(c -> new CardIds(c, 0))
                .toList();
        frontHand.forEach(CardIds::loadFace);
        var frontHandList = frontHand.stream()
                .map(c -> c.getCardAsArrayString(false, false, false, false))
                .toList();

        var backHand = hand.stream()
                .map(c -> new CardIds(c, 1))
                .toList();
        backHand.forEach(CardIds::loadFace);
        var backHandList = backHand.stream()
                .map(c -> c.getCardAsArrayString(false, false, false, false))
                        .toList();

        System.out.printf("│ %-74s │%n", "Front:");
        for (int i = 0; i < TEMPLATE.length; i++) {
            System.out.printf("│ " + frontHandList.getFirst()[i] + "  " + frontHandList.get(1)[i] + "  " + frontHandList.getLast()[i]);
        }

        System.out.printf("│ %-74s │%n", "Back:");
        for (int i = 0; i < TEMPLATE.length; i++) {
            System.out.printf("│ " + backHandList.getFirst()[i] + "  " + backHandList.get(1)[i] + "  " + backHandList.getLast()[i]);
        }
    }

    public int cardId;
    public int cardFace;

    private CardFace face;

    /**
     * Constructor for each type of cards out of objective cards
     * @param cardId        The ID of the card
     * @param cardFace      The face of the card (0 = front, 1 = back)
     */
    public CardIds(int cardId, int cardFace) {
        this.cardId = cardId;
        this.cardFace = cardFace;
    }

    /**
     * The constructor for the objective cards
     * @param cardId    The ID of the objective
     */
    public CardIds(int cardId) {
        this.cardId = cardId;
        this.cardFace = 0;
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

    public void loadFace() {
        if (face == null) {
            var card = KingdomCard.getCardWithId(cardId);
            if (cardFace == 0) {
                this.face = card.getFrontFace();
            } else if (cardFace == 1) {
                this.face = card.getBackFace();
            }
        }
    }

    public void loadStarterFace() {
        if (face == null) {
            var card = StarterCard.getCardWithId(cardId);
            if (cardFace == 0) {
                this.face = card.getFrontFace();
            } else if (cardFace == 1) {
                this.face = card.getBackFace();
            }
        }
    }

    public String[] getCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL){
        String[] result = TEMPLATE.clone();

        if (this.cardId >= 80 && this.cardId <= 85) {
            return getStarterCardAsArrayString(coveredTL, coveredTR, coveredBR, coveredBL, result);
        } else if (this.cardId >= 0 && this.cardId <= 39) {
            return getResourceCardAsArrayString(coveredTL, coveredTR, coveredBR, coveredBL, result);
        } else {
            return getGoldCardAsArrayString(coveredTL, coveredTR, coveredBR, coveredBL, result);
        }
    }

    public String[] getStarterCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        result[1] = result[1].formatted(
                getSymbol(this.face.getTopLeftCorner(), coveredTL),
                "",
                getSymbol(this.face.getTopRightCorner(), coveredTR));

        result[3] = result[3].formatted(this.getSymbol(this.face.getPermanentResources()));

        result[5] = result[5].formatted(
                getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                "",
                getSymbol(this.face.getBottomRightCorner(), coveredBR)
        );

        return result;
    }

    public String[] getResourceCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        result[3] = result[3].formatted(this.getSymbol(this.face.getPermanentResources()));

        if (this.cardFace == 0) {
            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getFrontFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR)
            );
        } else {
            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getBackFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR)
            );
        }

        result[5] = result[5].formatted(
                getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                "",
                getSymbol(this.face.getBottomRightCorner(), coveredBR));

        return result;
    }

    public String[] getGoldCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        result[3] = result[3].formatted(this.getSymbol(this.face.getPermanentResources()));

        if (this.cardFace == 0) {
            StringBuilder totalRequiredResources = getTotalRequiredResources(GoldCard.getCardWithId(this.cardId).getFrontFace().getRequiredResources());

            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getFrontFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR)
            );

            result[5] = result[5].formatted(
                    getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                    totalRequiredResources,
                    getSymbol(this.face.getBottomRightCorner(), coveredBR));
        } else {
            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getBackFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR));

            result[5] = result[5].formatted(
                    getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                    "",
                    getSymbol(this.face.getBottomRightCorner(), coveredBR));
        }

        return result;
    }

    public String[] getObjectiveAsArrayString() {
        String[] result = OBJECTIVE_TEMPLATE.clone();
        var obj = Objective.getObjectiveWithId(cardId);

        switch (Objective.getObjectives().stream().toList().indexOf(obj)) {
            case 0 -> result[2] = result[2].formatted("  Fungi Diagonal");
            case 1 -> result[2] = result[2].formatted("  Plant Diagonal");
            case 2 -> result[2] = result[2].formatted(" Animal Diagonal");
            case 3 -> result[2] = result[2].formatted("  Insect Diagonal");
            case 4 -> result[2] = result[2].formatted("    Fungi Tower");
            case 5 -> result[2] = result[2].formatted("    Plant Tower");
            case 6 -> result[2] = result[2].formatted("   Animal Tower");
            case 7 -> result[2] = result[2].formatted("   Insect Tower");
            case 8 -> result[2] = result[2].formatted("  Fungi Resources");
            case 9 -> result[2] = result[2].formatted("  Plant Resources");
            case 10 -> result[2] = result[2].formatted(" Animal Resources");
            case 11 -> result[2] = result[2].formatted(" Insect Resources");
            case 12 -> result[2] = result[2].formatted("     All Items");
            case 13 -> result[2] = result[2].formatted("   Feather Items");
            case 14 -> result[2] = result[2].formatted("     Ink Items");
            case 15 -> result[2] = result[2].formatted("   Vellum Items");
        }

        result[4] = result[4].formatted(obj.getBonusPoints());

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

    private static StringBuilder getTotalRequiredResources(ResourcesCounter requiredResources) {
        int plantRequired = requiredResources.getPlantCount();
        int animalRequired = requiredResources.getAnimalCount();
        int insectRequired = requiredResources.getInsectCount();
        int fungiRequired = requiredResources.getFungiCount();

        StringBuilder totalRequiredResources = new StringBuilder();
        totalRequiredResources.append("P".repeat(Math.max(0, plantRequired)));
        totalRequiredResources.append("A".repeat(Math.max(0, animalRequired)));
        totalRequiredResources.append("I".repeat(Math.max(0, insectRequired)));
        totalRequiredResources.append("F".repeat(Math.max(0, fungiRequired)));
        return totalRequiredResources;
    }
}