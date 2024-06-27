package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.view.gui.GuiApplication;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to handle the different scenes in the APP
 */
public class StageController {
    private static Stage appStage;
    public static Scene activeScene;

    /**
     * Method to set the general single application STAGE
     * @param initStage the SCENE at init of the application
     */
    public static void setStage(Stage initStage) {
        if (appStage == null) {
            appStage = initStage;
        }
    }

    /**
     * Method used to change the scene.
     * It loads the desired fxml, show it, and remove all listeners from old scene controller.
     * @param FXMLScene the url to fxml resource
     * @param title the title of the window
     * @param oldSceneObserver the old controller for the scene, used to remove it as observers
     */
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

