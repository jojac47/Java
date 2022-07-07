package PatientTracker.CodeFiles;

public class Appointment {

    String appointmentStart;
    String appointmentType;
    String notes;
    int patientId;
    int appointmentTypeId;
    int appointmentId;



    public Appointment(int appointmentId , String appointmentStart, int appointmentTypeId, String appointmentType, String notes, int patientId) {
   this.appointmentId = appointmentId;
    this.appointmentStart = appointmentStart;
    this.appointmentType = appointmentType;
    this.notes = notes;
    this.patientId = patientId;
    this.appointmentTypeId = appointmentTypeId;
    }
    public int getAppointmentId() {
        return appointmentId;
    }



    public int getAppointmentTypeId() {
        return appointmentTypeId;
    }



    public String getAppointmentStart() {
        return appointmentStart;
    }



    public String getAppointmentType() {
        return appointmentType;
    }


    public String getNotes() {
        return notes;
    }



    public int getPatientId() {
        return patientId;
    }



}
