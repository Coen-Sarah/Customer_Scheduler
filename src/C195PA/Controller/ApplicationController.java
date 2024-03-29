package C195PA.Controller;

import C195PA.Model.Appointment;
import C195PA.Model.Customer;
import C195PA.Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static C195PA.DAO.AppointmentDAO.getAllAppointments;
import static C195PA.DAO.CustomerDAO.getAllCustomers;

/**
 * Provides access to shared methods between all controller classes
 *
 * */
public class ApplicationController {

    public Label loggedInLabel;
    public Label timeLabel;
    public Timeline clock;
    static User currentUser;

    public ObservableList<Customer> allCustomers;
    public ObservableList<Appointment> allAppointments;

    /**
     * Fills the App Header with the header information and defines application wide variables
     * */
    void generateHeader(){
        generateClock();
        generateHeaderLabels();

        allCustomers = getAllCustomers();
        allAppointments = getAllAppointments();

    }

    /**
     * Adds the Clock to the header
     * The lambda expression within the generateClock method creates a live and updating clock to display in the header of the application.
     * The Timeline clock is constructed using two Keyframes, the first has the lamba expression.
     * It sets the variable e equal to timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ"))))
     * and allows e to be used as the String parameter within the KeyFrame that makes up the Timeline clock. The second Keyframe only includes a
     * duration of one second and by having the Timeline move through those two KeyFrames on a loop it creates a live clock.
     * */
    void generateClock(){
        //Clock code derived from Shekhar Rai's code at: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date
        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * Adds the "logged in as" information to the header
     * */
    void generateHeaderLabels(){
        loggedInLabel.setText("You are logged in as: " + currentUser.getUsername());
    }

    /**
     * Takes the user back to the main page.
     * */
    public void toMain(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
