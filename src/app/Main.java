package app;

import controller.*;
import model.*;
import view.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Initialize controllers
        PatientController patientController = new PatientController();
        ClinicianController clinicianController = new ClinicianController();
        AppointmentController appointmentController = new AppointmentController();
        PrescriptionController prescriptionController = new PrescriptionController();
        ReferralController referralController = new ReferralController();

        // Add some sample patients/clinicians here if needed
        Patient p1 = new Patient("P001", "S001");
        p1.setFirstName("Alice");
        p1.setLastName("Brown");
        patientController.addPatient(p1);

        Clinician c1 = new Clinician("C001", "John", "Doe", "Cardiology");
        clinicianController.addClinician(c1);

        // Initialize views (pass controllers to them)
        PatientView patientView = new PatientView(patientController);
        ClinicianView clinicianView = new ClinicianView(clinicianController);

        // Create a JFrame to display the GUI
        JFrame frame = new JFrame("Healthcare System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(patientView);
        frame.add(clinicianView);

        frame.pack();
        frame.setVisible(true);
    }
}
