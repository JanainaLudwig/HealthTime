package chatbot.view;


import chatbot.Watson;
import dashboard.view.DashboardController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController extends AnchorPane implements Initializable {
    @FXML
    private TextField message;
    @FXML
    private GridPane messages;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView sendIcon;

    private int numberOfMessages;
    private Watson assistant;
    private DashboardController dashboardController;

    @FXML
    private void send() {
        String userMessage = this.message.getText();

        if (userMessage.equals("")) {
            return;
        }

        //Add user message to chat
        MessageController message = new MessageController("sent", userMessage);
        messages.addRow(numberOfMessages, message);
        //Clear user input
        this.message.setText("");

        numberOfMessages++;

        Platform.runLater(() -> {
            //Scroll down
            scrollPane.setVvalue(1.0);
            ArrayList<String> responses = assistant.sendMessage(userMessage);
            for (String response : responses) {
                //Add watson response
                messages.addRow(numberOfMessages, new MessageController("received", response));
                numberOfMessages++;
                Platform.runLater(() -> {
                    //Scroll down
                    scrollPane.setVvalue(1.0);
                });
            }
        });

    }

    @FXML
    private void close() {
        dashboardController.closeAssistant();
    }

    @FXML
    private void minimize() {
        dashboardController.minimizeAssistant();
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public ChatController(DashboardController dashboard) {
        this.numberOfMessages = 0;
        this.assistant = new Watson();
        this.dashboardController = dashboard;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chat.fxml"));
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
    }
}
