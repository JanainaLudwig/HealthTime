package manager.card;

import manager.UserAppointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentManagerCardController extends AnchorPane implements Initializable {
    @FXML
    private Text hour, date, specialty, doctor, city;
    @FXML
    private Rectangle rectangleTime;

    private UserAppointment appointment;


    public AppointmentManagerCardController(/*UserAppointment appointment*/) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "AppointmentManagerCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //this.appointment = appointment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*if (DateUtils.isPast(appointment.getDate())) {
            rectangleTime.setStyle("-fx-background-color: #5DAFED;");
        } else {
            rectangleTime.setStyle("-fx-background-color: #79eb85;");
        }*/
    }
}
