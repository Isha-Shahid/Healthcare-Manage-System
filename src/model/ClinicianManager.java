package model;

import java.util.ArrayList;
import java.util.List;

public class ClinicianManager {

    private static ClinicianManager instance;
    private List<Clinician> clinicians = new ArrayList<>();

    private ClinicianManager() {}

    public static ClinicianManager getInstance() {
        if (instance == null) {
            instance = new ClinicianManager();
        }
        return instance;
    }

    public void addClinician(Clinician c) {
        clinicians.add(c);
    }

    public List<Clinician> getClinicians() {
        return clinicians;
    }
}
