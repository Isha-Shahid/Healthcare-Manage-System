package controller;

import model.Patient;
import model.DataStore;
import java.util.List;

public class PatientController {
    private List<Patient> patients;

    public PatientController() {
        // Load existing patients from CSV
        patients = DataStore.loadPatients("patients.csv");
    }

    // Add a new patient and persist
    public void addPatient(Patient p) {
        patients.add(p);
        DataStore.savePatients(patients, "patients.csv");
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patients;
    }

    // Delete a patient and persist
    public void deletePatient(Patient p) {
        patients.remove(p);
        DataStore.savePatients(patients, "patients.csv");
    }
}
