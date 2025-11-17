// src/model/DataStore.java
package model;

import util.FileHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    // Hold collections of all entities
    private List<Patient> allPatients = new ArrayList<>();
    // Use a Map for faster lookup by ID
    private Map<String, Clinician> allClinicians = new HashMap<>(); 
    private List<Appointment> allAppointments = new ArrayList<>();
    // ... lists/maps for all other entities

    public void loadAllData() {
        System.out.println("Loading data from CSV files...");
        FileHandler.loadPatients("patients.csv", allPatients);
        // FileHandler.loadClinicians("clinicians.csv", allClinicians);
        // ... load other files
        System.out.println("Data loading complete.");
    }
    
    /**
     * Fulfills the 'creating new records' requirement.
     */
    public void addPatient(Patient patient) {
        this.allPatients.add(patient);
        // Here you would also call a FileHandler method to append the new record to patients.csv
    }

    // public Patient getPatientByNhsNumber(String nhs) { ... }
    // public List<Clinician> findSpecialists(String specialty) { ... }
}