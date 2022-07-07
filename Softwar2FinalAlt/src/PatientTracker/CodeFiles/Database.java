package PatientTracker.CodeFiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.time.ZoneId;

import java.util.Date;
import java.util.TimeZone;

public class Database {

    //Database Server Stuff
    private static final String ConnectionDriver = "com.mysql.jdbc.Driver";
    private static final String DatabaseName = "U071rs";
    private static final String DatabaseURL = "jdbc:mysql://3.227.166.251/" + DatabaseName;
    public static Connection connection;

    //User Info
    private static final String User = "U071rs";
    private static final String Password = "53688933231";




    public Database() {}



    public static String createEtTimeStamp()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat.format(new Date());
    }
    public static String CreateLtTimeStamp()
    {

        ZoneId zone = ZoneId.systemDefault();


        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        return dateFormat.format(new Date());

    }



    // Connect to Database
    public static void DBconnect() {
        try {
            Class.forName(ConnectionDriver);
            connection = DriverManager.getConnection(DatabaseURL, User, Password);
            System.out.println("Connected to MySQL Database");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("The Following Vendor Error Occurred: " + e.getErrorCode());
        }
    }


    // Returns Database Connection
    public static Connection getConnection() {
        return connection;
    }
}
