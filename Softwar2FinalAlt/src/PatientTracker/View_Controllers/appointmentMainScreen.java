package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.Appointment;
import PatientTracker.CodeFiles.AppointmentDB;
import PatientTracker.CodeFiles.Patient;
import PatientTracker.CodeFiles.PatientDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class appointmentMainScreen implements Initializable {

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Integer> pIdCol;

    @FXML
    private TableColumn<Patient, String> patientNameCol;

    @FXML
    private TableColumn<Patient, String> patientInProviderCol;

    @FXML
    private RadioButton mnthRDBTTN;

    @FXML
    private RadioButton wkRDBTTN;

    @FXML
    private RadioButton biwklyRDBTTN;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, ?> aStCol;

    @FXML
    private TableColumn<Appointment, ?> aTCol;

    private Patient selectedPatient;
    private  Appointment selectedAppointment;


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        patientTable.setItems(PatientDB.getpDBAllPatients());

        pIdCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        patientInProviderCol.setCellValueFactory(new PropertyValueFactory<>("patientInsuranceProvider"));




        aStCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        aTCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));


    }

    @FXML
    public void handleCustomerClick(MouseEvent event) {
             appointmentTable.refresh();

        selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        int id = selectedPatient.getPatientId();
        if (mnthRDBTTN.isSelected())
            appointmentTable.setItems(AppointmentDB.getMonthlyCalendar(id));
       if (wkRDBTTN.isSelected())
            appointmentTable.setItems(AppointmentDB.getWeeklyCalendar(id));
       if (biwklyRDBTTN.isSelected())
           appointmentTable.setItems(AppointmentDB.getBiWeeklyCalendar(id));
    }

    @FXML
    void apAddBTTN(ActionEvent event) throws IOException {

       if (!patientTable.getSelectionModel().isEmpty()) {
           ((Node) (event.getSource())).getScene().getWindow().hide();
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/PatientTracker/Views/addAppointment.fxml"));
           loader.load();
           addAppointment AAController = loader.getController();
           AAController.aaSendPatient(patientTable.getSelectionModel().getSelectedItem());
           ((Node) (event.getSource())).getScene().getWindow();
           Stage stage = new Stage();
           Parent scene = loader.getRoot();
           stage.setScene(new Scene(scene));
           stage.show();
       }
       else
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText("appointmentMainScreen Error!");
               alert.setContentText("Please Select a Patient!");
               alert.showAndWait();
           }

    }

    @FXML
    void apBackBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/mainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("appointmentMainScreen Error: " + e.getMessage());
        }

    }

    @FXML
    void apDelBTTN(ActionEvent event) {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        //sets up Confirmation Alert to ensure delete is intentional
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Are You Sure You Want To Delete The Record For This Appointment: " + selectedAppointment.getAppointmentType() + "? ");
        alert.showAndWait().ifPresent((response -> {  // easy show and wait lamda to save lines of code.
            if(response == ButtonType.OK) {
                AppointmentDB.deleteAppointment(selectedAppointment.getAppointmentId());

            }
        }));

    }

    @FXML
    void apModBTTN(ActionEvent event) throws IOException {

        if (!appointmentTable.getSelectionModel().isEmpty()) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/PatientTracker/Views/modifyAppointment.fxml"));
            loader.load();
            modifyAppointment MAController = loader.getController();
            MAController.maSendAppointment(appointmentTable.getSelectionModel().getSelectedItem());
            ((Node) (event.getSource())).getScene().getWindow();
            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("appointmentMainScreen Error!");
            alert.setContentText("Please Select a Patient & Appointment to Modify!");
            alert.showAndWait();
        }

    }

}
