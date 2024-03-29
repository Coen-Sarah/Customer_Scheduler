package C195PA.Controller;

import C195PA.Model.Appointment;
import C195PA.Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195PA.DAO.AppointmentDAO.*;
import static C195PA.DAO.ContactDAO.getAllContacts;
import static C195PA.DAO.UserDAO.getAllUsers;

/**
 * Provides the shared methods between the Add and Update appointment pages.
 * */
public class AppointmentController extends ApplicationController implements Initializable {

    public TextField appointmentId ;
    public TextField appointmentTitle ;
    public TextField appointmentDescription ;
    public TextField appointmentLocation ;
    public TextField appointmentType ;
    public TextField customerId;
    public TextField userId;
    public ComboBox contactComboBox;

    public DatePicker appointmentStartDate;
    public ComboBox appointmentStartTimeHr;
    public ComboBox appointmentStartTimeMin;
    public ComboBox appointmentStartTimeP;

    public DatePicker appointmentEndDate;
    public ComboBox appointmentEndTimeHr;
    public ComboBox appointmentEndTimeMin;
    public ComboBox appointmentEndTimeP;

    public Button saveButton;
    public Button cancelButton;
    Appointment appointment;

    /**
     * Initializes the appointment pages with the needed information and calls to the ApplicationController method
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateHeader();
        appointmentId.setText("Auto-Generated");
        appointmentId.setDisable(true);
        setComboBoxes();

    }

    /**
     * Initializes the contact, hours, minutes and (am/pm) comboBoxes;
     * */
    private void setComboBoxes() {
        int hourMax = 12;
        int minMax = 60;

        for(int i = 0; i < hourMax; i++){
            appointmentStartTimeHr.getItems().add(i+1);
            appointmentEndTimeHr.getItems().add(i+1);
        }
        for(int i = 0; i <= minMax; i++){
            appointmentStartTimeMin.getItems().add(i);
            appointmentEndTimeMin.getItems().add(i);
        }
        appointmentStartTimeP.getItems().add("AM");
        appointmentStartTimeP.getItems().add("PM");

        appointmentEndTimeP.getItems().add("AM");
        appointmentEndTimeP.getItems().add("PM");

        appointmentStartTimeHr.getSelectionModel().select(8);
        appointmentStartTimeMin.getSelectionModel().select(0);
        appointmentStartTimeP.getSelectionModel().select(0);

        appointmentEndTimeHr.getSelectionModel().select(9);
        appointmentEndTimeMin.getSelectionModel().select(0);
        appointmentEndTimeP.getSelectionModel().select(0);

        appointmentStartDate.setValue(LocalDate.now());
        appointmentEndDate.setValue(LocalDate.now());

        contactComboBox.setItems(getAllContacts());

    }

