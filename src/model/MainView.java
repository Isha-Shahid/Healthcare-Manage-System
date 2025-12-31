package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JTabbedPane tabs;

    public MainView(PatientView patientView, ClinicianView clinicianView,
                    AppointmentView appointmentView, PrescriptionView prescriptionView) {

        setTitle("Healthcare System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabs = new JTabbedPane();
        tabs.addTab("Patients", patientView);
        tabs.addTab("Clinicians", clinicianView);
        tabs.addTab("Appointments", appointmentView);
        tabs.addTab("Prescriptions", prescriptionView);

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }
}
