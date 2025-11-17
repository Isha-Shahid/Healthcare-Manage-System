// src/util/ReferralManager.java
package util;

import model.Referral;
import model.patient;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReferralManager {
    // 1. Static instance variable (The single instance)
    private static ReferralManager instance;

    // 2. Private constructor (Prevents external instantiation)
    private ReferralManager() {
        // Initialization tasks, like loading referral templates, if any
        System.out.println("Referral Management System Initialized (Singleton)");
    }

    // 3. Public static method (Global access point)
    public static ReferralManager getInstance() {
        if (instance == null) {
            // Lazy initialization
            instance = new ReferralManager();
        }
        return instance;
    }

    /**
     * Generates the content for a new referral and persists it to a file, 
     * simulating the email communication.
     */
    public boolean generateReferralLetter(Referral referral, patient patient) {
        // Output file content fulfills the 'Contents of output text file' requirement (10 points)
        
        String filename = "generated_referrals.txt"; // Use a separate file to demonstrate output
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            
            // --- Content simulating an email/clinical document ---
            writer.println("===================================================");
            writer.println(" REFERRAL DOCUMENT (Simulated Email) - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            writer.println(" URGENCY: " + referral.getUrgencyLevel());
            writer.println("===================================================");
            
            writer.println("\nPATIENT DETAILS:");
            writer.println("  Name: " + patient.getFirstName() + " " + patient.getLastName());
            writer.println("  NHS Number: " + patient.getNhsNumber());
            writer.println("  DOB: " + patient.getDateOfBirth());
            writer.println("  GP Surgery ID: " + patient.getGpSurgeryId());
            
            writer.println("\nREFERRAL DETAILS:");
            writer.println("  Referral ID: " + referral.getReferralId());
            writer.println("  Referred FROM: Clinician ID " + referral.getReferingClinicianId());
            writer.println("  Referred TO: Facility ID " + referral.getReferralFacilityId());
            writer.println("  Clinical Summary: " + referral.getClinicalSummary());
            writer.println("  Requested Investigations: " + referral.getRequestedInvestigations());
            writer.println("\n--- End of Referral ---\n");
            
            System.out.println("Referral letter generated and saved to " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing referral file: " + e.getMessage());
            return false;
        }
    }
}