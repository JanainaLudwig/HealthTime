package manager.view;

import DAO.DAOAppointment;
import DAO.DAODoctorSpecialty;
import DAO.DAOUser;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dashboard.Doctor;
import dashboard.Specialty;
import dashboard.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import location.City;
import manager.UserAppointment;
import manager.view.card.AppointmentManagerCardController;
import dashboard.view.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Controller;
import utils.DateUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AppointmentManagerController implements Initializable, Controller {
    @FXML
    private GridPane gripAppointments;
    @FXML
    private AnchorPane paneForGrid;
    @FXML
    private Text noAppointments;
    @FXML
    protected JFXComboBox<Specialty> specialtyComboFilter;
    @FXML
    protected JFXComboBox<Doctor> doctorComboFilter;
//    @FXML
//    protected JFXComboBox cityFilter;
    @FXML
    private JFXDatePicker initialDate;
    @FXML
    private JFXDatePicker finalDate;

    ArrayList<Specialty> specialtyList = new ArrayList<>();
    ArrayList<Doctor> doctorList = new ArrayList<>();
    ArrayList cityList = new ArrayList();

    private Stage modalStage;
    private DashboardController dashboard;
    private User user;

    LocalDate firstDate;
    LocalDate lastDate;

    private int idSpecialty,
                idDoctor,
                idCity;

    public int selectedComboSpecialty = 0,
            selectedComboDoctor = 0;
    public static int selectedCity = 1;

    public AppointmentManagerController(Scene scene, DashboardController dashboard, User user) throws IOException {
        this.dashboard = dashboard;
        this.user = user;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppointmentManager.fxml"));
        fxmlLoader.setController(this);
        Parent modalRoot = null;
        modalRoot = fxmlLoader.load();

        Scene modalScene = new Scene(modalRoot);
        modalScene.setFill(Color.TRANSPARENT);

        modalStage = new Stage();
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setScene(modalScene);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(scene.getWindow());
        modalStage.setResizable(false);
        modalStage.centerOnScreen();

        //ESC Key
        modalStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                closeModal();
            }
        });

        modalStage.show();
    }

    public void createAppointmentsGrid() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //Removes existing cards
        gripAppointments.getChildren().removeIf(node -> node instanceof AppointmentManagerCardController);
        noAppointments.setOpacity(0);

        //Set the right size of AnchorPane that has the grid
        int numberOfAppointments = user.getUserAppointments().size();
        int numberOfRows = (numberOfAppointments % 2 == 0) ? numberOfAppointments/2 : (numberOfAppointments/2) + 1;

        if (numberOfAppointments == 0) {
            String specialtyText = "",
                    doctorText = "";

            DAODoctorSpecialty dao = new DAODoctorSpecialty();
            if (idSpecialty != 0) {
                specialtyText = " para a especialidade " + dao.getSpecialty(idSpecialty);
            }
            if (idDoctor != 0) {
                doctorText = " com o(a) médico(a) " + dao.getDoctorName(idDoctor);
            }
            noAppointments.setText("Você não possui nenhuma consulta" + specialtyText + doctorText + ".");
            noAppointments.setTextAlignment(TextAlignment.CENTER);
            noAppointments.setOpacity(1);
        }

        paneForGrid.setPrefHeight(150 * numberOfRows);

        //Add the necessary rows to the grid
        for (int i = 0; i < numberOfAppointments/2 + 1; i++) {
            RowConstraints row = new RowConstraints(130);
            gripAppointments.getRowConstraints().add(row);
        }

        //Insert the cards into the grid
        for (int i = 0, appointment = 0; i < numberOfRows; i++, appointment++) {
            gripAppointments.add(new AppointmentManagerCardController(user.getUserAppointments().get(appointment), this), 0, i);
            //If second exists, add it to the grid
            if (numberOfAppointments > ++appointment) {
                gripAppointments.add(new AppointmentManagerCardController(user.getUserAppointments().get(appointment), this), 1, i);
            }
        }

        try {
            specialtyCombo();
            doctorCombo();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void datePicker() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        DAOAppointment dao = new DAOAppointment();
        initialDate.setValue(LocalDate.parse(dao.minDate()));
        finalDate.setValue(LocalDate.parse(dao.maxDate()));

        firstDate = initialDate.getValue();
        lastDate = finalDate.getValue();

        switchDate();
    }

    @FXML
    public void specialtyCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        //Get specialties objects available to the patient
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        specialtyList = dao.getAllDescriptionFilter(this.user.getUserId(), firstDate, lastDate);

        //Prevent onChange behavior
        specialtyComboFilter.setOnAction(null);
        //Clear items
        specialtyComboFilter.getItems().clear();
        //Set converter
        specialtyComboFilter.setConverter(new StringConverter<Specialty>() {
            @Override
            public String toString(Specialty object) {
                return object.getDescription();
            }

            @Override
            public Specialty fromString(String string) {
                return null;
            }
        });
        //Set items
        specialtyComboFilter.setItems(FXCollections.observableArrayList(specialtyList));

        //Select one item in the combo
        findPositionSpecialty();
        specialtyComboFilter.getSelectionModel().select(selectedComboSpecialty);

        //Set onChange behavior
        specialtyComboFilter.setOnAction((event) -> {
            try {
                switchSpecialty();
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void doctorCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        int specialty = specialtyComboFilter.getSelectionModel().getSelectedItem().getSpecialtyId();

        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        doctorList = dao.getDoctorFilter(specialty, this.user.getUserId(), firstDate, lastDate);

        //Prevent onChange behavior
        doctorComboFilter.setOnAction(null);

        //Clear items
        doctorComboFilter.getItems().clear();
        //Set converter
        doctorComboFilter.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor object) {
                return object.getDoctorName();
            }

            @Override
            public Doctor fromString(String string) {
                return null;
            }
        });
        //Set items
        doctorComboFilter.setItems(FXCollections.observableArrayList(doctorList));

        //Select one item in the combo
        findPositionDoctor();
        doctorComboFilter.getSelectionModel().select(selectedComboDoctor);
        //Set onChange behavior
        doctorComboFilter.setOnAction((event) -> {
            try {
                switchDoctor();
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

//    @FXML
//    public void cityCombo() {
//
//        //Prevent onChange behavior
//        cityFilter.setOnAction(null);
//        //Clear items
//        cityFilter.getItems().clear();
//
//        for (int i = 0; i < user.getUserAppointments().size(); i++) {
//            cityList.add(user.getUserAppointments().get(i).getIdCity());
//        }
//
//        cityFilter.setItems(FXCollections.observableArrayList(cityList));
//    }

    @FXML
    public void switchDate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        firstDate = initialDate.getValue();
        lastDate = finalDate.getValue();


        try {
            specialtyCombo();
            doctorCombo();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        //Reseta as consultas a serem filtradas
        user.updateUserAppointments();

        //Adiciona consultas filtradas
        filters();

        //Cria o grid atualizado
        createAppointmentsGrid();
    }

    public void findPositionSpecialty() {
        for (int i = 0; i < specialtyComboFilter.getItems().size(); i++) {
            if (specialtyComboFilter.getItems().get(i).getSpecialtyId() == idSpecialty) {
                selectedComboSpecialty = i;
                return;
            }
        }
        setSelectedComboSpecialty(0);
    }

    public void findPositionDoctor() {
        for (int i = 0; i < doctorComboFilter.getItems().size(); i++) {
            if (doctorComboFilter.getItems().get(i).getDoctorId() == idDoctor) {
                selectedComboDoctor = i;
                return;
            }
        }
        setSelectedComboSpecialty(0);
//        findPositionSpecialty();
        setSelectedComboDoctor(0);
    }

    @FXML
    public void switchSpecialty() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        selectedComboSpecialty = specialtyComboFilter.getSelectionModel().getSelectedIndex();

        //Reseta o combo de doutores para "Todos"
        selectedComboDoctor = 0;
        doctorCombo();

        //Pega os filtros selecionados
        idSpecialty = specialtyComboFilter.getSelectionModel().selectedItemProperty().getValue().getSpecialtyId();
        idDoctor = doctorComboFilter.getSelectionModel().selectedItemProperty().getValue().getDoctorId();
        idCity = user.getCity().getId();

        //Reseta as consultas a serem filtradas
        user.updateUserAppointments();

        //Adiciona consultas filtradas
        filters();

        //Cria o grid atualizado
        createAppointmentsGrid();
    }

    @FXML
    public void switchDoctor() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        selectedComboDoctor = doctorComboFilter.getSelectionModel().getSelectedIndex();

        idSpecialty = specialtyComboFilter.getSelectionModel().selectedItemProperty().getValue().getSpecialtyId();
        idDoctor = doctorComboFilter.getSelectionModel().selectedItemProperty().getValue().getDoctorId();
        idCity = user.getCity().getId();

        //Reseta as consultas a serem filtradas
        user.updateUserAppointments();

        //Adiciona consultas filtradas
        filters();

        //Cria o grid atualizado
        createAppointmentsGrid();
    }

    public void filters() {
        updateFilterDate();
        updateFilterSpecialty();
        updateFilterDoctor();
    }

    @FXML
    public void resetFilters(ActionEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DAOAppointment dao = new DAOAppointment();
        initialDate.setValue(LocalDate.parse(dao.minDate()));
        finalDate.setValue(LocalDate.parse(dao.maxDate()));
        firstDate = initialDate.getValue();
        lastDate = finalDate.getValue();

        user.updateUserAppointments();
        createAppointmentsGrid();

        idSpecialty = 0;
        idDoctor = 0;

        try {
            setSelectedComboDoctor(0);
            setSelectedComboSpecialty(0);
            specialtyCombo();
            doctorCombo();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeModal() {
        dashboard.closeModal();
        modalStage.close();
    }

    private void updateFilterDate() {
        ArrayList<UserAppointment> newAppointmentsDate = new ArrayList<>();

        for (int i = 0; i < user.getUserAppointments().size(); i++) {
            if (user.getUserAppointments().get(i).getDate().toZonedDateTime().toLocalDate().isEqual(firstDate) ||
                    user.getUserAppointments().get(i).getDate().toZonedDateTime().toLocalDate().isAfter(firstDate)) {
                if (user.getUserAppointments().get(i).getDate().toZonedDateTime().toLocalDate().isEqual(lastDate) ||
                        user.getUserAppointments().get(i).getDate().toZonedDateTime().toLocalDate().isBefore(lastDate)) {
                    newAppointmentsDate.add(user.getUserAppointments().get(i));
                }
            }
        }

        user.setUserAppointments(newAppointmentsDate);
    }

    private void updateFilterSpecialty() {
        ArrayList<UserAppointment> newAppointmentsSpecialty = new ArrayList<>();

        if (idSpecialty != 0) {
            for (int i = 0; i < user.getUserAppointments().size(); i++) {
                if (user.getUserAppointments().get(i).getSpecialty().getSpecialtyId() == idSpecialty &&
                        user.getUserAppointments().get(i).getIdCity() == idCity) {
                    newAppointmentsSpecialty.add(user.getUserAppointments().get(i));
                }
            }

            user.setUserAppointments(newAppointmentsSpecialty);
        }
    }

    private void updateFilterDoctor() {
        ArrayList<UserAppointment> newAppointmentsDoctor = new ArrayList<>();

        if (idDoctor != 0) {
            for (int i = 0; i < user.getUserAppointments().size(); i++) {
                if (user.getUserAppointments().get(i).getDoctor().getDoctorId() == idDoctor) {
                    newAppointmentsDoctor.add(user.getUserAppointments().get(i));
                }
            }

            user.setUserAppointments(newAppointmentsDoctor);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setSelectedComboDoctor(0);
            setSelectedComboSpecialty(0);
            datePicker();
            specialtyCombo();
            doctorCombo();
//            cityCombo();

            createAppointmentsGrid();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public DashboardController getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    public int getSelectedComboSpecialty() {
        return selectedComboSpecialty;
    }

    public void setSelectedComboSpecialty(int selectedComboSpecialty) {
        this.selectedComboSpecialty = selectedComboSpecialty;
    }

    public int getSelectedComboDoctor() {
        return selectedComboDoctor;
    }

    public void setSelectedComboDoctor(int selectedComboDoctor) {
        this.selectedComboDoctor = selectedComboDoctor;
    }

    @Override
    public void update() {
        try {
            createAppointmentsGrid();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        dashboard.update();
    }
}
