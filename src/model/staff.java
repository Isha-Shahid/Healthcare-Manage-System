
package model;

// Staff extends User, as per your diagram
// **Note: Class names should start with a capital letter (Staff, not staff)**
public abstract class staff extends user {
    private String staffId;
    private String department;
    private String facilityId;

    // Constructor (calls User constructor)
    public staff(String userId, String firstName, String lastName, String dateOfBirth, String phone, String email, 
                 String staffId, String department, String facilityId) {
        // Correctly calls the matching constructor in the User class
        super(userId, firstName, lastName, dateOfBirth, phone, email);
        
        // Initialize Staff-specific attributes
        this.staffId = staffId;
        this.department = department;
        this.facilityId = facilityId;
    }
    
    // --- Getters and Setters for Staff-specific attributes ---
    
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
    
    // As Staff is abstract, it doesn't need to implement the abstract getRole() 
    // from User, but its subclasses (e.g., Clinician, AdminStaff) must.
}