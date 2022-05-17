package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.CustomerDAO.createCustomer;
import static DAO.CustomerDAO.updateCustomer;
import static DAO.LocationsDAO.getCountries;
import static DAO.LocationsDAO.getDivisions;

public class CustomerController implements Initializable {

    public TextField customerId;
    public TextField customerName;
    public TextField customerAddress;
    public ComboBox customerCountry;
    public ComboBox customerDivision;
    public TextField customerPostalCode;
    public TextField customerPhone;

    public Button saveButton;
    public Button cancelButton;

    public ObservableList<Country> allCountries;
    public ObservableList<Division> allDivisions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allCountries = getCountries();
        customerCountry.setItems(allCountries);

        customerCountry.setOnAction( e -> {
            determineDivisions();
        });

    }

    void determineDivisions() {
        Country selectedCountry = (Country) customerCountry.getSelectionModel().getSelectedItem();
        allDivisions = getDivisions();
        FilteredList<Division> selectedDivisions = allDivisions.filtered(division -> ((division.getCountryId()) == (selectedCountry.getCountryId())));

        customerDivision.setItems(selectedDivisions);
    }

    public void toMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void cancelCustomer(ActionEvent event){
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
    public void saveCustomer(boolean newCustomer,ActionEvent event){

        Division division = (Division) customerDivision.getSelectionModel().getSelectedItem();
        Country country = (Country) customerCountry.getSelectionModel().getSelectedItem();
        Customer customer = new Customer(customerName.getText(),
                customerAddress.getText(),
                customerPostalCode.getText(),
                customerPhone.getText(),
                division,
                country);
        if(newCustomer) {
            createCustomer(customer);
            try{ toMain(event);}
            catch(IOException ioe){ioe.printStackTrace();}
        }else{
            System.out.println("Saving Customer");
            customer.setCustomerId(Integer.parseInt(customerId.getText()));
            updateCustomer(customer);
            try{ toMain(event);}
            catch(IOException ioe){ioe.printStackTrace();}
        }
    }
}
