package PatientTracker.CodeFiles;

import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import java.sql.*;
import javafx.scene.control.Alert;
import java.time.*;
import javafx.collections.ObservableList;





public class AppointmentDB {

    static ObservableList<Appointment> apDBAppointments = FXCollections.observableArrayList();
// generates monthly Appointments

    public static ObservableList<Appointment> getMonthlyCalendar(int id) {

        Appointment weeklyAppointment;
        apDBAppointments.clear();

        LocalDate rightNow = LocalDate.now();
        LocalDate endofThisMonth = LocalDate.now().plusMonths(1);
        try {
            Statement DBConnStatement = Database.getConnection().createStatement();
            String monthlyCalQuery = "SELECT * FROM appointment WHERE pt_id = '" + id + "' AND " +
                    "start_datetime >= '" + rightNow + "' AND start_datetime <= '" + endofThisMonth + "'";
            ResultSet monthQueryResult = DBConnStatement.executeQuery(monthlyCalQuery);
            while(monthQueryResult.next()) {
        // getting time conversions from database squared away and displayed in local time
                String dbSt = monthQueryResult.getString("start_datetime");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime intputDateTime = LocalDateTime.parse(dbSt, format);
                System.out.println(dbSt);
                ZoneId localZid = ZoneId.systemDefault();
                ZoneId UTCZid  = ZoneId.of("UTC");
                ZonedDateTime zdt = intputDateTime.atZone(UTCZid);
                ZonedDateTime aptStZ =ZonedDateTime.ofInstant(zdt.toInstant(),localZid);
                String startTime = String.valueOf(aptStZ);



                int appointmentId = monthQueryResult.getInt("apt_id");
                int typeId = monthQueryResult.getInt("apt_type_id");
                String appointmentType;
                if (typeId == 1) {
                     appointmentType = "Mental Health Counseling";
                }
                else if (typeId == 2)
                {
                     appointmentType = "Relationship Counseling";
                }
                else if (typeId == 3)
                {
                    appointmentType = "Drug Counseling";
                }
                else if (typeId ==4) {
                    appointmentType ="Family Counseling";
                }
                else
                    appointmentType = "error";

                if (typeId ==1 ||typeId ==2 || typeId ==3||typeId==4) {
                    weeklyAppointment = new Appointment(appointmentId,startTime, typeId, appointmentType, monthQueryResult.getString("notes"), monthQueryResult.getInt("pt_id"));
                    apDBAppointments.add(weeklyAppointment);}
                else
                    System.out.println("Error adding appointment to calender");


            }
            DBConnStatement.close();

            return apDBAppointments;
        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return null;
        }
    }



    public static ObservableList<Appointment> getBiWeeklyCalendar(int weeklyCalPatientId) {
        ObservableList<Appointment> biWeeklyAppointments = FXCollections.observableArrayList();
        Appointment biWeeklyAppointment;

        LocalDate rightNow = LocalDate.now();
        LocalDate inTwoWeeks = LocalDate.now().plusWeeks(2);
        try {
            Statement DBConnStatement = Database.getConnection().createStatement();
            String biWeeklyCalQuery = "SELECT * FROM appointment WHERE pt_id = '" + weeklyCalPatientId + "' AND " +
                    "start_datetime >= '" + rightNow + "' AND start_datetime <= '" + inTwoWeeks + "'";
            ResultSet results = DBConnStatement.executeQuery(biWeeklyCalQuery);
            while(results.next()) {

                String dbSt = results.getString("start_datetime");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime intputDateTime = LocalDateTime.parse(dbSt, format);
                System.out.println(dbSt);
                ZoneId localZid = ZoneId.systemDefault();
                ZoneId UTCZid  = ZoneId.of("UTC");
                ZonedDateTime zdt = intputDateTime.atZone(UTCZid);
                ZonedDateTime aptStZ =ZonedDateTime.ofInstant(zdt.toInstant(),localZid);
                String startTime = String.valueOf(aptStZ);



                int appointmentId = results.getInt("apt_id");
                int typeId = results.getInt("apt_type_id");
                String appointmentType;
                if (typeId == 1) {
                    appointmentType = "Mental Health Counseling";
                }
                else if (typeId == 2)
                {
                    appointmentType = "Relationship Counseling";
                }
                else if (typeId == 3)
                {
                    appointmentType = "Drug Counseling";
                }
                else if (typeId ==4) {
                    appointmentType ="Family Counseling";
                }
                else
                    appointmentType = "error";

                if (typeId ==1 ||typeId ==2 || typeId ==3||typeId==4) {
                    biWeeklyAppointment = new Appointment(appointmentId,startTime, typeId, appointmentType, results.getString("notes"), results.getInt("pt_id"));
                    biWeeklyAppointments.add(biWeeklyAppointment);}
                else
                    System.out.println("Error adding appointment to calender");


            }
            DBConnStatement.close();

            return biWeeklyAppointments;
        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return null;
        }
    }


