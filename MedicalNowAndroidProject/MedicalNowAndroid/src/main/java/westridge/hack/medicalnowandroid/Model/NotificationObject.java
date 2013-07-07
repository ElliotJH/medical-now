package westridge.hack.medicalnowandroid.Model;

/**
 * Created by jordan on 7/6/13.
 */
public class NotificationObject {

    private String name;
    private String description;
    private String patient;
    private String patientEmail;

    public NotificationObject(String name, String description, String patient, String patientEmail) {

        this.name = name;
        this.description = description;
        this.patient = patient;
        this.patientEmail = patientEmail;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
