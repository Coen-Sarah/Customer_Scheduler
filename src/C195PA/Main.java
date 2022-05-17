package C195PA;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static C195PA.DAO.DBConnection.makeConnection;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
    //TODO SWAP BACK AFTER TESTING

        //Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/C195PA/View/main.fxml"));

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
