package model;

public class Nurse extends Clinician {

    private String nurseType; // e.g., "Practice Nurse", "Staff Nurse"

    public Nurse(String clinicianId, String firstName, String lastName, String title, String speciality,
                 String gmcNumber, String phoneNumber, String email, String workplaceId, String workplaceType,
                 String employmentStatus, String startDate, String nurseType) {
        super(clinicianId, firstName, lastName, title, speciality, gmcNumber, phoneNumber, email,
              workplaceId, workplaceType, employmentStatus, startDate);
        this.nurseType = nurseType;
    }

    public String getNurseType() { return nurseType; }
    public void setNurseType(String nurseType) { this.nurseType = nurseType; }
}
