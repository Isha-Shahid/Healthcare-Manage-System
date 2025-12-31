package view;

import controller.*;
import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StaffView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;
    private PatientController patientController;
    private ClinicianController clinicianController;

    private Staff loggedInStaff;

    private JButton addBtn, editBtn, deleteBtn, logoutBtn;

    public StaffView(
            PatientController pc,
            ClinicianController cc,
            StaffController sc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc,
            Staff loggedInStaff
    ) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;
        this.loggedInStaff = loggedInStaff;

        initUI();
        loadData();
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

        JLabel title;
        if (loggedInStaff != null) {
            title = new JLabel(
                    "Welcome, " + loggedInStaff.getFirstName() + " " + loggedInStaff.getLastName()
            );
        } else {
            title = new JLabel("Staff Management");
        }

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
                new String[]{"Staff ID", "First Name", "Last Name", "Role"}, 0
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

        if (loggedInStaff == null) {
            panel.add(addBtn);
            panel.add(editBtn);
            panel.add(deleteBtn);

            addBtn.addActionListener(e -> addStaff());
            editBtn.addActionListener(e -> editStaff());
            deleteBtn.addActionListener(e -> deleteStaff());
        }

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

    private void loadData() {
        model.setRowCount(0);

        List<Staff> staffList =
                loggedInStaff != null
                        ? List.of(loggedInStaff)
                        : staffController.getAllStaff();

        for (Staff s : staffList) {
            model.addRow(new Object[]{
                    s.getStaffId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getRole()
            });
        }
    }

    /* ================= STAFF CRUD ================= */

    private void addStaff() {
        String id = JOptionPane.showInputDialog(this, "Staff ID:");
        String first = JOptionPane.showInputDialog(this, "First Name:");
        String last = JOptionPane.showInputDialog(this, "Last Name:");
        String role = JOptionPane.showInputDialog(this, "Role:");

        if (id != null && first != null && last != null && role != null) {
            staffController.addStaff(new Staff(id, first, last, role));
            loadData();
        }
    }

    private void editStaff() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String staffId = (String) model.getValueAt(row, 0);
        Staff s = staffController.getAllStaff()
                .stream()
                .filter(x -> x.getStaffId().equals(staffId))
                .findFirst()
                .orElse(null);

        if (s == null) return;

        String first = JOptionPane.showInputDialog(this, "First Name:", s.getFirstName());
        String last = JOptionPane.showInputDialog(this, "Last Name:", s.getLastName());
        String role = JOptionPane.showInputDialog(this, "Role:", s.getRole());

        if (first != null) s.setFirstName(first);
        if (last != null) s.setLastName(last);
        if (role != null) s.setRole(role);

        loadData();
    }

    private void deleteStaff() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String staffId = (String) model.getValueAt(row, 0);
        staffController.getAllStaff()
                .removeIf(s -> s.getStaffId().equals(staffId));

        loadData();
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
