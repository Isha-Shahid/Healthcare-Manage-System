package view;

import controller.*;
import model.Patient;
import model.Clinician;
import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class StaffDashboard extends JPanel {

    private StaffController staffController;
    private PatientController patientController;
    private ClinicianController clinicianController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private JTable patientTable, appointmentTable, clinicianTable;
    private DefaultTableModel patientModel, appointmentModel, clinicianModel;

    public StaffDashboard(
            PatientController pc,
            ClinicianController cc,
            StaffController sc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc
    ) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Welcome, Staff");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JButton logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(108, 117, 125));
        logoutBtn.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        return header;
    }

    private JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Patients", createPatientPanel());
        tabs.add("Appointments", createAppointmentPanel());
        tabs.add("Clinicians", createClinicianPanel());

        return tabs;
    }

    /* ================= PATIENTS TAB ================= */
    private JPanel createPatientPanel() {
        patientModel = new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "DOB", "NHS Number", "Gender"}, 0
        );
        patientTable = new JTable(patientModel);
        styleTable(patientTable);
        loadPatients();

        JButton addBtn = new JButton("Add Patient");
        JButton editBtn = new JButton("Edit Patient");
        JButton deleteBtn = new JButton("Delete Patient");

        addBtn.addActionListener(e -> addPatient());
        editBtn.addActionListener(e -> editPatient());
        deleteBtn.addActionListener(e -> deletePatient());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadPatients() {
        patientModel.setRowCount(0);
        List<Patient> patients = patientController.getAllPatients();
        for (Patient p : patients) {
            patientModel.addRow(new Object[]{
                    p.getPatientId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(), p.getNhsNumber(), p.getGender()
            });
        }
    }

    private void addPatient() {
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField dob = new JTextField();
        JTextField nhs = new JTextField();
        JTextField gender = new JTextField();

        Object[] fields = {
                "First Name:", firstName,
                "Last Name:", lastName,
                "Date of Birth:", dob,
                "NHS Number:", nhs,
                "Gender (M/F):", gender
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Patient", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First and Last Name are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newId = generatePatientId();
        Patient p = new Patient(newId, firstName.getText());
        p.setLastName(lastName.getText());
        p.setDateOfBirth(dob.getText());
        p.setNhsNumber(nhs.getText());
        p.setGender(gender.getText());

        patientController.addPatient(p);
        loadPatients();
    }

    private void editPatient() {
        int row = patientTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = patientModel.getValueAt(row, 0).toString();
        Patient p = patientController.getPatientById(id);
        if (p == null) return;

        JTextField firstName = new JTextField(p.getFirstName());
        JTextField lastName = new JTextField(p.getLastName());
        JTextField dob = new JTextField(p.getDateOfBirth());
        JTextField nhs = new JTextField(p.getNhsNumber());
        JTextField gender = new JTextField(p.getGender());

        Object[] fields = {
                "First Name:", firstName,
                "Last Name:", lastName,
                "Date of Birth:", dob,
                "NHS Number:", nhs,
                "Gender (M/F):", gender
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Edit Patient", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        p.setFirstName(firstName.getText());
        p.setLastName(lastName.getText());
        p.setDateOfBirth(dob.getText());
        p.setNhsNumber(nhs.getText());
        p.setGender(gender.getText());

        patientController.updatePatient(p);
        loadPatients();
    }

    private void deletePatient() {
        int row = patientTable.getSelectedRow();
        if (row == -1) return;

        String id = patientModel.getValueAt(row, 0).toString();
        Patient p = patientController.getPatientById(id);
        if (p == null) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Delete patient " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            patientController.deletePatient(p);
            loadPatients();
        }
    }

    private String generatePatientId() {
        int max = 0;
        for (Patient p : patientController.getAllPatients()) {
            try {
                String num = p.getPatientId().replaceAll("[^0-9]", "");
                max = Math.max(max, Integer.parseInt(num));
            } catch (NumberFormatException ignored) {}
        }
        return String.format("P%03d", max + 1);
    }

    /* ================= APPOINTMENTS TAB ================= */
    private JPanel createAppointmentPanel() {
        appointmentModel = new DefaultTableModel(
                new String[]{"ID", "Patient", "Clinician", "Date"}, 0
        );
        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);
        loadAppointments();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        styleButton(refresh, new Color(52, 152, 219));
        refresh.addActionListener(e -> loadAppointments());
        JPanel btnPanel = new JPanel();
        btnPanel.add(refresh);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        for (Appointment a : appointmentController.getAllAppointments()) {
            Patient p = patientController.getPatientById(a.getPatientId());
            Clinician c = clinicianController.getClinicianById(a.getClinicianId());
            appointmentModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown",
                    c != null ? c.getFirstName() + " " + c.getLastName() : "Unknown",
                    a.getDate()
            });
        }
    }

    /* ================= CLINICIANS TAB ================= */
    private JPanel createClinicianPanel() {
        clinicianModel = new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "Speciality"}, 0
        );
        clinicianTable = new JTable(clinicianModel);
        styleTable(clinicianTable);
        loadClinicians();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(clinicianTable), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        styleButton(refresh, new Color(52, 152, 219));
        refresh.addActionListener(e -> loadClinicians());
        JPanel btnPanel = new JPanel();
        btnPanel.add(refresh);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadClinicians() {
        clinicianModel.setRowCount(0);
        for (Clinician c : clinicianController.getAllClinicians()) {
            clinicianModel.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getSpeciality()
            });
        }
    }

    /* ================= STYLING & LOGOUT ================= */
    private void styleTable(JTable table) {
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();

        new LoginView(
                patientController,
                clinicianController,
                staffController,
                appointmentController,
                prescriptionController,
                referralController
        );
    }
}
