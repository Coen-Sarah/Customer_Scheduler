package C195PA.DAO;

public class ReportItem {
    String month;
    String appointmentType;
    int count;

    public ReportItem(String month, String appointmentType, int count) {
        this.month = month;
        this.appointmentType = appointmentType;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
