package PatientTracker.View_Controllers;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class logsController {

    @FXML
    void badLogInBttn(ActionEvent event) {

        File file = new File("logInFailure.txt");
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    System.out.println("The Following Error Occurred While Trying to Open the Log In Failure Log: " + e.getMessage());
                }
            }
        }
    }


    @FXML
    void goodLogInBttn(ActionEvent event) {


        File file = new File("logInSuccess.txt");
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    System.out.println("The Following Error Occurred While Trying to Open the Successful Log in Log: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void backBttn(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/mainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("logsController Error: " + e.getMessage());
        }

    }
}