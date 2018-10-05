
package dashboard.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dashboard.MonthDay;
import dashboard.Specialty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import utils.ControllerUtils;
import utils.DateUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class DashboardMonthController extends DashboardController implements Initializable {
    private ArrayList<MonthDay> monthDays;
    private int monthDisplayed,
                yearDisplayed;

    //FXML elements
    @FXML
    private Text userName;
    @FXML
    private JFXComboBox specialtyCombo;
    @FXML
    private JFXComboBox doctorCombo;
    @FXML
    private GridPane calendar;
    @FXML
    private JFXButton previousM;
    @FXML
    private ImageView previousArrow;
    @FXML
    private Text month,
            year;

    @FXML
    public void dayButtonClick(Event e) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        JFXButton button = (JFXButton) e.getSource();
        int id = Integer.parseInt(button.getId());

        MonthDay monthDay = monthDays.get(id);

        ActionEvent event = (ActionEvent) e;

        DashboardWeekController controller = new DashboardWeekController(this.userId, monthDay.getDate(), selectedComboSpecialty, selectedComboDoctor);
        ControllerUtils.changeScene(controller, event, "../../dashboard/view/DashboardWeek.fxml");
    }

    @FXML
    public void weeklyVision(ActionEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar date = new GregorianCalendar(yearDisplayed, monthDisplayed, 1);

        if (date.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
            date = today;
        }

        DashboardWeekController controller = new DashboardWeekController(this.userId, date, selectedComboSpecialty, selectedComboDoctor);
        ControllerUtils.changeScene(controller, event, "../../dashboard/view/DashboardWeek.fxml");
    }

    @FXML
    public void nextMonth() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (this.monthDisplayed == 11) {
            this.yearDisplayed++;
            this.monthDisplayed = 0;
        } else {
            monthDisplayed++;
        }
        createCalendar();
    }

    @FXML
    public void previousMonth() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (this.monthDisplayed == 0) {
            this.yearDisplayed--;
            this.monthDisplayed = 11;
        } else {
            this.monthDisplayed--;
        }

        createCalendar();
    }

    @FXML
    public void goToday() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        GregorianCalendar calendar = new GregorianCalendar();
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
        createCalendar();
    }

    //Creates calendar visualization based on month and year displayed
    @Override
    public void createCalendar() throws FileNotFoundException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //Filters
        int specialty = specialtyComboBox.getSelectionModel().getSelectedItem().getSpecialtyId();
        int doctor = doctorComboBox.getSelectionModel().getSelectedItem().getDoctorId();

        //Labels for navigation
        month.setText(MONTH_NAME[this.monthDisplayed]);
        year.setText(String.valueOf(this.yearDisplayed));

        GregorianCalendar date = new GregorianCalendar(yearDisplayed, monthDisplayed, 1);
        date.add(Calendar.DATE, -(date.get(Calendar.DAY_OF_WEEK) - 1));

        monthDays = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            GregorianCalendar parameterDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
            monthDays.add(new MonthDay((JFXButton) calendar.getChildren().get(i), parameterDate, user));

            JFXButton button = monthDays.get(i).getButton();

            if (monthDays.get(i).buttonIsDisabled()) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }

            if (date.get(Calendar.MONTH) != monthDisplayed) {
                ((Node) button).setStyle("-fx-text-fill: #838c9c;");
            } else {
                button.setStyle("-fx-text-fill: " + GRAY + ";");
            }

            if ((! DateUtils.isPast(parameterDate)) && monthDays.get(i).hasAvailableAppointment(specialty, selectedCity, doctor)) {
                button.setStyle("-fx-text-fill: #22918e;");
                if (date.get(Calendar.MONTH) != monthDisplayed) {
                    button.setStyle("-fx-text-fill: #30cfcb;");
                }
            }

            if (DateUtils.isToday(date)) {
                button.setStyle("-fx-text-fill: " + BLUE + ";");
            }

            date.add(Calendar.DATE, 1);
        }

        GregorianCalendar today = new GregorianCalendar();
        Class<?> classDashboardController = DashboardMonthController.class;
        InputStream input;

        if (this.monthDisplayed == today.get(Calendar.MONTH) && this.yearDisplayed == today.get(Calendar.YEAR)) {
            previousM.setDisable(true);
            previousArrow.setDisable(true);
            input = classDashboardController.getResourceAsStream("/resources/images/arrow_left_disabled.png");
        } else {
            previousM.setDisable(false);
            previousArrow.setDisable(false);
            input = classDashboardController.getResourceAsStream("/resources/images/arrow_left.png");
        }

        Image previousArrowImg = new Image(input);
        previousArrow.setImage(previousArrowImg);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        try {
            createCalendar();
        } catch (FileNotFoundException | IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DashboardMonthController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
        GregorianCalendar calendar = new GregorianCalendar();
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
    }

    public DashboardMonthController(int userId, GregorianCalendar calendar) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
    }

    public DashboardMonthController(int userId, GregorianCalendar calendar, int currentComboSpecialty, int currentComboDoctor) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
        this.selectedComboSpecialty = currentComboSpecialty;
        this.selectedComboDoctor = currentComboDoctor;
    }
}
