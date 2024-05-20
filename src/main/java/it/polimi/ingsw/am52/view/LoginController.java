package it.polimi.ingsw.am52.view;
import it.polimi.ingsw.am52.LobbyView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void  login(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/fxml/lobby-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);


    }
}
