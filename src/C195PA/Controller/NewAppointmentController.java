package C195PA.Controller;

import C195PA.Model.Appointment;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Provides access to the New Appointments page
 * */
public class NewAppointmentController extends AppointmentController  {

    Appointment appointment;

    /**
     * Initializes the New Appointment page and sets the saveButton action.
     * This method uses a lambda within the setOnAction method when it's applied to the saveButton. It allows for the
     * saveAppointment method from the AppointmentController super class to be called with an additional boolean parameter
     * that states if an appointment is a new appointment (true) or is an updated appointment (false). This improves the code
     * because the FXML onclick attribute can't be used on a method that has a parameter list other than (ActionEvent event).
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        saveButton.setOnAction((ActionEvent event) ->{
            saveAppointment(true,event);
        });
    }


}
