package dashboard.appointmentNotification;

import dashboard.view.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import manager.UserAppointment;
import manager.view.modal.CancelAppointment;
import utils.DateUtils;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AppointmentNotification extends Pane implements Initializable {
    @FXML
    private Text day, time, specialty, month;
    @FXML
    private Pane cancel;

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
        controller.openModal();

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
        day.setText(String.valueOf(userAppointment.getDate().get(Calendar.DAY_OF_MONTH)));
        month.setText(DateUtils.getThreeMonthLetters(userAppointment.getDate().get(Calendar.MONTH)));
        time.setText(userAppointment.getTime().getInitialTime());
    }
}
