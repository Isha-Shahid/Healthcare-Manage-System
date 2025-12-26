package view;

import controller.PatientController;
import controller.AppointmentController;
import controller.PrescriptionController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientDashboard extends JPanel {
    private PatientController patientController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;

    private JTable appointmentTable, prescriptionTable;
    private DefaultTableModel appointmentModel, prescriptionModel;

    public PatientDashboard(PatientController pc, AppointmentController ac, PrescriptionController prc) {
        this.patientController = pc;
        this.appointmentController = ac;
        this.prescriptionController = prc;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Top label
        JLabel title = new JLabel("Patient Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(65, 105, 225));
        add(title, BorderLayout.NORTH);

        // Tabbed pane for Appointments & Prescriptions
        JTabbedPane tabs = new JTabbedPane();

        // Appointment tab
        appointmentModel = new DefaultTableModel(new String[]{"ID", "Date", "Doctor", "Status"}, 0);
        appointmentTable = new JTable(appointmentModel);
        tabs.add("Appointments", new JScrollPane(appointmentTable));

        // Prescription tab
        prescriptionModel = new DefaultTableModel(new String[]{"Prescription ID", "Drug", "Condition", "Doctor"}, 0);
        prescriptionTable = new JTable(prescriptionModel);
        tabs.add("Prescriptions", new JScrollPane(prescriptionTable));

        add(tabs, BorderLayout.CENTER);

        // Load data
        loadAppointments();
        loadPrescriptions();
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        // Here you should filter appointments for the logged-in patient
        // Example:
        // List<Appointment> list = appointmentController.getAppointmentsByPatient(patientId);
        // for (Appointment a : list) appointmentModel.addRow(...)
    }

    private void loadPrescriptions() {
        prescriptionModel.setRowCount(0);
        // Example: filter prescriptions for patient
    }
}
