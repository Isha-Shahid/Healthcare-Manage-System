package model;

public class Referral {
    private String referralId;
    private String patientId;
    private String clinicianId;

    public Referral(String referralId, String patientId, String clinicianId) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
    }

    public String getReferralId() { return referralId; }
    public String getPatientId() { return patientId; }
    public String getClinicianId() { return clinicianId; }
}
