package C195PA.Controller;

import C195PA.Model.Appointment;
import javafx.event.ActionEvent;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static C195PA.Controller.MainController.getSelectedAppointment;
import static C195PA.DAO.ContactDAO.getContact;

public class ModifyAppointmentController extends AppointmentController  {
    Appointment appointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        appointment = getSelectedAppointment();
        fillAppointmentData();
        saveButton.setOnAction((ActionEvent event) ->{
            saveAppointment(false,event);
        });
    }

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

        if(startHour >= 11){
            startHour -= 12;
            appointmentStartTimeP.getSelectionModel().select(1);
        }else if(startHour == 0) {
            startHour = 12;
            appointmentStartTimeP.getSelectionModel().select(0);
        } else {
            appointmentStartTimeP.getSelectionModel().select(0);
        }

        if(endHour >= 12){
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
