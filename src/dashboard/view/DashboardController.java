
package dashboard.view;

import com.jfoenix.controls.JFXButton;
import dashboard.Day;
import dashboard.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.DateUtils;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DashboardController implements Initializable {
    private ArrayList<Day> days;
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
    @FXML
    private ImageView previousArrow;
    @FXML
    private Text month, year;


    @FXML
    public void buttonClick(Event e) {
        //days.get(days.indexOf((Button) e.getSource())).buttonClick();
        //Pegar id do botão
        //Definir indice de days pelo id
        //Executar ação definida na classe dia
    }

    @FXML
    public void nextMonth() throws FileNotFoundException {
        if (this.monthDisplayed== 11) {
            this.yearDisplayed++;
            this.monthDisplayed = 0;
        } else {
            monthDisplayed++;
        }
        createCalendar();
    }

    @FXML
    public void previousMonth() throws FileNotFoundException {
        if (this.monthDisplayed == 0) {
            this.yearDisplayed--;
            this.monthDisplayed = 11;
        } else {
            this.monthDisplayed--;
        }

        createCalendar();
    }

    @FXML
    public void goToday() throws FileNotFoundException {
        GregorianCalendar calendar = new GregorianCalendar();
        this.monthDisplayed = calendar.get(Calendar.MONTH);
        this.yearDisplayed = calendar.get(Calendar.YEAR);
        createCalendar();
    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) calendar.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }


    //Creates calendar visualization based on month and year displayed
    private void createCalendar() throws FileNotFoundException {
        //Labels for navigation
        month.setText(MONTH_NAME[this.monthDisplayed]);
        year.setText(String.valueOf(this.yearDisplayed));

        GregorianCalendar date = new GregorianCalendar(yearDisplayed, monthDisplayed, 1);
        date.add(Calendar.DATE, - (date.get(Calendar.DAY_OF_WEEK) - 1));

        days = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            days.add(new Day((JFXButton) calendar.getChildren().get(i), date));

            JFXButton button = days.get(i).getButton();

            if (days.get(i).buttonIsDisabled()) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }

            if (DateUtils.isToday(date)) {
                button.setStyle("-fx-text-fill: #168EE9;");
            }

            date.add(Calendar.DATE, 1);
        }

        GregorianCalendar today = new GregorianCalendar();
        Class<?> classDashboardController = DashboardController.class;
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
        try {
            createCalendar();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
