package controller;

import model.Prescription;
import model.PrescriptionManager;
import model.DataStore;
import java.util.List;

public class PrescriptionController {

    public PrescriptionController() {
        // Load prescriptions from CSV if manager is empty
        if (PrescriptionManager.getInstance().getPrescriptions().isEmpty()) {
            List<Prescription> prescriptions = DataStore.loadPrescriptions("prescriptions.csv");
            for (Prescription p : prescriptions) {
                PrescriptionManager.getInstance().addPrescription(p);
            }
        }
    }

    public void addPrescription(Prescription p) {
        PrescriptionManager.getInstance().addPrescription(p);
        DataStore.savePrescriptions(PrescriptionManager.getInstance().getPrescriptions(), "prescriptions.csv");
    }

    public List<Prescription> getAllPrescriptions() {
        return PrescriptionManager.getInstance().getPrescriptions();
    }

    public void deletePrescription(Prescription p) {
        PrescriptionManager.getInstance().getPrescriptions().remove(p);
        DataStore.savePrescriptions(PrescriptionManager.getInstance().getPrescriptions(), "prescriptions.csv");
    }
}
