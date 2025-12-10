package app;

import controller.*;
import model.*;
import view.*;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // -------------------- Load data from CSV --------------------
        List<Patient> patients = DataStore.loadPatients("patients.csv");
        List<Clinician> clinicians = DataStore.loadClinicians("clinicians.csv");
        List<Appointment> appointments = DataStore.loadAppointments("appointments.csv");
        List<Prescription> prescriptions = DataStore.loadPrescriptions("prescriptions.csv");
        List<Referral> referrals = DataStore.loadReferrals("referrals.csv");

        // -------------------- Initialize controllers --------------------
        PatientController patientController = new PatientController();
        for (Patient p : patients) patientController.addPatient(p);

        ClinicianController clinicianController = new ClinicianController();
        for (Clinician c : clinicians) clinicianController.addClinician(c);

        AppointmentController appointmentController = new AppointmentController();
        for (Appointment a : appointments) appointmentController.scheduleAppointment(a);

        PrescriptionController prescriptionController = new PrescriptionController();
        for (Prescription p : prescriptions) prescriptionController.addPrescription(p);

        ReferralController referralController = new ReferralController();
        for (Referral r : referrals) referralController.createReferral(r);

        // -------------------- Optional: Add sample data --------------------
        if (patients.isEmpty()) {
            Patient p1 = new Patient("P001", "S001");
            p1.setFirstName("Alice");
            p1.setLastName("Brown");
            patientController.addPatient(p1);
        }

        if (clinicians.isEmpty()) {
            Clinician c1 = new Clinician("C001", "John", "Doe", "Cardiology");
            clinicianController.addClinician(c1);
        }

        // -------------------- Initialize views --------------------
        PatientView patientView = new PatientView(patientController);
        ClinicianView clinicianView = new ClinicianView(clinicianController);

        // -------------------- Create JFrame --------------------
        JFrame frame = new JFrame("Healthcare System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(patientView);
        frame.add(clinicianView);

        frame.pack();
        frame.setVisible(true);
    }
}
