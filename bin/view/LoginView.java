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
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove window borders for modern look
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome to Healthcare System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(65, 105, 225));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel label = new JLabel("Enter your ID:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(label, gbc);

        idField = new JTextField(20);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(idField, gbc);

        loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(60, 179, 113));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> login());
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
    }

    private void login() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID");
            return;
        }

        // Patient
        Patient patient = patientController.getAllPatients().stream()
                .filter(p -> p.getPatientId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
        if (patient != null) {
            openDashboard("Patient Dashboard",
                    new PatientView(patientController, clinicianController, staffController,
                            appointmentController, prescriptionController, referralController, patient));
            return;
        }

        // Clinician
        Clinician clinician = clinicianController.getAllClinicians().stream()
                .filter(c -> c.getClinicianId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
        if (clinician != null) {
            openDashboard("Clinician Dashboard",
                    new ClinicianView(patientController, clinicianController, staffController,
                            appointmentController, prescriptionController, referralController, clinician));
            return;
        }

        // Staff
        Staff staff = staffController.getAllStaff().stream()
                .filter(s -> s.getStaffId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
        if (staff != null) {
            openDashboard("Staff Dashboard",
                    new StaffView(patientController, clinicianController, staffController,
                            appointmentController, prescriptionController, referralController, staff));
            return;
        }

        JOptionPane.showMessageDialog(this, "ID not found");
    }

    private void openDashboard(String title, JPanel view) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(view);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        dispose();
    }
}
