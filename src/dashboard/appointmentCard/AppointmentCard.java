package dashboard.appointmentCard;

import dashboard.AppointmentTime;
import dashboard.WeekDay;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.IOException;

public class AppointmentCard extends Pane {

    private WeekDay weekDay;
    private AppointmentTime time;

    public AppointmentCard() {
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
}
