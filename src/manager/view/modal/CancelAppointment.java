package manager.view.modal;

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
import manager.UserAppointment;
import manager.view.AppointmentManagerController;
import manager.view.card.AppointmentManagerCardController;
import utils.Controller;
import utils.DateUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CancelAppointment implements Initializable {
    @FXML
    private Text appointmentDate,
                 appointmentSpecialty,
                 appointmentTime;

    private Stage modalStage;
    private Controller controller;
    private UserAppointment userAppointment;

    public CancelAppointment(UserAppointment userAppointment, Scene scene, Controller controller) throws IOException {
        this.userAppointment = userAppointment;
        this.controller = controller;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CancelAppointment.fxml"));
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
    public void cancelAppointment() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userAppointment.cancelAppointment();

        controller.update();
        closeModal();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DAODoctorSpecialty daoAppointment = null;
        String specialty = null;

        try {
            daoAppointment = new DAODoctorSpecialty();
            specialty = daoAppointment.getSpecialty(userAppointment.getIdSpecialty());

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (specialty != null) {
            appointmentSpecialty.setText(specialty);
        }

        appointmentTime.setText(userAppointment.getTime().getInitialTime());
        appointmentDate.setText(DateUtils.getDateDMY(userAppointment.getDate()));
    }

    @FXML
    private void closeModal() {
        modalStage.close();
    }
}
