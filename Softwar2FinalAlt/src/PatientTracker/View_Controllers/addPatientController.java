package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.PatientDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class addPatientController {
    private ObservableList<String> errors = FXCollections.observableArrayList();

    @FXML
    private TextField aPnTXT;

    @FXML
    private TextField aPpnTXT;

    @FXML
    private TextField aPsTXT;

    @FXML
    private TextField aPipTXT;

    @FXML
    void cancelBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/patientMainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Patient Main Error: " + e.getMessage());
        }

    }

    @FXML
        boolean saveBTTN(ActionEvent event) {
        errors.clear();
        String patientName = aPnTXT.getText();
        String patientPhone = aPpnTXT.getText();
        String patientState = aPsTXT.getText();
        String insuranceProvider = aPipTXT.getText();


        if(!validateName(patientName)||!validatePhone(patientPhone)||!validateState(patientState)||!validateInsuranceProvider(insuranceProvider)){
            return false;
        } else {

            if (PatientDB.savePatient(patientName, patientState, patientPhone,insuranceProvider))
                System.out.println(" Patient Successfully Added");
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/patientMainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("addAppointment Error: " + e.getMessage());
            }

                return true;
        }
    }

    public boolean validateName(String patientName) {
        if(patientName.isEmpty()) {
            errors.add("Name Field must have Characters");
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePhone(String patientPhone) {
        if(patientPhone.isEmpty()) {
            errors.add("Phone Number Field must have Characters");
            return false;
        } else {
            return true;
        }
    }
    public boolean validateState(String patientName) {
        if(patientName.isEmpty()) {
            errors.add("State Field must have Characters");
            return false;
        } else {
            return true;
        }
    }
    public boolean validateInsuranceProvider(String insuranceProvider) {
        if(insuranceProvider.isEmpty()) {
            errors.add("Insurance Provider Field must have Characters");
            return false;
        } else {
            return true;
        }
    }



}
