package PatientTracker.CodeFiles;

import java.time.ZonedDateTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
public class Logger {
    private static final String FILENAMES = "logInSuccess.txt";
    private static final String FILENAMEF = "logInFailure.txt";



    public static void log (String counselorUserName, boolean successfulLogIn) {

        if (successfulLogIn) {
            try (FileWriter successfulFW = new FileWriter(FILENAMES, true);
                 BufferedWriter SuccessfulBW = new BufferedWriter(successfulFW);
                 PrintWriter SuccessfulPW = new PrintWriter(SuccessfulBW)) {
                SuccessfulPW.println(ZonedDateTime.now() + " " + counselorUserName + " Successfully Logged in");
            } catch (IOException e) {
                System.out.println("The Following Error Logging Data Occurred: " + e.getMessage());
            }
        }
        else {

            try (FileWriter FailedFW = new FileWriter(FILENAMEF, true);
                 BufferedWriter FailedBW = new BufferedWriter(FailedFW);
                 PrintWriter FailedPW = new PrintWriter(FailedBW)) {
                FailedPW.println(ZonedDateTime.now() + " " + counselorUserName + " Log In Attempt Failed");
            } catch (IOException e) {
                System.out.println("The Following Error Logging Data Occurred: " + e.getMessage());
            }

        }
    }

}