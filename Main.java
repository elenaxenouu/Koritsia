import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Φόρτωση δεδομένων συστήματος
        AppointmentSystem.loadCustomers();
        AppointmentSystem.loadAppointments();

        // Εκκίνηση του κύριου μενού
        Menu.showMainMenu(scanner);

        // Αποθήκευση δεδομένων πριν την έξοδο
        AppointmentSystem.saveCustomers();
        AppointmentSystem.saveAppointments();

        System.out.println("Exiting the system. Thank you!");
    }
}
