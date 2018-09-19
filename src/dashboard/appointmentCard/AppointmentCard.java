package dashboard.appointmentCard;

import dashboard.Appointment;
import dashboard.modal.ConfirmAppointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AppointmentCard extends Pane {
    private Appointment appointment;

    public AppointmentCard(Appointment appointment) {
        this.appointment = appointment;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "AppointmentCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private Text startHour;
    @FXML
    private Text endHour;

    @FXML
    public void cardClicked() throws IOException {
        ConfirmAppointment modal = new ConfirmAppointment(this.appointment, this.getScene());
    }

    public void setStartHour(String text) {
        startHour.setText(text);
    }

    public void setEndHour(String text) {
        endHour.setText(text);
    }

    public Text getStartHour() {
        return startHour;
    }

    public void setStartHour(Text startHour) {
        this.startHour = startHour;
    }

    public Text getEndHour() {
        return endHour;
    }

    public void setEndHour(Text endHour) {
        this.endHour = endHour;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
