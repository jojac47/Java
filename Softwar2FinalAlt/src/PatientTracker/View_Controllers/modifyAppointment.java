package PatientTracker.View_Controllers;

import PatientTracker.CodeFiles.Appointment;
import PatientTracker.CodeFiles.AppointmentDB;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;



public class modifyAppointment implements Initializable {

    @FXML
    private ComboBox<String> maStCB;

    @FXML
    private DatePicker maAdDP;

    @FXML
    private ComboBox<String> maCCB;

    @FXML
    private ComboBox<String> maTCB;

    @FXML
    private TextField maNTXT;

    private final ObservableList<String> counselorNames = FXCollections.observableArrayList("admin");
    private final ObservableList<String> times = FXCollections.observableArrayList("9:00 AM","10:00 AM","11:00 AM","12:00 PM","1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM","6:00 PM","7:00 PM","8:00 PM", "9:00 PM");
    private final ObservableList<String> types = FXCollections.observableArrayList("Mental Health Counseling","Relationship Counseling","Drug Counseling","Family Counseling");

    private int aptid;
    public void initialize(URL url, ResourceBundle rb)
    {
        maStCB.setItems(times);
        maTCB.setItems(types);
        maCCB.setItems(counselorNames);
        maAdDP.setDayCellFactory(picker -> new DateCell() { //used lamda to set day cell factory for improved efficiency.
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                //disable holidays and off business hours from date picker and set color to red
                setDisable(
                        empty ||
                                ((date.getMonth()== Month.NOVEMBER)&&(date.getDayOfWeek()== DayOfWeek.THURSDAY)&&((date.getDayOfMonth()>21)&&(date.getDayOfMonth()<29)))||
                                ((date.getMonth()== Month.MAY)&&(date.getDayOfWeek()== DayOfWeek.MONDAY)&&(date.getDayOfMonth()>24))||
                                ((date.getMonth() == Month.JANUARY) && (date.getDayOfWeek() == DayOfWeek.MONDAY) && ((date.getDayOfMonth()> 14) && (date.getDayOfMonth()<22))) ||
                                ((date.getMonth() == Month.NOVEMBER)&&(date.getDayOfMonth()==11))||
                                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                                date.isBefore(LocalDate.now()));
                //set date picker to red for these days
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
    void maCancelBTTN(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel Updating Appointment?");
        alert.setContentText("Are you sure you want to cancel appointment Update?");
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
                    System.out.println("Patient Main Error: " + e.getMessage());
                }
            }
        }));

    }

    @FXML
    void maSaveBTTN(ActionEvent event) {
        boolean error = false;
        //saved just in case i need to add a 2nd counselor later
        int counselorName = maCCB.getSelectionModel().getSelectedIndex();
        int aptTypeId = maTCB.getSelectionModel().getSelectedIndex() + 1;
        String aptNotes = maNTXT.getText().trim();
        int aptTime = maStCB.getSelectionModel().getSelectedIndex();
        LocalDate ld = maAdDP.getValue();
      int aptStIndex = maStCB.getSelectionModel().getSelectedIndex();


        String time = maStCB.getValue();

        String date = maAdDP.getValue().toString();


        String hour = time.split(":")[0];
        int tfhTime = Integer.parseInt(hour);

        if(tfhTime < 9&&aptStIndex!=12) {
            tfhTime += 12;
        }
        else if (aptStIndex == 12)
        {
            tfhTime = 21;
        }


        ZoneId zdt = ZoneId.systemDefault();
        ZoneId ezdt = ZoneId.of("America/New_York");
        ZoneId utczdt = ZoneId.of("UTC");

        String comboDTString = String.format("%s %02d:%s", date,+ tfhTime, "00");
        String CheckTimeString = String.format("%s %02d:%s", date,+ 9, "00");
        String CheckTimeStringClose = String.format("%s %02d:%s", date,+ 21, "00");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");


        LocalDateTime rNow = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.parse(comboDTString, df);
        LocalDateTime sofBusiness = LocalDateTime.parse(CheckTimeString,df);
        LocalDateTime eofBusiness = LocalDateTime.parse(CheckTimeStringClose,df);

        ZonedDateTime dNow = rNow.atZone(zdt); // right now in sys default
        ZonedDateTime etNow = ZonedDateTime.ofInstant(dNow.toInstant(),ezdt); // convert system time to ET
        ZonedDateTime etSOfBusiness = sofBusiness.atZone(ezdt);
        ZonedDateTime etEndOfBusiness = eofBusiness.atZone(ezdt);
        ZonedDateTime localStartTime = ldt.atZone(zdt);


        ZonedDateTime dStart = ldt.atZone(zdt);   // start time in sys default
        ZonedDateTime etStart = ZonedDateTime.ofInstant(dStart.toInstant(),ezdt); // start time in ET
        ZonedDateTime UTCStartTime = ZonedDateTime.ofInstant(localStartTime.toInstant(),utczdt);




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



        if (maStCB.getSelectionModel().isEmpty()) {
            errorWindow(3);
            error = true;

        }

        if (maAdDP.getValue()==null) {
            errorWindow(5);
            error = true;

        }
        if (maNTXT.getText().trim().isEmpty()) {
            errorWindow(1);
            error = true;

        }

        if (maCCB.getSelectionModel().isEmpty())
        {
            errorWindow(2);
            error = true;

        }

        if(maTCB.getSelectionModel().isEmpty()) {
            errorWindow(7);
            error = true;

        }

        if(AppointmentDB.overlappingAppointment(-1,ld.toString(), times.get(aptTime),aptStIndex)) {
            errorWindow(8);
            error = true;
        }

        if (!error) {

            if (AppointmentDB.updateAppointment( aptTypeId, ld.toString(), times.get(aptTime),aptNotes, aptid,aptStIndex)) {
                {
                    try {
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/PatientTracker/Views/appointmentMainScreen.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.println("Patient Main Error: " + e.getMessage());
                    }
                }

            } else {
                errorWindow(6);

            }
        }



    }


    public void errorWindow(int code) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error appointment!");

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


    public void maSendAppointment(Appointment appointment)
    {

        aptid = appointment.getAppointmentId();

        String DateTime = appointment.getAppointmentStart();
        //grab just the date
        String date = DateTime.split("T")[0];
        DateTimeFormatter df =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldt = LocalDate.parse(date,df);
        //grab just the time

        String timeWithZone = DateTime.substring(DateTime.indexOf("T") + 1);
        String timeOnly = timeWithZone.split("-")[0];

        //Convert Back to AM PM format string for combo box

        int timeOnlyampm = Integer.parseInt(timeOnly.split(":")[0]);

        if (timeOnlyampm > 12)
        {
            timeOnlyampm -= 12;
            timeOnly = String.valueOf(timeOnlyampm +":00 PM");
        }
        else if (timeOnlyampm == 12)
        {
            timeOnly = String.valueOf(timeOnlyampm + ":00 PM");
        }
        else
        {
            timeOnly = String.valueOf(timeOnlyampm + ":00 AM");
        }




        // set previous selections for input boxes

        maAdDP.setValue(ldt);
        maStCB.setValue(timeOnly);
        maTCB.setValue(appointment.getAppointmentType());
        maNTXT.setText(appointment.getNotes());
        maCCB.setValue("admin");

        
    }

}
