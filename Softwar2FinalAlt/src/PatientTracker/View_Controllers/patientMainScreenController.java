package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.Patient;
import PatientTracker.CodeFiles.PatientDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class patientMainScreenController implements Initializable {

    private Patient selectedPatient;

    @FXML
    private TableView<Patient> patientTblV;

    @FXML
    private TableColumn<Patient, Integer> pId;

    @FXML
    private TableColumn<Patient, String> pName;

    @FXML
    private TableColumn<Patient, String> pState;


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        patientTblV.setItems(PatientDB.getpDBAllPatients());

        pId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        pName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        pState.setCellValueFactory(new PropertyValueFactory<>("patientState"));
    }

@FXML
void addPatientBTTN(ActionEvent event){

    try {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/addPatient.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.out.println("patientMainScreenController Error: " + e.getMessage());
    }

}


    @FXML
    void ModifyPatient(ActionEvent event) throws IOException {
        if(patientTblV.getSelectionModel().getSelectedItem() != null) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/PatientTracker/Views/modifyPatient.fxml"));
            loader.load();
            modifyPatientController MDPController = loader.getController();
            MDPController.sendPatient(patientTblV.getSelectionModel().getSelectedItem());
            ((Node) (event.getSource())).getScene().getWindow();
            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Patient Modify Error!");
            alert.setContentText("Please Select A Patient To Modify!");
            alert.showAndWait();
        }
    }
    @FXML
    void pDeleteBTTN(ActionEvent event) {
        if(patientTblV.getSelectionModel().getSelectedItem() != null) {
            selectedPatient = patientTblV.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        //sets up Confirmation Alert to ensure delete is intentional
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Are You Sure You Want To Delete The Record For: " + selectedPatient.getPatientName() + " ? This will also delete all associated appointments");
        alert.showAndWait().ifPresent((response -> {  // easy show and wait lamda to save lines of code.
            if(response == ButtonType.OK) {
                PatientDB.deletePatient(selectedPatient.getPatientId());
                patientTblV.setItems(PatientDB.getpDBAllPatients());
            }
        }));
    }

    @FXML
    void pBackBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/mainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("patientMainScreenController Error: " + e.getMessage());
        }
    }







    }

