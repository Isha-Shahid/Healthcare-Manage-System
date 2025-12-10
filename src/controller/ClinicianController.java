package controller;

import model.Clinician;
import model.DataStore;
import java.util.List;

public class ClinicianController {
    private List<Clinician> clinicians;

    public ClinicianController() {
        clinicians = DataStore.loadClinicians("clinicians.csv");
    }

    public void addClinician(Clinician c) {
        clinicians.add(c);
        DataStore.saveClinicians(clinicians, "clinicians.csv");
    }

    public List<Clinician> getAllClinicians() {
        return clinicians;
    }

    public void deleteClinician(Clinician c) {
        clinicians.remove(c);
        DataStore.saveClinicians(clinicians, "clinicians.csv");
    }
}
