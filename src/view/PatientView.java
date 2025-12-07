package view;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientView extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private PatientController controller;

    public PatientView(PatientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Patient ID", "First Name", "Last Name", "GP Surgery ID"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel p = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        p.add(addBtn);
        p.add(editBtn);
        p.add(deleteBtn);
        add(p, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addPatient());
        editBtn.addActionListener(e -> editPatient());
        deleteBtn.addActionListener(e -> deletePatient());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Patient> patients = controller.getAllPatients();
        for (Patient p : patients) {
            model.addRow(new Object[]{p.getPatientId(), p.getFirstName(), p.getLastName(), p.getGpSurgeryId()});
        }
    }

    private void addPatient() {
        String id = JOptionPane.showInputDialog("Patient ID:");
        String first = JOptionPane.showInputDialog("First Name:");
        String last = JOptionPane.showInputDialog("Last Name:");
        String gp = JOptionPane.showInputDialog("GP Surgery ID:");

        if (id != null && first != null && last != null && gp != null) {
            Patient p = new Patient(id, gp);
            p.setFirstName(first);
            p.setLastName(last);
            controller.addPatient(p);
            loadData();
        }
    }

    private void editPatient() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String patientId = (String) model.getValueAt(row, 0);
        String newFirst = JOptionPane.showInputDialog("New First Name:");
        String newLast = JOptionPane.showInputDialog("New Last Name:");

        for (Patient p : controller.getAllPatients()) {
            if (p.getPatientId().equals(patientId)) {
                if (newFirst != null) p.setFirstName(newFirst);
                if (newLast != null) p.setLastName(newLast);
                break;
            }
        }
        loadData();
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String patientId = (String) model.getValueAt(row, 0);
        controller.getAllPatients().removeIf(p -> p.getPatientId().equals(patientId));
        loadData();
    }
}
