package PatientTracker;

import PatientTracker.CodeFiles.Patient;
import PatientTracker.CodeFiles.PatientDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/logInScreen.fxml"));
        primaryStage.setTitle("Software 2 Final");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
