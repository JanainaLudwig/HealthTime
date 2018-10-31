package dashboard.view;

import DAO.DAODoctorSpecialty;
import DAO.DAOUser;
import com.jfoenix.controls.JFXComboBox;
import dashboard.Doctor;
import dashboard.Specialty;
import dashboard.User;
import dashboard.appointmentNotification.AppointmentNotification;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import manager.UserAppointment;
import manager.view.AppointmentManagerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Controller;
import utils.DateUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DashboardController implements Initializable, Controller {
    protected final String BLUE = "#168EE9";
    protected final String GRAY = "#394e5e";
    protected int userId;
    protected User user;


    ArrayList<Specialty> specialtyList = new ArrayList<>();
    ArrayList<Doctor> doctorList = new ArrayList<>();

    public Rectangle modalOpened;

    public int selectedComboSpecialty = 0,
                  selectedComboDoctor = 0;
    public static int selectedCity = 1;

    @FXML
    public AnchorPane pane;
    @FXML
    private Text userName, dayWeek, dayMonth;
    @FXML
    protected JFXComboBox<Specialty> specialtyComboBox;
    @FXML
    protected JFXComboBox<Doctor> doctorComboBox;
    @FXML
    private Pane notification1, notification2;
    @FXML
    private Rectangle rectNotification1, rectNotification2;

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) userName.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    public void specialtyCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        //Get specialties objects available to the patient
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        specialtyList = dao.getAllDescription(this.userId);

        //Prevent onChange behavior
        specialtyComboBox.setOnAction(null);
        //Clear items
        specialtyComboBox.getItems().clear();
        //Set converter
        specialtyComboBox.setConverter(new StringConverter<Specialty>() {
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
        specialtyComboBox.setItems(FXCollections.observableArrayList(specialtyList));
        //Select one item in the combo
        specialtyComboBox.getSelectionModel().select(selectedComboSpecialty);

        //Set onChange behavior
        specialtyComboBox.setOnAction((event) -> {
            try {
                switchSpecialty();
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void doctorCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        int specialty = specialtyComboBox.getSelectionModel().getSelectedItem().getSpecialtyId();

        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        doctorList = dao.getDoctor(specialty, user.getIdCity());

        //Prevent onChange behavior
        doctorComboBox.setOnAction(null);

        //Clear items
        doctorComboBox.getItems().clear();
        //Set converter
        doctorComboBox.setConverter(new StringConverter<Doctor>() {
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
        doctorComboBox.setItems(FXCollections.observableArrayList(doctorList));

        //Select one item in the combo
        doctorComboBox.getSelectionModel().select(selectedComboDoctor);
        //Set onChange behavior
        doctorComboBox.setOnAction((event) -> {
            try {
                switchDoctor();
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void switchSpecialty() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        selectedComboSpecialty = specialtyComboBox.getSelectionModel().getSelectedIndex();
        selectedComboDoctor = 0;
        doctorCombo();
        createCalendar();
    }

    @FXML
    public void switchDoctor() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        selectedComboDoctor = doctorComboBox.getSelectionModel().getSelectedIndex();

        createCalendar();
    }

    @FXML
    public void openManager() throws IOException {
        openModal();

        AppointmentManagerController manager = new AppointmentManagerController(userName.getScene(), this, this.user);
    }

    public void openModal() {
        modalOpened = new Rectangle(1220, 660);
        modalOpened.setStyle("-fx-background-color: #303030b1");
        modalOpened.setOpacity(0.5);
        pane.getChildren().addAll(modalOpened);
    }

    public void closeModal() {
        pane.getChildren().remove(modalOpened);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         * Get the username
         * with two first names
         */
        String name;
        String[] nameSplitted = user.getUserName().split(" ");

        if (nameSplitted.length > 1) {
            name = nameSplitted[0] + " " + nameSplitted[1];
        } else {
            name = nameSplitted[0];
        }

        userName.setText(name);

        //Set actual date
        GregorianCalendar today = new GregorianCalendar();
        int day = today.get(Calendar.DAY_OF_MONTH);
        String month = DateUtils.getThreeMonthLetters(today.get(Calendar.MONTH));
        dayMonth.setText(day + " " + month);
        dayWeek.setText(DateUtils.DAY_NAME[today.get(Calendar.DAY_OF_WEEK) - 1]);

        /*
         * Specialty ComboBox
         */
        try {
            specialtyCombo();
        } catch (ClassNotFoundException | NullPointerException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
         * Doctor ComboBox
         */
        try {
            doctorCombo();
        } catch (ClassNotFoundException | NullPointerException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

        setNotifications();
    }

    public void createCalendar() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    }

    public void setNotifications() {
        //Remove existing notifications
        notification1.getChildren().removeIf(node -> node instanceof AppointmentNotification);
        notification2.getChildren().removeIf(node -> node instanceof AppointmentNotification);

        DAOUser dao = null;
        ArrayList<UserAppointment> nextAppointments = null;

        try {
            dao = new DAOUser(this.user);
            nextAppointments = dao.getNextAppointments();
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        if (nextAppointments.size()== 2) {
            notification1.getChildren().add(new AppointmentNotification(nextAppointments.get(0), this));
            notification2.getChildren().add(new AppointmentNotification(nextAppointments.get(1), this));
            rectNotification1.setOpacity(1);
            rectNotification2.setOpacity(1);
        } else if (nextAppointments.size()== 1) {
            notification1.getChildren().add(new AppointmentNotification(nextAppointments.get(0), this));
            rectNotification1.setOpacity(1);
            rectNotification2.setOpacity(0);
        } else {
            rectNotification1.setOpacity(0);
            rectNotification2.setOpacity(0);
        }

    }

    public DashboardController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;
        this.user = new User(userId);
        selectedCity = user.getIdCity();
    }

    public DashboardController(int userId, JFXComboBox<Specialty> comboSpecialty) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;
        this.user = new User(userId);
        selectedCity = user.getIdCity();
        this.specialtyComboBox = comboSpecialty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        //TODO: ajustar lista
        user.updateUserAppointments();
        try {
            createCalendar();
            //TODO: tentar pegar o objeto dos combos para depois de repopular o combo buscar os antigos objetos e setá-los
            setSelectedComboDoctor(0);
            setSelectedComboSpecialty(0);
            specialtyCombo();
            setNotifications();
        } catch (FileNotFoundException | ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
