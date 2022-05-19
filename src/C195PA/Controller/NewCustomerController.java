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
