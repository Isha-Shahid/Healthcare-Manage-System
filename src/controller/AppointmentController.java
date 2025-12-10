package controller;

import model.Appointment;
import model.AppointmentManager;
import model.DataStore;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {

    public void scheduleAppointment(Appointment a) {
        AppointmentManager.getInstance().addAppointment(a);
        DataStore.saveAppointments(AppointmentManager.getInstance().getAppointments(), "appointments.csv");
    }

    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return AppointmentManager.getInstance().getAppointments()
                .stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }
}
