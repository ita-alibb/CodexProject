module it.polimi.ingsw.am52 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;

    opens it.polimi.ingsw.am52 to javafx.fxml;
    exports it.polimi.ingsw.am52;
    exports it.polimi.ingsw.am52.exceptions;
    exports it.polimi.ingsw.am52.model.cards;
    exports it.polimi.ingsw.am52.model.player;
    exports it.polimi.ingsw.am52.model.playingBoards;

    opens it.polimi.ingsw.am52.json to com.fasterxml.jackson.databind;

    exports it.polimi.ingsw.am52.json.response to java.rmi;
    exports it.polimi.ingsw.am52.model.game to java.rmi;
    exports it.polimi.ingsw.am52.controller to java.rmi;
    exports it.polimi.ingsw.am52.network to java.rmi;

    exports it.polimi.ingsw.am52.network.rmi to java.rmi;
    exports it.polimi.ingsw.am52.network.rmi.client to java.rmi;
    exports it.polimi.ingsw.am52.json to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am52.json.request to com.fasterxml.jackson.databind;
    opens it.polimi.ingsw.am52.json.request to com.fasterxml.jackson.databind;
}