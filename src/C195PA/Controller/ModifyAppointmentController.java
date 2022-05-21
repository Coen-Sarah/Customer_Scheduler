package C195PA.Controller;

import C195PA.Model.Appointment;
import javafx.event.ActionEvent;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static C195PA.Controller.MainController.getSelectedAppointment;
import static C195PA.DAO.ContactDAO.getContact;

/**
 * Provides access to the Modify Appointments page
 * */
public class ModifyAppointmentController extends AppointmentController  {
    Appointment appointment;

    /**
     * Initializes the Update Appointment page and sets the saveButton action.
     * This method uses a lambda within the setOnAction method when it's applied to the saveButton. It allows for the
     * saveAppointment method from the AppointmentController super class to be called with an additional boolean parameter
     * that states if an appointment is a new appointment (true) or is an updated appointment (false). This improves the code
     * because the FXML onclick attribute can't be used on a method that has a parameter list other than (ActionEvent event).
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        appointment = getSelectedAppointment();
        fillAppointmentData();
        saveButton.setOnAction((ActionEvent event) ->{
            saveAppointment(false,event);
        });
    }

    /**
     * Inserts the selected Appointment's information into the update form
     * */
    private void fillAppointmentData() {
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        appointmentTitle.setText(appointment.getTitle());
        appointmentDescription.setText(appointment.getDescription());
        appointmentLocation.setText(appointment.getLocation());
        appointmentType.setText(appointment.getType());
        customerId.setText(String.valueOf(appointment.getCustomerId()));
        userId.setText(String.valueOf(appointment.getUserId()));

        LocalDateTime localStartTime = appointment.getStartTime();
        LocalDateTime localEndTime = appointment.getEndTime();
        int startHour = localStartTime.getHour();
        int endHour = localEndTime.getHour();

        if(startHour > 12){
            startHour -= 12;
            appointmentStartTimeP.getSelectionModel().select(1);
        }else if(startHour == 0) {
            startHour = 12;
            appointmentStartTimeP.getSelectionModel().select(0);
        } else {
            appointmentStartTimeP.getSelectionModel().select(0);
        }

        if(endHour > 12){
            endHour -= 12;
            appointmentEndTimeP.getSelectionModel().select(1);
        } else if(endHour == 0) {
            endHour = 12;
            appointmentEndTimeP.getSelectionModel().select(0);
        } else {
            appointmentEndTimeP.getSelectionModel().select(0);
        }

        appointmentStartDate.setValue(localStartTime.toLocalDate());
        appointmentStartTimeHr.getSelectionModel().select(startHour-1);
        appointmentStartTimeMin.getSelectionModel().select(appointment.getStartTime().getMinute());

        appointmentEndDate.setValue(localStartTime.toLocalDate());
        appointmentEndTimeHr.getSelectionModel().select(endHour-1);
        appointmentEndTimeMin.getSelectionModel().select(appointment.getEndTime().getMinute());

        contactComboBox.getSelectionModel().select(getContact(appointment.getContactId()));
    }

}
