package queue.modal;

import DAO.DAOAppointment;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dashboard.AppointmentTime;
import dashboard.AvailableAppointment;
import dashboard.User;
import dashboard.view.DashboardController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import location.City;
import manager.UserAppointment;
import utils.DateUtils;
import utils.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class QueueController implements Initializable {
    @FXML
    private JFXDatePicker queueDate;
    @FXML
    protected JFXComboBox<String> queueCombo;
    @FXML
    private Label errorLabel;

    private Stage modalStage;
    private DashboardController controller;
    private UserAppointment appointment;
    private User user;
    private LocalDate initialDate;

    private int specialty, city;

    private AvailableAppointment availableAppointment;

    public QueueController(DashboardController controller, Scene scene, int specialty, int city, User user, LocalDate initialDate) throws IOException {
        this.controller = controller;
        this.specialty = specialty;
        this.city = city;
        this.user = user;
        this.initialDate = initialDate;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Queue.fxml"));
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
    public void confirmQueue() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //Verificar os dados selecionados no modal
        LocalDate date = queueDate.getValue();
        String periodo = queueCombo.getValue();
        int period = 0;
        String timeText = "";

        String DATE_INCOMPLETE = "Informe a data desejada.";
        String TIME_INCOMPLETE = "Informe o período desejado.";
        String HAS_APPOINTMENT_PERIOD = "Ainda existe um horário disponível no período selecionado.";

        if (date == null) {
            errorLabel.setText(DATE_INCOMPLETE);
            return;
        }
        if (periodo != null) {
            if (periodo.equals("Manhã")) {
                period = 1;
                timeText = "manhã";
            } else if (periodo.equals("Tarde")) {
                period = 2;
                timeText = "tarde";
            }
        } else {
            errorLabel.setText(TIME_INCOMPLETE);
            return;
        }

        //Verificar os horários vagos do dia selecionado
        GregorianCalendar dateg = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        DAOAppointment dao = new DAOAppointment();

        if (dao.hasAppointmentPeriod(specialty, city, period, dateg, 0, user.getUserId())) {
            errorLabel.setText(HAS_APPOINTMENT_PERIOD);
            return;
        } else {
            dao.addAppointmentQueue(this.user.getUserId(), specialty, city, date, period);
        }

        //Fechar o modal
        closeModal();

        //Mostrar a notificação de fila marcada
        String message = "Seu pedido de consulta para " + DateUtils.getDateDMY(dateg) + ", no período da " + timeText +
                ", está na fila de espera e aguarda algum cancelamento para ser efetivada.";
        NotificationUtils.showNotification("Consulta em fila de espera", message);
    }

    public void disableDates() {
        queueDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) <= 0 || date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7);
            }
        });
    }

    public void queueCombo() {
        ArrayList<String> turnos = new ArrayList();
        turnos.add("Manhã");
        turnos.add("Tarde");

        //Prevent onChange behavior
        queueCombo.setOnAction(null);

        //Clear items
        queueCombo.getItems().clear();

        queueCombo.setItems(FXCollections.observableArrayList(turnos));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setText("");
        disableDates();
        queueCombo();

        queueDate.setValue(initialDate);
    }

    @FXML
    private void closeModal() {
        controller.closeModal();

        modalStage.close();
    }
}