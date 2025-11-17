
package util;

import model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileHandler {

    // Method to load all patients from the CSV file
    public static void loadPatients(String filePath, List<Patient> patientList) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Assuming the CSV columns match the Patient constructor order
                if (values.length >= 10) { 
                    Patient patient = new Patient(
                        values[0], // userId
                        values[1], // firstName
                        values[2], // lastName
                        values[3], // dateOfBirth
                        values[4], // phone
                        values[5], // email
                        values[6], // nhsNumber
                        values[7], // address
                        values[8], // gpSurgeryId
                        values[9]  // registrationDate
                    );
                    patientList.add(patient);
                }
            }
            System.out.println("Successfully loaded " + patientList.size() + " patients.");
        } catch (IOException e) {
            System.err.println("Could not load patients.csv: " + e.getMessage());
        }
    }
    
    // public static void loadClinicians(...) { ... }
    // public static void loadAppointments(...) { ... }
    
    // public static void appendPrescription(Prescription p, String filePath) { 
    //     // Logic to write a new prescription record to prescriptions.csv
    // }
}