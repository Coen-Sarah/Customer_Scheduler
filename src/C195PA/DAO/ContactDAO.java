package C195PA.DAO;

import C195PA.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static C195PA.DAO.Query.getResult;
import static C195PA.DAO.Query.makeQuery;

/**
 * Provides creation of Contact objects via access to the database
 * */
public class ContactDAO {

    /**
     * @param contactId the contactId of a specific contact within the database
     * @return contact - a specific contact object generated from the database information
     * */
    public static Contact getContact(int contactId){

        Contact contact;

        try {
            String getContactString =
                    "SELECT contact_id, contact_name, email FROM contacts " +
                    "WHERE contact_id = \"" + contactId + "\";";
            makeQuery(getContactString);
            ResultSet contactResults = getResult();

            contactResults.next();
            contact = new Contact(
                    contactResults.getInt("contact_id"),
                    contactResults.getString("contact_name"),
                    contactResults.getString("email"));
            return contact;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @return allContacts - an ObservableList containing all contact objects
     * */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        Contact contact;

        try {
            String getContactString =
                    "SELECT contact_id, contact_name, email FROM contacts";
            makeQuery(getContactString);
            ResultSet contactResults = getResult();

            while (contactResults.next()) {
                contact = new Contact(
                        contactResults.getInt("contact_id"),
                        contactResults.getString("contact_name"),
                        contactResults.getString("email"));
                allContacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allContacts;
    }
}
