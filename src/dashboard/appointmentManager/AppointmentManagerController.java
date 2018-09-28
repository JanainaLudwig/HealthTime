package dashboard.appointmentManager;

import dashboard.Appointment;
import dashboard.view.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentManagerController implements Initializable {

    private Stage modalStage;
    private DashboardController dashboard;

    public AppointmentManagerController(Scene scene, DashboardController dashboard) throws IOException {
        this.dashboard = dashboard;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppointmentManager.fxml"));
        fxmlLoader.setController(this);
        Parent modalRoot = null;
        modalRoot = fxmlLoader.load();

        Scene modalScene = new Scene(modalRoot);
        modalScene.setFill(Color.TRANSPARENT);

        modalStage = new Stage();
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setScene(modalScene);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(scene.getWindow());
        modalStage.setResizable(false);
        modalStage.centerOnScreen();
        modalStage.show();

    }

    @FXML private void closeModal() {
        dashboard.pane.getChildren().remove(dashboard.modalOpened);
        modalStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
