import java.util.Scanner;

public class Menu {

    public static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome to the Appointment Management System.");
            System.out.println("1. Login as Customer");
            System.out.println("2. New Customer");
            System.out.println("3. Login as Administrator");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    AppointmentSystem.customerLogin(scanner);
                    break;
                case "2":
                    AppointmentSystem.registerCustomer(scanner);
                    
                    break;
                case "3":
                    AppointmentSystem.adminLogin(scanner);
                    break;
                case "4":
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
   
    public static void showCustomerMenu(Customer customer, Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome, " + customer.getName());
            System.out.println("1. Book an Appointment");
            System.out.println("2. View Your Appointments");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    AppointmentSystem.addAppointment(customer, scanner);
                    break;
                case "2":
                    AppointmentSystem.viewAppointments(customer);
                    break;
                case "3":
                    System.out.println("Logging out of your account.");
                    System.exit(0); // Διακόπτει την εκτέλεση του προγράμματος
                break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome, Administrator.");
            System.out.println("1. View Optimized Appointment Schedule");
            System.out.println("2. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Optimized appointment schedule: (under construction).");
                    break;
                case "2":
                    System.out.println("Logging out of the administrator account.");
                    System.exit(0); // Διακόπτει την εκτέλεση του προγράμματος
                break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
