
package model;

// Based on your class diagram's User entity
public abstract class user {
    private String userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth; // Use String for simplicity with CSV, or LocalDate for better design
    private String phone;
    private String email;
    private String postCode;
    // ... add all other User attributes from your diagram

    // Constructor (example with core attributes)
    public user(String userId, String firstName, String lastName, String dateOfBirth, String phone, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
    }

    // Abstract method placeholder for required User functionality
    public abstract String getRole(); 

    // Getters and Setters (Implement these for ALL attributes)
    public String getUserId() {
        return userId;
    }
    // ... other getters and setters
}