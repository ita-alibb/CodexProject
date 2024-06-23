package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class JoinLobbyController extends GuiController implements Initializable {
    private static int id;
    @FXML
    private TextField nickNameTextField;
    @FXML
    private Button joinLobbyButton;

    public static void setId(int id){
        JoinLobbyController.id = id;
    }

    public void joinLobby(ActionEvent event) {
        System.out.println(id);
        if (ViewModelState.getInstance().getLobbies().containsKey(id)) {
            ResponseStatus networkResponse = ClientConnection.joinLobby(nickNameTextField.getText(), id);
            System.out.println(networkResponse.getCurrPlayer());
            if (networkResponse != null && networkResponse.getErrorCode() == 0) {
                StageController.changeScene("fxml/waiting-room.fxml", "Waiting room",event);
                System.out.println(ViewModelState.getInstance().getPhase());




            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StackPane.setAlignment(joinLobbyButton, javafx.geometry.Pos.BOTTOM_RIGHT);
    }
}
