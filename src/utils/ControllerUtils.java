package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerUtils {

    public static void changeScene(Initializable controller, ActionEvent event, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource(path));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
