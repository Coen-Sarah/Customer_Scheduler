package C195PA.Controller;

import C195PA.Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static C195PA.DAO.UserDAO.getUser;

/**
 * Generates and controls the Login scene
 * */
public class LoginController implements Initializable {

    public Label timeLabel;
    public Label loginLabel;
    public Label usernameLabel;
    public TextField usernameField;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button loginButton;

    public ResourceBundle rb;
    static Timeline clock;

    /**
     * Initializes the login window and enables language localization
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rb = ResourceBundle.getBundle("C195PA", Locale.getDefault());

        loginLabel.setText(rb.getString("login"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));

    //TODO CITE: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date
        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm zz")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


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
                toMainPage(event);
            }else{
                invalidPasswordAlert.show();
                loginLogger(false);
            }
        }

    }

    private void toMainPage(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/main.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
