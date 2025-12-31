package view;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientForm extends JDialog {

    private JTextField idField, firstField, lastField, gpField;
    private PatientController controller;
    private Patient patient;

    public PatientForm(JFrame parent, PatientController controller, Patient patient) {
        super(parent, true);
        this.controller = controller;
        this.patient = patient;

        setTitle(patient == null ? "Add Patient" : "Edit Patient");
        setSize(400, 280);
        setLocationRelativeTo(parent);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ---------- Header ----------
        JLabel title = new JLabel(
                patient == null ? "Add New Patient" : "Edit Patient Details"
        );
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        add(title, BorderLayout.NORTH);

        // ---------- Form ----------
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Patient ID"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("First Name"));
        firstField = new JTextField();
        formPanel.add(firstField);

        formPanel.add(new JLabel("Last Name"));
        lastField = new JTextField();
        formPanel.add(lastField);

        formPanel.add(new JLabel("GP Surgery ID"));
        gpField = new JTextField();
        formPanel.add(gpField);

        add(formPanel, BorderLayout.CENTER);

        // ---------- Buttons ----------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        styleButton(saveBtn, new Color(60, 179, 113));
        styleButton(cancelBtn, new Color(220, 20, 60));

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // ---------- Edit mode ----------
        if (patient != null) {
            idField.setText(patient.getPatientId());
            idField.setEnabled(false);
            firstField.setText(patient.getFirstName());
            lastField.setText(patient.getLastName());
            gpField.setText(patient.getGpSurgeryId());
        }

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void save() {
        if (idField.getText().isEmpty()
                || firstField.getText().isEmpty()
                || lastField.getText().isEmpty()
                || gpField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        if (patient == null) {
            Patient p = new Patient(idField.getText(), gpField.getText());
            p.setFirstName(firstField.getText());
            p.setLastName(lastField.getText());
            controller.addPatient(p);
        } else {
            patient.setFirstName(firstField.getText());
            patient.setLastName(lastField.getText());
            patient.setGpSurgeryId(gpField.getText());
            controller.updatePatient(patient);
        }

        dispose();
    }
}
