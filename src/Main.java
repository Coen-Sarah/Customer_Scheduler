import Model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static DAO.AppointmentDAO.getAllAppointments;
import static DAO.AppointmentDAO.getAppointment;
import static DAO.ContactDAO.getAllContacts;
import static DAO.ContactDAO.getContact;
import static DAO.CustomerDAO.getAllCustomers;
import static DAO.DBConnection.makeConnection;
import static DAO.Query.getResult;
import static DAO.Query.makeQuery;
import static DAO.UserDAO.getAllUsers;
import static DAO.UserDAO.getUser;
import static java.time.ZoneId.getAvailableZoneIds;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            makeConnection();
            String format = "M / d / y  hh:mm a ";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            LocalDateTime testTime = getAppointment(1).getStartTime();
            ZonedDateTime zoneTestTime = testTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime myTestTime = zoneTestTime.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime easternTime = zoneTestTime.withZoneSameInstant(ZoneId.of("America/New_York"));

            System.out.println(getAvailableZoneIds());

            String zoneTimeFormatted = testTime.format(formatter);

            format ="M/d/y hh:mm a  ZZZZ";
            formatter = DateTimeFormatter.ofPattern(format);

            String testTimeFormatted = zoneTestTime.format(formatter);
            String myTimeFormatted = myTestTime.format(formatter);
            String easterTimeFormatted = easternTime.format(formatter);

            System.out.println(testTime);
            System.out.println(testTimeFormatted + "\n");
            System.out.println(zoneTestTime);
            System.out.println(zoneTimeFormatted + "\n");
            System.out.println(myTestTime);
            System.out.println(myTimeFormatted + "\n");
            System.out.println(easternTime);
            System.out.println(easterTimeFormatted);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        //launch(args);
        Platform.exit();
    }
}
