// src/controller/MainController.java
package controller;

import model.DataStore;
import model.patient;
import view.MainFrame;
import javax.swing.SwingUtilities;

public class MainController {
    private DataStore dataStore;
    private MainFrame view;

    public MainController() {
        this.dataStore = new DataStore();
        // 1. Load data before starting GUI
        this.dataStore.loadAllData(); 
        
        // 2. Initialize the view and pass this controller instance
        this.view = new MainFrame(this); 
    }

    // Public method for the GUI to call when it needs patient data
    public List<patient> getAllPatients() {
        return dataStore.getAllPatients();
    }
    
    // public void handleNewPatientRequest(String data) { ... }
    
    public static void main(String[] args) {
        // Start the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MainController());
    }
}