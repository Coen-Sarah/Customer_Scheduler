package C195PA.DAO;

import C195PA.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static C195PA.DAO.LocationsDAO.getCountry;
import static C195PA.DAO.LocationsDAO.getDivision;
import static C195PA.DAO.Query.getResult;
import static C195PA.DAO.Query.makeQuery;

/**
 * Provides creation of Customer objects via access to the database
 * */
public class CustomerDAO {

    /**
     * @param customerId the customerId of a specific customer within the database
     * @return customer a specific customer object generated from the database information
     * */
    public static Customer getCustomer(int customerId){
        Customer customer;

        try {
            String getCustomerString =
                    "SELECT Customer_ID, Customer_Name, Address, Postal_code, Phone, first_level_divisions.division AS Division, countries.country AS Country FROM customers \n" +
                            "Join first_level_divisions ON first_level_divisions.division_id = customers.division_id\n" +
                            "join countries ON countries.country_id = first_level_divisions.country_id\n" +
                            "WHERE Customer_Id = \""+ customerId +"\";";
            makeQuery(getCustomerString);
            ResultSet customerResults = getResult();

            customerResults.next();

            customer = new Customer(
                    customerResults.getInt("Customer_Id"),
                    customerResults.getString("Customer_name"),
                    customerResults.getString("Address"),
                    customerResults.getString("Postal_Code"),
                    customerResults.getString("Phone"),
                    getDivision(customerResults.getInt("Division")),
                    getCountry(customerResults.getInt("Country")));
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @return allCustomers - an ObservableList containing all customer objects
     * */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Customer customer;

        try {
            String getCustomerString =
                    "SELECT Customer_ID, Customer_Name, Address, Postal_code, Phone, customers.division_id AS Division, countries.country_id AS Country FROM customers \n" +
                            "Join first_level_divisions ON first_level_divisions.division_id = customers.division_id\n" +
                            "join countries ON countries.country_id = first_level_divisions.country_id;";
            makeQuery(getCustomerString);
            ResultSet customerResults = getResult();

            while (customerResults.next()) {
                customer = new Customer(
                        customerResults.getInt("Customer_Id"),
                        customerResults.getString("Customer_name"),
                        customerResults.getString("Address"),
                        customerResults.getString("Postal_Code"),
                        customerResults.getString("Phone"),
                        getDivision(customerResults.getInt("Division")),
                        getCountry(customerResults.getInt("Country")));
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
    /**
     * Queries the database to create a customer
     * */
    public static void createCustomer(Customer customer){
        String createCustomerQuery =
                "INSERT INTO customers(" +
                "customer_name," +
                "address," +
                "postal_code," +
                "phone," +
                "division_id)" +
        "VALUES (" +
                "\""+ customer.getName() +
                "\", \""+ customer.getAddress() +
                "\",\""+ customer.getPostalCode() +
                "\",\""+ customer.getPhone() +
                "\","+ customer.getDivision().getDivisionId() +
                ");";

        makeQuery(createCustomerQuery);

    }
    /**
     * Query's the database to update a customer
     * */
    public static void updateCustomer(Customer customer){
        String updateCustomerQuery =
                "UPDATE Customers SET " +
                "customer_name = \""+customer.getName()+ "\",\n" +
                "address = \""+customer.getAddress()+ "\",\n" +
                "postal_Code = \""+customer.getPostalCode()+ "\",\n" +
                "phone = \"" + customer.getPhone()+ "\",\n" +
                "division_id = " + customer.getDivision().getDivisionId() + " " +
                "WHERE customer_id = " + customer.getCustomerId() +";";
        System.out.println(updateCustomerQuery);
        makeQuery(updateCustomerQuery);
    }


    /**
     * Queries the database to delete a customer
     * */
    public static void destroyCustomer(Customer customer){
        String deleteCustomerQuery = "DELETE FROM Customers\n" +
                "WHERE customer_id ="+ customer.getCustomerId();
        makeQuery(deleteCustomerQuery);
    }
}
