package C195PA.Controller;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Provides access to the New Customer page
 * */
public class NewCustomerController extends CustomerController {

    /**
     * Initializes the New Customer page and sets the saveButton action.
     * This method uses a lambda within the setOnAction method when it's applied to the saveButton. It allows for the
     * saveCustomer method from the CustomerController super class to be called with an additional boolean parameter
     * that states if an appointment is a new customer (true) or is an updated customer (false). This improves the code
     * because the FXML onclick attribute can't be used on a method that has a parameter list other than (ActionEvent event).
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        saveButton.setOnAction((ActionEvent event) ->{
            System.out.println(customerDivision.getSelectionModel().getSelectedItem() + " " +
                    customerCountry.getSelectionModel().getSelectedItem());
            saveCustomer(true,event);
        });
        super.customerId.setText("Auto-Generated");
        super.customerId.setDisable(true);
    }
}
