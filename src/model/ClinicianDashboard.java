package view;

import controller.*;
import model.Appointment;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianDashboard extends JPanel {

    private ClinicianController clinicianController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private PatientController patientController;

    private JTable patientTable, appointmentTable;
    private DefaultTableModel patientModel, appointmentModel;

    private JButton logoutBtn;
    private String loggedInClinicianId;

    public ClinicianDashboard(
            ClinicianController cc,
            PatientController pc,
            AppointmentController ac,
            PrescriptionController prc,
            String clinicianId
    ) {
        this.clinicianController = cc;
        this.patientController = pc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.loggedInClinicianId = clinicianId;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        loadPatients();
        loadAppointments();
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Clinician Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 37, 41));

        logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(108, 117, 125));
        logoutBtn.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        return header;
    }

    /* ================= MAIN CONTENT ================= */

    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.setBackground(Color.WHITE);

        tabs.add("Patients", createPatientsPanel());
        tabs.add("Appointments", createAppointmentsPanel());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        wrapper.setBackground(new Color(240, 242, 245));
        wrapper.add(tabs, BorderLayout.CENTER);

        return wrapper;
    }

    /* ================= PATIENTS ================= */

    private JPanel createPatientsPanel() {
        patientModel = new DefaultTableModel(
                new String[]{"Patient ID", "Name", "Date of Birth", "Gender"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        patientTable = new JTable(patientModel);
        styleTable(patientTable);

        return wrapTable(patientTable);
    }

    /* ================= APPOINTMENTS ================= */

    private JPanel createAppointmentsPanel() {
        appointmentModel = new DefaultTableModel(
                new String[]{"Appointment ID", "Patient", "Date", "Status"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);

        return wrapTable(appointmentTable);
    }

    /* ================= STYLING ================= */

    private JPanel wrapTable(JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(13, 110, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 250));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(110, 34));
    }

    /* ================= DATA ================= */

    private void loadPatients() {
        patientModel.setRowCount(0);
        if (loggedInClinicianId == null) return;

        List<Patient> patients = patientController.getAllPatients()
                .stream()
                .filter(p -> loggedInClinicianId.equals(p.getGpSurgeryId()))
                .toList();

        for (Patient p : patients) {
            patientModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName() + " " + p.getLastName(),
                    p.getDateOfBirth(),
                    p.getGender()
            });
        }
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        if (loggedInClinicianId == null) return;

        List<Appointment> appointments =
                appointmentController.getAppointmentsByClinicianId(loggedInClinicianId);

        for (Appointment a : appointments) {
            String patientName = patientController.getAllPatients()
                    .stream()
                    .filter(p -> p.getPatientId().equals(a.getPatientId()))
                    .map(p -> p.getFirstName() + " " + p.getLastName())
                    .findFirst()
                    .orElse("Unknown");

            appointmentModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    patientName,
                    a.getDate(),
                    "Scheduled"
            });
        }
    }

    /* ================= LOGOUT ================= */

    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }
}
