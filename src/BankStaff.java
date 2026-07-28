public class BankStaff {

    private final String staffID;
    private final String name;
    private final String role;

    public BankStaff(String staffID, String name, String role) {
        this.staffID = staffID;
        this.name = name;
        this.role = role;
    }

    public void updateCustomerRecord(Customer c, String newEmail) {
        c.setEmail(newEmail);
    }

    public Report generateReport(String reportType, String data) {
        Report r = new Report("R" + System.currentTimeMillis(), reportType, data);
        return r;
    }

    public String getStaffDetails() {
        return "Staff ID : " + staffID + "\nName     : " + name + "\nRole     : " + role;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
