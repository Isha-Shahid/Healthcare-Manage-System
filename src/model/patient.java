
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Patient, extending the base User class.
 * This class includes patient-specific demographic data and an association 
 * with their Appointment records.
 */
// **Correction 1: Class name must be capitalized (Patient)**
public class patient extends user {
    
    // **Correction 2: Attribute names should be camelCase (nhsNumber)**
    private String nhsNumber;
    private String address;
    private String gpSurgeryId; // The ID of the registered GP surgery/facility
    private String registrationDate;

    // Association: Patient to Appointments (Composition/Aggregation)
    private List<Appointment> appointments;

    // Constructor
    public patient(String userId, String firstName, String lastName, String dateOfBirth, String phone, String email,
                   String nhsNumber, String address, String gpSurgeryId, String registrationDate) {
        
        // Correctly call the constructor of the User superclass
        super(userId, firstName, lastName, dateOfBirth, phone, email);
        
        // Initialize Patient-specific attributes
        this.nhsNumber = nhsNumber;
        this.address = address;
        this.gpSurgeryId = gpSurgeryId;
        this.registrationDate = registrationDate;
        
        // Initialize the association list
        this.appointments = new ArrayList<>();
    }
    
    // **Correction 3: Must implement the abstract method from User (getRole)**
    @Override
    public String getRole() {
        return "Patient";
    }

    // --- Getters and Setters (Partial list - implement all of them) ---

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }
    
    // Method to fulfill the diagram's relationship: Patient to Appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}