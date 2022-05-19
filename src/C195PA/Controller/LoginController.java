package C195PA.Controller;

import C195PA.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static C195PA.DAO.UserDAO.getUser;

/**
 * Generates and controls the Login scene
 * */
public class LoginController extends ApplicationController implements Initializable {

    public Label loginLabel;
    public Label usernameLabel;
    public TextField usernameField;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button loginButton;

    public ResourceBundle rb;

    /**
     * Initializes the login window and enables language localization
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateClock();
        rb = ResourceBundle.getBundle("C195PA", Locale.getDefault());

        loginLabel.setText(rb.getString("login"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));

    }

    /**
     * Allows users to login while verifying login data
     * */
    @FXML
    public void login(ActionEvent event){
        Alert invalidUserAlert = new Alert(Alert.AlertType.ERROR,rb.getString("invalidUserError"));
        Alert invalidPasswordAlert = new Alert(Alert.AlertType.ERROR,rb.getString("invalidPasswordError"));

        User input = getUser(usernameField.getText());
        if(input == null){
            invalidUserAlert.show();
            loginLogger(false);
        }else{
            String inputPassword = passwordField.getText();
            if(input.getPassword().equals(inputPassword)){
                loginLogger(true);
                currentUser = input;
                toMain(event);
            }else{
                invalidPasswordAlert.show();
                loginLogger(false);
            }
        }
    }

    /**
     * Adds login data to the loginAttempts report
     * */
    public void loginLogger(boolean loginSuccess){

        String loginAttempt = "User: " + usernameField.getText();
        if(loginSuccess){
            loginAttempt += " successfully logged in at ";
        }else{
            loginAttempt += " attempted to login at ";
        }
        loginAttempt += timeLabel.getText();

        System.out.println(loginAttempt);
        try{
            File file = new File("loginAttempts.txt");
            if (file.createNewFile()){}
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            out.println(loginAttempt);
            out.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
