package view;

import controller.ReferralController;
import model.Referral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReferralView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ReferralView() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Referral ID", "Patient ID", "Clinician ID"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void loadReferrals(List<Referral> referrals) {
        model.setRowCount(0);
        for (Referral r : referrals) {
            model.addRow(new Object[]{r.getReferralId(), r.getPatientId(), r.getClinicianId()});
        }
    }
}
