package it.polimi.ingsw.am52.view.gui.guiControllers;

import it.polimi.ingsw.am52.model.game.GamePhase;
import it.polimi.ingsw.am52.network.client.ClientConnection;
import it.polimi.ingsw.am52.view.viewModel.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatViewController extends ModelObserver {
    public BorderPane borderPane;
    public TextArea messageBox;
    public ListView<Text> chatPane;
    public Button buttonSend;

    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.CHAT);

        for (String msg : ViewModelState.getInstance().getChatRecords()) {
            chatPane.getItems().add(new Text(msg));
        }
    }

    public void sendButtonAction() throws IOException {
        String msg = messageBox.getText();
        String recipient = "";
        var groups = getGroups(msg);
        if (groups != null) {
            recipient = groups[0];
            msg = groups[1];
        }

        if (!msg.trim().isEmpty()) {
            ClientConnection.chat(msg.trim(), recipient);
            messageBox.clear();
        }
    }

    private static String[] getGroups(String input) {
        Pattern pattern = Pattern.compile("^\\\\w\\s(\\w+)\\s(.*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        } else {
            return null;
        }
    }

    public void sendMethod(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonAction();
        }
    }

    @Override
    protected void updateChat() {
        Platform.runLater(() -> {
            chatPane.getItems().add(new Text(ViewModelState.getInstance().getChatRecords().getLast()));
        });
    }
}
