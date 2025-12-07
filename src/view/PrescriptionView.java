package view;

import controller.PrescriptionController;
import model.Prescription;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PrescriptionView extends JPanel {

    private PrescriptionController controller;
    private JTable table;
    private DefaultTableModel model;

    public PrescriptionView(PrescriptionController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new String[]{"ID","Patient","Medication"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table));

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        for (Prescription p : controller.getAllPrescriptions()) {
            model.addRow(new Object[]{p.getPrescriptionId(), p.getPatientId(), p.getMedication()});
        }
    }
}
