package dashboard;

public class AppointmentTime {
    private int timeCode;
    private String[] initialTime = {
            "8:00",
            "8:30",
            "9:00",
            "9:30",
            "10:00",
            "10:30",
            "11:00",
            "13:30",
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30"
    };
    private String[] finalTime = {
            "8:30",
            "9:00",
            "9:30",
            "10:00",
            "10:30",
            "11:00",
            "13:30",
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00"
    };

    public AppointmentTime(int timeCode) {
        this.timeCode = timeCode;
    }

    public String getInitialTime() {
        return initialTime[timeCode-1];
    }

    public String getFinalTime() {
        return finalTime[timeCode-1];
    }

    public int getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(int timeCode) {
        this.timeCode = timeCode;
    }
}
