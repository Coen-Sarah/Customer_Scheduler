package Controller;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MainController.getSelectedCustomer;

public class NewCustomerController extends CustomerController {

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