    /**
     * Allows the user to return to the main page without saving
     * */
    public void cancelAppointment(ActionEvent event){
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel without saving?");
        Optional<ButtonType> userInput = cancelAlert.showAndWait();
        if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
            toMain(event);
        }

    }

    /**
     * Allows the user to save an appointment to the database
     * */
    public void saveAppointment(boolean newAppointment, ActionEvent event){
        if(checkTextFields()) {
            Pair<Boolean, Integer> overlap = checkAppointmentOverlap();
            if (!overlap.getKey()) {
                if (checkBusinessHours()) {
                    appointment = new Appointment(
                            0,
                            appointmentTitle.getText(),
                            appointmentDescription.getText(),
                            appointmentLocation.getText(),
                            appointmentType.getText(),
                            getStartTime(),
                            getEndTime(),
                            Integer.valueOf(customerId.getText()),
                            ((Contact) contactComboBox.getSelectionModel().getSelectedItem()).getContactId(),
                            Integer.valueOf(userId.getText())
                    );
                    if (newAppointment) {
                        createAppointment(appointment);
                    }
                     else {
                        appointment.setAppointmentId(Integer.parseInt(appointmentId.getText()));
                        System.out.println("Saving Appointment " + appointmentId.getText());
                        updateAppointment(appointment);
                    }
                        toMain(event);
                } else {
                    Alert notBusinessHoursAlert = new Alert(Alert.AlertType.ERROR,
                            "This appointment is scheduled for outside of business hours\n " +
                                    "Business hours are 8AM to 10PM EST, Monday through Friday\n" +
                                    "Please reschedule for a time that falls within those hours.");
                    notBusinessHoursAlert.show();
                }
            } else {
                Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps with: " +
                        allAppointments.get(overlap.getValue()).getTitle() + ", ID: " +
                        allAppointments.get(overlap.getValue()).getAppointmentId() + ".");
                overlapAlert.show();
            }
        }
    }

    /**
     * Ensures that all form fields are valid prior to saving
     * */
    private boolean checkTextFields() {
        boolean allValidTextFields = true;
        String allTextInput[] = new String[4];
        try {
            allTextInput = new String[]{
                    appointmentTitle.getText(),
                    appointmentDescription.getText(),
                    appointmentLocation.getText(),
                    appointmentType.getText()
            };
            int customerIdTemp = Integer.valueOf(customerId.getText());
            ((Contact) contactComboBox.getSelectionModel().getSelectedItem()).getContactId();
            int userIdTemp = Integer.valueOf(userId.getText());

            boolean validCustomerId = false;
            boolean validUserId = false;

            for(int i = 0; i < allCustomers.size(); i++){
                if(customerIdTemp == allCustomers.get(i).getCustomerId()){
                    validCustomerId = true;
                    break;
                }
            }

            for(int i = 0; i < getAllUsers().size(); i++){
                if(userIdTemp == getAllUsers().get(i).getUserId()){
                    validUserId = true;
                    break;
                }
            }

            if(!validCustomerId || !validUserId ){
                Alert invalidIdAlert = new Alert(Alert.AlertType.ERROR, "Please ensure that Customer ID and User ID are valid IDs.");
                invalidIdAlert.show();
                return false;
            }
        } catch (NumberFormatException numE) {
            Alert numberAlert = new Alert(Alert.AlertType.ERROR, "Please ensure that the Customer ID and User ID are numbers.");
            numberAlert.show();
            allValidTextFields = false;
            return allValidTextFields;
        } catch (NullPointerException nPointE){
            Alert noContactAlert = new Alert(Alert.AlertType.ERROR,"Please select a contact for the appointment.");
            noContactAlert.show();
            return allValidTextFields;
        }
        for(int i = 0; i < allTextInput.length; i++){
            if(allTextInput[i].isBlank()){
                allValidTextFields = false;
            }
        }

        if(!allValidTextFields){
            Alert emptyTextFields = new Alert(Alert.AlertType.ERROR,"Please fill out all fields.");
            emptyTextFields.show();
        }
        if(getStartTime().isAfter(getEndTime())){
            Alert invalidTimeAlert;
            if(getStartTime().isBefore(LocalDateTime.now()) || getEndTime().isBefore(LocalDateTime.now())) {
                invalidTimeAlert = new Alert(Alert.AlertType.ERROR, "Appointments can not be scheduled in the past.");
            }else{
                invalidTimeAlert = new Alert(Alert.AlertType.ERROR, "Appointment end times must be after appointment start times.");
                System.out.println(getStartTime() + " : " + getEndTime());
            }
            allValidTextFields = false;
            invalidTimeAlert.show();
        }
        return allValidTextFields;
    }

    /**
     * Checks if the appointment is scheduled for within business hours
     * */
    private boolean checkBusinessHours() {
        boolean isInBusinessHours = true;
        ZonedDateTime startESTTime = getStartTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endESTTime = getEndTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(22,0);

        if((startTime.isBefore(LocalTime.from(startESTTime)) || startTime.equals(LocalTime.from(startESTTime)))&& LocalTime.from(startESTTime).isBefore(endTime) &&
                startTime.isBefore(LocalTime.from(endESTTime)) && (LocalTime.from(endESTTime).isBefore(endTime) || LocalTime.from(endESTTime).equals(endTime)))
        {
            if((startESTTime.getDayOfWeek() == DayOfWeek.SATURDAY || startESTTime.getDayOfWeek() == DayOfWeek.SUNDAY) ||
                (endESTTime.getDayOfWeek() == DayOfWeek.SATURDAY || endESTTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                isInBusinessHours = false;
            }
        }else{
            isInBusinessHours = false;
        }
        return isInBusinessHours;

    }

    /**
     * Checks if the appointment overlaps with any other appointments
     * */
    private Pair<Boolean, Integer> checkAppointmentOverlap() {
        LocalDateTime startTime = getStartTime();
        LocalDateTime endTime = getEndTime();

        int currentAppointmentId = -1;
        try{
            currentAppointmentId = Integer.parseInt(appointmentId.getText());
        }catch(NumberFormatException numE){
            // Do nothing
        }

        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getCustomerId() == Integer.valueOf(customerId.getText()) &&
                allAppointments.get(i).getAppointmentId() != currentAppointmentId ){
                Appointment appointment = allAppointments.get(i);
                LocalDateTime appointmentStart = appointment.getStartTime().atZone(ZoneId.systemDefault())
                                                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
                LocalDateTime appointmentEnd = appointment.getEndTime().atZone(ZoneId.systemDefault())
                                                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
                boolean doAppointmentsOverlap = false;
                if(endTime.isBefore(appointmentStart) || startTime.isAfter(appointmentEnd))
                {
                    doAppointmentsOverlap = false;

                }else{
                    doAppointmentsOverlap = true;
                    return new Pair<Boolean,Integer>(doAppointmentsOverlap, i);
                }
            }
        }
        return new Pair<Boolean, Integer>(false,0);

    }

    /**
     * @return the start time the user input as a UTC LocalDateTime
     * */
    public LocalDateTime getStartTime(){
        int startHour = (int) appointmentStartTimeHr.getSelectionModel().getSelectedItem();
        int startMin = (int) appointmentStartTimeMin.getSelectionModel().getSelectedItem();
        int startP = appointmentStartTimeP.getSelectionModel().getSelectedIndex();
        System.out.println(startP);
        if(startP == 1){
            System.out.println("PM");
            startHour += 12;
        }
        LocalDateTime localDate = appointmentStartDate.getValue().atTime(startHour, startMin);
        return localDate;
    }

    /**
     * @return the end time the user input as a LocalDateTime
     * */
    public LocalDateTime getEndTime(){

        int endHour = (int) appointmentEndTimeHr.getSelectionModel().getSelectedItem();
        int endMin = (int) appointmentEndTimeMin.getSelectionModel().getSelectedItem();
        int endP = (int) appointmentEndTimeP.getSelectionModel().getSelectedIndex();
        if(endP == 1){
            System.out.println("PM");
            endHour += 12;
        }

        LocalDateTime localDate = appointmentEndDate.getValue().atTime(endHour, endMin);
        return localDate;
    }
}
