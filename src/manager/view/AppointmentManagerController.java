package manager.view;

import dashboard.User;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import manager.view.card.AppointmentManagerCardController;
import dashboard.view.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentManagerController implements Initializable, Controller {
    @FXML
    private GridPane gripAppointments;
    @FXML
    private AnchorPane paneForGrid;
    @FXML
    private Text noAppointments;

    private Stage modalStage;
    private DashboardController dashboard;
    private User user;

    public AppointmentManagerController(Scene scene, DashboardController dashboard, User user) throws IOException {
        this.dashboard = dashboard;
        this.user = user;

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

        //ESC Key
        modalStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                closeModal();
            }
        });

        modalStage.show();
    }

    public void createAppointmentsGrid() {
        //Removes existing cards
        gripAppointments.getChildren().removeIf(node -> node instanceof AppointmentManagerCardController);

        //Set the right size of AnchorPane that has the grid
        int numberOfAppointments = user.getUserAppointments().size();
        int numberOfRows = (numberOfAppointments % 2 == 0) ? numberOfAppointments/2 : (numberOfAppointments/2) + 1;

        if (numberOfAppointments == 0) {
            noAppointments.setOpacity(1);
        }

        paneForGrid.setPrefHeight(150 * numberOfRows);

        //Add the necessary rows to the grid
        for (int i = 0; i < numberOfAppointments/2 + 1; i++) {
            RowConstraints row = new RowConstraints(130);
            gripAppointments.getRowConstraints().add(row);
        }

        //Insert the cards into the grid
        for (int i = 0, appointment = 0; i < numberOfRows; i++, appointment++) {
            gripAppointments.add(new AppointmentManagerCardController(user.getUserAppointments().get(appointment), this), 0, i);
            //If second exists, add it to the grid
            if (numberOfAppointments > ++appointment) {
                gripAppointments.add(new AppointmentManagerCardController(user.getUserAppointments().get(appointment), this), 1, i);
            }
        }
    }

    @FXML
    private void closeModal() {
        dashboard.closeModal();
        modalStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAppointmentsGrid();
    }

    public DashboardController getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void update() {
        createAppointmentsGrid();
        dashboard.update();
    }
}
