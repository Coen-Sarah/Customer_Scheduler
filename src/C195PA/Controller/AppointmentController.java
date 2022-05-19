package C195PA.Controller;

import C195PA.Model.Appointment;
import C195PA.Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195PA.DAO.AppointmentDAO.createAppointment;
import static C195PA.DAO.AppointmentDAO.updateAppointment;
import static C195PA.DAO.ContactDAO.getAllContacts;

public class AppointmentController extends HeaderController implements Initializable {

    public Label loggedInLabel;
    public Label timeLabel;

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

    int hourMax = 12;
    int minMax = 60;

    final DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MM/DD/YYYY HH:mm ZZ");

    Appointment appointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateHeader();
        appointmentId.setText("Auto-Generated");
        appointmentId.setDisable(true);
        setTimeComboBoxes();

    }

    private void setTimeComboBoxes() {
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

    public void toMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void cancelAppointment(ActionEvent event){
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel without saving?");
        try {
            Optional<ButtonType> userInput = cancelAlert.showAndWait();
            if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
                toMain(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAppointment(boolean newAppointment, ActionEvent event){

        appointment = new Appointment(
                0,
                appointmentTitle.getText(),
                appointmentDescription.getText(),
                appointmentLocation.getText(),
                appointmentType.getText(),
                getStartTime(),
                getEndTime(),
                Integer.valueOf(customerId.getText()),
                ((Contact)contactComboBox.getSelectionModel().getSelectedItem()).getContactId(),
                Integer.valueOf(userId.getText())
        );
        if(newAppointment) {
            createAppointment(appointment);
            try{ toMain(event);}
            catch(IOException ioe){ioe.printStackTrace();}
        }else{
            appointment.setAppointmentId(Integer.parseInt(appointmentId.getText()));
            System.out.println("Saving Appointment " + appointmentId.getText() );
            updateAppointment(appointment);
            try{ toMain(event);}
            catch(IOException ioe){ioe.printStackTrace();}
        }
    }

    public LocalDateTime getStartTime(){
        int startHour = (int) appointmentStartTimeHr.getSelectionModel().getSelectedItem();
        int startMin = (int) appointmentStartTimeMin.getSelectionModel().getSelectedItem();
        int startP = appointmentStartTimeP.getSelectionModel().getSelectedIndex();
        if(startP == 1){
            System.out.println("PM");
            startHour += 12;
        }else if(startHour == 12){
            System.out.println("Midnight Local Time");
            startHour = 0;
        }
        LocalDateTime localDate = appointmentStartDate.getValue().atTime(startHour, startMin);
        ZonedDateTime localZoneTime = localDate.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStartTime = localZoneTime.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(localZoneTime.format(DateTimeFormatter.ofPattern("MM/dd/YY\nhh:mm a")));
        LocalDateTime startTime = utcStartTime.toLocalDateTime();
        return startTime;
    }

    public LocalDateTime getEndTime(){
        int endHour = (int) appointmentEndTimeHr.getSelectionModel().getSelectedItem();
        int endMin = (int) appointmentEndTimeMin.getSelectionModel().getSelectedItem();
        LocalDateTime localDate = appointmentEndDate.getValue().atTime(endHour, endMin);
        ZonedDateTime localZoneTime = localDate.atZone(ZoneId.systemDefault());
        ZonedDateTime utcEndTime = localZoneTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime endTime = utcEndTime.toLocalDateTime();
        return endTime;
    }
}
