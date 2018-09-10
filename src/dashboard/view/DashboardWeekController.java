package dashboard.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import utils.ControllerUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardWeekController extends DashboardController implements Initializable {

    @FXML
    GridPane schedule;

    private boolean morning;

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

    public void displayHours() {
        String[] hours = (morning) ? morningHours : afternoonHours;

        for (int i = 0; i < 5; i++) {
            Text text = (Text) schedule.getChildren().get(i);
            text.setText(hours[i]);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        morning = true;
        displayHours();
    }

    public DashboardWeekController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super(userId);
    }
}
