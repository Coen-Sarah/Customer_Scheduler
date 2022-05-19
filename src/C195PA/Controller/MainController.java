package C195PA.Controller;

import C195PA.Model.Appointment;
import C195PA.Model.Customer;
import C195PA.Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195PA.Controller.LoginController.getLoggedInUser;
import static C195PA.DAO.AppointmentDAO.destroyAppointment;
import static C195PA.DAO.AppointmentDAO.getAllAppointments;
import static C195PA.DAO.CustomerDAO.destroyCustomer;
import static C195PA.DAO.CustomerDAO.getAllCustomers;
import static C195PA.DAO.UserDAO.getUser;

public class MainController extends HeaderController implements Initializable {

    public TableView customerTable;
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostalCode;
    public TableColumn customerPhone;

    public Button reportButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;

    public Label notificationsLabel;
    public TableView appointmentTable;
    public TableColumn appointmentId;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDescription;
    public TableColumn appointmentContact;
    public TableColumn appointmentStart;
    public TableColumn appointmentEnd;

    public ObservableList<Appointment> allAppointments;
    public ObservableList<Customer> allCustomers;
    public static Customer selectedCustomer;
    public static Appointment selectedAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateHeader();
        allCustomers = getAllCustomers();
        allAppointments = getAllAppointments();

        initializeTables();
        checkUpcomingAppointments();

    }

    private void initializeTables() {
        customerTable.setItems(allCustomers);
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        appointmentTable.setItems(allAppointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("fullDescription"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("inviteList"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("formattedStartTime"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("formattedEndTime"));
    }

    private void checkUpcomingAppointments() {
        String appointmentNotice = "";
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime upcomingTime = currentTime.plusMinutes(15);
        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getUserId() == currentUser.getUserId()) {
                LocalDateTime appointmentDateTime = allAppointments.get(i).getStartTime();
                if ((appointmentDateTime.isAfter(currentTime) || appointmentDateTime.isEqual(currentTime)) &&
                        (appointmentDateTime.isBefore(upcomingTime) || appointmentDateTime.isEqual(upcomingTime))) {
                    appointmentNotice += "Your appointment " + allAppointments.get(i).getTitle() + ", ID: " +
                            allAppointments.get(i).getAppointmentId() + ", starts at " +
                            allAppointments.get(i).getStartTime().format(DateTimeFormatter.ofPattern("MM/dd/YY hh:mm a"));
                }
            }
        }
        if(appointmentNotice.length() == 0){
            appointmentNotice += "You have no upcoming appointments at this time.";
        }
        notificationsLabel.setText(appointmentNotice);
    }

    public void addCustomer(ActionEvent event){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/new_customer.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyCustomer(ActionEvent event){
        try{
            selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            if(allCustomers.contains(selectedCustomer)) {
                Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/modify_customer.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a customer to modify.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(ActionEvent event) {

        selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if(allCustomers.contains(selectedCustomer)) {
            String deleteCustomer = "Are you sure you want to delete " +
                    selectedCustomer.getName() + "?\n";
            ObservableList<Appointment> customerAppointments = checkCustomerAppointments();

                if(customerAppointments.size() > 0){
                    deleteCustomer = "In order to delete " + selectedCustomer.getName() +
                            " all associated appointments must be deleted.\n" + selectedCustomer.getName() +
                            " currently has these appointments:\n";
                    for(int i = 0; i < customerAppointments.size(); i++){
                        deleteCustomer += customerAppointments.get(i).getTitle() + ", ID: " +
                                        customerAppointments.get(i).getAppointmentId() + ", on "+
                                        customerAppointments.get(i).getStartTime()
                                                        .format(DateTimeFormatter.ofPattern("MM/dd/YY hh:mm a")) + ".\n";
                    }
                    deleteCustomer += "Do you want to delete these appointments with " + selectedCustomer.getName();
                }

            Alert deleteCustomerAlert = new Alert(Alert.AlertType.CONFIRMATION,deleteCustomer);
            Optional<ButtonType> userInput = deleteCustomerAlert.showAndWait();
            if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
                deleteMultipleAppointments(customerAppointments);
                destroyCustomer(selectedCustomer);
            }
            customerTable.setItems(getAllCustomers());

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a customer to delete.");
            alert.show();
        }

    }

    private ObservableList<Appointment> checkCustomerAppointments() {
        ObservableList customerAppointments = FXCollections.observableArrayList();
        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getCustomerId() == selectedCustomer.getCustomerId()){
                customerAppointments.add(allAppointments.get(i));
            }
        }
        return customerAppointments;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
    }

    public void modifyAppointment(ActionEvent event) {
        try{
            selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            if(allAppointments.contains(selectedAppointment)) {
                Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/modify_appointment.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please select an appointment to modify.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/new_appointment.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteMultipleAppointments(ObservableList<Appointment> appointmentList){
            int listSize = appointmentList.size();
            for(int i = 0; i < listSize; i++){
                destroyAppointment(appointmentList.get(i));
            }
    }

    public void deleteAppointment(ActionEvent event) {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        if(allAppointments.contains(selectedAppointment)) {

            Alert deleteCustomerAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete " +
                    selectedAppointment.getTitle() + ", appointment ID: "+ selectedAppointment.getAppointmentId() +"?");
            Optional<ButtonType> userInput = deleteCustomerAlert.showAndWait();
            if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
                destroyAppointment(selectedAppointment);
            }
            appointmentTable.setItems(getAllAppointments());

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please select an appointment to delete.");
            alert.show();
        }
    }

    public void viewWeeksAppointments(ActionEvent event){
        ObservableList thisWeeksAppointments = FXCollections.observableArrayList();
            for(int i = 0; i < allAppointments.size();i++){
                LocalDate today = LocalDate.now();
                LocalDate endOfWeek = today.plusDays(7);
                LocalDate appointmentDate = allAppointments.get(i).getStartTime().toLocalDate();
                if((appointmentDate.isAfter(today) || appointmentDate.isEqual(today))&&
                        (appointmentDate.isBefore(endOfWeek) || appointmentDate.isEqual(endOfWeek))){
                    thisWeeksAppointments.add(allAppointments.get(i));
                }
            }
        appointmentTable.setItems(thisWeeksAppointments);
    }

    public void viewMonthsAppointments(ActionEvent event){
        ObservableList thisMonthsAppointments = FXCollections.observableArrayList();
        for(int i = 0; i < allAppointments.size();i++){
            LocalDate today = LocalDate.now();
            LocalDate appointmentDate = allAppointments.get(i).getStartTime().toLocalDate();
            if((appointmentDate.isAfter(today ) && appointmentDate.getMonth() == today.getMonth())){
                thisMonthsAppointments.add(allAppointments.get(i));
            }
        }
        appointmentTable.setItems(thisMonthsAppointments);
    }

    public void viewAllAppointments(ActionEvent event){
        appointmentTable.setItems(allAppointments);
    }

    public void toReport(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/report.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch( IOException ioe){
            ioe.printStackTrace();
        }
    }
}
