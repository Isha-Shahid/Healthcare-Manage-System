package view;

import controller.ReferralController;
import model.Referral;

import javax.swing.*;
import java.awt.*;

public class ReferralForm extends JDialog {

    private JTextField referralIdField;
    private JTextField patientIdField;
    private JTextField clinicianIdField;
    private JComboBox<String> priorityBox;

    private final ReferralController referralController;
    private final Referral referral; // null = add, not null = edit

    public ReferralForm(JFrame parent, ReferralController rc, Referral referral) {
        super(parent, true);
        this.referralController = rc;
        this.referral = referral;

        setTitle(referral == null ? "Add Referral" : "Edit Referral");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        initUI();

        if (referral != null) loadData();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Referral ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Referral ID:"), gbc);
        referralIdField = new JTextField(15);
        gbc.gridx = 1;
        add(referralIdField, gbc);

        // Patient ID
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Patient ID:"), gbc);
        patientIdField = new JTextField(15);
        gbc.gridx = 1;
        add(patientIdField, gbc);

        // Clinician ID
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Clinician ID:"), gbc);
        clinicianIdField = new JTextField(15);
        gbc.gridx = 1;
        add(clinicianIdField, gbc);

        // Priority
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Priority:"), gbc);
        priorityBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        gbc.gridx = 1;
        add(priorityBox, gbc);

        // Buttons
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy++;
        add(saveBtn, gbc);
        gbc.gridx = 1;
        add(cancelBtn, gbc);

        saveBtn.addActionListener(e -> saveReferral());
        cancelBtn.addActionListener(e -> dispose());

        // If editing, Referral ID should not be editable
        if (referral != null) {
            referralIdField.setEditable(false);
        }
    }

    private void loadData() {
        referralIdField.setText(referral.getReferralId());
        patientIdField.setText(referral.getPatientId());
        clinicianIdField.setText(referral.getClinicianId());
        priorityBox.setSelectedItem(referral.getPriority());
    }

    private void saveReferral() {
        String rid = referralIdField.getText().trim();
        String pid = patientIdField.getText().trim();
        String cid = clinicianIdField.getText().trim();
        String priority = (String) priorityBox.getSelectedItem();

        if (rid.isEmpty() || pid.isEmpty() || cid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (referral == null) {
            // Add new referral
            Referral newReferral = new Referral(rid, pid, cid, priority);
            referralController.addReferral(newReferral);
        } else {
            // Update existing referral
        	referral.setPatientId(pid);
        	referral.setClinicianId(cid);
        	referral.setPriority(priority);
        	referralController.updateReferral(referral);
        }

        dispose(); // Close dialog
    }
}
