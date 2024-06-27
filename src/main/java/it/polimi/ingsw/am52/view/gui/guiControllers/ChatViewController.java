package it.polimi.ingsw.am52.view.gui.guiControllers;

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
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The controller for the Chat view
 * Uses {@link ModelObserver} to be updated with the {@link ViewModelState}
 */
public class ChatViewController extends ModelObserver {
    public BorderPane borderPane;
    public TextArea messageBox;
    public ListView<Text> chatPane;
    public Button buttonSend;

    /**
     * Default initialization method. Called every time the FXML view is shown.
     * FXML chat-view.fxml
     * Initialize all board with data present on {@link ViewModelState}
     */
    @FXML
    public void initialize() {
        ViewModelState.getInstance().registerObserver(this, EventType.CHAT);

        for (String msg : ViewModelState.getInstance().getChatRecords()) {
            chatPane.getItems().add(new Text(msg));
        }
    }

    /**
     * Action bound to the send button.
     * Sends the message saved in the {@link #messageBox}
     */
    public void sendButtonAction() {
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

    /**
     * Private method used to compute the special \w whisper action
     * @param input the string input
     * @return the recipient and the message
     */
    private static String[] getGroups(String input) {
        Pattern pattern = Pattern.compile("^\\\\w\\s(\\w+)\\s(.*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        } else {
            return null;
        }
    }

    /**
     * Method bound to the press of enter
     * @param event the press action
     */
    public void sendMethod(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonAction();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateChat() {
        Platform.runLater(() -> {
            chatPane.getItems().add(new Text(ViewModelState.getInstance().getChatRecords().getLast()));
        });
    }
}
