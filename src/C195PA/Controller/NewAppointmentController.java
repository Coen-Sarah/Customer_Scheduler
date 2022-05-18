package C195PA.Controller;

import C195PA.Model.Appointment;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static C195PA.Controller.MainController.getSelectedAppointment;

public class NewAppointmentController extends AppointmentController  {

    Appointment appointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        saveButton.setOnAction((ActionEvent event) ->{
            saveAppointment(false,event);
        });
    }


}
