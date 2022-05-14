package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.Query.getResult;
import static DAO.Query.makeQuery;

public class AppointmentDAO {

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

