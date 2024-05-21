package it.polimi.ingsw.am52;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class StageController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public  static void swithscene(String FXMLScene, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(StageController.class.getResource(FXMLScene));
        root = loader.load();

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
