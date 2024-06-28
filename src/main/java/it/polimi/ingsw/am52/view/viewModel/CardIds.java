package it.polimi.ingsw.am52.view.viewModel;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.objectives.Objective;

import java.util.Optional;

/**
 * The class to represents an image of a card, with all the related information and the methods to print a face of the card.
 */
public class CardIds {
    /**
     * The template used to create a card.
     * <ul>
     *     <li>Angles : The items/resources of each card</li>
     *     <li>Top Center : The points assigned to the player, the first and second symbols are for
     *     the pattern, the last one is for the points assigned</li>
     *     <li>Bottom Center : The requirement to place the card</li>
     *     <li>Center : The permanent resource of the card</li>
     * </ul>
     */
    public static final String[] TEMPLATE = {
            "┌─────┬─────────┬─────┐",
            "│  %c  │  %-5s  │  %c  │",
            "├─────┘         └─────┤",
            "│        %-5s        │",
            "├─────┐         ┌─────┤",
            "│  %c  │  %-5s  │  %c  │",
            "└─────┴─────────┴─────┘"
    };

    /**
     * The template for the empty slots, with the coordinates for available slots
     */
    public static final String[] EMPTY_TEMPLATE = {
            "┌·····················┐",
            ":                     :",
            ":                     :",
            ":      h=%-2d,v=%-2d      :",
            ":                     :",
            ":                     :",
            "└·····················┘",
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
     * @param isClosed  True if after the card a closing character is needed, otherwise false
     */
    public static void printTwoObjectives(CardIds first, CardIds second, boolean isClosed) {
        var objList1 = first.getObjectiveAsArrayString();
        var objList2 = second.getObjectiveAsArrayString();

        if (isClosed) {
            for (int i = 0; i < 7; i++) {
                System.out.printf("│ " + objList1[i] + "   " + objList2[i] + "%20s│%n", "");
            }
        } else {
            for (int i = 0; i < 7; i++) {
                System.out.printf("│ " + objList1[i] + "   " + objList2[i] + "%n", "");
            }
        }
    }

    /**
     * The method to print a single objective card
     * @param first     The first and only objective
     * @param isClosed  True if after the card a closing character is needed, otherwise false
     */
    public static void printSingleObjective (CardIds first, boolean isClosed) {
        var objList = first.getObjectiveAsArrayString();

        if (isClosed) {
            for (int i = 0; i < 7; i++) {
                System.out.printf("│ " + objList[i] + "%46s│%n", "");
            }
        } else {
            for (int i = 0; i < 7; i++) {
                System.out.printf("│ " + objList[i] + "%n", "");
            }
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

        System.out.printf( "│ %-68s%n", "Card Id: " + firstFront.cardId);
        System.out.printf( "│ %-23s   %-43s%n", "Front face:", "Back face:");
        var resListFront1 = firstFront.getCardAsArrayString(false, false, false, false);
        var resListBack1 = firstBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < resListFront1.length; i++) {
            System.out.printf("│ " + resListFront1[i] + "   " + resListBack1[i] + "%20s%n", "");
        }

        System.out.printf( "│ %-68s %n", "Card Id: " + secondFront.cardId);
        System.out.printf( "│ %-23s   %-43s%n", "Front face:", "Back face:");
        var resListFront2 = secondFront.getCardAsArrayString(false, false, false, false);
        var resListBack2 = secondBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < resListFront1.length; i++) {
            System.out.printf("│ " + resListFront2[i] + "   " + resListBack2[i] + "%20s%n", "");
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

        System.out.printf( "│ %-68s%n", "Card Id: " + firstFront.cardId);
        System.out.printf( "│ %-23s   %-43s%n", "Front face:", "Back face:");
        var goldListFront1 = firstFront.getCardAsArrayString(false, false, false, false);
        var goldListBack1 = firstBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < goldListFront1.length; i++) {
            System.out.printf("│ " + goldListFront1[i] + "   " + goldListBack1[i] + "%20s%n", "");
        }

        System.out.printf( "│ %-68s %n", "Card Id: " + secondFront.cardId);
        System.out.printf( "│ %-23s   %-43s%n", "Front face:", "Back face:");
        var goldListFront2 = secondFront.getCardAsArrayString(false, false, false, false);
        var goldListBack2 = secondBack.getCardAsArrayString(false, false, false, false);

        for (int i = 0; i < goldListFront1.length; i++) {
            System.out.printf("│ " + goldListFront2[i] + "   " + goldListBack2[i] + "%20s%n", "");
        }
    }

    /**
     * The basic information of a card
     */
    public int cardId;
    public int cardFace;

    /**
     * The face of the card, useful to know the intrinsic information of the card, such as the bonus points or the requirements.
     */
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

    /**
     * Method to create an available slot, with its coordinates, to be printed on the viewing board.
     * @param h     Horizontal coordinate
     * @param v     Vertical coordinate
     * @return      The template filled with the formatted coordinates
     */
    public static String[] getEmptyTemplate(int h, int v) {
        var card =  EMPTY_TEMPLATE.clone();

        card[3] = card[3].formatted(h, v);

        return card;
    }

    /**
     * Method to create an empty template, to be printed on the viewing board
     * @return      The empty template
     */
    public static String[] getEmptyTemplate() {
        var card =  EMPTY_TEMPLATE.clone();

        card[3] = card[2];

        return card;
    }

    /**
     * Print the back of the top card of the deck
     * @param deckTopId     The ID of the top card in the deck
     */
    public static void printBack(int deckTopId) {
        var stringRep = getEmptyTemplate();

        if (deckTopId != -1) {
            var back = new CardIds(deckTopId, 1);
            back.loadFace();
            stringRep = back.getCardAsArrayString(false, false, false, false);
        }

        for (String line : stringRep) {
            System.out.printf("│ " + line + "%n", "");
        }
    }

