package view;

import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JButton addBtn;
    private JButton editBtn;
    private JButton deleteBtn;

    public AppointmentView() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Appointment ID", "Patient ID", "Clinician ID", "Date"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel();

        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");

        controls.add(addBtn);
        controls.add(editBtn);
        controls.add(deleteBtn);

        add(controls, BorderLayout.SOUTH);
    }

    public void showAppointments(List<Appointment> appointments) {
        model.setRowCount(0);
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getDate()
            });
        }
    }

    public JButton getAddButton() { return addBtn; }
    public JButton getEditButton() { return editBtn; }
    public JButton getDeleteButton() { return deleteBtn; }
    public JTable getTable() { return table; }
}
