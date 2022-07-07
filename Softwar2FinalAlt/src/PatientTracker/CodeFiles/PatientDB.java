package PatientTracker.CodeFiles;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.*;
import java.time.LocalDateTime;

public class PatientDB {
    private static ObservableList<Patient> pDBAllPatients = FXCollections.observableArrayList();





public static String getPatientName(int patientId)
    {
        try {
            String queryPN = "SELECT patient.pt_name From patient WHERE patient.pt_id = '" + patientId + "'";
            PreparedStatement psPN = Database.getConnection().prepareStatement(queryPN);
            ResultSet rsPN = psPN.executeQuery();

            if (rsPN.next());
            {
                String Patientname = rsPN.getString("pt_name");
                return Patientname;

            }

        }
        catch (SQLException e)
        {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return null;


        }

    }

    // Returns all Patients in Database
    public static ObservableList<Patient> getpDBAllPatients() {
        try {
            pDBAllPatients.clear();
            String allPatientsQuery = "SELECT patient.pt_id, patient.pt_name, patient.INS_PR, address.phone, address.state FROM patient INNER JOIN address ON patient.address_id = address.address_id";

            PreparedStatement ps = Database.getConnection().prepareStatement(allPatientsQuery);

            ResultSet getAllPatientsResults = ps.executeQuery();
            while(getAllPatientsResults.next()) {
                int id =   getAllPatientsResults.getInt(1);
                String  name = getAllPatientsResults.getString(2);
                String insurance = getAllPatientsResults.getString(3);
                String state = getAllPatientsResults.getString(4);
                String number = getAllPatientsResults.getString(5);


                Patient p = new Patient(id, name, insurance,  state, number);
                pDBAllPatients.add(p);

            }

        } catch (SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
            return null;
        }

        return pDBAllPatients;
    }




    // Save a new Patient to Database
    public static boolean savePatient(String pName, String pState, String pPhoneNumber, String pInsuranceProvider) {
        LocalDateTime rightnow = LocalDateTime.now();
        int pId = 1;
        int aId = 1;

        try {
            pDBAllPatients.clear();
            String sqla = "Insert INTO address VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            String query = "SELECT * From address";


            // Generates  address Id

            PreparedStatement psai = Database.getConnection().prepareStatement(query);
            ResultSet rsSavePatient = psai.executeQuery();

            while(rsSavePatient.next())
            {
                aId = rsSavePatient.getInt("address_id");
                aId +=1;
            }


            // Inserts values into address table

            PreparedStatement psa = Database.getConnection().prepareStatement(sqla);

            psa.setInt(1,aId);
            psa.setString(2, "not needed");
            psa.setString(3, "not needed");
            psa.setString(4,"not needed");
            psa.setString(5, pState);
            psa.setString(6,"not needed");
            psa.setString(7,pPhoneNumber);
            psa.setTimestamp(8, Timestamp.valueOf(rightnow));
            psa.setString(9,"not needed");
            psa.setTimestamp(10,Timestamp.valueOf(rightnow));
            psa.setString(11,"not needed");
            psa.execute();

            // generates patient id
            String pquery = "SELECT * From patient";
            PreparedStatement pspi = Database.getConnection().prepareStatement(pquery);
            ResultSet rsp = pspi.executeQuery();

            while (rsp.next())
            {
                pId = rsp.getInt("pt_id");
                pId += 1;
            }
            // Inserts values into patient Table

            String sqlp = "INSERT INTO patient VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement psp = Database.getConnection().prepareStatement(sqlp);
            psp.setInt(1,pId);
            psp.setString(2,pName);
            psp.setInt(3,aId);
            psp.setString(4,pInsuranceProvider);
            psp.setTimestamp(5, Timestamp.valueOf(rightnow));
            psp.setString(6,"not needed");
            psp.setTimestamp(7,Timestamp.valueOf(rightnow));
            psp.setString(8, "not needed");
            psp.setInt(9,aId);
            psp.execute();

            return true;

        }
        catch (SQLException e) {

            System.out.println("SQLException: " + e.getMessage());
            return false;
        }




    }

    public static boolean updatePatient( int id, String pName, String pState, String pPhone, String insuranceProvider) {
        try {
            pDBAllPatients.clear();

            String sqlp = "UPDATE patient SET pt_name = ?, INS_PR = ? WHERE pt_id = ?";

            PreparedStatement psp = Database.getConnection().prepareStatement(sqlp);
            psp.setString(1,pName);
            psp.setString(2,insuranceProvider);
            psp.setInt(3,id);
            psp.execute();

            String sqlgai = "SELECT address_id FROM patient WHERE pt_id = ? ";
            PreparedStatement psai = Database.getConnection().prepareStatement(sqlgai);
            psai.setInt(1,id);
            ResultSet rs = psai.executeQuery();
            rs.next();
            int addressId = rs.getInt(1);

            String sqla = "UPDATE address SET phone = ?, state = ? WHERE address_id = ? ";
            PreparedStatement psa = Database.getConnection().prepareStatement(sqla);
            psa.setString(1,pPhone);
            psa.setString(2,pState);
            psa.setInt(3,addressId);
            psa.execute();
            return true;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    public static boolean deletePatient(int id) {
        try {


            String sqladID = "SELECT address_id FROM patient WHERE pt_id = ? ";
            PreparedStatement psai = Database.getConnection().prepareStatement(sqladID);
            psai.setInt(1,id);
            ResultSet rs = psai.executeQuery();
            rs.next();
            int addressId = rs.getInt(1);



            pDBAllPatients.clear();
            Statement statement = Database.getConnection().createStatement();
            String sql1 = "DELETE FROM appointment WHERE pt_id=" + id;
            statement.executeUpdate(sql1);

            String sql2 = "Delete from patient WHERE pt_id=" + id;
            int updateTwo = statement.executeUpdate(sql2);

            if(updateTwo == 1) {
                    String sql3 = "DELETE FROM address WHERE address_id =" + addressId;
                    statement.executeUpdate(sql3);
                    return true;
                }

        } catch(SQLException e) {
            System.out.println("The Following SQL Exception Occurred: " + e.getMessage());
        }
        return false;

    }



}
