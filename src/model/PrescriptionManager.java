package model;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager {

    private static PrescriptionManager instance;
    private List<Prescription> prescriptions = new ArrayList<>();

    private PrescriptionManager() {}

    public static PrescriptionManager getInstance() {
        if (instance == null) {
            instance = new PrescriptionManager();
        }
        return instance;
    }

    public void addPrescription(Prescription p) {
        prescriptions.add(p);
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
