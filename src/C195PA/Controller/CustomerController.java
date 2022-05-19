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

public class CustomerController extends HeaderController implements Initializable {

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
        generateHeader();
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
        Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/main.fxml"));
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
