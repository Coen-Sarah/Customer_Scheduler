import Model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.JDBC;

import java.sql.ResultSet;

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


            System.out.println(getAppointment(1));
        }catch(Exception ex){

        }

        //launch(args);
        Platform.exit();
    }
}
