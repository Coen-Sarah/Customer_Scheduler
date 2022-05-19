package C195PA;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static C195PA.DAO.DBConnection.closeConnection;
import static C195PA.DAO.DBConnection.makeConnection;

public class Main extends Application {


    /**
     * Launches the login page;
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/login.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Connects to the database and launches the entire application;
     * */
    public static void main(String[] args) {

        try {
            makeConnection();
            launch(args);
            closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        Platform.exit();
    }



}
