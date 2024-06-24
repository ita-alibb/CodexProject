package it.polimi.ingsw.am52.view.gui;

import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.settings.NetworkMode;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application {
    @FXML
    private Pane playingBoard;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("fxml/menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("css/menu-view.css").toExternalForm());

            stage.setTitle("Codex Naturalis");
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant load the game due to error " + e.getMessage());
        }
    }

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
