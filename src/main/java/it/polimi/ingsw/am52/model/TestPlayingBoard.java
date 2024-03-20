package it.polimi.ingsw.am52.model;

import it.polimi.ingsw.am52.model.cards.*;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;
import it.polimi.ingsw.am52.model.playingBoards.PlayingBoard;

import java.util.List;

public class TestPlayingBoard {

    public static void main(String[] args) {

        StarterCard starterCard = StarterCard.getCardWithId(85);

        StarterCardFace card = starterCard.getFrontFace();

        System.out.println("Resources: " + card.getResources());

        PlayingBoard board = new PlayingBoard(card);
        System.out.println("Resources: " + board.getResources());
        System.out.println("Items: " + board.getItems());

        displayAvailableSlots(board.getAvailableSlots().toList());
        displayPlacedSlots(board.getCoveredSlots().toList());

        int cardId = 0;
        KingdomCardFace kingCard = GoldCard.getCards().get(cardId).getBackFace();

        int points = board.placeCard(new BoardSlot(1, 1), kingCard);

        System.out.println("Gained points: " + points);

        System.out.println("Resources: " + board.getResources());
        System.out.println("Items: " + board.getItems());
        displayAvailableSlots(board.getAvailableSlots().toList());
        displayPlacedSlots(board.getCoveredSlots().toList());


        cardId = 3;
        kingCard = ResourceCard.getCards().get(cardId).getFrontFace();

        points = board.placeCard(new BoardSlot(0, 2), kingCard);

        System.out.println("Gained points: " + points);

        System.out.println("Resources: " + board.getResources());
        System.out.println("Items: " + board.getItems());
        displayAvailableSlots(board.getAvailableSlots().toList());
        displayPlacedSlots(board.getCoveredSlots().toList());


        cardId = 5;
        kingCard = GoldCard.getCards().get(cardId).getFrontFace();

        points = board.placeCard(new BoardSlot(1, 3), kingCard);

        System.out.println("Gained points: " + points);

        System.out.println("Resources: " + board.getResources());
        System.out.println("Items: " + board.getItems());
        displayAvailableSlots(board.getAvailableSlots().toList());
        displayPlacedSlots(board.getCoveredSlots().toList());

    }

    private static void displayPlacedSlots(List<BoardSlot> slots) {
        displaySlots("Placed slots:", slots);
    }

    private static void displayAvailableSlots(List<BoardSlot> slots) {
        displaySlots("Available slots:", slots);
    }

    private static void displaySlots(String title, List<BoardSlot> slots) {

        System.out.println(title);
        if (slots.isEmpty()) {
            System.out.println("None");
            return;
        }
        for (BoardSlot slot : slots) {
            System.out.println(slot);
        }

    }
}
