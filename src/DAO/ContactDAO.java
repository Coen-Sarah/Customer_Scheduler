package DAO;

import Model.Contact;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.Query.getResult;
import static DAO.Query.makeQuery;

public class ContactDAO {

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
