package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.AppointmentDB;
import PatientTracker.CodeFiles.Patient;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.sql.SQLException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.*;

public class addAppointment implements Initializable {

    @FXML
    private ComboBox<String> aaStCB;

    @FXML
    private DatePicker aaAdDP;

    @FXML
    private ComboBox<String> aaTCB;

    @FXML
    private TextField aaNTXT;
    @FXML
    private ComboBox<String> aaCoCB;


    private int id;
    private final ObservableList<String> counselorNames = FXCollections.observableArrayList("admin");
    private final ObservableList<String> times = FXCollections.observableArrayList("9:00 AM","10:00 AM","11:00 AM","12:00 PM","1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM","6:00 PM","7:00 PM","8:00 PM", "9:00 PM");
    private final ObservableList<String> types = FXCollections.observableArrayList("Mental Health Counseling","Relationship Counseling","Drug Counseling","Family Counseling");



    public void initialize(URL url, ResourceBundle rb)
    {
        aaStCB.setItems(times);
        aaTCB.setItems(types);
        aaCoCB.setItems(counselorNames);
        aaAdDP.setDayCellFactory(picker -> new DateCell() { //used lamda to set day cell factory for improved efficiency.

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                //disable holidays and off business hours from date picker and set color to red
                setDisable(
                        empty ||
                                ((date.getMonth()==Month.NOVEMBER)&&(date.getDayOfWeek()==DayOfWeek.THURSDAY)&&((date.getDayOfMonth()>21)&&(date.getDayOfMonth()<29)))||
                                ((date.getMonth()== Month.MAY)&&(date.getDayOfWeek()== DayOfWeek.MONDAY)&&(date.getDayOfMonth()>24))||
                                ((date.getMonth() == Month.JANUARY) && (date.getDayOfWeek() == DayOfWeek.MONDAY) && ((date.getDayOfMonth()> 14) && (date.getDayOfMonth()<22))) ||
                                ((date.getMonth() == Month.NOVEMBER)&&(date.getDayOfMonth()==11))||
                                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                                date.isBefore(LocalDate.now()));
              // change date picker to red for these days

               if(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        date.isBefore(LocalDate.now())||
                        ((date.getMonth() == Month.JANUARY) && (date.getDayOfWeek() == DayOfWeek.MONDAY) && ((date.getDayOfMonth()> 14) && (date.getDayOfMonth()<22)))||
                        ((date.getMonth()== Month.MAY)&&(date.getDayOfWeek()== DayOfWeek.MONDAY)&&(date.getDayOfMonth()>24))||
                        ((date.getMonth() == Month.NOVEMBER)&&(date.getDayOfMonth()==11))||
                        ((date.getMonth()==Month.NOVEMBER)&&(date.getDayOfWeek()==DayOfWeek.THURSDAY)&&((date.getDayOfMonth()>21)&&(date.getDayOfMonth()<29)))||
                        ((date.getMonth()==Month.NOVEMBER)&&(date.getDayOfWeek()==DayOfWeek.FRIDAY)&&((date.getDayOfMonth()>22)&&(date.getDayOfMonth()<30))) ) {
                    setStyle("-fx-background-color: #ffc4c4;");
                }
            }
        });

    }


    @FXML
    void aaCancelBTTN(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel Creating Appointment?");
        alert.setContentText("Are you sure you want to cancel appointment creation?");
        alert.showAndWait().ifPresent((response -> {  // easy show and wait lamda to save lines of code.
            if(response == ButtonType.OK) {
                try {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/appointmentMainScreen.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Error in AddAppointment: " + e.getMessage());
                }
            }
        }));

    }

    //ask Mark K if i can get rid of 9PM apt or if he recommends me going selected index value instead

    @FXML
    void aaSaveBTTN(ActionEvent event) throws SQLException, IOException {

        boolean error = false;
        int counselorName = aaCoCB.getSelectionModel().getSelectedIndex();
        int aptTypeId = aaTCB.getSelectionModel().getSelectedIndex() + 1;
        String aptNotes = aaNTXT.getText();
        int aptTime = aaStCB.getSelectionModel().getSelectedIndex();
        LocalDate ld = aaAdDP.getValue();
        int aptStIndex = aaStCB.getSelectionModel().getSelectedIndex();

        System.out.println(aptStIndex);

       String time = aaStCB.getValue();

        String date = aaAdDP.getValue().toString();


        String hour = time.split(":")[0];
        int tfhTime = Integer.parseInt(hour);

        if(tfhTime < 9 && aptStIndex!= 12) {
            tfhTime += 12;
        }
        else if (aptStIndex==12)
        {
            tfhTime = 21;

        }

        ZoneId zdt = ZoneId.systemDefault();
        ZoneId ezdt = ZoneId.of("America/New_York");

        String comboDTString = String.format("%s %02d:%s", date,+ tfhTime, "00");
        String CheckTimeString = String.format("%s %02d:%s", date,+ 9, "00");
        String CheckTimeStringClose = String.format("%s %02d:%s", date,+ 21, "00");
        DateTimeFormatter aapDf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");


        LocalDateTime rNow = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.parse(comboDTString, aapDf);
        LocalDateTime sofBusiness = LocalDateTime.parse(CheckTimeString,aapDf);
        LocalDateTime eofBusiness = LocalDateTime.parse(CheckTimeStringClose,aapDf);

        ZonedDateTime dNow = rNow.atZone(zdt); // right now in sys default
        ZonedDateTime etNow = ZonedDateTime.ofInstant(dNow.toInstant(),ezdt); // convert system time to ET
        ZonedDateTime etSOfBusiness = sofBusiness.atZone(ezdt);
        ZonedDateTime etEndOfBusiness = eofBusiness.atZone(ezdt);


        ZonedDateTime dStart = ldt.atZone(zdt);   // start time in sys default
        ZonedDateTime etStart = ZonedDateTime.ofInstant(dStart.toInstant(),ezdt); // start time in ET






        if (etStart.isBefore(etSOfBusiness)||etStart.isAfter(etEndOfBusiness))
        {
            errorWindow(9);
            error = true;

        }


        if (etStart.compareTo(etNow) < 0)
        {
            errorWindow(3);
            error = true;
        }



        if (aaStCB.getSelectionModel().isEmpty()) {
            errorWindow(3);
            error = true;

        }

        if (aaAdDP.getValue()==null) {
            errorWindow(5);
            error = true;

        }
        if (aaNTXT.getText().trim().isEmpty()) {
            errorWindow(1);
            error = true;

        }

        if (aaCoCB.getSelectionModel().isEmpty())
        {
            errorWindow(2);
            error = true;

        }

        if(aaTCB.getSelectionModel().isEmpty()) {
            errorWindow(7);
            error = true;

        }

        if(AppointmentDB.overlappingAppointment(-1,ld.toString(), times.get(aptTime),aptStIndex)) {
                errorWindow(8);
                error = true;
            }

        if (!error) {

                if (AppointmentDB.addAppointment(counselorNames.get(counselorName), id, aptTypeId, ld.toString(), times.get(aptTime), aptNotes,aptStIndex)) {
                    {
                        try {
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/appointmentMainScreen.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            System.out.println("Error In AddAppointment: " + e.getMessage());
                        }
                    }

                } else {
                    errorWindow(6);

                }
            }



    }


    public void aaSendPatient(Patient patient)
    {
        id = patient.getPatientId();
    }









    public void errorWindow(int code) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("addAppointment Error!");

        switch (code) {
            case 1: {
                alert.setContentText("Empty notes field, please add information!");
                alert.showAndWait();
                break;
            }

            case 2: {
                alert.setContentText("Please Make Sure a Counselor is Selected!");
                alert.showAndWait();
                break;

            }

            case 3: {
                alert.setContentText("Make Sure a Time is Selected and the Time Selected is not in The Past!");
                alert.showAndWait();
                break;

            }
            case 4: {
                alert.setContentText("Time selected is out of business hours. We are open 9am to 9pm Eastern Time!");
                alert.showAndWait();
                break;
            }

            case 5: {
                alert.setContentText("Make sure to select a date for your appointment!");
                alert.showAndWait();
                break;
            }
            case 6:
                {
                    alert.setContentText("SQL Error!");
                    alert.showAndWait();
                    break;
                }
            case 7: {
                alert.setContentText("Please select a type of appointment");
                alert.showAndWait();
                break;
            }
            case  8: {
                alert.setContentText("Time slot is either taken or in the past. Please be advised we do function on New York Time");
                alert.showAndWait();
                break;
            }
            case  9: {
                alert.setContentText("Scheduled Time is Out of Bounds. Our Main Office Operates on Eastern Time.");
                alert.showAndWait();
                break;
            }
        }

    }

}
