package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageController {
    private static Stage appStage;
    public static Scene activeScene;

    public static void setStage(Stage initStage) {
        if (appStage == null) {
            appStage = initStage;
        }
    }

    public static void changeScene(String FXMLScene, String title, ModelObserver oldSceneObserver) {
        //unregister the oldSceneObserver
        ViewModelState.getInstance().removeObserver(oldSceneObserver);

        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource(FXMLScene));
        try {
            Parent root = fxmlLoader.load();
            activeScene = new Scene(root);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "not found resource");
        }

        appStage.setScene(activeScene);
        appStage.setTitle(title);
        appStage.setMaximized(true);
        appStage.show();
    }
}

