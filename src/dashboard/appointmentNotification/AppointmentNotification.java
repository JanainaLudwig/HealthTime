package dashboard.appointmentNotification;

import dashboard.view.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import manager.UserAppointment;
import manager.view.modal.CancelAppointment;
import utils.Controller;
import utils.DateUtils;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentNotification extends Pane implements Initializable {
    @FXML
    private Text dateTime, specialty;
    @FXML
    private Button cancel;

    private UserAppointment userAppointment;
    private DashboardController controller;

    public AppointmentNotification(UserAppointment userAppointment, DashboardController controller) {
        this.userAppointment = userAppointment;
        this.controller = controller;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "AppointmentNotification.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void cancelAppointment() {
        try {
            CancelAppointment modal = new CancelAppointment(this.userAppointment, this.getScene(), controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Disable cancel if appointment is today or past
        if (DateUtils.isPast(userAppointment.getDate()) || DateUtils.isToday(userAppointment.getDate())) {
            cancel.setDisable(true);
            cancel.setOpacity(0);
        }

        specialty.setText(userAppointment.getSpecialty().getDescription());
        dateTime.setText(DateUtils.getDateDMY(userAppointment.getDate()) + " - " +  userAppointment.getTime().getInitialTime());
    }
}
