package model;

public class SpecialistDoctor extends Clinician {

    public SpecialistDoctor(String clinicianId, String speciality) {
        super(clinicianId); // calls the Clinician(String clinicianId) constructor
        setTitle("Specialist Doctor");
        setSpeciality(speciality);
    }

    public void performSpecialistProcedure() {
        System.out.println("Performing specialist procedure in " + getSpeciality());
    }
}
