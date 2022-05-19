package C195PA.Controller;

import C195PA.Model.Country;
import C195PA.Model.Customer;
import C195PA.Model.Division;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195PA.DAO.CustomerDAO.createCustomer;
import static C195PA.DAO.CustomerDAO.updateCustomer;
import static C195PA.DAO.LocationsDAO.getCountries;
import static C195PA.DAO.LocationsDAO.getDivisions;

/**
 * Provides shared methods between the Add and Update customer pages
 * */
public class CustomerController extends ApplicationController implements Initializable {

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

    /**
     * Initializes the customer pages with then needed information
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateHeader();
        allCountries = getCountries();
        customerCountry.setItems(allCountries);

        customerCountry.setOnAction( e -> {
            determineDivisions();
        });

    }

    /**
     * Determines which divisions should be visible upon a user selecting a country
     * */
    void determineDivisions() {
        Country selectedCountry = (Country) customerCountry.getSelectionModel().getSelectedItem();
        allDivisions = getDivisions();
        FilteredList<Division> selectedDivisions = allDivisions.filtered(division -> ((division.getCountryId()) == (selectedCountry.getCountryId())));

        customerDivision.setItems(selectedDivisions);
    }

    /**
     * Allows the user to return to the main page without saving
     * */
    public void cancelCustomer(ActionEvent event){
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel without saving?");

        Optional<ButtonType> userInput = cancelAlert.showAndWait();
        if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
            toMain(event);
        }
    }
    /**
     * Allows the user to save the customer to the database
     * */
    public void saveCustomer(boolean newCustomer,ActionEvent event) {
        if (allValidTextFields()) {
            Division division = (Division) customerDivision.getSelectionModel().getSelectedItem();
            Country country = (Country) customerCountry.getSelectionModel().getSelectedItem();
            Customer customer = new Customer(customerName.getText(),
                    customerAddress.getText(),
                    customerPostalCode.getText(),
                    customerPhone.getText(),
                    division,
                    country);
            if (newCustomer) {
                createCustomer(customer);
                toMain(event);
            } else {
                System.out.println("Saving Customer");
                customer.setCustomerId(Integer.parseInt(customerId.getText()));
                updateCustomer(customer);
                toMain(event);
            }
        }
    }
    /**
     * Ensures that all of the form fields have valid information prior to saving
     * */
    private boolean allValidTextFields() {
        boolean allValidTextFields = true;
        String allTextInput[] = new String[4];
        try {
            allTextInput = new String[]{
                    customerName.getText(),
                    customerAddress.getText(),
                    customerPostalCode.getText(),
                    customerPhone.getText()
            };
            int country = ((Country) customerCountry.getSelectionModel().getSelectedItem()).getCountryId();
            int division = ((Division) customerDivision.getSelectionModel().getSelectedItem()).getDivisionId();
        } catch (NullPointerException nPointE){
            Alert missingLocationAlert = new Alert(Alert.AlertType.ERROR,"Please select both a country and a division.");
            allValidTextFields = false;
            missingLocationAlert.show();
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
        return allValidTextFields;
    }
}
