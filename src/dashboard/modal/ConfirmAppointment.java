package dashboard.modal;

import DAO.DAOAppointment;
import DAO.DAODoctorSpecialty;
import dashboard.AvailableAppointment;
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
import utils.Notification;
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

    private AvailableAppointment availableAppointment;

    public ConfirmAppointment(AvailableAppointment availableAppointment, Scene scene) throws IOException {
        this.availableAppointment = availableAppointment;

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

    @FXML
    public void confirmAppointment() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DAOAppointment daoAppointment = new DAOAppointment();

        daoAppointment.scheduleAppointment(availableAppointment);
        availableAppointment.getDay().getUser().updateUserAppointments();

        closeModal();

        availableAppointment.getDay().getController().update();

        String message = "Sua consulta foi agendada para " + appointmentDate.getText() + " às " + appointmentTime.getText();
        Notification.showNotification("Consulta agendada", message);

    }

    public AvailableAppointment getAvailableAppointment() {
        return availableAppointment;
    }

    public void setAvailableAppointment(AvailableAppointment availableAppointment) {
        this.availableAppointment = availableAppointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DAODoctorSpecialty daoAppointment = null;
        String specialty = null;

        try {
            daoAppointment = new DAODoctorSpecialty();
            specialty = daoAppointment.getSpecialty(availableAppointment.getIdSpecialty());

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (specialty != null) {
            appointmentSpecialty.setText(specialty);
        }

        appointmentTime.setText(availableAppointment.getTime().getInitialTime());
        appointmentDate.setText(DateUtils.getDateDMY(availableAppointment.getDay().getDate()));

    }

    @FXML
    private void closeModal() {
        modalStage.close();
    }


}
