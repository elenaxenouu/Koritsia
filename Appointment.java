import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String serviceName;
    private double cost;
    private String duration;
    private LocalTime startTime;
    private LocalTime endTime;

    // Ανανεωμένος constructor για να δεχτεί δύο ώρες (startTime και endTime)
    public Appointment(String serviceName, double cost, String duration, LocalTime startTime, LocalTime endTime) {
        this.serviceName = serviceName;
        this.cost = cost;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        // Εμφανίζουμε την αρχική και τελική ώρα μαζί με την υπόλοιπη πληροφορία
        return serviceName + " - " + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " to " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + duration + " - " + cost + "€";
    }
}
