package C195PA.DAO;

import C195PA.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
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
                System.out.println(appointmentResults.getTimestamp("start").toLocalDateTime());
                allAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }
    /**
     * @param appointment the appointment object to save to the database
     * */
    public static void createAppointment(Appointment appointment){
        System.out.println("HERE --->>> " + appointment.getStartTime());
        String createAppointmentQuery =
                "INSERT INTO Appointments(" +
                        "title," +
                        "description," +
                        "location," +
                        "type," +
                        "start," +
                        "end," +
                        "contact_id," +
                        "customer_id," +
                        "user_id)" +
                        "VALUES (" +
                        "\""+ appointment.getTitle() +
                        "\", \""+ appointment.getDescription() +
                        "\",\""+ appointment.getLocation() +
                        "\",\""+ appointment.getType() +
                        "\",\""+ appointment.getStartTime() +
                        "\",\""+ appointment.getEndTime() +
                        "\",\""+ appointment.getContactId() +
                        "\",\""+ appointment.getCustomerId() +
                        "\","+ appointment.getUserId() +
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
                        "start = \"" + appointment.getStartTime()+ "\",\n" +
                        "end = \"" + appointment.getEndTime()+ "\",\n" +
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
        reportDeletedAppointment(appointment);
        String deleteAppointmentQuery = "DELETE FROM Appointments\n" +
                "WHERE appointment_id ="+ appointment.getAppointmentId();
        makeQuery(deleteAppointmentQuery);
    }

    /**
     * Saves relevant appointment data to a local file for use in report creation.
     * @param appointment appointment to be saved for report
     * */
    public static void reportDeletedAppointment(Appointment appointment){
        String appointmentDelete =
                "Appointment:: " + appointment.getTitle() + "::ID::" + appointment.getAppointmentId() +
                "::Description::" + appointment.getDescription() + " ::Start::" + appointment.getStartTime() +
                "::End::" + appointment.getEndTime() + "::Customer ID::" + appointment.getCustomerId() +
                "::Contact ID::" + appointment.getContactId() + "::User ID::" + appointment.getUserId() +"\n";
        try{
            File file = new File("canceledAppointments.txt");
            if (file.createNewFile()){}
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            out.println(appointmentDelete);
            out.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}

