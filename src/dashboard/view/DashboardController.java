package dashboard.view;

import DAO.DAODoctorSpecialty;
import com.jfoenix.controls.JFXComboBox;
import dashboard.Doctor;
import dashboard.Specialty;
import dashboard.User;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;
import manager.view.AppointmentManagerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    protected final String BLUE = "#168EE9";
    protected final String GRAY = "#394e5e";
    protected int userId;
    protected User user;
    protected static final String[] MONTH_NAME = {
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
    };

    ArrayList<Specialty> specialtyList = new ArrayList<>();
    ArrayList<Doctor> doctorList = new ArrayList<>();

    public Rectangle modalOpened;

    public int selectedComboSpecialty = 0,
                  selectedComboDoctor = 0;
    public static int selectedCity = 1;

    @FXML
    public AnchorPane pane;
    @FXML
    private Text userName;
    @FXML
    protected JFXComboBox<Specialty> specialtyComboBox;
    @FXML
    protected JFXComboBox<Doctor> doctorComboBox;

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
        modalOpened = new Rectangle(1220, 660);
        modalOpened.setStyle("-fx-background-color: #303030b1");
        modalOpened.setOpacity(0.5);
        pane.getChildren().addAll(modalOpened);

        AppointmentManagerController manager = new AppointmentManagerController(userName.getScene(), this, this.user);
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
    }

    public void createCalendar() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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
}
