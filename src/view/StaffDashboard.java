package view;

import controller.StaffController;
import controller.PatientController;
import controller.AppointmentController;
import controller.ReferralController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffDashboard extends JPanel {
    private StaffController staffController;
    private PatientController patientController;
    private AppointmentController appointmentController;
    private ReferralController referralController;

    private JTable patientTable, appointmentTable, referralTable;
    private DefaultTableModel patientModel, appointmentModel, referralModel;

    public StaffDashboard(StaffController sc, PatientController pc, AppointmentController ac, ReferralController rc) {
        this.staffController = sc;
        this.patientController = pc;
        this.appointmentController = ac;
        this.referralController = rc;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Staff Dashboard", JLabel.CENTER);
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

        // Referrals tab
        referralModel = new DefaultTableModel(new String[]{"Referral ID", "Patient", "From", "To", "Status"}, 0);
        referralTable = new JTable(referralModel);
        tabs.add("Referrals", new JScrollPane(referralTable));

        add(tabs, BorderLayout.CENTER);

        loadPatients();
        loadAppointments();
        loadReferrals();
    }

    private void loadPatients() {
        patientModel.setRowCount(0);
        // load patient data
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        // load appointment data
    }

    private void loadReferrals() {
        referralModel.setRowCount(0);
        // load referral data
    }
}
