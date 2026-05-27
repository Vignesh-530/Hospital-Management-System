//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String URL =
            "jdbc:mysql://localhost:3307/hospital";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "Vigneshpatel45";
    private static Connection con = null;
    private static final Scanner scanner;

    public static void main(String[] args) {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );
                System.out.println("Connected to DB successfully.\n");
                mainMenu();
            } catch (Exception e) {
                System.out.println("Failed to connect to DB: " + e.getMessage());
                e.printStackTrace();
            }

        } finally {
            ;
        }
    }

    private static void mainMenu() {
        while(true) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
            System.out.println("Login as:");
            System.out.println("1. Admin");
            System.out.println("2. Doctor");
            System.out.println("3. Patient");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = readInt();
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    doctorLoginFlow();
                    break;
                case 3:
                    patientLoginFlow();
                    break;
                case 4:
                    closeEverything();
                    System.out.println("Thank you visit again ");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void adminLogin() {
        System.out.print("Enter admin username: ");
        String user = scanner.next();
        System.out.print("Enter admin password: ");
        String pass = scanner.next();
        if ("admin".equals(user) && "admin123".equals(pass)) {
            System.out.println("Admin login successful.");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }

    }

    private static void adminMenu() {
        while(true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. View Doctors");
            System.out.println("4. Book Appointment");
            System.out.println("5. View Appointments (all)");
            System.out.println("6. View Appointments by Doctor");
            System.out.println("7. Exit to Main Menu");
            System.out.print("Choice: ");
            int c = readInt();
            switch (c) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    viewDoctors();
                    break;
                case 4:
                    bookAppointmentFlow();
                    break;
                case 5:
                    viewAllAppointments();
                    break;
                case 6:
                    viewAppointmentsByDoctorFlow();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void doctorLoginFlow() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        String sql = "SELECT doctor_id FROM doctor_login WHERE username = ? AND password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int doctorId = rs.getInt("doctor_id");
                    System.out.println("Doctor login successful. Welcome Doctor ID: " + doctorId);
                    doctorMenu(doctorId);
                } else {
                    System.out.println("Invalid doctor credentials.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during doctor login: " + e.getMessage());
        }

    }

    private static void doctorMenu(int doctorId) {
        while(true) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View My Appointments");
            System.out.println("2. View Patient Details (by patient id)");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            int c = readInt();
            switch (c) {
                case 1:
                    viewAppointmentsByDoctor(doctorId);
                    break;
                case 2:
                    viewPatientByIdFlow();
                    break;
                case 3:
                    System.out.println("Thank you ");
                   return ;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void patientLoginFlow() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        String sql = "SELECT patient_id FROM patient_login WHERE username = ? AND password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int patientId = rs.getInt("patient_id");
                    System.out.println("Patient login successful. Welcome Patient ID: " + patientId);
                    patientMenu(patientId);
                } else {
                    System.out.println("Invalid patient credentials.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during patient login: " + e.getMessage());
        }

    }

    private static void patientMenu(int patientId) {
        while(true) {
            System.out.println("\n--- Patient Menu ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. View My Appointments");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            int c = readInt();
            switch (c) {
                case 1:
                    bookAppointmentForPatient(patientId);
                    break;
                case 2:
                    viewAppointmentsByPatient(patientId);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void bookAppointmentFlow() {
        System.out.print("Enter Patient Id: ");
        int pid = readInt();
        System.out.print("Enter Doctor Id: ");
        int did = readInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String dateStr = scanner.next();
        bookAppointment(pid, did, dateStr);
    }

    private static void viewAppointmentsByDoctorFlow() {
        System.out.print("Enter Doctor ID: ");
        int doctorId = readInt();
        viewAppointmentsByDoctor(doctorId);
    }

    private static void viewPatientByIdFlow() {
        System.out.print("Enter Patient ID: ");
        int pid = readInt();
        viewPatientById(pid);
    }

    private static void addPatient() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.next();
            System.out.print("Enter age: ");
            int age = readInt();
            System.out.print("Enter gender (Male/Female/Other): ");
            String gender = scanner.next();
            System.out.print("Enter phone: ");
            String phone = scanner.next();
            scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter blood group: ");
            String bg = scanner.next();
            String sql = "INSERT INTO patients (name, age, gender, phone, address, blood_group) VALUES (?,?,?,?,?,?)";

            try (PreparedStatement ps = con.prepareStatement(sql, 1)) {
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setString(3, gender);
                ps.setString(4, phone);
                ps.setString(5, address);
                ps.setString(6, bg);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            int patientId = keys.getInt(1);
                            System.out.println("Patient added with ID: " + patientId);
                            System.out.print("Create login for patient? (y/n): ");
                            String ans = scanner.next();
                            if ("y".equalsIgnoreCase(ans)) {
                                createPatientLogin(patientId);
                            }
                        }
                    }
                } else {
                    System.out.println("Failed to add patient.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }

    }

    private static void createPatientLogin(int patientId) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();
            String sql = "INSERT INTO patient_login (patient_id, username, password) VALUES (?,?,?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, patientId);
                ps.setString(2, username);
                ps.setString(3, password);
                int r = ps.executeUpdate();
                if (r > 0) {
                    System.out.println("Patient login created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating patient login: " + e.getMessage());
        }

    }

    private static void viewPatients() {
        String sql = "SELECT id, name, age, gender, phone FROM patients";

        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            System.out.println("\nPatients:");

            while(rs.next()) {
                System.out.printf("ID: %d | Name: %s | Age: %s | Gender: %s | Phone: %s%n", rs.getInt("id"), rs.getString("name"), rs.getObject("age"), rs.getString("gender"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing patients: " + e.getMessage());
        }

    }

    private static void viewPatientById(int patientId) {
        String sql = "SELECT * FROM patients WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Patient details:");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + String.valueOf(rs.getObject("age")));
                    System.out.println("Gender: " + rs.getString("gender"));
                    System.out.println("Phone: " + rs.getString("phone"));
                    System.out.println("Address: " + rs.getString("address"));
                    System.out.println("Blood Group: " + rs.getString("blood_group"));
                    System.out.println("Medical History: " + rs.getString("medical_history"));
                } else {
                    System.out.println("Patient not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patient: " + e.getMessage());
        }

    }

    private static void viewDoctors() {
        String sql = "SELECT id, name, specialization FROM doctors";

        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            System.out.println("\nDoctors:");

            while(rs.next()) {
                System.out.printf("ID: %d | Name: %s | Specialization: %s%n", rs.getInt("id"), rs.getString("name"), rs.getString("specialization"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing doctors: " + e.getMessage());
        }

    }

    private static void bookAppointmentForPatient(int patientId) {
        System.out.print("Enter Doctor Id: ");
        int doctorId = readInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String dateStr = scanner.next();
        bookAppointment(patientId, doctorId, dateStr);
    }

    private static void bookAppointment(int patientId, int doctorId, String dateStr) {
        if (!existsInTable("patients", patientId)) {
            System.out.println("Patient does not exist.");
        } else if (!existsInTable("doctors", doctorId)) {
            System.out.println("Doctor does not exist.");
        } else {
            LocalDate date;
            try {
                date = LocalDate.parse(dateStr);
            } catch (Exception var12) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            String check = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

            try {
                label124: {
                    try (PreparedStatement ps = con.prepareStatement(check)) {
                        ps.setInt(1, doctorId);
                        ps.setDate(2, Date.valueOf(date));

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next() || rs.getInt(1) <= 0) {
                                break label124;
                            }

                            System.out.println("Doctor already has an appointment on that date.");
                        }
                    }

                    return;
                }
            } catch (SQLException e) {
                System.out.println("Error checking availability: " + e.getMessage());
                return;
            }

            String insert = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, patientId);
                ps.setInt(2, doctorId);
                ps.setDate(3, Date.valueOf(date));
                int r = ps.executeUpdate();
                if (r > 0) {
                    System.out.println("Appointment booked successfully.");
                } else {
                    System.out.println("Failed to book appointment.");
                }
            } catch (SQLException e) {
                System.out.println("Error booking appointment: " + e.getMessage());
            }

        }
    }

    private static boolean existsInTable(String table, int id) {
        String sql = String.format("SELECT 1 FROM %s WHERE id = ? LIMIT 1", table);

        try {
            boolean var5;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    var5 = rs.next();
                }
            }

            return var5;
        } catch (SQLException e) {
            System.out.println("Error checking " + table + ": " + e.getMessage());
            return false;
        }
    }

    private static void viewAllAppointments() {
        String sql = "SELECT a.appointment_id, a.appointment_date, p.name AS patient, d.name AS doctor FROM appointments a JOIN patients p ON a.patient_id = p.id JOIN doctors d ON a.doctor_id = d.id ORDER BY a.appointment_date";

        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            System.out.println("\nAll Appointments:");

            while(rs.next()) {
                System.out.printf("ID:%d | Date:%s | Patient:%s | Doctor:%s%n", rs.getInt("appointment_id"), rs.getDate("appointment_date"), rs.getString("patient"), rs.getString("doctor"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }

    }

    private static void viewAppointmentsByDoctor(int doctorId) {
        String sql = "SELECT a.appointment_date, p.name AS patient_name FROM appointments a JOIN patients p ON a.patient_id = p.id WHERE a.doctor_id = ? ORDER BY a.appointment_date";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                System.out.println("\nAppointments for Doctor ID: " + doctorId);

                while(rs.next()) {
                    found = true;
                    System.out.printf("Date: %s | Patient: %s%n", rs.getDate("appointment_date"), rs.getString("patient_name"));
                }

                if (!found) {
                    System.out.println("No appointments found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }

    }

    private static void viewAppointmentsByPatient(int patientId) {
        String sql = "SELECT a.appointment_date, d.name AS doctor_name FROM appointments a JOIN doctors d ON a.doctor_id = d.id WHERE a.patient_id = ? ORDER BY a.appointment_date";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                System.out.println("\nAppointments for Patient ID: " + patientId);

                while(rs.next()) {
                    found = true;
                    System.out.printf("Date: %s | Doctor: %s%n", rs.getDate("appointment_date"), rs.getString("doctor_name"));
                }

                if (!found) {
                    System.out.println("No appointments found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }

    }

    private static int readInt() {
        while(true) {
            try {
                return Integer.parseInt(scanner.next());
            } catch (Exception var1) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private static void closeEverything() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException var1) {
        }

    }

    static {
        scanner = new Scanner(System.in);
    }
}
