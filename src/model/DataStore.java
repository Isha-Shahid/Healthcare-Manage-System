package model;

import java.io.*;
import java.util.*;

public class DataStore {

    // -------------------- Patients --------------------
    public static List<Patient> loadPatients(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // include empty strings
                if (data.length < 14) continue;
                Patient p = new Patient(data[0].trim(), data[13].trim());
                p.setFirstName(data[1].trim());
                p.setLastName(data[2].trim());
                p.setDateOfBirth(data[3].trim());
                p.setNhsNumber(data[4].trim());
                p.setGender(data[5].trim());
                p.setPhoneNumber(data[6].trim());
                p.setEmail(data[7].trim());
                p.setAddress(data[8].trim());
                p.setPostcode(data[9].trim());
                p.setEmergencyContactName(data[10].trim());
                p.setEmergencyContactPhone(data[11].trim());
                p.setRegistrationDate(data[12].trim());
                patients.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static void savePatients(List<Patient> patients, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("PatientID,FirstName,LastName,DOB,NHSNumber,Gender,Phone,Email,Address,Postcode,EmergencyName,EmergencyPhone,RegistrationDate,GPID\n");
            for (Patient p : patients) {
                writer.write(String.join(",",
                        safe(p.getPatientId()),
                        safe(p.getFirstName()),
                        safe(p.getLastName()),
                        safe(p.getDateOfBirth()),
                        safe(p.getNhsNumber()),
                        safe(p.getGender()),
                        safe(p.getPhoneNumber()),
                        safe(p.getEmail()),
                        safe(p.getAddress()),
                        safe(p.getPostcode()),
                        safe(p.getEmergencyContactName()),
                        safe(p.getEmergencyContactPhone()),
                        safe(p.getRegistrationDate()),
                        safe(p.getGpSurgeryId())
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Prescriptions --------------------
    public static List<Prescription> loadPrescriptions(String filePath) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 13) continue;
                Prescription p = new Prescription(
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(),
                        data[4].trim(), data[5].trim(), data[6].trim(),
                        parseIntSafe(data[7]), data[8].trim(), data[9].trim(), data[10].trim(),
                        data[11].trim(), data[12].trim()
                );
                prescriptions.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public static void savePrescriptions(List<Prescription> prescriptions, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("PrescriptionID,PatientID,ClinicianID,AppointmentID,Medication,Dosage,Frequency,Duration,Quantity,Instructions,Pharmacy,Status,IssueDate\n");
            for (Prescription p : prescriptions) {
                writer.write(String.join(",",
                        safe(p.getPrescriptionId()),
                        safe(p.getPatientId()),
                        safe(p.getClinicianId()),
                        safe(p.getAppointmentId()),
                        safe(p.getMedication()),
                        safe(p.getDosage()),
                        safe(p.getFrequency()),
                        String.valueOf(p.getDuration()),
                        safe(p.getQuantity()),
                        safe(p.getInstructions()),
                        safe(p.getPharmacy()),
                        safe(p.getStatus()),
                        safe(p.getIssueDate())
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Clinicians --------------------
    public static List<Clinician> loadClinicians(String filePath) {
        List<Clinician> clinicians = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 4) continue;
                Clinician c = new Clinician(
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim()
                );
                clinicians.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clinicians;
    }

    public static void saveClinicians(List<Clinician> clinicians, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ClinicianID,FirstName,LastName,Speciality\n");
            for (Clinician c : clinicians) {
                writer.write(String.join(",",
                        safe(c.getClinicianId()),
                        safe(c.getFirstName()),
                        safe(c.getLastName()),
                        safe(c.getSpeciality())
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Appointments --------------------
    public static List<Appointment> loadAppointments(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 4) continue;
                Appointment a = new Appointment(
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim()
                );
                appointments.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static void saveAppointments(List<Appointment> appointments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("AppointmentID,PatientID,ClinicianID,Date\n");
            for (Appointment a : appointments) {
                writer.write(String.join(",",
                        safe(a.getAppointmentId()),
                        safe(a.getPatientId()),
                        safe(a.getClinicianId()),
                        safe(a.getDate())
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Referrals --------------------
    public static List<Referral> loadReferrals(String filePath) {
        List<Referral> referrals = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 4) continue;
                Referral r = new Referral(
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim()
                );
                referrals.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return referrals;
    }

    public static void saveReferrals(List<Referral> referrals, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ReferralID,PatientID,ClinicianID,Priority\n");
            for (Referral r : referrals) {
                writer.write(String.join(",",
                        safe(r.getReferralId()),
                        safe(r.getPatientId()),
                        safe(r.getClinicianId()),
                        safe(r.getPriority())
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------- Utility Methods --------------------
    private static String safe(String s) {
        return s == null ? "" : s.replace(",", ""); // remove commas in data to avoid CSV issues
    }

    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
