package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.Query.getResult;
import static DAO.Query.makeQuery;

public class UserDAO {

    public static User getUser(int userId){
        User user;

        try {
            String getUserString =
                    "SELECT user_Id, user_Name, password FROM users " +
                    "WHERE user_Id = \""+ userId +"\";";
            makeQuery(getUserString);
            ResultSet userResults = getResult();

            userResults.next();
            user = new User(userResults.getInt("user_Id"),
                    userResults.getString("user_name"),
                    userResults.getString("password"));
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        User user;

        try {
            String getUserString =
                    "SELECT user_ID, user_Name, password FROM users";
            makeQuery(getUserString);
            ResultSet userResults = getResult();

            while (userResults.next()) {
                user = new User(userResults.getInt("user_Id"),
                                userResults.getString("user_name"),
                                userResults.getString("password"));
                allUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

}
