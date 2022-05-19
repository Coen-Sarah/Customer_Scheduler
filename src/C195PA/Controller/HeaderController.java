package C195PA.Controller;

import C195PA.Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static C195PA.DAO.UserDAO.getUser;

public class HeaderController {

    public Label loggedInLabel;
    public Label timeLabel;
    public Timeline clock;
    static User currentUser;

    void generateHeader(){
        generateClock();
        generateHeaderLabels();
    }

    void generateClock(){
        //TODO CITE: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date
        //TODO LAMBA DOCUMENTAION FOR CLOCK
        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    void generateHeaderLabels(){
        //TODO REMOVE DUMMY USER STATEMENT
        if(currentUser == null){
            currentUser = getUser(1);
            //currentUser = getLoggedInUser();
        }

        loggedInLabel.setText("You are logged in as: " + currentUser.getUsername());
    }

}
