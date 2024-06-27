package it.polimi.ingsw.am52.view.gui;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.settings.NetworkMode;
import it.polimi.ingsw.am52.view.gui.guiControllers.StageController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Gui main class. Initializes the connection, data in ViewModelState and show the first scene.
 */
public class GuiApplication extends Application {
    /**
     * The start method for the application
     * @param stage the stage of the application
     * @throws IOException if load of file fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            StageController.setStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("fxml/menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            scene.getStylesheets().add(GuiApplication.class.getResource("css/menu-view.css").toExternalForm());

            stage.setTitle("Codex Naturalis");
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant load the game due to error " + e.getMessage());
            alertBox.show();
        }
    }

    /**
     * Create the connection and then launches the GUI application
     * @param serverIp the server to which connect
     * @param port the port to which connect
     * @param connectionMode the type of connection RMI/TCP
     */
    public static void run(String serverIp, int port, NetworkMode connectionMode) {
        try {
            ClientConnection.setConnection(serverIp, port, connectionMode );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClientConnection.getLobbyList();

        launch();
    }
}
