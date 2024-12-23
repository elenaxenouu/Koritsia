import java.util.ArrayList;
import java.util.List;

public class OptimizationAlgorithm {

    public static List<Appointment> optimizeAppointments(List<Appointment> appointments) {
        // 1. Sort appointments by cost in descending order
        appointments.sort((a1, a2) -> Double.compare(a2.getCost(), a1.getCost()));

        List<Appointment> optimizedSchedule = new ArrayList<>();

        // 2. For each appointment in sorted order (most expensive first):
        for (Appointment current : appointments) {
            // 3. Check if it overlaps with any already-chosen appointments
            boolean overlaps = false;
            for (Appointment chosen : optimizedSchedule) {
                if (isOverlapping(current, chosen)) {
                    overlaps = true;
                    break;
                }
            }

            // If it doesn't overlap, add it to the optimized schedule
            if (!overlaps) {
                optimizedSchedule.add(current);
            }
        }

        // Return the list of non-overlapping, highest-cost appointments
        return optimizedSchedule;
    }

    // Helper method to check if two appointments overlap
    private static boolean isOverlapping(Appointment a1, Appointment a2) {
        // Two appointments overlap if one's start time is strictly before the other's end time
        // and its end time is strictly after the other's start time
        return a1.getStartTime().isBefore(a2.getEndTime()) &&
                a1.getEndTime().isAfter(a2.getStartTime());
    }
}
