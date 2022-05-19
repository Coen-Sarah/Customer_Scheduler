package C195PA.Model;
/**
 * Provides ability to store and generate reports
 * */
public class ReportItem {
    /**
     * The month an appointment happened
     * */
    String month;
    /**
     * The type of appointment that happened
     * */
    String appointmentType;
    /**
     * The number of times an appointment type happened per month
     * */
    int count;


    /**
     * Constructor that instantiate a ReportItem object
     * */
    public ReportItem(String month, String appointmentType, int count) {
        this.month = month;
        this.appointmentType = appointmentType;
        this.count = count;
    }

    /**
     * @return month the month
     * */
    public String getMonth() {
        return month;
    }

    /**
     * @param month sets the month
     * */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return appointmentType the type of appointment
     * */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @param appointmentType sets the appointmentType
     * */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * @return count the appointment count
     * */
    public int getCount() {
        return count;
    }
    /**
     * @param count sets the appointment count
     * */
    public void setCount(int count) {
        this.count = count;
    }
}
