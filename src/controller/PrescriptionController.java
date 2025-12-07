package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {

    private List<Prescription> prescriptions;

    public PrescriptionController() {
        this.prescriptions = DataStore.loadPrescriptions("prescriptions.csv");
    }

    public void addPrescription(Prescription p) {
        prescriptions.add(p);
        DataStore.savePrescriptions(prescriptions, "prescriptions.csv");
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptions;
    }

    public void removePrescription(Prescription p) {
        prescriptions.remove(p);
        DataStore.savePrescriptions(prescriptions, "prescriptions.csv");
    }
}
