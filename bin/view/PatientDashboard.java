package view;

import controller.*;
import model.Appointment;
import model.Patient;
import model.Prescription;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class PatientDashboard extends JPanel {

    private PatientController patientController;
    private ClinicianController clinicianController;
    private StaffController staffController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private Patient loggedInPatient;

    private JTable appointmentTable, prescriptionTable;
    private DefaultTableModel appointmentModel, prescriptionModel;

    public PatientDashboard(
            PatientController pc,
            ClinicianController cc,
            StaffController sc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc,
            Patient loggedInPatient
    ) {
        this.patientController = pc;
        this.clinicianController = cc;
        this.staffController = sc;
        this.appointmentController = ac;
        this.prescriptionController = prc;
        this.referralController = rc;
        this.loggedInPatient = loggedInPatient;

        setLayout(new BorderLayout(10, 10));
        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);

        loadAppointments();
        loadPrescriptions();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcome = new JLabel(
                "Welcome, " + loggedInPatient.getFirstName() + " " + loggedInPatient.getLastName()
        );
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> logout());

        header.add(welcome, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        return header;
    }

    private JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Appointments", createAppointmentPanel());
        tabs.add("Prescriptions", createPrescriptionPanel());
        return tabs;
    }

    private JPanel createAppointmentPanel() {
        appointmentModel = new DefaultTableModel(
                new String[]{"ID", "ClinicianId", "FacilityId"}, 0
        );
        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);

        JButton addBtn = new JButton("Book");
        JButton editBtn = new JButton("Reschedule");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> addAppointment());
        editBtn.addActionListener(e -> updateAppointment());
        deleteBtn.addActionListener(e -> deleteAppointment());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JScrollPane createPrescriptionPanel() {
        prescriptionModel = new DefaultTableModel(
                new String[]{"ID", "Medication", "Dosage", "Clinician"}, 0
        );
        prescriptionTable = new JTable(prescriptionModel);
        styleTable(prescriptionTable);
        return new JScrollPane(prescriptionTable);
    }

    private void loadAppointments() {
        appointmentModel.setRowCount(0);
        List<Appointment> list =
                appointmentController.getAppointmentsByPatientId(
                        loggedInPatient.getPatientId()
                );

        for (Appointment a : list) {
            appointmentModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getDate(),
                    a.getClinicianId()
            });
        }
    }

    private void loadPrescriptions() {
        prescriptionModel.setRowCount(0);
        for (Prescription p : prescriptionController.getAllPrescriptions()) {
            if (p.getPatientId().equals(loggedInPatient.getPatientId())) {
                prescriptionModel.addRow(new Object[]{
                        p.getPrescriptionId(),
                        p.getMedication(),
                        p.getDosage(),
                        p.getClinicianId()
                });
            }
        }
    }

    private void addAppointment() {
        String date = JOptionPane.showInputDialog(this, "Enter appointment date:");
        String clinicianId = JOptionPane.showInputDialog(this, "Enter clinician ID:");

        if (date == null || clinicianId == null) return;

        Appointment a = new Appointment(
                UUID.randomUUID().toString(),
                loggedInPatient.getPatientId(),
                clinicianId,
                date
        );

        appointmentController.addAppointment(a);
        loadAppointments();
    }

    private void updateAppointment() {
        int row = appointmentTable.getSelectedRow();
        if (row == -1) return;

        String id = appointmentModel.getValueAt(row, 0).toString();
        String newDate = JOptionPane.showInputDialog(this, "New date:");

        if (newDate == null) return;

        for (Appointment a : appointmentController.getAllAppointments()) {
            if (a.getAppointmentId().equals(id)) {
                a.setDate(newDate);
                appointmentController.updateAppointment(a);
                break;
            }
        }
        loadAppointments();
    }

    private void deleteAppointment() {
        int row = appointmentTable.getSelectedRow();
        if (row == -1) return;

        String id = appointmentModel.getValueAt(row, 0).toString();

        for (Appointment a : appointmentController.getAllAppointments()) {
            if (a.getAppointmentId().equals(id)) {
                appointmentController.deleteAppointment(a);
                break;
            }
        }
        loadAppointments();
    }

    private void styleTable(JTable table) {
        table.setRowHeight(26);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();

        new LoginView(
                patientController,
                clinicianController,
                staffController,
                appointmentController,
                prescriptionController,
                referralController
        );
    }
}
