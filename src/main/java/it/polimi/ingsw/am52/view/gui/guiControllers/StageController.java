package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.view.gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController {
    private static Stage currentStage;
    public static Scene activeScene;

    public static void changeScene(String FXMLScene, String title, ActionEvent event) {


        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource(FXMLScene));

        try {
            Parent root = fxmlLoader.load();
            activeScene = new Scene(root);

        } catch (IOException ignored) {

        }
        currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(activeScene);
        currentStage.setTitle(title);
        currentStage.show();
    }
    public static void changeScene(String FXMLScene, String title, Node node) {


        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource(FXMLScene));

        try {
            Parent root = fxmlLoader.load();
            activeScene = new Scene(root);




        } catch (IOException ignored) {

        }
        currentStage = (Stage)node.getScene().getWindow();;
        currentStage.setScene(activeScene);
        currentStage.setTitle(title);
        currentStage.show();
    }

}

