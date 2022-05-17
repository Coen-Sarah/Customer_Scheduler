package C195PA.DAO;

import C195PA.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static C195PA.DAO.Query.getResult;
import static C195PA.DAO.Query.makeQuery;

/**
 * Provides creation of Appointment objects via access to the database
 * */
public class AppointmentDAO {

    /**
     * @param appointmentId the appointmentId of a specific appointment within the database
     * @return appointment - a specific appointment object generated from the database information
     * */
    public static Appointment getAppointment(int appointmentId){
        Appointment appointment;

        try {
            String getAppointmentString =
                    "SELECT appointment_id, title, description, location, type, start, end FROM appointments " +
                            "WHERE appointment_Id = \""+ appointmentId +"\";";
            makeQuery(getAppointmentString);
            ResultSet appointmentResults = getResult();

            appointmentResults.next();
            appointment = new Appointment(appointmentResults.getInt("appointment_Id"),
                    appointmentResults.getString("title"),
                    appointmentResults.getString("description"),
                    appointmentResults.getString("location"),
                    appointmentResults.getString("type"),
                    appointmentResults.getTimestamp("start").toLocalDateTime(),
                    appointmentResults.getTimestamp("end").toLocalDateTime());
            return appointment;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @return allAppointments - an ObservableList containing all appointment objects
     * */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String getAppointmentString =
                    "SELECT appointment_id, title, description, location, type, start, end FROM appointments";
            makeQuery(getAppointmentString);
            ResultSet appointmentResults = getResult();

            while (appointmentResults.next()) {
                appointment = new Appointment(appointmentResults.getInt("appointment_ID"),
                        appointmentResults.getString("title"),
                        appointmentResults.getString("description"),
                        appointmentResults.getString("location"),
                        appointmentResults.getString("type"),
                        appointmentResults.getTimestamp("start").toLocalDateTime(),
                        appointmentResults.getTimestamp("end").toLocalDateTime());
                allAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }
}

