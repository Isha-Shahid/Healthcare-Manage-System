package controller;

import model.DataStore;
import model.Staff;

import java.util.List;

public class StaffController {
    private List<Staff> staffList;

    public StaffController() {
        staffList = DataStore.loadStaff("data/staff.csv");
    }

    public List<Staff> getAllStaff() {
        return staffList;
    }

    public void addStaff(Staff s) {
        staffList.add(s);
        DataStore.saveStaff(staffList, "data/staff.csv");
    }

    public void deleteStaff(Staff s) {
        staffList.remove(s);
        DataStore.saveStaff(staffList, "data/staff.csv");
    }
}
