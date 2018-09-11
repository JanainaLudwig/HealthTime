package dashboard.view;

import com.jfoenix.controls.JFXButton;
import dashboard.WeekDay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.ControllerUtils;
import utils.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class DashboardWeekController extends DashboardController implements Initializable {

    @FXML
    private GridPane schedule;
    @FXML
    private GridPane weekDays;
    @FXML
    private JFXButton previousW;
    @FXML
    private ImageView previousArrow;
    @FXML
    private Text fDay, lDay,
                 fMonth, lMonth,
                 fYear, lYear;

    private boolean morning;
    private GregorianCalendar dayDisplayed;
    private ArrayList<WeekDay> days;
    private String[] morningHours = {
        "8:00",
        "9:00",
        "10:00",
        "11:00",
        "12:00"
    };
    private String[] afternoonHours = {
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00"
    };

    @FXML
    public void changePeriod() {
        morning = !morning;
        displayHours();
    }

    @FXML
    public void monthVision(ActionEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        DashboardMonthController controller = new DashboardMonthController(this.userId);
        ControllerUtils.changeScene(controller, event, "../../dashboard/view/DashboardMonth.fxml");
    }

    @FXML
    public void nextWeek() {
        dayDisplayed.add(Calendar.DATE, 1);
        createSchedule();
    }

    @FXML
    public void previousWeek() {
        dayDisplayed.add(Calendar.DATE, -8);
        createSchedule();
    }

    @FXML
    public void goToday()  {
        dayDisplayed = new GregorianCalendar();
        createSchedule();
    }

    public void displayHours() {
        String[] hours = (morning) ? morningHours : afternoonHours;

        for (int i = 0; i < 5; i++) {
            Text text = (Text) schedule.getChildren().get(i);
            text.setText(hours[i]);
        }
    }

    public void createSchedule() {
        //Go to fisrt monday of week
        dayDisplayed.add(Calendar.DATE, -(dayDisplayed.get(Calendar.DAY_OF_WEEK) - 1));

        Text today = null;

        for (int i = 0; i < 7; i++) {
            //Displays start day on navigation
            if (i == 0) {
                fDay.setText(String.valueOf(dayDisplayed.get(Calendar.DAY_OF_MONTH)));
                fMonth.setText(MONTH_NAME[dayDisplayed.get(Calendar.MONTH)]);
                fYear.setText(String.valueOf(dayDisplayed.get(Calendar.YEAR)));
            }
            //Displays end day on navigation
            if (i == 6) {
                lDay.setText(String.valueOf(dayDisplayed.get(Calendar.DAY_OF_MONTH)));
                lMonth.setText(MONTH_NAME[dayDisplayed.get(Calendar.MONTH)]);
                lYear.setText(String.valueOf(dayDisplayed.get(Calendar.YEAR)));
            }

            days.add(new WeekDay(dayDisplayed));

            VBox vbox = (VBox) weekDays.getChildren().get(i);
            Text dayOfMonth = (Text) vbox.getChildren().get(0);
            Text dayOfWeek = (Text) vbox.getChildren().get(1);

            dayOfMonth.setText(String.valueOf(dayDisplayed.get(Calendar.DAY_OF_MONTH)));


            //Highlight on today
            if (today == null && DateUtils.isToday(dayDisplayed)) {
                today = dayOfMonth;

                dayOfMonth.setStyle("-fx-fill: " + BLUE + ";");
                dayOfWeek.setStyle("-fx-fill: " + BLUE + ";");
            } else {
                dayOfMonth.setStyle("-fx-fill: " + GRAY + ";");
                dayOfWeek.setStyle("-fx-fill: " + GRAY + ";");
            }

            dayDisplayed.add(Calendar.DATE, 1);
        }


        //If is today, disable previousWeek()
        Class<?> classDashboardController = DashboardMonthController.class;
        InputStream input;

        if (today != null) {
            previousW.setDisable(true);
            previousArrow.setDisable(true);
            input = classDashboardController.getResourceAsStream("/resources/images/arrow_left_disabled.png");
        } else {
            previousW.setDisable(false);
            previousArrow.setDisable(false);
            input = classDashboardController.getResourceAsStream("/resources/images/arrow_left.png");
        }

        Image previousArrowImg = new Image(input);
        previousArrow.setImage(previousArrowImg);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        morning = true;
        displayHours();
        createSchedule();
    }

    public DashboardWeekController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
        days = new ArrayList<>();
        dayDisplayed = new GregorianCalendar();
    }


    public DashboardWeekController(int userId, GregorianCalendar date) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
        days = new ArrayList<>();
        dayDisplayed = date;
    }
}
