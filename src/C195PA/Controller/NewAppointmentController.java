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
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        //TODO LAMBA DOCUMENTATION
        saveButton.setOnAction((ActionEvent event) ->{
            saveAppointment(true,event);
        });
    }


}
