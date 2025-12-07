package controller;

import model.Appointment;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    private List<Appointment> appointments = new ArrayList<>();

    public void scheduleAppointment(Appointment a) { appointments.add(a); }
    public List<Appointment> getAppointmentsForPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments)
            if (a.getPatientId().equals(patientId)) result.add(a);
        return result;
    }
}
