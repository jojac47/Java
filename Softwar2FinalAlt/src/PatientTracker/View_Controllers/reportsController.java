package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;



public class reportsController implements Initializable {
    @FXML
    private TextArea TabOneTextArea;

    @FXML
    private TextArea TabTwoTextArea;

    @FXML
    private TextArea TabThreeTextArea;


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
            System.out.println("Patient Main Error: " + e.getMessage());
        }
    }


    public void ReportOne() {
        String resultsString = "";


        String query1 = " SELECT count(*) from appointment WHERE apt_type_id = 1 && year(start_datetime) = year(curdate()) ";
        String query2 = " SELECT count(*) from appointment WHERE apt_type_id = 2 && year(start_datetime) = year(curdate())";
        String query3 = " SELECT count(*) from appointment WHERE apt_type_id = 3 && year(start_datetime) = year(curdate())";
        String query4 = " SELECT count(*) from appointment WHERE apt_type_id = 4 && year(start_datetime) = year(curdate())";
        try {


            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query1);

            results.next();
            int count = results.getInt(1);
            String apttype1String = "Number of mental health appointments this year: "+count + "\n _______________________________________________________________ \n \n";
            resultsString = apttype1String.concat(resultsString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query2);

            results.next();
            int count = results.getInt(1);
            String apttype2String = "Number of relationship counselling appointments this year: "+count + "\n _______________________________________________________________ \n \n";
            resultsString = apttype2String.concat(resultsString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query3);

            results.next();
            int count = results.getInt(1);
            String apttype3String = "Number of drug counselling appointments this year: "+count + "\n _______________________________________________________________ \n \n";
            resultsString = apttype3String.concat(resultsString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query4);

            results.next();
            int count = results.getInt(1);
            String apttype4String = "Number of family counselling appointments this year: "+count + " \n _______________________________________________________________ \n \n";
            resultsString = apttype4String.concat(resultsString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        TabOneTextArea.setText(resultsString);

    }

    public void ReportTwo() {
        String resultsString = "";


        String query1 = " SELECT count(*) from appointment WHERE cr_id = 1 && year(start_datetime) = year(curdate()) ";

        try {


            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query1);

            results.next();
            int count = results.getInt(1);
            String appointments4Admin = "Number of appointments for admin this year: "+count + "\n _______________________________________________________________ \n \n";
            resultsString = appointments4Admin.concat(resultsString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        TabTwoTextArea.setText(resultsString);

    }


    public void ReportThree() {
        String resultsString = "";

        String query1 = "select address.state, count(*) FROM appointment join patient ON appointment.pt_id = patient.pt_id join address On patient.address_id = address.address_id WHERE year(start_datetime) = year(curdate()) Group By address.state  ";


        try {


            Statement statement = Database.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query1);

            while (results.next()) {
                String stateString = results.getString(1);
                int aptByStInt = results.getInt(2);
                String appointmentsByState = "Number of appointments in " + stateString + " this year: " + aptByStInt + "\n _______________________________________________________________ \n \n";
                resultsString = appointmentsByState.concat(resultsString);
            }
            } catch(SQLException ex){
                ex.printStackTrace();
            }
        
            
        TabThreeTextArea.setText(resultsString);
    }

    //populate Text Areas
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReportOne();
        ReportTwo();
        ReportThree();
    }

}
