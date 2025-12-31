package view;

import controller.*;
import model.Appointment;
import model.Clinician;
import model.Patient;
import model.Prescription;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientDashboard extends JPanel {

    private PatientController patientController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ClinicianController clinicianController;
    private Patient loggedInPatient;

    private JTable appointmentTable, prescriptionTable;
    private DefaultTableModel appointmentModel, prescriptionModel;
    private JButton logoutBtn;

    public PatientDashboard(
            PatientController pc,
            AppointmentController ac,
            PrescriptionController prc,
            ClinicianController cc,
            Patient loggedInPatient
    ) {
        this.patientController = pc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.clinicianController = cc;
        this.loggedInPatient = loggedInPatient;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        loadAppointments();
        loadPrescriptions();
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcome = new JLabel(
                "Welcome, " + loggedInPatient.getFirstName() + " " + loggedInPatient.getLastName()
        );
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(new Color(33, 37, 41));

        logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(108, 117, 125));
        logoutBtn.addActionListener(e -> logout());

        header.add(welcome, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        return header;
    }

    /* ================= MAIN CONTENT ================= */

    private JComponent createContent() {
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        wrapper.setBackground(new Color(240, 242, 245));

        wrapper.add(createStatsPanel(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.setBackground(Color.WHITE);

        tabs.add("Appointments", createAppointmentPanel());
        tabs.add("Prescriptions", createPrescriptionPanel());

        wrapper.add(tabs, BorderLayout.CENTER);

        return wrapper;
    }

    /* ================= STATS PANEL ================= */

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(1, 2, 10, 10));
        stats.setBackground(new Color(240, 242, 245));
        stats.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        int appointmentCount = appointmentController.getAppointmentsByPatientId(loggedInPatient.getPatientId()).size();
        long prescriptionCount = prescriptionController.getAllPrescriptions().stream()
                .filter(p -> p.getPatientId().equals(loggedInPatient.getPatientId()))
                .count();

        JLabel appointmentLabel = createStatCard("Appointments", appointmentCount, new Color(13, 110, 253));
        JLabel prescriptionLabel = createStatCard("Prescriptions", prescriptionCount, new Color(0, 123, 255));

        stats.add(appointmentLabel);
        stats.add(prescriptionLabel);

        return stats;
    }

    private JLabel createStatCard(String title, long value, Color bg) {
        JLabel label = new JLabel("<html><center>" + title + "<br/><h2>" + value + "</h2></center></html>", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return label;
    }

    /* ================= APPOINTMENTS ================= */

    private JPanel createAppointmentPanel() {
        appointmentModel = new DefaultTableModel(
                new String[]{"Appointment ID", "Date", "Doctor", "Status"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);

        addRowStriping(appointmentTable);

        return wrapTable(appointmentTable);
    }

    /* ================= PRESCRIPTIONS ================= */

    private JPanel createPrescriptionPanel() {
        prescriptionModel = new DefaultTableModel(
                new String[]{"Prescription ID", "Medication", "Dosage", "Doctor"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        prescriptionTable = new JTable(prescriptionModel);
        styleTable(prescriptionTable);

        addRowStriping(prescriptionTable);

        return wrapTable(prescriptionTable);
    }

    /* ================= STYLING ================= */

    private JPanel wrapTable(JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scrollPane, BorderLayout.CENTER);
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

    private void addRowStriping(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                return c;
            }
        });
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(110, 34));
    }

    /* ================= DATA ================= */

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        if (loggedInPatient == null) return;

        List<Appointment> appointments =
                appointmentController.getAppointmentsByPatientId(loggedInPatient.getPatientId());

        for (Appointment a : appointments) {
            appointmentModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getDate(),
                    getClinicianName(a.getClinicianId()),
                    "Scheduled"
            });
        }
    }

    private void loadPrescriptions() {
        prescriptionModel.setRowCount(0);
        if (loggedInPatient == null) return;

        boolean found = false;

        for (Prescription p : prescriptionController.getAllPrescriptions()) {
            if (p.getPatientId().equals(loggedInPatient.getPatientId())) {
                found = true;
                prescriptionModel.addRow(new Object[]{
                        p.getPrescriptionId(),
                        p.getMedication(),
                        p.getDosage(),
                        getClinicianName(p.getClinicianId())
                });
            }
        }

        if (!found) {
            prescriptionModel.addRow(new Object[]{"No prescriptions", "", "", ""});
        }
    }

    private String getClinicianName(String clinicianId) {
        if (clinicianId == null) return "Unknown";

        return clinicianController.getAllClinicians().stream()
                .filter(c -> c.getClinicianId().equals(clinicianId))
                .map(c -> c.getFirstName() + " " + c.getLastName())
                .findFirst()
                .orElse("Unknown");
    }

    /* ================= LOGOUT ================= */

    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        // Redirect to LoginView if needed
    }
}
