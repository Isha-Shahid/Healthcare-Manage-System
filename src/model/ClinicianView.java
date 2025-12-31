package view;

import controller.*;
import model.Clinician;
import model.Prescription;
import model.Referral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private ClinicianController clinicianController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;
    private PatientController patientController;
    private StaffController staffController;

    private Clinician loggedInClinician;

    private JButton prescriptionBtn, referralBtn, logoutBtn;

    public ClinicianView(
            PatientController pc,
            ClinicianController cc,
            StaffController sc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc,
            Clinician loggedInClinician
    ) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;
        this.loggedInClinician = loggedInClinician;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);

        loadData();
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel(
                "Clinician Dashboard – Dr. " +
                        loggedInClinician.getFirstName() + " " +
                        loggedInClinician.getLastName()
        );
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 37, 41));

        logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(108, 117, 125));
        logoutBtn.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        return header;
    }

    /* ================= CENTER TABLE ================= */

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        model = new DefaultTableModel(
                new String[]{"Clinician ID", "First Name", "Last Name", "Speciality"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(13, 110, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 250));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /* ================= ACTION BUTTONS ================= */

    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        prescriptionBtn = new JButton("Add Prescription");
        referralBtn = new JButton("Create Referral");

        styleButton(prescriptionBtn, new Color(25, 135, 84));
        styleButton(referralBtn, new Color(255, 193, 7));

        prescriptionBtn.addActionListener(e -> addPrescription());
        referralBtn.addActionListener(e -> createReferral());

        panel.add(prescriptionBtn);
        panel.add(referralBtn);

        return panel;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(160, 36));
    }

    /* ================= DATA ================= */

    private void loadData() {
        model.setRowCount(0);

        List<Clinician> clinicians = List.of(loggedInClinician);

        for (Clinician c : clinicians) {
            model.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getSpeciality()
            });
        }
    }

    /* ================= PRESCRIPTION ================= */

    private void addPrescription() {
        String patientId = JOptionPane.showInputDialog(this, "Patient ID:");
        String medication = JOptionPane.showInputDialog(this, "Medication:");
        String dosage = JOptionPane.showInputDialog(this, "Dosage:");

        if (patientId == null || medication == null || dosage == null) return;

        Prescription p = new Prescription(
                "P" + System.currentTimeMillis(),
                patientId,
                loggedInClinician.getClinicianId(),
                "",
                medication,
                dosage,
                "",
                0,
                "",
                "",
                "",
                "",
                ""
        );

        prescriptionController.addPrescription(p);
        JOptionPane.showMessageDialog(this, "Prescription added successfully.");
    }

    /* ================= REFERRAL ================= */

    private void createReferral() {
        String patientId = JOptionPane.showInputDialog(this, "Patient ID:");
        String priority = JOptionPane.showInputDialog(this, "Priority (High / Medium / Low):");

        if (patientId == null || priority == null) return;

        Referral r = new Referral(
                "R" + System.currentTimeMillis(),
                patientId,
                loggedInClinician.getClinicianId(),
                priority
        );

        referralController.addReferral(r);
        JOptionPane.showMessageDialog(this, "Referral created successfully.");
    }

    /* ================= LOGOUT ================= */

    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();

        SwingUtilities.invokeLater(() -> new LoginView(
                patientController,
                clinicianController,
                staffController,
                appointmentController,
                prescriptionController,
                referralController
        ));
    }
}
