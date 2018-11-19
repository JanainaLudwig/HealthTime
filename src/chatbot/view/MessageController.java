package chatbot.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageController extends AnchorPane implements Initializable {
    private String message;

    @FXML
    private Label textMessage;
    @FXML
    private Rectangle messageBackground;

    public MessageController(String type, String message) {
        this.message = message;

        String fxml = (type.equals("sent")) ? "MessageSent.fxml" : "MessageReceived.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textMessage.setText(message);
    }
}