    public static ObservableList<Appointment> getWeeklyCalendar(int WeeklyCalPatientId) {

        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        Appointment weeklyAppointment;

        LocalDate rightNow = LocalDate.now();
        LocalDate oneWeekLater = LocalDate.now().plusWeeks(1);
        try {
            Statement DBConnStatement = Database.getConnection().createStatement();
            String weeklyCalQuery = "SELECT * FROM appointment WHERE pt_id = '" + WeeklyCalPatientId + "' AND " +
                    "start_datetime >= '" + rightNow + "' AND start_datetime <= '" + oneWeekLater + "'";
            ResultSet weeklyCalQueryResults = DBConnStatement.executeQuery(weeklyCalQuery);
            while(weeklyCalQueryResults.next()) {

                
                //get rid of utc to local conversions from timestamp.
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dbSt = weeklyCalQueryResults.getString("start_datetime");

                LocalDateTime ldt = LocalDateTime.parse(dbSt, format);
                System.out.println(dbSt);
                ZoneId localZid = ZoneId.systemDefault();
                ZoneId UTCZid  = ZoneId.of("UTC");
                ZonedDateTime zdt = ldt.atZone(UTCZid);
                ZonedDateTime aptStZ =ZonedDateTime.ofInstant(zdt.toInstant(),localZid);
               String startTime = String.valueOf(aptStZ);
               // String startTime = aptStZ.toLocalDateTime().toString();



                int appointmentId = weeklyCalQueryResults.getInt("apt_id");
                int typeId = weeklyCalQueryResults.getInt("apt_type_id");

                String appointmentType;
                if (typeId == 1) {
                    appointmentType = "Mental Health Counseling";
                }
                else if (typeId == 2)
                {
                    appointmentType = "Relationship Counseling";
                }
                else if (typeId == 3)
                {
                    appointmentType = "Drug Counseling";
                }
                else if (typeId ==4) {
                    appointmentType ="Family Counseling";
                }
                else
                    appointmentType = "error";

                if (typeId ==1 ||typeId ==2 || typeId ==3||typeId==4) {
                    weeklyAppointment = new Appointment(appointmentId,startTime, typeId, appointmentType, weeklyCalQueryResults.getString("notes"), weeklyCalQueryResults.getInt("pt_id"));
                    weeklyAppointments.add(weeklyAppointment);}
                else
                    System.out.println("Error adding appointment to calender");


            }
            DBConnStatement.close();

            return weeklyAppointments;
        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return null;
        }
    }




