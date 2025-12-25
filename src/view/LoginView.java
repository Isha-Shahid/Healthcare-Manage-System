package view;

import controller.*;
import model.Clinician;
import model.Patient;
import model.Staff;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField idField;
    private JButton loginBtn;

    private PatientController patientController;
    private ClinicianController clinicianController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    public LoginView(PatientController pc, ClinicianController cc, StaffController sc,
                     AppointmentController ac, PrescriptionController prc, ReferralController rc) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;

        setTitle("Healthcare System Login");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Welcome to Healthcare System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(65, 105, 225));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel label = new JLabel("Enter your ID:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(label, gbc);

        idField = new JTextField(20);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        add(idField, gbc);

        loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(60, 179, 113));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginBtn, gbc);

        loginBtn.addActionListener(e -> login());

        setLocationRelativeTo(null);
        setVisible(true);

        // Debug: print loaded IDs
        clinicianController.getAllClinicians().forEach(c ->
                System.out.println("Loaded Clinician ID: " + c.getClinicianId())
        );
        staffController.getAllStaff().forEach(s ->
                System.out.println("Loaded Staff ID: " + s.getStaffId())
        );
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35));
    }

    private void login() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID");
            return;
        }

        // ----------------- Patient login -----------------
        Patient matchedPatient = patientController.getAllPatients().stream()
                .filter(p -> p.getPatientId().trim().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (matchedPatient != null) {
            JOptionPane.showMessageDialog(this, "Patient login successful");
            JFrame frame = new JFrame("Patient Dashboard");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new PatientView(patientController, appointmentController, prescriptionController, matchedPatient));
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            this.dispose();
            return;
        }

        // ----------------- Clinician login -----------------
        Clinician matchedClinician = clinicianController.getAllClinicians().stream()
                .filter(c -> c.getClinicianId().trim().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (matchedClinician != null) {
            JOptionPane.showMessageDialog(this, "Clinician login successful");
            JFrame frame = new JFrame("Clinician Dashboard");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new ClinicianView(clinicianController, appointmentController, prescriptionController, referralController, matchedClinician));
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            this.dispose();
            return;
        }

        // ----------------- Staff login -----------------
        Staff matchedStaff = staffController.getAllStaff().stream()
                .filter(s -> s.getStaffId().trim().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (matchedStaff != null) {
            JOptionPane.showMessageDialog(this, "Staff login successful");
            JFrame frame = new JFrame("Staff Dashboard");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new StaffView(staffController, appointmentController, prescriptionController, referralController, matchedStaff));
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            this.dispose();
            return;
        }

        // ----------------- ID not found -----------------
        JOptionPane.showMessageDialog(this, "ID not found");
    }
}
