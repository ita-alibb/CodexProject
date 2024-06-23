package it.polimi.ingsw.am52;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayingBoard extends Application {
    @FXML
    private Pane playingBoard;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayingBoard.class.getResource("fxml/playing-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("css/playing-board.css").toExternalForm());
        playingBoard.setPrefWidth(stage.getWidth()*0.8);
        playingBoard.setPrefHeight(stage.getHeight()*0.8);
        playingBoard.setMaxWidth(stage.getWidth()*0.8);
        playingBoard.setMaxHeight(stage.getHeight()*0.8);




        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