    public static boolean addAppointment(String counselorName, int id, int type, String date, String time, String notes,int aptStIndex) throws SQLException {
        //Generate appointment Id

        int aptId =1;
        String pquery = "SELECT * From appointment";
        PreparedStatement psai = Database.getConnection().prepareStatement(pquery);
        ResultSet rsa = psai.executeQuery();

        while (rsa.next())
        {
            aptId = rsa.getInt("apt_id");
            aptId += 1;
        }



            int counselorId = CounselorDB.getCounselorId(counselorName);


            String hour = time.split(":")[0];
            int tfhTime = Integer.parseInt(hour);

        if(tfhTime < 9 && aptStIndex!=12) {
            tfhTime += 12;
        }
        else if (aptStIndex==12)
        {
            tfhTime = 21;

        }


                String comboDTString = String.format("%s %02d:%s", date, tfhTime, "00");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
                LocalDateTime addAptLDT = LocalDateTime.parse(comboDTString, formatter);




                Timestamp ts = Timestamp.valueOf(addAptLDT);



        try {


            LocalDateTime rightnow = LocalDateTime.now();


            String sqlc = "Insert INTO appointment VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psaa = Database.getConnection().prepareStatement(sqlc);
            psaa.setInt(1,aptId);   // aptId
            psaa.setInt(2, id); //patientId
            psaa.setInt(3,counselorId);  //crId
            psaa.setInt(4,type);  //aptTypeId
            psaa.setString(5,notes);  //notes
            psaa.setTimestamp(6,ts);  //start dateTime
            psaa.setTimestamp(7,Timestamp.valueOf(rightnow));    //created at
            psaa.setString(8,"not needed");  //created by
            psaa.setTimestamp(9, Timestamp.valueOf(rightnow));   //updated at
            psaa.setString(10, "not needed"); //updated by
            psaa.setInt(11, id); //patient pt_id
            psaa.setInt(12,counselorId); //counselor c_id
            psaa.setInt(13,type);  //apttype_appttype id
            psaa.execute();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteAppointment(int id)
    {
        try {
            apDBAppointments.clear();
            Statement statement = Database.getConnection().createStatement();
            String sql1 = "DELETE FROM appointment WHERE apt_id=" + id;
            statement.executeUpdate(sql1);
        }
        catch (SQLException e)
        {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;

    }

    public static boolean updateAppointment(int aptTypeId, String date, String time, String aptNotes, int aptId,int aptStIndex)
    {
        String hour = time.split(":")[0];
        int tfhTime = Integer.parseInt(hour);

        if(tfhTime < 9 && aptStIndex!= 12) {
            tfhTime += 12;
        }
       else if(aptStIndex == 12)
        {
             tfhTime = 21;
        }

        DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        String comboDTString = String.format("%s %02d:%s", date, tfhTime, "00");
        System.out.println(comboDTString);
        LocalDateTime updateAppointmentLdt = LocalDateTime.parse(comboDTString, Format);




        Timestamp ts = Timestamp.valueOf(updateAppointmentLdt);

        System.out.println(ts);

        try {
            apDBAppointments.clear();

            String sqla = "UPDATE appointment SET start_datetime = ?, apt_type_id = ?, notes = ? WHERE apt_id = ?";

            PreparedStatement psa = Database.getConnection().prepareStatement(sqla);
            psa.setTimestamp(1,ts);
            psa.setInt(2,aptTypeId);
            psa.setString(3,aptNotes);
            psa.setInt(4,aptId);
            psa.execute();
            return true;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
        }
        return false;
    }

    public static ObservableList<Appointment> appointmentsIn4Hours() {
        apDBAppointments.clear();
        Appointment appointment;
        String aptType = "temp";
        LocalDateTime now = LocalDateTime.now();
        ZoneId sysZid = ZoneId.systemDefault();
        ZoneId zid2 = ZoneId.of("UTC");
        ZonedDateTime sysZdt = now.atZone(sysZid);
        LocalDateTime sysLdt = sysZdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime sysLdtPlus4Hrs = sysLdt.plusHours(4);
        String user = CounselorDB.getActiveCounselor().getCounselorName();
        int cr_id = CounselorDB.getCounselorId(user);
        try {
            Statement DBConnStatement = Database.getConnection().createStatement();
            String apptIn4hrQuery = "SELECT * FROM appointment WHERE start_datetime BETWEEN '" + sysLdt + "' AND '" + sysLdtPlus4Hrs + "' AND " +
                    "cr_id='" + cr_id + "'";
            ResultSet fourHrAptResults = DBConnStatement.executeQuery(apptIn4hrQuery);
            while (fourHrAptResults.next()) {
              String aptStTimeUTC =  fourHrAptResults.getString("start_datetime");

                DateTimeFormatter fourHourFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime ldt3 = LocalDateTime.parse(aptStTimeUTC, fourHourFormatter);


              ZonedDateTime zdtutc = ldt3.atZone(zid2);
              ZonedDateTime apstzdtSysDef = ZonedDateTime.ofInstant(zdtutc.toInstant(),sysZid);
              String Start = String.valueOf(apstzdtSysDef);

                appointment = new Appointment(fourHrAptResults.getInt("apt_id"),Start, fourHrAptResults.getInt("apt_type_id"),aptType, fourHrAptResults.getString("notes"),
                        fourHrAptResults.getInt("pt_id"));
                apDBAppointments.add(appointment);
            }

        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
        }

        if (!apDBAppointments.isEmpty())
        {
            return apDBAppointments;
        }

        return null;
    }


    public static boolean overlappingAppointment(int id, String date, String time,int aptStIndex) {

        // Converts combo box and datepicker into a date time and checks database for duplicate entries.
        String hour = time.split(":")[0];
        int tfhTime = Integer.parseInt(hour);

        if (tfhTime < 9 && aptStIndex!=12) {
            tfhTime += 12;
        }
        else if (aptStIndex==12)
        {
            tfhTime = 21;

        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        ZoneId zidD = ZoneId.systemDefault();
        ZoneId zid = ZoneId.of("UTC");
        String comboDTString = String.format("%s %02d:%s", date, tfhTime, "00");
        LocalDateTime overlappingLDT = LocalDateTime.parse(comboDTString, format);
        ZonedDateTime mzdt = overlappingLDT.atZone(zidD);
        ZonedDateTime aptStartTime = ZonedDateTime.ofInstant(mzdt.toInstant(), zid); //converting entry into utc time for accurate time checking

        // Checking if input time is in the past
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime nzdt = now.atZone(zidD);
        ZonedDateTime UTCNow = ZonedDateTime.ofInstant(nzdt.toInstant(), zid);

        if (aptStartTime.compareTo(UTCNow) > 0) {
            try {
                Statement DBConnStatement = Database.getConnection().createStatement();
                String overlappingQuery = "SELECT * FROM appointment WHERE start_datetime = '" + aptStartTime + "'";
                ResultSet overlapingQueryResults = DBConnStatement.executeQuery(overlappingQuery);
                if (overlapingQueryResults.next()) {
                    if (overlapingQueryResults.getInt("appointmentId") == id) {
                        DBConnStatement.close();
                        return false;
                    }
                    DBConnStatement.close();
                    return true;
                } else {
                    DBConnStatement.close();
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
                return true;
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Error");
            alert.setHeaderText("Date & Time Selected Has Already Passed!");
            alert.setContentText("The Time You entered is in the past, please enter a current date");
            alert.showAndWait();
            return true;
        }

    }

}
