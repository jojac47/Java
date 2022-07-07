package PatientTracker.CodeFiles;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient {

        private final SimpleIntegerProperty patientId = new SimpleIntegerProperty();
        private final SimpleStringProperty patientName = new SimpleStringProperty();
        private final SimpleStringProperty patientState = new SimpleStringProperty();
        private final SimpleStringProperty patientPhoneNumber = new SimpleStringProperty();
        private final SimpleStringProperty patientInsuranceProvider = new SimpleStringProperty();

        public Patient(){}

        public Patient(int id,String name,String insurance, String number, String state )

        {
                setPatientId(id);
                setPatientName(name);
                setPatientState(state);
                setPatientPhoneNumber(number);
                setPatientInsuranceProvider(insurance);
        }

        public int getPatientId() {
                return patientId.get();
        }

        public void setPatientId(int patientId) {
                this.patientId.set(patientId);

        }

        public String getPatientName() {
                return patientName.get();
        }

        public void setPatientName(String patientName) {
                this.patientName.set(patientName);
        }

        public String getPatientState() {
                return patientState.get();
        }

        public void setPatientState(String patientState) {
                this.patientState.set(patientState);
        }

        public String getPatientPhoneNumber() {
                return patientPhoneNumber.get();
        }

        public void setPatientPhoneNumber(String patientPhoneNumber) {
                this.patientPhoneNumber.set(patientPhoneNumber);
        }

        public String getPatientInsuranceProvider() {
                return patientInsuranceProvider.get();
        }

        public void setPatientInsuranceProvider(String patientInsuranceProvider) {
                this.patientInsuranceProvider.set(patientInsuranceProvider);
        }
}
