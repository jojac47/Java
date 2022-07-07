package PatientTracker.CodeFiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CounselorDB {

    private static Counselor activeCounselor;

    public static Counselor getActiveCounselor() {
        return activeCounselor;
    }

    public static int getCounselorId(String counselorName){
        try {
            Statement statement = Database.getConnection().createStatement();
            String sqlGetCounselorId = "SELECT c_Id From counselor WHERE c_name = '" + counselorName + "'";
            ResultSet rs = statement.executeQuery(sqlGetCounselorId);
            rs.next();
            int counselorId = rs.getInt(1);

            return counselorId;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Attempt Login
    public static Boolean login(String counselorName, String password, String pin) {
        try {
            Database.DBconnect();
            Statement DBConnStatement = Database.getConnection().createStatement();

            String counselorNameQuery = "SELECT * FROM counselor WHERE c_name='" + counselorName + "' AND c_password='" + password + "' AND c_pin='" + pin +"'";
            ResultSet results = DBConnStatement.executeQuery(counselorNameQuery);
            if(results.next()) {
                activeCounselor = new Counselor();
                activeCounselor.setCounselorName(results.getString("c_name"));
                DBConnStatement.close();
                Logger.log(counselorName, true);
                return true;
            } else {
               Logger.log(counselorName, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return false;
        }
    }


}
