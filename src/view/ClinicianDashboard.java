package view;

import controller.ClinicianController;
import controller.PrescriptionController;
import controller.AppointmentController;
import model.Clinician;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClinicianDashboard extends JPanel {
    private ClinicianController clinicianController;
    private PrescriptionController prescriptionController;
    private AppointmentController appointmentController;

    private JTable patientTable, appointmentTable;
    private DefaultTableModel patientModel, appointmentModel;

    public ClinicianDashboard(ClinicianController cc, AppointmentController ac, PrescriptionController pc) {
        this.clinicianController = cc;
        this.appointmentController = ac;
        this.prescriptionController = pc;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Clinician Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(65, 105, 225));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Patients tab
        patientModel = new DefaultTableModel(new String[]{"Patient ID", "Name", "Age", "Gender"}, 0);
        patientTable = new JTable(patientModel);
        tabs.add("Patients", new JScrollPane(patientTable));

        // Appointments tab
        appointmentModel = new DefaultTableModel(new String[]{"Appointment ID", "Patient", "Date", "Status"}, 0);
        appointmentTable = new JTable(appointmentModel);
        tabs.add("Appointments", new JScrollPane(appointmentTable));

        add(tabs, BorderLayout.CENTER);

        loadPatients();
        loadAppointments();
    }

    private void loadPatients() {
        patientModel.setRowCount(0);
        // Example: load patients assigned to this clinician
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        // Example: load clinician's appointments
    }
}
