package C195PA.Controller;

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
import java.util.ResourceBundle;

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
    public ObservableList<Customer> allCustomers;
    public static Customer selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm zz")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        allCustomers = getAllCustomers();

        customerTable.setItems(allCustomers);
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
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

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void addAppointment(ActionEvent actionEvent) {
    }

    public void modifyAppointment(ActionEvent actionEvent) {
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }
}
