import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;






public class AppointmentSystem {

    private static final String CUSTOMER_FILE = "customers.txt"; // Customer storage file
    private static final String APPOINTMENT_FILE = "appointments.txt"; // Appointment storage file
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static final Service[] SERVICES = {
        new Service("Manicure", 20.00, "60 minutes", "Standard manicure with polish"),
        new Service("Pedicure", 25.00, "70 minutes", "Standard pedicure with polish"),
        new Service("Waxing", 30.00, "30 minutes", "Leg waxing with wax"),
        new Service("Massage", 50.00, "90 minutes", "Relaxing body massage"),
        new Service("Facial Treatment", 40.00, "60 minutes", "Face cleansing and hydration"),
        new Service("Anti-Aging Treatment", 60.00, "75 minutes", "Anti-aging facial treatment"),
        new Service("Hair Coloring", 45.00, "90 minutes", "Professional hair coloring"),
        new Service("Natural Waxing", 35.00, "45 minutes", "Waxing with natural products")
    };

    public static void loadCustomers() {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    customers.add(new Customer(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers.");
        }
    }

    public static void saveCustomers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer customer : customers) {
                bw.write(customer.getName() + "," + customer.getUsername() + "," + customer.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customers.");
        }
    }

    public static void loadAppointments() {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String serviceName = parts[0];
                    double cost = Double.parseDouble(parts[1]);
                    String duration = parts[2];
                    
                    // Διαβάζουμε μόνο την ώρα και την αναλύουμε ως LocalTime
                    LocalTime time = LocalTime.parse(parts[3], DateTimeFormatter.ofPattern("HH:mm"));
                    
                    // Δημιουργούμε το ραντεβού με την τρέχουσα ημερομηνία και την ώρα
                    Appointment appointment = new Appointment(serviceName, cost, duration, time);
                    customers.get(0).addAppointment(appointment); // Πρόσθεση του ραντεβού στον πελάτη
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments.");
        }
    }
    
    

    public static void saveAppointments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Customer customer : customers) {
                for (Appointment appointment : customer.getAppointments()) {
                    // Μορφοποιούμε μόνο την ώρα
                    String timeString = appointment.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                    bw.write(appointment.getServiceName() + "," + appointment.getCost() + "," + appointment.getDuration() + "," + timeString);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments.");
        }
    }
    
    

    public static void customerLogin(Scanner scanner) {
        System.out.print("\nEnter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String encryptedPassword = encryptPassword(password);

        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(encryptedPassword)) {
                System.out.println("Login successful! Welcome, " + customer.getName() + "!");
                Menu.showCustomerMenu(customer, scanner);
                return;
            }
        }
        System.out.println("Incorrect username or password. Please try again.");
    }

    public static void registerCustomer(Scanner scanner) {
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();

        String username;
        boolean usernameTaken;

        // Loop until a unique username is found
        do {
            System.out.print("Enter a username: ");
            username = scanner.nextLine();

            usernameTaken = false;
            for (Customer customer : customers) {
                if (customer.getUsername().equals(username)) {
                    usernameTaken = true;
                    System.out.println("This username already exists. Please try again.");
                    break;
                }
            }
            if (usernameTaken) {
                System.out.println("Suggested usernames: ");
                System.out.println(username + "123");
                System.out.println(username + "_01");
                System.out.println("user" + System.currentTimeMillis());
            }
        } while (usernameTaken);

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        String encryptedPassword = encryptPassword(password);

        Customer newCustomer = new Customer(name, username, encryptedPassword);
        customers.add(newCustomer);
        System.out.println("Registration was successful!");
        Menu.showCustomerMenu(newCustomer, scanner);
            

    }

    public static void adminLogin(Scanner scanner) {
        String username;
        String password;
    
        while (true) {
            System.out.print("\nEnter admin username: ");
            username = scanner.nextLine();
            System.out.print("Enter admin password: ");
            password = scanner.nextLine();
    
            if (username.equals("admin") && password.equals("admin123")) {
                System.out.println("Admin login successful.");
                Menu.showAdminMenu(scanner);
                return; // Είσοδος επιτυχής, έξοδος από την μέθοδο
            } else {
                System.out.println("Invalid admin credentials. Please try again.");
            }
        }
    }
    
    

   

    public static void addAppointment(Customer customer, Scanner scanner) {
        int serviceChoice = -1;
        
        // Επαναλαμβάνουμε την επιλογή υπηρεσίας μέχρι να δώσει σωστή τιμή
        while (serviceChoice < 1 || serviceChoice > SERVICES.length) {
            System.out.println("\nSelect a service:");
            for (int i = 0; i < SERVICES.length; i++) {
                System.out.println((i + 1) + ". " + SERVICES[i].getName() + " - " + SERVICES[i].getDuration() + " - " + SERVICES[i].getCost() + "€");
            }
    
            System.out.print("Enter service number: ");
            // Ελέγχουμε αν ο χρήστης εισάγει έγκυρο αριθμό
            if (scanner.hasNextInt()) {
                serviceChoice = scanner.nextInt();
                scanner.nextLine(); // clear buffer
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // clear invalid input
            }
    
            if (serviceChoice < 1 || serviceChoice > SERVICES.length) {
                System.out.println("Invalid service choice. Please try again.");
            }
        }
    
        // Αν η τιμή είναι έγκυρη, συνεχίζουμε
        Service selectedService = SERVICES[serviceChoice - 1];
    
        LocalTime appointmentTime = null;
        // Επαναλαμβάνουμε την είσοδο της ώρας μέχρι να είναι έγκυρη
        while (appointmentTime == null) {
            System.out.print("Operating hours are from 08:00 to 22:00.\nEnter time (format: HH:mm): ");
            String timeStr = scanner.nextLine();
    
            try {
                // Αναλύουμε την ώρα
                appointmentTime = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
    
                // Έλεγχος αν η ώρα είναι εντός των ωρών λειτουργίας (08:00 - 22:00)
                if (appointmentTime.isBefore(LocalTime.of(8, 0)) || appointmentTime.isAfter(LocalTime.of(22, 0))) {
                    System.out.println("Invalid time. The time must be between 08:00 and 22:00. Please try again.");
                    appointmentTime = null; // Επαναλαμβάνουμε την εισαγωγή
                }
            } catch (Exception e) {
                System.out.println("Invalid time format. Please try again.");
            }
        }
    
        // Δημιουργούμε το ραντεβού με την ώρα που εισήγαγε ο χρήστης
        Appointment newAppointment = new Appointment(selectedService.getName(), selectedService.getCost(), selectedService.getDuration(), appointmentTime);
        customer.addAppointment(newAppointment);
    
        System.out.println("Appointment successfully booked!");
    }
      

    public static void viewAppointments(Customer customer) {
        if (customer.getAppointments().isEmpty()) {
            System.out.println("You have no appointments.");
        } else {
            System.out.println("\nYour appointments:");
            for (Appointment appointment : customer.getAppointments()) {
                System.out.println(appointment);
            }
        }
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
}
