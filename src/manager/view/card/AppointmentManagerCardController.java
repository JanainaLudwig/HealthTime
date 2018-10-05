package manager.view.card;

import dashboard.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import manager.UserAppointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import manager.view.AppointmentManagerController;
import manager.view.modal.CancelAppointment;
import utils.DateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentManagerCardController extends AnchorPane implements Initializable {
    @FXML
    private Text time, date, specialty, doctor, city;
    @FXML
    private Rectangle rectangleTime;
    @FXML
    private Button cancelAppointment;
    @FXML
    private AnchorPane cardPane;

    private UserAppointment appointment;
    private String test;
    private AppointmentManagerController managerController;

    public AppointmentManagerCardController(UserAppointment appointment, AppointmentManagerController managerController) {
        this.appointment = appointment;
        this.managerController = managerController;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "AppointmentManagerCard.fxml"));
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
        if (DateUtils.isPast(appointment.getDate())) {
            rectangleTime.setStyle("-fx-fill: #5DAFED;");
            cancelAppointment.setDisable(true);
            cancelAppointment.setOpacity(0);
        } else if (DateUtils.isToday(appointment.getDate())) {
            cancelAppointment.setDisable(true);
            cancelAppointment.setOpacity(0);
        } else {
            rectangleTime.setStyle("-fx-fill: #79eb85;");
        }

        time.setText(appointment.getTime().getInitialTime());
        date.setText(DateUtils.getDateDMY(appointment.getDate()));
        specialty.setText(appointment.getSpecialty().getDescription());
        doctor.setText(appointment.getDoctor().getDoctorName());
        city.setText("Cidade");
    }

    @FXML
    private void deleteAppointment() {
        try {
            CancelAppointment modal = new CancelAppointment(appointment, this.getScene(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppointmentManagerController getManagerController() {
        return managerController;
    }

    public void setManagerController(AppointmentManagerController managerController) {
        this.managerController = managerController;
    }
}
