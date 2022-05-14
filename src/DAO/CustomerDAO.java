package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.Query.getResult;
import static DAO.Query.makeQuery;

public class CustomerDAO {

    public static Customer getCustomer(int customerId){
        Customer customer;

        try {
            String getCustomerString =
                    "SELECT Customer_ID, Customer_Name, Address, Postal_code,Phone FROM Customers " +
                            "WHERE Customer_Id = \""+ customerId +"\";";
            makeQuery(getCustomerString);
            ResultSet customerResults = getResult();

            customerResults.next();
            customer = new Customer(
                    customerResults.getInt("Customer_Id"),
                    customerResults.getString("Customer_name"),
                    customerResults.getString("Address"),
                    customerResults.getString("Postal_Code"),
                    customerResults.getString("Phone"));
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Customer customer;

        try {
            String getCustomerString =
                    "SELECT Customer_ID, Customer_Name, Address, Postal_code,Phone FROM Customers;";
            makeQuery(getCustomerString);
            ResultSet customerResults = getResult();

            while (customerResults.next()) {
                customer = new Customer(
                        customerResults.getInt("Customer_Id"),
                        customerResults.getString("Customer_name"),
                        customerResults.getString("Address"),
                        customerResults.getString("Postal_Code"),
                        customerResults.getString("Phone"));
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
    
}
