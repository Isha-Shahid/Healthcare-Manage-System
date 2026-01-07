package view;

import controller.AppointmentController;
import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentView extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JButton addBtn, editBtn, deleteBtn;

    private AppointmentController appointmentController;

    public AppointmentView(AppointmentController appointmentController) {
        this.appointmentController = appointmentController;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));

        add(createHeader(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);

        loadData();
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Appointments");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 37, 41));

        header.add(title, BorderLayout.WEST);
        return header;
    }

    /* ================= CENTER TABLE ================= */

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        model = new DefaultTableModel(
                new String[]{"Appointment ID", "Patient ID", "Clinician ID", "Date"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(13, 110, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 250));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /* ================= ACTION BUTTONS ================= */

    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");

        styleButton(addBtn, new Color(25, 135, 84));   // green
        styleButton(editBtn, new Color(13, 110, 253)); // blue
        styleButton(deleteBtn, new Color(220, 53, 69)); // red

        addBtn.addActionListener(e -> addAppointment());
        editBtn.addActionListener(e -> editAppointment());
        deleteBtn.addActionListener(e -> deleteAppointment());

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);

        return panel;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(120, 36));
    }

    /* ================= DATA ================= */

    private void loadData() {
        showAppointments(appointmentController.getAllAppointments());
    }

    public void showAppointments(List<Appointment> appointments) {
        model.setRowCount(0);
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getDate()
            });
        }
    }

    /* ================= CRUD ================= */

    private void addAppointment() {
        String id = JOptionPane.showInputDialog(this, "Appointment ID:");
        String patientId = JOptionPane.showInputDialog(this, "Patient ID:");
        String clinicianId = JOptionPane.showInputDialog(this, "Clinician ID:");
        String date = JOptionPane.showInputDialog(this, "Date:");

        if (id == null || patientId == null || clinicianId == null || date == null) return;

        Appointment a = new Appointment(id, patientId, clinicianId, date);
        appointmentController.addAppointment(a);
        loadData();
    }

    private void editAppointment() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = (String) model.getValueAt(row, 0);

        for (Appointment a : appointmentController.getAllAppointments()) {
            if (a.getAppointmentId().equals(id)) {
                String patientId = JOptionPane.showInputDialog(this, "Patient ID:", a.getPatientId());
                String clinicianId = JOptionPane.showInputDialog(this, "Clinician ID:", a.getClinicianId());
                String date = JOptionPane.showInputDialog(this, "Date:", a.getDate());

                if (patientId != null) a.setPatientId(patientId);
                if (clinicianId != null) a.setClinicianId(clinicianId);
                if (date != null) a.setDate(date);
                break;
            }
        }
        loadData();
    }

    private void deleteAppointment() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = (String) model.getValueAt(row, 0);

        appointmentController.getAllAppointments()
                .removeIf(a -> a.getAppointmentId().equals(id));

        loadData();
    }
}
