package it.polimi.ingsw.am52;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyView  extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        Parent root =  FXMLLoader.load(LobbyView.class.getResource("lobby-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
