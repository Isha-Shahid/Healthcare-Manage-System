package app;

import model.*;
import java.util.List;

public class AppLoader {
    public static void main(String[] args) {
        // File paths
        String patientFile = "data/patients.csv";
        String clinicianFile = "data/clinicians.csv";
        String facilityFile = "data/facilities.csv";
        String staffFile = "data/staff.csv";
        String appointmentFile = "data/appointments.csv";
        String prescriptionFile = "data/prescriptions.csv";
        String referralFile = "data/referrals.csv";

        // Load data from CSVs
        List<Patient> patients = DataStore.loadPatients(patientFile);
        List<Clinician> clinicians = DataStore.loadClinicians(clinicianFile);
        List<Facility> facilities = DataStore.loadFacilities(facilityFile);
        List<Staff> staffList = DataStore.loadStaff(staffFile);
        List<Appointment> appointments = DataStore.loadAppointments(appointmentFile);
        List<Prescription> prescriptions = DataStore.loadPrescriptions(prescriptionFile);
        List<Referral> referrals = DataStore.loadReferrals(referralFile);

        // Get system managers
        SystemManager sm = SystemManager.getInstance();
        ReferralManager rm = ReferralManager.getInstance();

        // Add data to system managers
        for (Patient p : patients) sm.addPatient(p);
        for (Clinician c : clinicians) sm.addClinician(c);
        for (Facility f : facilities) sm.addFacility(f);
        for (Staff s : staffList) sm.addStaff(s);
        for (Appointment a : appointments) sm.addAppointment(a);
        for (Prescription p : prescriptions) sm.addPrescription(p);
        for (Referral r : referrals) rm.addReferral(r);

        // Print confirmation
        System.out.println("Data loaded successfully!");
        System.out.println("Patients: " + patients.size());
        System.out.println("Clinicians: " + clinicians.size());
        System.out.println("Facilities: " + facilities.size());
        System.out.println("Staff: " + staffList.size());
        System.out.println("Appointments: " + appointments.size());
        System.out.println("Prescriptions: " + prescriptions.size());
        System.out.println("Referrals: " + referrals.size());
    }
}
