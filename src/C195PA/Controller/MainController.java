package C195PA.Controller;

import C195PA.Model.Appointment;
import C195PA.Model.Customer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195PA.DAO.AppointmentDAO.destroyAppointment;
import static C195PA.DAO.AppointmentDAO.getAllAppointments;
import static C195PA.DAO.CustomerDAO.destroyCustomer;
import static C195PA.DAO.CustomerDAO.getAllCustomers;

public class MainController implements Initializable {
    public Label timeLabel;

    public TableView customerTable;
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostalCode;
    public TableColumn customerPhone;



    public Button addCustomerButton;
    public Button modifyCustomerButton;

    public Timeline clock;

    public Label notificationsLabel;
    public Label loggedInLabel;
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

        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        allCustomers = getAllCustomers();
        allAppointments = getAllAppointments();

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

            Alert deleteCustomerAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete " +
                    selectedCustomer.getName() + "?");
            Optional<ButtonType> userInput = deleteCustomerAlert.showAndWait();
            if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
                destroyCustomer(selectedCustomer);
            }
            customerTable.setItems(getAllCustomers());

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a customer to delete.");
            alert.show();
        }

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


}
