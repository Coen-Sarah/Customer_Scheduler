package Controller;

import Model.Customer;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MainController.getSelectedCustomer;
import static DAO.LocationsDAO.getCountries;

public class ModifyCustomerController extends CustomerController {
    public Customer customer;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = getSelectedCustomer();
        fillCustomerData();
        saveButton.setOnAction((ActionEvent event) ->{
            System.out.println(customerDivision.getSelectionModel().getSelectedItem() + " " +
                    customerCountry.getSelectionModel().getSelectedItem());
            saveCustomer(false,event);
        });
    }

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
