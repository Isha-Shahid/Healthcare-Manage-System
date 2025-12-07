package model;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String date; // make sure this exists

    public Appointment(String appointmentId, String patientId, String clinicianId, String date) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.date = date;
    }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getClinicianId() { return clinicianId; }
    public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }

    public String getDate() { return date; }   // <-- this is needed
    public void setDate(String date) { this.date = date; }
}
