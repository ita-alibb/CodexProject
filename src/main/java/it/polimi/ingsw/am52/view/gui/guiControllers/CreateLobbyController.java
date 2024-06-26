package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.EventType;
import it.polimi.ingsw.am52.view.viewModel.ModelObserver;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateLobbyController extends ModelObserver {
   public TextField nicknameTextField;
   public Spinner<Integer> playerNumberSpinner;
   public Button createLobbyButton;

   @FXML
   public void initialize() {
      ViewModelState.getInstance().registerObserver(this, EventType.JOIN_LOBBY);

      SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 2);
      playerNumberSpinner.setValueFactory(valueFactory);

      nicknameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
         // Show the button if the TextField is not empty, otherwise hide it
         createLobbyButton.setVisible(!newValue.trim().isEmpty());
      });
   }

   public void createLobby(){
      ResponseStatus res = ClientConnection.createLobby(nicknameTextField.getText(), playerNumberSpinner.getValue());
      if (res.getErrorCode() != 0) {
         Alert alertBox = new Alert(Alert.AlertType.ERROR);
         alertBox.setContentText("Cant load the game due to error " + res.getErrorMessage());
         alertBox.show();
         StageController.changeScene("fxml/menu-view.fxml", "Codex Naturalis", this);
      }
   }

   @Override
   protected void updateJoinLobby() {
      Platform.runLater(()-> {
         StageController.changeScene("fxml/waiting-room.fxml", "Waiting room", this);
      });
   }
}
