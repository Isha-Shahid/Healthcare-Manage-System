package model;

public class Referral {
    private String referralId;
    private String patientId;
    private String clinicianId;
    private String priority;

    public Referral(String referralId, String patientId, String clinicianId, String priority) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.priority = priority;
    }

    public String getReferralId() { return referralId; }
    public String getPatientId() { return patientId; }
    public String getClinicianId() { return clinicianId; }
    public String getPriority() { return priority; }
}
