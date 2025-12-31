package view;

import controller.StaffController;
import controller.PatientController;
import controller.AppointmentController;
import controller.ReferralController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffDashboard extends JPanel {
    private StaffController staffController;
    private PatientController patientController;
    private AppointmentController appointmentController;
    private ReferralController referralController;

    private JTable patientTable, appointmentTable, referralTable;
    private DefaultTableModel patientModel, appointmentModel, referralModel;

    public StaffDashboard(StaffController sc, PatientController pc, AppointmentController ac, ReferralController rc) {
        this.staffController = sc;
        this.patientController = pc;
        this.appointmentController = ac;
        this.referralController = rc;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Staff Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(65, 105, 225));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Patients tab
        patientModel = new DefaultTableModel(new String[]{"Patient ID", "Name", "Age", "Gender"}, 0);
        patientTable = new JTable(patientModel);
        styleTable(patientTable);
        tabs.add("Patients", new JScrollPane(patientTable));

        // Appointments tab
        appointmentModel = new DefaultTableModel(new String[]{"Appointment ID", "Patient", "Date", "Status"}, 0);
        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);
        tabs.add("Appointments", new JScrollPane(appointmentTable));

        // Referrals tab
        referralModel = new DefaultTableModel(new String[]{"Referral ID", "Patient", "From", "To", "Status"}, 0);
        referralTable = new JTable(referralModel);
        styleTable(referralTable);
        tabs.add("Referrals", new JScrollPane(referralTable));

        add(tabs, BorderLayout.CENTER);

        loadPatients();
        loadAppointments();
        loadReferrals();
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(13, 110, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 250));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Add row striping
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                return c;
            }
        });
    }

    private void loadPatients() {
        patientModel.setRowCount(0);
        // load patient data
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        // load appointment data
    }

    private void loadReferrals() {
        referralModel.setRowCount(0);
        // load referral data
    }
}
