package view;

import controller.*;
import model.Prescription;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PrescriptionView extends JPanel {

    private PrescriptionController prescriptionController;
    private PatientController patientController;
    private ClinicianController clinicianController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private ReferralController referralController;

    private JTable table;
    private DefaultTableModel model;

    private JButton addBtn, editBtn, deleteBtn, logoutBtn;

    public PrescriptionView(
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

        JLabel title = new JLabel("Prescription Management");
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
                new String[]{
                        "Prescription ID",
                        "Patient ID",
                        "Clinician ID",
                        "Appointment ID",
                        "Medication",
                        "Dosage",
                        "Frequency",
                        "Duration"
                }, 0
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

        addBtn.addActionListener(e -> addPrescription());
        editBtn.addActionListener(e -> editPrescription());
        deleteBtn.addActionListener(e -> deletePrescription());

        return panel;
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(150, 32));
    }

    /* ================= DATA ================= */

    private void loadData() {
        model.setRowCount(0);
        List<Prescription> prescriptions = prescriptionController.getAllPrescriptions();

        for (Prescription p : prescriptions) {
            model.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getAppointmentId(),
                    p.getMedication(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getDuration()
            });
        }
    }

    /* ================= CRUD ================= */

    private void addPrescription() {
        JTextField idField = new JTextField();
        JTextField patientField = new JTextField();
        JTextField clinicianField = new JTextField();
        JTextField appointmentField = new JTextField();
        JTextField medicationField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField frequencyField = new JTextField();
        JTextField durationField = new JTextField();

        Object[] message = {
                "Prescription ID:", idField,
                "Patient ID:", patientField,
                "Clinician ID:", clinicianField,
                "Appointment ID:", appointmentField,
                "Medication:", medicationField,
                "Dosage:", dosageField,
                "Frequency:", frequencyField,
                "Duration (days):", durationField
        };

        int option = JOptionPane.showConfirmDialog(
                this, message, "Add Prescription", JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                Prescription p = new Prescription(
                        idField.getText(),
                        patientField.getText(),
                        clinicianField.getText(),
                        appointmentField.getText(),
                        medicationField.getText(),
                        dosageField.getText(),
                        frequencyField.getText(),
                        Integer.parseInt(durationField.getText()),
                        "", "", "", "", ""
                );
                prescriptionController.addPrescription(p);
                loadData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Duration must be a number");
            }
        }
    }

    private void editPrescription() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = (String) model.getValueAt(row, 0);
        Prescription p = prescriptionController.getPrescriptionById(id);
        if (p == null) return;

        JTextField patientField = new JTextField(p.getPatientId());
        JTextField clinicianField = new JTextField(p.getClinicianId());
        JTextField appointmentField = new JTextField(p.getAppointmentId());
        JTextField medicationField = new JTextField(p.getMedication());
        JTextField dosageField = new JTextField(p.getDosage());
        JTextField frequencyField = new JTextField(p.getFrequency());
        JTextField durationField = new JTextField(String.valueOf(p.getDuration()));

        Object[] message = {
                "Patient ID:", patientField,
                "Clinician ID:", clinicianField,
                "Appointment ID:", appointmentField,
                "Medication:", medicationField,
                "Dosage:", dosageField,
                "Frequency:", frequencyField,
                "Duration (days):", durationField
        };

        int option = JOptionPane.showConfirmDialog(
                this, message, "Edit Prescription", JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                p.setPatientId(patientField.getText());
                p.setClinicianId(clinicianField.getText());
                p.setAppointmentId(appointmentField.getText());
                p.setMedication(medicationField.getText());
                p.setDosage(dosageField.getText());
                p.setFrequency(frequencyField.getText());
                p.setDuration(Integer.parseInt(durationField.getText()));
                prescriptionController.updatePrescription(p);
                loadData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Duration must be a number");
            }
        }
    }

    private void deletePrescription() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = (String) model.getValueAt(row, 0);
        Prescription p = prescriptionController.getPrescriptionById(id);

        if (p != null) {
            prescriptionController.deletePrescription(p);
            loadData();
        }
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
