package C195PA.Controller;

import C195PA.Model.Customer;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static C195PA.Controller.MainController.getSelectedCustomer;
import static C195PA.DAO.LocationsDAO.getCountries;
/**
 * Provides access to the Modify Customer page
 * */
public class ModifyCustomerController extends CustomerController {
    public Customer customer;

    /**
     * Initializes the Update Customer page and sets the saveButton action.
     * This method uses a lambda within the setOnAction method when it's applied to the saveButton. It allows for the
     * saveCustomer method from the CustomerController super class to be called with an additional boolean parameter
     * that states if an appointment is a new customer (true) or is an updated customer (false). This improves the the code
     * because the FXML onclick attribute can't be used on a method that has a parameter list other than (ActionEvent event).
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        customer = getSelectedCustomer();
        fillCustomerData();
        saveButton.setOnAction((ActionEvent event) ->{
            System.out.println(customerDivision.getSelectionModel().getSelectedItem() + " " +
                    customerCountry.getSelectionModel().getSelectedItem());
            saveCustomer(false,event);
        });
    }

    /**
     * Inserts the selected Customer's information into the update form
     * */
    private void fillCustomerData() {

        customerId.setText(String.valueOf(customer.getCustomerId()));
        customerName.setText(customer.getName());
        customerAddress.setText(customer.getAddress());
        customerCountry.setValue(customer.getCountry());
        customerDivision.setValue(customer.getDivision());
        customerPostalCode.setText(customer.getPostalCode());
        customerPhone.setText(customer.getPhone());

        customerId.setDisable(true);

        allCountries = getCountries();
        customerCountry.setItems(allCountries);
        determineDivisions();

    }
}
