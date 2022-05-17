import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static DAO.CustomerDAO.*;
import static DAO.DBConnection.makeConnection;
import static DAO.LocationsDAO.getCountries;
import static DAO.LocationsDAO.getDivisions;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
    //TODO SWAP BACK AFTER TESTING

        //Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/View/main.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            makeConnection();


        }catch(Exception ex){
            ex.printStackTrace();
        }

        launch(args);
        Platform.exit();
    }


}
