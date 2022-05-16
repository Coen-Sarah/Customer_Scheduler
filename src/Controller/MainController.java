package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label timeLabel;

    public Timeline clock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm zz")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
