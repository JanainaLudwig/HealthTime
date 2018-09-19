package dashboard.modal;

import DAO.DAOAppointment;
import DAO.DAODoctorSpecialty;
import dashboard.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DateUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfirmAppointment implements Initializable {
    @FXML
    private Text appointmentDate,
                 appointmentSpecialty,
                 appointmentTime;

    private Stage modalStage;

    private Appointment appointment;

    public ConfirmAppointment(Appointment appointment, Scene scene) throws IOException {
        this.appointment = appointment;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmAppointment.fxml"));
        fxmlLoader.setController(this);
        Parent modalRoot = fxmlLoader.load();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DAODoctorSpecialty daoAppointment = null;
        String specialty = null;

        try {
            daoAppointment = new DAODoctorSpecialty();
            specialty = daoAppointment.getSpecialty(appointment.getId_specialty());

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (specialty != null) {
            appointmentSpecialty.setText(specialty);
        }

        appointmentTime.setText(appointment.getTime().getInitialTime());
        appointmentDate.setText(DateUtils.getDateDMY(appointment.getDay().getDate()));

    }

    @FXML
    private void closeModal() {
        modalStage.close();
    }
}
