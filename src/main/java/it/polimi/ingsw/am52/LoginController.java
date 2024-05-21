package it.polimi.ingsw.am52;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    TextField usernameTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void  login(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("waiting-view.fxml"));
        root = loader.load();

        WaitingController scene2Controller = loader.getController();
        scene2Controller.display(username);
        ConnectionController.setusername(username);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}
