package it.polimi.ingsw.am52.view.gui;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    @FXML
    private Pane playingBoard;
    @Override
    public void start(Stage stage) throws IOException {
       ClientConnection.setConnection(false);
       FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("fxml/menu-view.fxml"));
       Scene scene = new Scene(fxmlLoader.load(), 1980, 1080);
       scene.getStylesheets().add(getClass().getResource("css/menu-view.css").toExternalForm());


       stage.setTitle("Codex Naturalis");
       stage.setScene(scene);
       stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
