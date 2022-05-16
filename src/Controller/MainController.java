package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label dateLabel;
    public Label timeLabel;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;

    public ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Locale.setDefault(new Locale("en"));
        rb = ResourceBundle.getBundle("C195", Locale.getDefault());

    //TODO CITE: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm zz")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


    }

    public void login(){
        //System.out.println(timeLabel.getText().toString());

        System.out.println(rb.getString("hello"));
        System.out.println();
    }
}
