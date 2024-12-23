import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String serviceName;
    private double cost;
    private String duration;
    private LocalTime time;  // Χρησιμοποιούμε LocalTime και όχι LocalDateTime

    public Appointment(String serviceName, double cost, String duration, LocalTime time) {
        this.serviceName = serviceName;
        this.cost = cost;
        this.duration = duration;
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public String getDuration() {
        return duration;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        // Εμφανίζουμε μόνο την ώρα χωρίς ημερομηνία
        return serviceName + " - " + time.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + duration + " - " + cost + "€";
    }
}
