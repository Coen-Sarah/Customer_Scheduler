package C195PA.DAO;

import C195PA.Model.Appointment;
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
                    "SELECT * FROM appointments " +
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
                    appointmentResults.getTimestamp("end").toLocalDateTime(),
                    appointmentResults.getInt("customer_id"),
                    appointmentResults.getInt("contact_id"),
                    appointmentResults.getInt("user_id"));
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
                    "SELECT * FROM appointments";
            makeQuery(getAppointmentString);
            ResultSet appointmentResults = getResult();

            while (appointmentResults.next()) {
                appointment = new Appointment(appointmentResults.getInt("appointment_ID"),
                        appointmentResults.getString("title"),
                        appointmentResults.getString("description"),
                        appointmentResults.getString("location"),
                        appointmentResults.getString("type"),
                        appointmentResults.getTimestamp("start").toLocalDateTime(),
                        appointmentResults.getTimestamp("end").toLocalDateTime(),
                        appointmentResults.getInt("customer_id"),
                        appointmentResults.getInt("contact_id"),
                        appointmentResults.getInt("user_id"));

                allAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }
    public static void createAppointment(Appointment Appointment){
        String createAppointmentQuery =
                "INSERT INTO Appointments(" +
                        "Appointment_name," +
                        "address," +
                        "postal_code," +
                        "phone," +
                        "division_id)" +
                        "VALUES (" +
                        "\""+ Appointment.getTitle() +
                        "\", \""+ Appointment.getDescription() +
                        "\",\""+ Appointment.getLocation() +
                        "\",\""+ Appointment.getType() +
                        "\",\""+ Appointment.getStartTime() +
                        "\",\""+ Appointment.getEndTime() +
                        "\",\""+ Appointment.getContactId() +
                        "\",\""+ Appointment.getCustomerId() +
                        "\","+ Appointment.getUserId() +
                        ");";

        makeQuery(createAppointmentQuery);

    }
    /**
     * Query's the database to update a appointment
     * */
    public static void updateAppointment(Appointment appointment){
        String updateAppointmentQuery =
                "UPDATE Appointments SET " +
                        "title = \""+appointment.getTitle()+ "\",\n" +
                        "description = \""+appointment.getDescription()+ "\",\n" +
                        "location = \""+appointment.getLocation()+ "\",\n" +
                        "type = \"" + appointment.getType()+ "\",\n" +
                        "type = \"" + appointment.getStartTime()+ "\",\n" +
                        "type = \"" + appointment.getStartTime()+ "\",\n" +
                        "contact_id = \"" + appointment.getContactId()+ "\",\n" +
                        "user_id = \"" + appointment.getUserId()+ "\",\n" +
                        "customer_id = " + appointment.getCustomerId()+ " " +
                        "WHERE Appointment_id = " + appointment.getAppointmentId() +";";
        System.out.println(updateAppointmentQuery);
        makeQuery(updateAppointmentQuery);
    }


    /**
     * Queries the database to delete a Appointment
     * */
    public static void destroyAppointment(Appointment appointment){
        String deleteAppointmentQuery = "DELETE FROM Appointments\n" +
                "WHERE appointment_id ="+ appointment.getAppointmentId();
        makeQuery(deleteAppointmentQuery);
    }
}

