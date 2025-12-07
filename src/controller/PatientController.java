package controller;

import model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient p) { patients.add(p); }
    public List<Patient> getAllPatients() { return patients; }
}
