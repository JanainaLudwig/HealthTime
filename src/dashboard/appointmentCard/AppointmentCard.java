package dashboard.appointmentCard;

import dashboard.AvailableAppointment;
import dashboard.modal.ConfirmAppointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AppointmentCard extends Pane {
    private AvailableAppointment availableAppointment;

    public AppointmentCard(AvailableAppointment availableAppointment) {
        this.availableAppointment = availableAppointment;

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
        this.availableAppointment.getDay().getController().openModal();

        ConfirmAppointment modal = new ConfirmAppointment(this.availableAppointment, this.getScene());
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

    public AvailableAppointment getAvailableAppointment() {
        return availableAppointment;
    }

    public void setAvailableAppointment(AvailableAppointment availableAppointment) {
        this.availableAppointment = availableAppointment;
    }
}