    /**
     * Method to load the face of a card, using the basic data initialized in the constructor of the class.
     */
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

    /**
     * Method to load the face of a starter card, using the basic data initialized in the constructor of the class.
     */
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

    /**
     * This method uses the template of a generic card and creates an array of String formatted with the correct information
     * to be visualized by the player, i.e. the bonus points, the requirements, the resources/items and the permanent resource of the card.
     * @param coveredTL     True is the Top-Left Corner is covered, otherwise False
     * @param coveredTR     True is the Top-Right Corner is covered, otherwise False
     * @param coveredBR     True is the Bottom-Left Corner is covered, otherwise False
     * @param coveredBL     True is the Bottom-Right Corner is covered, otherwise False
     * @return              The array of formatted String
     */
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

    /**
     * Private method to format the String array with the correct information of a starter card.
     * @param coveredTL     True is the Top-Left Corner is covered, otherwise False
     * @param coveredTR     True is the Top-Right Corner is covered, otherwise False
     * @param coveredBR     True is the Bottom-Left Corner is covered, otherwise False
     * @param coveredBL     True is the Bottom-Right Corner is covered, otherwise False
     * @param result        The String array partially formatted in the generic method
     * @return              The array completely formatted
     */
    private String[] getStarterCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        result[1] = result[1].formatted(
                getSymbol(this.face.getTopLeftCorner(), coveredTL),
                "",
                getSymbol(this.face.getTopRightCorner(), coveredTR));

        result[3] = result[3].formatted(getResourceCounterString(this.face.getPermanentResources()));

        result[5] = result[5].formatted(
                getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                "",
                getSymbol(this.face.getBottomRightCorner(), coveredBR)
        );

        return result;
    }

    /**
     * Private method to format the String array with the correct information of a resource card.
     * @param coveredTL     True is the Top-Left Corner is covered, otherwise False
     * @param coveredTR     True is the Top-Right Corner is covered, otherwise False
     * @param coveredBR     True is the Bottom-Left Corner is covered, otherwise False
     * @param coveredBL     True is the Bottom-Right Corner is covered, otherwise False
     * @param result        The String array partially formatted in the generic method
     * @return              The array completely formatted
     */
    private String[] getResourceCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        if (this.cardFace == 0) {
            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getFrontFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR)
            );
            result[3] = result[3].formatted("");
        } else {
            result[1] = result[1].formatted(
                    getSymbol(this.face.getTopLeftCorner(), coveredTL),
                    KingdomCard.getCardWithId(this.cardId).getBackFace().getPoints(),
                    getSymbol(this.face.getTopRightCorner(), coveredTR)
            );
            result[3] = result[3].formatted(getResourceCounterString(this.face.getPermanentResources()));
        }

        result[5] = result[5].formatted(
                getSymbol(this.face.getBottomLeftCorner(), coveredBL),
                "",
                getSymbol(this.face.getBottomRightCorner(), coveredBR));

        return result;
    }

    /**
     * Private method to format the String array with the correct information of a gold card.
     * @param coveredTL     True is the Top-Left Corner is covered, otherwise False
     * @param coveredTR     True is the Top-Right Corner is covered, otherwise False
     * @param coveredBR     True is the Bottom-Left Corner is covered, otherwise False
     * @param coveredBL     True is the Bottom-Right Corner is covered, otherwise False
     * @param result        The String array partially formatted in the generic method
     * @return              The array completely formatted
     */
    private String[] getGoldCardAsArrayString(boolean coveredTL, boolean coveredTR, boolean coveredBR, boolean coveredBL, String[] result){
        result[3] = result[3].formatted(getResourceCounterString(this.face.getPermanentResources()));

        if (this.cardFace == 0) {
            String totalRequiredResources = getResourceCounterString(GoldCard.getCardWithId(this.cardId).getFrontFace().getRequiredResources());

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

    /**
     * Private method to format the String array with the correct information of an objective card.
     * @return      The array completely formatted
     */
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

    /**
     * Private method to return the correct character which represents the correct resource/item on the given corner
     * @param corner        The corner considered on the card, which is Optional because it can be missing
     * @param isCovered     True if the corner is covered, otherwise False
     * @return              The char representing the resource/item
     */
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

    /**
     * Private method to insert on the card the resource
     * @param counter   The resource counter of the card
     * @return          The character associated to the resource
     */
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
    /**
     * Private method to insert on the card the item
     * @param counter   The item counter of the card
     * @return          The character associated to the item
     */
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

    /**
     * Private method to build a String with the correct number of required resources to place a card
     * @param requiredResources     The required resources associated to the card
     * @return                      The String formatted
     */
    private static String getResourceCounterString(ResourcesCounter requiredResources) {
        int plantRequired = requiredResources.getPlantCount();
        int animalRequired = requiredResources.getAnimalCount();
        int insectRequired = requiredResources.getInsectCount();
        int fungiRequired = requiredResources.getFungiCount();

        int width = 5;
        int padding = (width - (plantRequired + animalRequired + insectRequired + fungiRequired)) / 2;

        String totalRequiredResources = "P".repeat(Math.max(0, plantRequired)) +
                "A".repeat(Math.max(0, animalRequired)) +
                "I".repeat(Math.max(0, insectRequired)) +
                "F".repeat(Math.max(0, fungiRequired));

        return " ".repeat(Math.max(0, padding)) +
                totalRequiredResources +
                " ".repeat(Math.max(0, padding));
    }
}