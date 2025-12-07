package view;

import controller.ClinicianController;
import model.Clinician;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianView extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private ClinicianController controller;

    public ClinicianView(ClinicianController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Clinician ID", "First Name", "Last Name", "Speciality"}, 0);
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

        addBtn.addActionListener(e -> addClinician());
        editBtn.addActionListener(e -> editClinician());
        deleteBtn.addActionListener(e -> deleteClinician());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Clinician> clinicians = controller.getAllClinicians();
        for (Clinician c : clinicians) {
            model.addRow(new Object[]{c.getClinicianId(), c.getFirstName(), c.getLastName(), c.getSpeciality()});
        }
    }

    private void addClinician() {
        String id = JOptionPane.showInputDialog("Clinician ID:");
        String first = JOptionPane.showInputDialog("First Name:");
        String last = JOptionPane.showInputDialog("Last Name:");
        String spec = JOptionPane.showInputDialog("Speciality:");

        if (id != null && first != null && last != null && spec != null) {
            Clinician c = new Clinician(id, first, last, spec);
            controller.addClinician(c);
            loadData();
        }
    }

    private void editClinician() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String clinicianId = (String) model.getValueAt(row, 0);
        String newFirst = JOptionPane.showInputDialog("New First Name:");
        String newLast = JOptionPane.showInputDialog("New Last Name:");
        String newSpec = JOptionPane.showInputDialog("New Speciality:");

        for (Clinician c : controller.getAllClinicians()) {
            if (c.getClinicianId().equals(clinicianId)) {
                if (newFirst != null) c.setFirstName(newFirst);
                if (newLast != null) c.setLastName(newLast);
                if (newSpec != null) c.setSpeciality(newSpec);
                break;
            }
        }
        loadData();
    }

    private void deleteClinician() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String clinicianId = (String) model.getValueAt(row, 0);
        controller.getAllClinicians().removeIf(c -> c.getClinicianId().equals(clinicianId));
        loadData();
    }
}
