
package PatientTracker.View_Controllers;
import PatientTracker.CodeFiles.Appointment;
import PatientTracker.CodeFiles.AppointmentDB;
import PatientTracker.CodeFiles.PatientDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;



public class mainScreenController implements Initializable {
    private static ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String resultText = "";
        appointments = AppointmentDB.appointmentsIn4Hours();
        if(appointments != null) {
            System.out.println(appointments);

            for (Appointment fourhrAppt: appointments
                 ) {


                       int aptTypeId = fourhrAppt.getAppointmentTypeId();
                       int patientId =fourhrAppt.getPatientId();
                       String patientName = PatientDB.getPatientName(patientId);
                       String appointmentType;

                if (aptTypeId == 1) {
                    appointmentType = "Mental Health Counseling";
                }
                else if (aptTypeId == 2)
                {
                    appointmentType = "Relationship Counseling";
                }
                else if (aptTypeId == 3)
                {
                    appointmentType = "Drug Counseling";
                }
                else if (aptTypeId ==4) {
                    appointmentType ="Family Counseling";
                }
                else
                    appointmentType = "error";

                System.out.println(patientName);



                String text = ("-> Appointment Type: " + appointmentType + " |Patient: " + patientName + " |Start Time: " + fourhrAppt.getAppointmentStart() + "\n \n");

                 resultText = text.concat(resultText);
                 System.out.println(resultText);

                    }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText("Appointments Within 4 Hours");
            alert.setContentText(resultText);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText("Appointments Within 4 Hours");
            alert.setContentText("no appointments in the next 4 hours!");
            alert.showAndWait();
        }

    }

@FXML

void logsBttn(ActionEvent event)
{
    try {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/logsScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.out.println("The Following Error Occurred on Patient Main Screen: " + e.getMessage());
    }



}

    @FXML
    void msAppointmentBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/appointmentMainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("The Following Error Occurred on Patient Main Screen: " + e.getMessage());
        }

    }

    @FXML
    void msExitBTTN(ActionEvent event) {
    System.exit(0);
    }


    @FXML
    void msPatientBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/patientMainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("The Following Error Occurred on Patient Main Screen: " + e.getMessage());
        }

    }

    @FXML
    void msReportsBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/reports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("The Following Error Occurred on Patient Main Screen: " + e.getMessage());
        }

    }

}
