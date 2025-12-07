package model;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private static SystemManager instance;

    private List<Patient> patients = new ArrayList<>();
    private List<Clinician> clinicians = new ArrayList<>();
    private List<Staff> staffList = new ArrayList<>();

    private SystemManager() {}

    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    // Accessors
    public List<Patient> getPatients() { return patients; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Staff> getStaffList() { return staffList; }

    // Methods to add entities
    public void addPatient(Patient p) { patients.add(p); }
    public void addClinician(Clinician c) { clinicians.add(c); }
    public void addStaff(Staff s) { staffList.add(s); }
}
