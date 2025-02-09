module it.polimi.ingsw.am52 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires java.desktop;

    opens it.polimi.ingsw.am52 to javafx.fxml;
    opens it.polimi.ingsw.am52.view.gui.guiControllers to javafx.fxml;
    exports it.polimi.ingsw.am52;
    exports it.polimi.ingsw.am52.view.gui;
    exports it.polimi.ingsw.am52.view.gui.guiControllers;
    exports it.polimi.ingsw.am52.exceptions;
    exports it.polimi.ingsw.am52.model.cards;
    exports it.polimi.ingsw.am52.model.objectives;
    exports it.polimi.ingsw.am52.model.player;
    exports it.polimi.ingsw.am52.model.playingBoards;
    exports it.polimi.ingsw.am52.view.tui.state;
    exports it.polimi.ingsw.am52.settings;
    exports it.polimi.ingsw.am52.view.viewModel;

    opens it.polimi.ingsw.am52.json to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am52.model.game to java.rmi, com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am52.json.response to java.rmi, com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am52.json.request to java.rmi, com.fasterxml.jackson.databind;

    opens it.polimi.ingsw.am52.json.request to com.fasterxml.jackson.databind;
    opens it.polimi.ingsw.am52.json.response to com.fasterxml.jackson.databind;
    opens it.polimi.ingsw.am52.model.game to com.fasterxml.jackson.databind;

    exports it.polimi.ingsw.am52.controller to java.rmi;
    exports it.polimi.ingsw.am52.network.server to java.rmi;
    exports it.polimi.ingsw.am52.network.client to java.rmi;

    exports it.polimi.ingsw.am52.network.server.rmi to java.rmi;
    exports it.polimi.ingsw.am52.network.server.tcp to java.rmi;
    exports it.polimi.ingsw.am52.json to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am52.json.dto;
    exports it.polimi.ingsw.am52.view.tui to java.rmi;
    exports it.polimi.ingsw.am52.view.tui.strategy to java.rmi;
}