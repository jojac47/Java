package PatientTracker.View_Controllers;

        import PatientTracker.CodeFiles.Patient;
        import PatientTracker.CodeFiles.PatientDB;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;

        import javafx.stage.Stage;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import java.io.IOException;
        import javafx.scene.control.TextField;

        public class modifyPatientController {

    private ObservableList<String> errors = FXCollections.observableArrayList();
    private int id;

    @FXML
    private TextField mPnTXT;

    @FXML
    private TextField mPpnTXT;

    @FXML
    private TextField mPsTXT;

    @FXML
    private TextField mPipTXT;

    @FXML
    void mpCancelBTTN(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/patientMainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("modifyPatientController Error: " + e.getMessage());
        }
    }

    @FXML
    boolean mpSaveBTTN(ActionEvent event) {
        errors.clear();

        String patientName = mPnTXT.getText();
        String patientState = mPsTXT.getText();
        String patientPhoneNumber = mPpnTXT.getText();
        String insuranceProvider = mPipTXT.getText();
        if(!validateName(patientName)||!validatePhone(patientPhoneNumber)||!validateState(patientState)||!validateInsuranceProvider(insuranceProvider)){
            return false;
        }
        else {

            if(PatientDB.updatePatient(id, patientName, patientState, patientPhoneNumber,insuranceProvider));
            {
                System.out.println("Successfully Modified");
            }
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/patientMainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("modifyPatientController: " + e.getMessage());
            }

            return true;
        }

    }

    public void sendPatient(Patient patient)
    {
        mPnTXT.setText(String.valueOf(patient.getPatientName()));
        mPpnTXT.setText(String.valueOf(patient.getPatientPhoneNumber()));
        mPsTXT.setText(String.valueOf(patient.getPatientState()));
        mPipTXT.setText(String.valueOf(patient.getPatientInsuranceProvider()));
        id = patient.getPatientId();
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
