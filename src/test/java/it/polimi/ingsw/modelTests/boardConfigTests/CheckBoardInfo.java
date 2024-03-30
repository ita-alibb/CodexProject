package it.polimi.ingsw.modelTests.boardConfigTests;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.objectives.Objective;
import it.polimi.ingsw.am52.model.playingBoards.BoardInfo;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckBoardInfo {

    //region Check Slots Methods

    /**
     * Check if the playing board contains all and only the specified paced and available slots
     * locations.
     * @param board The board to check.
     * @param placed The expected set of board locations with a kingdom card placed on them.
     * @param available The expected set of available board location, where a kingdom card can be placed.
     */
    public static void checkBoardSlots(BoardInfo board, Set<BoardSlot> placed, Set<BoardSlot> available) {

        // Check all and only placed are covered on the playing board.
        assertEquals(placed.size(), board.getCoveredSlots().size());
        assert(placed.containsAll(board.getCoveredSlots().toList()));
        // Check all and only available are available on the playing board.
        assertEquals(available.size(), board.getAvailableSlots().size());
        assert(available.containsAll(board.getAvailableSlots().toList()));
    }

    //endregion

    //region Check Patterns Methods

    /**
     * Check the calculated points of all diagonal pattern for the specified playing board.
     * @param board The playing board to inspect.
     * @param fungiPoints The expected points gained by the fungi diagonal pattern.
     * @param plantPoints The expected points gained by the plant diagonal pattern.
     * @param animalPoints The expected points gained by the animal diagonal pattern.
     * @param insectPoints The expected points gained by the insect diagonal pattern.
     */
    public static void checkDiagonalPatterns(BoardInfo board, int fungiPoints, int plantPoints, int animalPoints, int insectPoints) {

        // Check fungi diagonal pattern.
        Objective target = Objective.getObjectiveWithId(0);
        assertEquals(fungiPoints, target.calculatePoints(board));

        // Check plant diagonal pattern.
        target = Objective.getObjectiveWithId(1);
        assertEquals(plantPoints, target.calculatePoints(board));

        // Check animal diagonal pattern.
        target = Objective.getObjectiveWithId(2);
        assertEquals(animalPoints, target.calculatePoints(board));

        // Check insect diagonal pattern.
        target = Objective.getObjectiveWithId(3);
        assertEquals(insectPoints, target.calculatePoints(board));
    }

    /**
     * Check the calculated points of all tower pattern for the specified playing board.
     * @param board The playing board to inspect.
     * @param fungiPoints The expected points gained by the fungi tower pattern.
     * @param plantPoints The expected points gained by the plant tower pattern.
     * @param animalPoints The expected points gained by the animal tower pattern.
     * @param insectPoints The expected points gained by the insect tower pattern.
     */
    public static void checkTowerPatterns(BoardInfo board, int fungiPoints, int plantPoints, int animalPoints, int insectPoints) {

        // Check fungi tower pattern.
        Objective target = Objective.getObjectiveWithId(4);
        assertEquals(fungiPoints, target.calculatePoints(board));

        // Check plant tower pattern.
        target = Objective.getObjectiveWithId(5);
        assertEquals(plantPoints, target.calculatePoints(board));

        // Check animal tower pattern.
        target = Objective.getObjectiveWithId(6);
        assertEquals(animalPoints, target.calculatePoints(board));

        // Check insect tower pattern.
        target = Objective.getObjectiveWithId(7);
        assertEquals(insectPoints, target.calculatePoints(board));
    }

    /**
     * Check the calculated points of all resource pattern for the specified playing board.
     * @param board The playing board to inspect.
     * @param fungiPoints The expected points gained by the fungi resource pattern.
     * @param plantPoints The expected points gained by the plant resource pattern.
     * @param animalPoints The expected points gained by the animal resource pattern.
     * @param insectPoints The expected points gained by the insect resource pattern.
     */
    public static void checkResourcePatterns(BoardInfo board, int fungiPoints, int plantPoints, int animalPoints, int insectPoints) {

        // Check fungi resource pattern.
        Objective target = Objective.getObjectiveWithId(8);
        assertEquals(fungiPoints, target.calculatePoints(board));

        // Check plant resource pattern.
        target = Objective.getObjectiveWithId(9);
        assertEquals(plantPoints, target.calculatePoints(board));

        // Check animal resource pattern.
        target = Objective.getObjectiveWithId(10);
        assertEquals(animalPoints, target.calculatePoints(board));

        // Check insect resource pattern.
        target = Objective.getObjectiveWithId(11);
        assertEquals(insectPoints, target.calculatePoints(board));
    }

    /**
     * Check the calculated points of the all-items objective for the specified playing board.
     * @param board The playing board to inspect.
     * @param points The expected points of teh all-items objective.
     */
    public static void checkMultiItemPatterns(BoardInfo board, int points) {

        // Check all-items pattern.
        Objective target = Objective.getObjectiveWithId(12);
        assertEquals(points, target.calculatePoints(board));
    }

    /**
     * Check the calculated points of the single-items objectives for the specified playing board.
     * @param board The playing board to inspect.
     * @param featherPoints The expected points of the feather item objective.
     * @param inkPoints The expected points of the ink item objective.
     * @param vellumPoints The expected points of the vellum item objective.
     */
    public static void checkSingleItemPatterns(BoardInfo board, int featherPoints, int inkPoints, int vellumPoints) {

        // Check feather item pattern.
        Objective target = Objective.getObjectiveWithId(13);
        assertEquals(featherPoints, target.calculatePoints(board));

        // Check ink item pattern.
        target = Objective.getObjectiveWithId(14);
        assertEquals(inkPoints, target.calculatePoints(board));

        // Check vellum item pattern.
        target = Objective.getObjectiveWithId(15);
        assertEquals(vellumPoints, target.calculatePoints(board));
    }

    //endregion

    //region Check Resources and Items Methods

    /**
     * Check the available resources of the specified playing board.
     * @param board The playing board to inspect.
     * @param fungi The expected available fungi.
     * @param plants The expected available plants.
     * @param animals The expected available animals.
     * @param insects The expected available insects.
     */
    public static void checkResources(BoardInfo board, int fungi, int plants, int animals, int insects) {

        // Get available resources from the playing board.
        final ResourcesCounter resources = board.getResources();

        // Check all resources.
        assertEquals(fungi, resources.getFungiCount());
        assertEquals(plants, resources.getPlantCount());
        assertEquals(animals, resources.getAnimalCount());
        assertEquals(insects, resources.getInsectCount());
    }

    /**
     * Check the available items of the specified playing board.
     * @param board The playing board to inspect.
     * @param feathers The expected available feathers.
     * @param inks The expected available inks.
     * @param vellum The expected available vellum.
     */
    public static void checkItems(BoardInfo board, int feathers, int inks, int vellum) {
        // Get available resources from the playing board.
        final ItemsCounter items = board.getItems();

        // Check all resources.
        assertEquals(feathers, items.getFeatherCount());
        assertEquals(inks, items.getInkCount());
        assertEquals(vellum, items.getVellumCount());
    }

    //endregion

    //region Check Placeable Cards

    /**
     * Check if all Insect Gold cards are placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of not-placeable cards.
     */
    public static void checkInsectGoldCardsArePlaceableExcept(BoardInfo board, int... ids) {

        // The set of not-placeable cards.
        Set<Integer> notPlaceable = new HashSet<>();
        for (int i : ids) {
            notPlaceable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeArePlaceableExcept(board, 70, 80, notPlaceable);
    }

    /**
     * Check if all Insect Gold cards are NOT placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of placeable cards.
     */
    public static void checkInsectGoldCardsNotPlaceableExcept(BoardInfo board, int... ids) {

        // The set of placeable cards.
        Set<Integer> placeable = new HashSet<>();
        for (int i : ids) {
            placeable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeNotPlaceableExcept(board, 70, 80, placeable);
    }

    /**
     * Check if all Animal Gold cards are placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of not-placeable cards.
     */
    public static void checkAnimalGoldCardsArePlaceableExcept(BoardInfo board, int... ids) {

        // The set of not-placeable cards.
        Set<Integer> notPlaceable = new HashSet<>();
        for (int i : ids) {
            notPlaceable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeArePlaceableExcept(board, 60, 70, notPlaceable);
    }

    /**
     * Check if all Animal Gold cards are NOT placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of placeable cards.
     */
    public static void checkAnimalGoldCardsNotPlaceableExcept(BoardInfo board, int... ids) {

        // The set of placeable cards.
        Set<Integer> placeable = new HashSet<>();
        for (int i : ids) {
            placeable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeNotPlaceableExcept(board, 60, 70, placeable);
    }

    /**
     * Check if all Plant Gold cards are placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of not-placeable cards.
     */
    public static void checkPlantGoldCardsArePlaceableExcept(BoardInfo board, int... ids) {

        // The set of not-placeable cards.
        Set<Integer> notPlaceable = new HashSet<>();
        for (int i : ids) {
            notPlaceable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeArePlaceableExcept(board, 50, 60, notPlaceable);
    }

    /**
     * Check if all Plant Gold cards are NOT placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of placeable cards.
     */
    public static void checkPlantGoldCardsNotPlaceableExcept(BoardInfo board, int... ids) {

        // The set of placeable cards.
        Set<Integer> placeable = new HashSet<>();
        for (int i : ids) {
            placeable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeNotPlaceableExcept(board, 50, 60, placeable);
    }

    /**
     * Check if all Fungi Gold cards are placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of not-placeable cards.
     */
    public static void checkFungiGoldCardsArePlaceableExcept(BoardInfo board, int... ids) {

        // The set of not-placeable cards.
        Set<Integer> notPlaceable = new HashSet<>();
        for (int i : ids) {
            notPlaceable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeArePlaceableExcept(board, 40, 50, notPlaceable);
    }

    public static void checkFungiGoldCardsNotPlaceableExcept(BoardInfo board, int... ids) {

        // The set of placeable cards.
        Set<Integer> placeable = new HashSet<>();
        for (int i : ids) {
            placeable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeNotPlaceableExcept(board, 40, 50, placeable);
    }

    /**
     * Check if all Gold cards are placeable on the given board, except for the
     * specified ids.
     * @param board The playing board configuration.
     * @param ids The ids of not-placeable cards.
     */
    public static void checkGoldCardsArePlaceableExcept(BoardInfo board, int... ids) {

        // The set of not-placeable cards.
        Set<Integer> notPlaceable = new HashSet<>();
        for (int i : ids) {
            notPlaceable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeArePlaceableExcept(board, 40, 80, notPlaceable);
    }

    public static void checkGoldCardsNotPlaceableExcept(BoardInfo board, int... ids) {

        // The set of placeable cards.
        Set<Integer> placeable = new HashSet<>();
        for (int i : ids) {
            placeable.add(i);
        }

        // Check all Gold cards.
        checkCardsInRangeNotPlaceableExcept(board, 40, 80, placeable);
    }

    /**
     * Check if all card in the specified range of ids are placeable on the given board
     * configuration, except for the specified set of ids.
     * @param board The playing board configuration.
     * @param origin The first id (inclusive) of the range.
     * @param bound The last id (exclusive) of the range.
     * @param notPlaceable Set of ids for not-placeable cards.
     */
    public static void checkCardsInRangeArePlaceableExcept(BoardInfo board, int origin, int bound, Set<Integer> notPlaceable) {

        // Iterate over all cards in the range and check if they are placeable,
        // except for the specified not-placeable ids.
        for (int cardId = origin; cardId != bound; cardId++) {
            // Get the Gold card front face.
            KingdomCardFace goldCard = KingdomCard.getCardWithId(cardId).getFrontFace();
            // Check the canPlace() method of the card.
            checkPlaceableCard(board, goldCard, !notPlaceable.contains(cardId));
        }
    }

    public static void checkCardsInRangeNotPlaceableExcept(BoardInfo board, int origin, int bound, Set<Integer> placeable) {

        // Iterate over all cards in the range and check if they are not placeable,
        // except for the specified placeable ids.
        for (int cardId = origin; cardId != bound; cardId++) {
            // Get the Gold card front face.
            KingdomCardFace goldCard = KingdomCard.getCardWithId(cardId).getFrontFace();
            // Check the canPlace() method of the card.
            checkPlaceableCard(board, goldCard, placeable.contains(cardId));
        }
    }

    /**
     * Check if the method canPlace() of the specified card returns the expected value,
     * for the given board configuration.
     * @param board The playing board configuration.
     * @param card The card to check.
     * @param expected The expected value of the method canPlace().
     */
    public static void checkPlaceableCard(BoardInfo board, KingdomCardFace card, boolean expected) {
        assertEquals(expected, card.canPlace(board));
    }

    /**
     * Check if the method canPlace() of the specified card returns true, for the given board configuration.
     * @param board The playing board configuration.
     * @param card The card to check.
     */
    public void checkIsPlaceable(BoardInfo board, KingdomCardFace card) {
        checkPlaceableCard(board, card, true);
    }

    /**
     * Check if the method canPlace() of the specified card returns false, for the given board configuration.
     * @param board The playing board configuration.
     * @param card The card to check.
     */
    public void checkNotPlaceable(BoardInfo board, KingdomCardFace card) {
        checkPlaceableCard(board, card, false);
    }

    //endregion
}
