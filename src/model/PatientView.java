package view;

import controller.*;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private PatientController patientController;
    private ClinicianController clinicianController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private Patient loggedInPatient;

    public PatientView(PatientController pc, ClinicianController cc, StaffController sc,
                       AppointmentController ac, PrescriptionController prc,
                       ReferralController rc, Patient loggedInPatient) {

        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;
        this.loggedInPatient = loggedInPatient;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initTopPanel();
        initMainContent();
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(65, 105, 225));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel;
        if (loggedInPatient != null) {
            welcomeLabel = new JLabel("Welcome, " + loggedInPatient.getFirstName() + " " + loggedInPatient.getLastName());
        } else {
            welcomeLabel = new JLabel("Patient Management");
        }
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        // No logout button here

        add(topPanel, BorderLayout.NORTH);
    }

    private void initMainContent() {
        if (loggedInPatient != null) {
            PatientDashboard dashboard = new PatientDashboard(
                    patientController,
                    appointmentController,
                    prescriptionController,
                    clinicianController,
                    loggedInPatient
            );
            add(dashboard, BorderLayout.CENTER);
        } else {
            model = new DefaultTableModel(new String[]{"Patient ID", "First Name", "Last Name", "GP Surgery ID"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(model);
            styleTable(table);
            add(new JScrollPane(table), BorderLayout.CENTER);

            JPanel controls = new JPanel();
            controls.setBackground(Color.WHITE);
            JButton addBtn = new JButton("Add");
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");

            styleControlButton(addBtn);
            styleControlButton(editBtn);
            styleControlButton(deleteBtn);

            addBtn.addActionListener(e -> openForm(null));
            editBtn.addActionListener(e -> editPatient());
            deleteBtn.addActionListener(e -> deletePatient());

            controls.add(addBtn);
            controls.add(editBtn);
            controls.add(deleteBtn);

            add(controls, BorderLayout.SOUTH);
            loadData();
        }
    }

    private void styleControlButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 139, 87));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113));
            }
        });
    }

    private void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadData() {
        if (loggedInPatient != null) return;

        model.setRowCount(0);
        List<Patient> patients = patientController.getAllPatients();
        for (Patient p : patients) {
            model.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getGpSurgeryId()
            });
        }
    }

    private void openForm(Patient patient) {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        PatientForm form = new PatientForm(parent, patientController, patient);
        form.setVisible(true);
        loadData();
    }

    private void editPatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient first");
            return;
        }

        String id = (String) model.getValueAt(row, 0);
        Patient patient = patientController.getAllPatients()
                .stream()
                .filter(p -> p.getPatientId().equals(id))
                .findFirst()
                .orElse(null);

        if (patient != null) openForm(patient);
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this patient?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) model.getValueAt(row, 0);
            patientController.deletePatientById(id);
            loadData();
        }
    }
}
