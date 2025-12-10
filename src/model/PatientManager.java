package model;

import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private static PatientManager instance = null;
    private List<Patient> patients = new ArrayList<>();

    private PatientManager() {}

    public static PatientManager getInstance() {
        if (instance == null) instance = new PatientManager();
        return instance;
    }

    public void addPatient(Patient p) {
        patients.add(p);
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
