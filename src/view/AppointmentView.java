// AppointmentView.java
package view;

import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public AppointmentView() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Appointment ID", "Patient ID", "Clinician ID", "Date"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel p = new JPanel();
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton remove = new JButton("Delete");
        p.add(add);
        p.add(edit);
        p.add(remove);
        add(p, BorderLayout.SOUTH);
    }

    // Method to display appointments from controller
    public void showAppointments(List<Appointment> appointments) {
        model.setRowCount(0); // Clear existing table rows
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getAppointmentDate()
            });
        }
    }
}
