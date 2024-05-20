package it.polimi.ingsw.am52;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitingController {
    @FXML
    Label nameLabel;

    public void display(String username){
        nameLabel.setText("Welcome " + username);
    }
}
