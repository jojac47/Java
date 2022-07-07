package PatientTracker.View_Controllers;
import PatientTracker.CodeFiles.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class logInController implements Initializable {




        @FXML
        private Label lTITLELBL;

        @FXML
        private Label lLILBL;

        @FXML
        private Label lUNLBL;

        @FXML
        private TextField lUNTXT;

        @FXML
        private Label lPWLBL;

        @FXML
        private PasswordField  lPWTXT;

        @FXML
        private Label lPINLBL;

        @FXML
        private PasswordField  lPINTXT;

        @FXML
        private Label lSYSLBL;

        @FXML
        private TextField lSTTXT;

        @FXML
        private Label lMOTLBL;

        @FXML
        private TextField lMOTTXT;

        @FXML
        private Button lLIBTTN;
        @FXML
        private Label Message;

        @FXML
        private Label LanguageMessageLine;



        private String errorHeader;
        private String errorTitle;
        private String errorText;


        @Override
        public void initialize(URL url, ResourceBundle rb) {

                //display the time in local and ET
                lSTTXT.setText(Database.CreateLtTimeStamp());
                lMOTTXT.setText(Database.createEtTimeStamp());


                //Locale.setDefault(new Locale("es")); //for testing spanish
                //Locale.setDefault(new Locale("de")); //for testing german
                Locale locale = Locale.getDefault();
                rb = ResourceBundle.getBundle("PatientTracker/login_login", locale);
                lTITLELBL.setText(rb.getString("PatientTracker"));
                lLILBL.setText(rb.getString("LogInPlease"));
                lUNLBL.setText(rb.getString("username"));
                lPWLBL.setText(rb.getString("password"));
                lPINLBL.setText(rb.getString("pin"));
                lLIBTTN.setText(rb.getString("login"));
                lSYSLBL.setText(rb.getString("SystemTime"));
                lMOTLBL.setText(rb.getString("OfficeTime"));
                Message.setText(rb.getString("message"));
                LanguageMessageLine.setText(rb.getString("language"));
                errorHeader = rb.getString("errorheader");
                errorTitle = rb.getString("errortitle");
                errorText = rb.getString("errortext");
        }


        @FXML
        public void handleLogIn(ActionEvent event) throws IOException {
                String username = lUNTXT.getText();
                String password = lPWTXT.getText();
                String pin = lPINTXT.getText();
                boolean validUser = CounselorDB.login(username, password, pin);
                if(validUser) {
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/mainScreen.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(errorTitle);
                        alert.setHeaderText(errorHeader);
                        alert.setContentText(errorText);
                        alert.showAndWait();
                }
        }




}


