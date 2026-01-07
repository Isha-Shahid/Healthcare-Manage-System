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
    private PatientController patientController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private String clinicianId;
    private JTable appointmentTable;
    private DefaultTableModel appointmentModel;

    public ClinicianDashboard(
            PatientController pc,
            ClinicianController cc,
            StaffController sc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc,
            String clinicianId
    ) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;
        this.clinicianId = clinicianId;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        loadAppointments();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Welcome, Clinician " + clinicianId);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JButton logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(108, 117, 125));
        logoutBtn.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        return header;
    }

    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Appointments", createAppointmentPanel());
        // you can add more tabs later (e.g., Patients, Prescriptions)
        return tabs;
    }

    private JScrollPane createAppointmentPanel() {
        appointmentModel = new DefaultTableModel(
                new String[]{"ID", "Patient Name", "Date"}, 0
        );
        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        // optional: buttons if needed in the future
        JPanel btnPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        styleButton(refreshBtn, new Color(52, 152, 219));
        refreshBtn.addActionListener(e -> loadAppointments());
        btnPanel.add(refreshBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return new JScrollPane(panel);
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        List<Appointment> list = appointmentController.getAppointmentsByClinicianId(clinicianId);

        for (Appointment a : list) {
            Patient p = patientController.getPatientById(a.getPatientId());
            appointmentModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown",
                    a.getDate()
            });
        }
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
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
