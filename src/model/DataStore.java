package model;

import java.io.*;
import java.util.*;

public class DataStore {

    // ----------------- PATIENTS -----------------
    public static List<Patient> loadPatients(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
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
                        p.getPatientId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(),
                        p.getNhsNumber(), p.getGender(), p.getPhoneNumber(), p.getEmail(),
                        p.getAddress(), p.getPostcode(), p.getEmergencyContactName(),
                        p.getEmergencyContactPhone(), p.getRegistrationDate(), p.getGpSurgeryId()
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------- CLINICIANS -----------------
    public static List<Clinician> loadClinicians(String filePath) {
        List<Clinician> clinicians = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 12) continue;
                Clinician c = new Clinician(
                        data[0], data[1], data[2], data[4]
                );
                c.setTitle(data[3]);
                c.setGmcNumber(data[5]);
                c.setPhoneNumber(data[6]);
                c.setEmail(data[7]);
                c.setWorkplaceId(data[8]);
                c.setWorkplaceType(data[9]);
                c.setEmploymentStatus(data[10]);
                c.setStartDate(data[11]);
                clinicians.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clinicians;
    }

    public static void saveClinicians(List<Clinician> clinicians, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ClinicianID,FirstName,LastName,Title,Speciality,GMCNumber,Phone,Email,WorkplaceID,WorkplaceType,EmploymentStatus,StartDate\n");
            for (Clinician c : clinicians) {
                writer.write(String.join(",",
                        c.getClinicianId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                        c.getSpeciality(), c.getGmcNumber(), c.getPhoneNumber(), c.getEmail(),
                        c.getWorkplaceId(), c.getWorkplaceType(), c.getEmploymentStatus(), c.getStartDate()
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------- PRESCRIPTIONS -----------------
    public static List<Prescription> loadPrescriptions(String filePath) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 13) continue;
                Prescription p = new Prescription(
                        data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6], Integer.parseInt(data[7]),
                        data[8], data[9], data[10], data[11], data[12]
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
                        p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(), p.getAppointmentId(),
                        p.getMedication(), p.getDosage(), p.getFrequency(), String.valueOf(p.getDuration()),
                        p.getQuantity(), p.getInstructions(), p.getPharmacy(), p.getStatus(), p.getIssueDate()
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------- APPOINTMENTS -----------------
    public static List<Appointment> loadAppointments(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4) continue;
                Appointment a = new Appointment(data[0], data[1], data[2], data[3]);
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
                writer.write(String.join(",", a.getAppointmentId(), a.getPatientId(), a.getClinicianId(), a.getDate()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------- REFERRALS -----------------
    public static List<Referral> loadReferrals(String filePath) {
        List<Referral> referrals = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 3) continue;
                Referral r = new Referral(data[0], data[1], data[2]);
                referrals.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return referrals;
    }

    public static void saveReferrals(List<Referral> referrals, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ReferralID,PatientID,ClinicianID\n");
            for (Referral r : referrals) {
                writer.write(String.join(",", r.getReferralId(), r.getPatientId(), r.getClinicianId()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
