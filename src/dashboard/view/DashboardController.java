
package dashboard.view;

import DAO.DAODoctorSpecialty;
import com.jfoenix.controls.JFXComboBox;
import dashboard.Doctor;
import dashboard.Specialty;
import dashboard.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    /*
     * Change according to filter ComboBoxes and call createCalendar()
     * selectedSpecialty: clínica geral = 0
     * selectedDoctor: 0
     */
    protected int selectedSpecialty = 1,
                  selectedDoctor = 0;
    public static int selectedCity = 1;


    @FXML
    private Text userName;
    @FXML
    private JFXComboBox specialtyComboBox;
    @FXML
    private JFXComboBox doctorComboBox;

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) userName.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    public void specialtyCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        specialtyList = dao.getAllDescription();

        for (int i = 0; i < specialtyList.size(); i++) {
            this.specialtyComboBox.getItems().add(specialtyList.get(i).getDescription());
        }
        this.specialtyComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void doctorCombo() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException {
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        doctorList = dao.getDoctor((String) specialtyComboBox.getValue());

        this.doctorComboBox.getItems().clear();
        for (int i = 0; i < doctorList.size(); i++) {
            this.doctorComboBox.getItems().add(doctorList.get(i).getDoctorName());
        }

        this.doctorComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void switchSpecialty() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        for (int i = 0; i < specialtyList.size(); i++) {
            if (specialtyList.get(i).getDescription().equals(specialtyComboBox.getValue())) {
                this.selectedSpecialty = specialtyList.get(i).getSpecialtyId();
            }
        }
        this.selectedDoctor = 0;
        doctorCombo();
        createCalendar();
    }

    @FXML
    public void switchDoctor() throws ClassNotFoundException, NullPointerException, SQLException, InstantiationException, IllegalAccessException, FileNotFoundException {
        for (int i = 0; i < doctorList.size(); i++) { //Verifica na lista de doutores
            if (doctorList.get(i).getDoctorName().equals(doctorComboBox.getValue())) {
                this.selectedDoctor = doctorList.get(i).getDoctorId();
            }
        }
        createCalendar();
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
         * Doctor ComboBox
         */
        try {
            doctorCombo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void createCalendar() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    }

    public DashboardController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;
        this.user = new User(userId);
    }

}
