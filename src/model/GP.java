package model;

public class GP extends Clinician {

    public GP(String clinicianId) {
        super(clinicianId);
        setTitle("GP");
        setSpeciality("General Practice");
    }

    public void conductConsultation() {
        System.out.println("Conducting general consultation.");
    }
}
