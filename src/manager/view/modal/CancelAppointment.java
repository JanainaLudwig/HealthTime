package manager.view.modal;

import DAO.DAOAppointment;
import DAO.DAODoctorSpecialty;
import SMS.SMS;
import dashboard.User;
import dashboard.view.DashboardController;
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
import queue.AppointmentQueue;
import utils.Controller;
import utils.DateUtils;
import utils.NotificationUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class CancelAppointment implements Initializable {
    @FXML
    private Text appointmentDate,
                 appointmentSpecialty,
                 appointmentTime,
                 title;

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
    public void cancelAppointment() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userAppointment.getIdAppointment() == 0) {
            DAOAppointment dao = new DAOAppointment();
            dao.cancelAppointmentQueue(userAppointment.getIdQueueAppointment());

            controller.update();
        } else {
            updateQueue();
        }

        closeModal();

        String message = "Consulta cancelada com sucesso.";
        NotificationUtils.showNotification("Consulta cancelada", message);
    }

    public void updateQueue() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        int idCity = this.userAppointment.getCity().getId();
        int idDoctor = this.userAppointment.getDoctor().getDoctorId();
        GregorianCalendar date = userAppointment.getDate();
        int time;

        int timeCode = userAppointment.getTimeCode();
        if (timeCode < 8) {
            time = 1;
        } else {
            time = 2;
        }

        DAOAppointment dao = new DAOAppointment();
        //Busca por consultas na fila (exceto Clínica geral)
        AppointmentQueue newConsultant = dao.findSpecificSpecialty(idCity, idDoctor, date, time);

        if (newConsultant != null) { //Se encontrar, atualiza os campos da consulta
            updateAppointment(newConsultant.getIdConsultant(), newConsultant.getIdSpecialty());

            //Exclui a solicitação da fila de espera
            dao.cancelAppointmentQueue(newConsultant.getIdQueue());

            controller.update();
        } else { //Se não encontrar, busca as de clínica
            newConsultant = dao.findClinic(idCity, idDoctor, date, time);

            if (newConsultant != null) { //Se encontrar, atualiza os campos da consulta
                updateAppointment(newConsultant.getIdConsultant(), newConsultant.getIdSpecialty());

                //Exclui a solicitação da fila de espera
                dao.cancelAppointmentQueue(newConsultant.getIdQueue());

                controller.update();
            }
        }

        // Se uma consulta foi agendada, envia um SMS ao paciente
        if (newConsultant != null) {
            SMS sms = new SMS();

            User releasedUser = new User(newConsultant.getIdConsultant());

            String smsMessage = "Consulta em fila de espera agendada para " +
                    DateUtils.getDateDMY(userAppointment.getDate()) +
                    ", às " +
                    userAppointment.getTime().getInitialTime() +
                    ". " +
                    userAppointment.getCity().getStation() +
                    " - " +
                    userAppointment.getCity().getName() +
                    ".";

            //Se for maior que 130 caracteres, corta a string.
            if (smsMessage.length() > 130) {
                smsMessage = smsMessage.substring(0, 131);            }

            try {
                sms.send(releasedUser.getTelephoneNumber(), smsMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { //Se não encontrar mesmo assim, exclui a consulta
            this.userAppointment.cancelAppointment();

            controller.update();
        }
    }

    public void updateAppointment(int idConsultant, int idSpecialty) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        int idAppointment = this.userAppointment.getIdAppointment();
        DAOAppointment dao = new DAOAppointment();
        dao.updateAppointment(idAppointment, idConsultant, idSpecialty);

        controller.update();
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

        if (userAppointment.getIdAppointment() == 0) {
            title.setText("Cancelar Fila");
            if (userAppointment.getTimeCode() == 1) {
                appointmentTime.setText("Manhã");
            } else if (userAppointment.getTimeCode() == 2) {
                appointmentTime.setText("Tarde");
            }
        } else {
            title.setText("Cancelar Consulta");
            appointmentTime.setText(userAppointment.getTime().getInitialTime());
        }

        appointmentDate.setText(DateUtils.getDateDMY(userAppointment.getDate()));
    }

    @FXML
    private void closeModal() {
        if (controller instanceof DashboardController) ((DashboardController) controller).closeModal();

        modalStage.close();
    }
}
