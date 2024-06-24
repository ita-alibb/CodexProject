package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.model.game.GameManager;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateLobbyController extends GuiController implements Initializable {
   @FXML
   TextField nicknameTextField;
   @FXML
    Spinner<Integer> playerNumberSpinner;
   @FXML
   Button createLobbyButton;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 2);
      playerNumberSpinner.setValueFactory(valueFactory);
      playerNumberSpinner.setEditable(true);
      nicknameTextField.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            // Show the button if the TextField is not empty, otherwise hide it
            createLobbyButton.setVisible(!newValue.trim().isEmpty());
         }
      });
      StackPane.setAlignment(createLobbyButton, javafx.geometry.Pos.BOTTOM_RIGHT);
   }

   public void createLobby(ActionEvent event){
      if (playerNumberSpinner.getValue() <= GameManager.MAX_PLAYERS) {
         ResponseStatus res = ClientConnection.createLobby(nicknameTextField.getText(), playerNumberSpinner.getValue());
         if (res.getErrorCode() != 0) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setContentText("Cant load the game due to error " + res.getErrorMessage());
            StageController.changeScene("fxml/menu-view.fxml", "Codex Naturalis",event);
            return;
         }
         StageController.changeScene("fxml/waiting-room.fxml", "Waiting room",event);
      }
   }
}
