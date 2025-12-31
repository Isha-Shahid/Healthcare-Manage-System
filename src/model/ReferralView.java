package view;

import controller.*;
import model.Referral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReferralView extends JPanel {

    private ReferralController referralController;
    private PatientController patientController;
    private ClinicianController clinicianController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;

    private JTable table;
    private DefaultTableModel model;

    private JButton addBtn, editBtn, deleteBtn, logoutBtn;

    public ReferralView(
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

        initUI();
        loadReferrals();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        add(createHeader(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(245, 245, 250));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("Referral Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(new Color(65, 105, 225));

        logoutBtn = new JButton("Logout");
        styleButton(logoutBtn, new Color(120, 120, 120));
        logoutBtn.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        return header;
    }

    /* ================= TABLE ================= */

    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(
                new String[]{"Referral ID", "Patient ID", "Clinician ID", "Priority"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        return new JScrollPane(table);
    }

    /* ================= ACTION PANEL ================= */

    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");

        styleButton(addBtn, new Color(60, 179, 113));
        styleButton(editBtn, new Color(65, 105, 225));
        styleButton(deleteBtn, new Color(220, 20, 60));

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);

        addBtn.addActionListener(e -> openAddForm());
        editBtn.addActionListener(e -> openEditForm());
        deleteBtn.addActionListener(e -> deleteReferral());

        return panel;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(150, 32));
    }

    /* ================= DATA ================= */

    private void loadReferrals() {
        model.setRowCount(0);
        List<Referral> referrals = referralController.getAllReferrals();

        for (Referral r : referrals) {
            model.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getClinicianId(),
                    r.getPriority()
            });
        }
    }

    /* ================= CRUD ================= */

    private void openAddForm() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        ReferralForm form = new ReferralForm(parent, referralController, null);
        form.setVisible(true);
        loadReferrals();
    }

    private void openEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to edit");
            return;
        }

        String referralId = (String) model.getValueAt(row, 0);
        Referral referral = referralController.getAllReferrals()
                .stream()
                .filter(r -> r.getReferralId().equals(referralId))
                .findFirst()
                .orElse(null);

        if (referral != null) {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            ReferralForm form = new ReferralForm(parent, referralController, referral);
            form.setVisible(true);
            loadReferrals();
        }
    }

    private void deleteReferral() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this referral?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        String referralId = (String) model.getValueAt(row, 0);
        referralController.getAllReferrals()
                .removeIf(r -> r.getReferralId().equals(referralId));

        loadReferrals();
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
