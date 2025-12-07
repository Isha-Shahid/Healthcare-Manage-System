package controller;

import model.Clinician;
import java.util.ArrayList;
import java.util.List;

public class ClinicianController {
    private List<Clinician> clinicians = new ArrayList<>();

    public void addClinician(Clinician c) { clinicians.add(c); }
    public List<Clinician> getAllClinicians() { return clinicians; }
}
