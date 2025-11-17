
package view;

import controller.MainController;
import javax.swing.*;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    private MainController controller;

    public MainFrame(MainController controller) {
        this.controller = controller;
        setTitle("Healthcare Management System (MVC)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // --- Navigation Panel ---
        JPanel navPanel = createNavigationPanel();
        add(navPanel, BorderLayout.NORTH);

        // --- Main Content Area ---
        JTextArea contentArea = new JTextArea("Welcome to the System. Load data, then select a tab.");
        add(new JScrollPane(contentArea), BorderLayout.CENTER);
        
        // Example of a panel that would be dynamically swapped
        // PatientPanel patientView = new PatientPanel(controller);
        // add(patientView, BorderLayout.CENTER);

        setVisible(true);
    }
    
    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        // Add buttons for the main GUI functionality required by the marking guide (Patients, Clinicians, etc.)
        JButton patientBtn = new JButton("Manage Patients");
        // patientBtn.addActionListener(e -> displayPatientPanel()); // Link to controller method
        
        panel.add(patientBtn);
        // ... add buttons for Clinicians, Appointments, Prescriptions, Referrals
        return panel;
    }
}