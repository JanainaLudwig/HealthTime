
package dashboard.view;

import com.jfoenix.controls.JFXButton;
import dashboard.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DashboardController implements Initializable {

    private int userId;
    private int monthDisplayed;
    private  int yearDisplayed;
    private User user;
    private static final String[] MONTH_NAME = {
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

    @FXML
    private Text userName;
    @FXML
    private GridPane calendar;
    @FXML
    private JFXButton previousM;
    //@FXML
    //private Image previousArrow;
    @FXML
    private Text month, year;


    @FXML
    public void nextMonth() {
        if (this.monthDisplayed== 11) {
            this.yearDisplayed++;
            this.monthDisplayed = 0;
        } else {
            monthDisplayed++;
        }
        createCalendar();
    }

    @FXML
    public void previousMonth() {
        if (this.monthDisplayed == 0) {
            this.yearDisplayed--;
            this.monthDisplayed = 11;
        } else {
            this.monthDisplayed--;
        }
        createCalendar();
    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) calendar.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
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
        createCalendar();
    }


    /*
     * Creates calendar visualization based on month and year displayed
     */
    private void createCalendar() {
        month.setText(MONTH_NAME[this.monthDisplayed]);
        year.setText(String.valueOf(this.yearDisplayed));

        ArrayList<JFXButton> buttons = new ArrayList<JFXButton>();
        for (int i = 0; i < 42; i++) {
            buttons.add((JFXButton) calendar.getChildren().get(i));
        }

        GregorianCalendar day = new GregorianCalendar(yearDisplayed, monthDisplayed, 1);
        GregorianCalendar today = new GregorianCalendar();
        int dayOfWeekStart = day.get(Calendar.DAY_OF_WEEK) - 1;

        // Month days
        for (int i = dayOfWeekStart; i < 42; i++) {
            JFXButton button = buttons.get(i);
            button.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));

            if (day.get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH) && today.get(Calendar.MONTH) == this.monthDisplayed) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }

            //Disable weekends
            if (calendar.getColumnIndex(button) == null || calendar.getColumnIndex(button) == 6) {
                button.setDisable(true);
            }

            day.add(Calendar.DATE, 1);
        }

        //Previous days
        GregorianCalendar previousDay = new GregorianCalendar(yearDisplayed, monthDisplayed, 1);

        for (int i = dayOfWeekStart-1; i >= 0; i--) {
            JFXButton button = buttons.get(i);
            previousDay.add(Calendar.DATE, -1);
            button.setText(String.valueOf(previousDay.get(Calendar.DAY_OF_MONTH)));

            if (today.get(Calendar.MONTH) == this.monthDisplayed) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }

            //Disable weekends
            if (calendar.getColumnIndex(button) == null) {
                button.setDisable(true);
            }
        }

        //Disable past months
        if (this.monthDisplayed == today.get(Calendar.MONTH) && this.yearDisplayed == today.get(Calendar.YEAR)) {
            previousM.setDisable(true);

        } else {
            previousM.setDisable(false);
        }

    }


    public DashboardController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;
        this.user = new User(userId);
        GregorianCalendar calendar = new GregorianCalendar();
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
    }

}
